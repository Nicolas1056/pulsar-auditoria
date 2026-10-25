package com.metrofico.pulsar.launcher.daemon;

import com.metrofico.pulsar.launcher.auth.AuthException;
import com.metrofico.pulsar.launcher.auth.AuthorizationRequest;
import com.metrofico.pulsar.launcher.auth.DeviceCode;
import com.metrofico.pulsar.launcher.auth.MicrosoftAuth;
import com.metrofico.pulsar.launcher.auth.MinecraftSession;
import com.metrofico.pulsar.launcher.auth.MsaToken;
import com.metrofico.pulsar.launcher.auth.SecretStore;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class AuthService {
   public static final String REFRESH_KEY = "msa-refresh-token";
   private final MicrosoftAuth auth;
   private final Function<String, MicrosoftAuth> issuers;
   private final SecretStore secrets;
   private final AccountStore accounts;
   private final Consumer<String> events;
   private volatile MinecraftSession session;
   private volatile Thread polling;
   private volatile AuthorizationRequest authorization;
   private volatile boolean restoring;

   static String refreshKey(String uuid) {
      return "msa-refresh-token:" + uuid;
   }

   public AuthService(MicrosoftAuth auth, Function<String, MicrosoftAuth> issuers, SecretStore secrets, AccountStore accounts, Consumer<String> events) {
      this.auth = auth;
      this.issuers = issuers;
      this.secrets = secrets;
      this.accounts = accounts;
      this.events = events;
   }

   public boolean restoring() {
      return this.restoring;
   }

   public Optional<Account> account() {
      return this.accounts.current();
   }

   public Map<String, Object> current() {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("account", this.accounts.current().orElse(null));
      m.put("accounts", this.accounts.all());
      m.put("signedIn", this.session != null && !this.session.expired(System.currentTimeMillis()));
      m.put("restoring", this.restoring);
      m.put("secretStore", this.secrets.describe());
      m.put("persistent", this.secrets.persistent());
      m.put("clientId", this.auth.endpoints().clientId());
      return m;
   }

   public synchronized Map<String, Object> begin() {
      this.cancel();

      DeviceCode code;
      try {
         code = this.auth.beginDeviceCode();
      } catch (AuthException var3) {
         throw new DaemonException(var3.code(), var3.title(), var3.getMessage(), null, var3);
      }

      Thread t = new Thread(() -> this.poll(code), "pulsar-auth-poll");
      t.setDaemon(true);
      this.polling = t;
      t.start();
      LauncherLog.info("auth begin");
      return Map.of(
         "userCode",
         code.userCode(),
         "verificationUri",
         code.verificationUri(),
         "expiresAtEpochMs",
         code.expiresAtEpochMs(),
         "intervalSeconds",
         code.intervalSeconds()
      );
   }

   public synchronized Map<String, Object> beginWindow() {
      this.cancel();
      AuthorizationRequest request = this.auth.beginAuthorization();
      this.authorization = request;
      LauncherLog.info("auth window begin");
      this.emit("pending", null, null);
      return Map.of("url", request.authorizeUrl(), "redirectPrefix", request.redirectUri());
   }

   public synchronized Map<String, Object> completeWindow(String redirectUrl) {
      AuthorizationRequest request = this.authorization;
      this.authorization = null;
      if (request == null) {
         throw new DaemonException("bad_request", "No hay un inicio de sesión en curso", "Vuelve a pulsar Iniciar sesión.");
      } else {
         Thread t = new Thread(() -> {
            try {
               this.signIn(this.auth.exchangeRedirect(request, redirectUrl));
            } catch (AuthException var4) {
               LauncherLog.warn("auth window failed · " + var4.code());
               this.emit("error", null, var4);
            }
         }, "pulsar-auth-window");
         t.setDaemon(true);
         this.polling = t;
         t.start();
         return Map.of("accepted", true);
      }
   }

   private void poll(DeviceCode code) {
      this.emit("pending", null, null);

      try {
         while (!Thread.currentThread().isInterrupted()) {
            if (System.currentTimeMillis() > code.expiresAtEpochMs()) {
               throw new AuthException("code_expired", "El código caducó", "Vuelve a pulsar Iniciar sesión para obtener uno nuevo.");
            }

            Optional<MsaToken> token = this.auth.pollDeviceCode(code);
            if (token.isPresent()) {
               this.signIn(token.get());
               return;
            }

            Thread.sleep(code.intervalSeconds() * 1000L);
         }
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         this.emit("cancelled", null, null);
      } catch (AuthException var4) {
         LauncherLog.warn("auth failed · " + var4.code());
         this.emit("error", null, var4);
      }
   }

   private void signIn(MsaToken msa) throws AuthException {
      MinecraftSession s = this.auth.complete(msa);
      this.session = s;
      Account account = new Account(s.name(), s.uuid().toString(), s.xuid(), s.skinUrl(), System.currentTimeMillis(), this.auth.endpoints().clientId());
      if (msa.refreshToken() != null) {
         this.secrets.put(refreshKey(account.uuid()), msa.refreshToken());
      }

      this.accounts.save(account);
      LauncherLog.info("auth ok · account uuid " + s.uuid());
      this.emit("ok", account, null);
   }

   public Optional<MinecraftSession> restore() {
      this.restoring = true;

      Optional var1;
      try {
         var1 = this.restoreNow();
      } finally {
         this.restoring = false;
      }

      return var1;
   }

   private synchronized Optional<MinecraftSession> restoreNow() {
      if ("1".equals(System.getenv("PULSAR_NO_AUTH"))) {
         return Optional.empty();
      } else if (this.session != null && !this.session.expired(System.currentTimeMillis())) {
         return Optional.of(this.session);
      } else {
         String uuid = this.accounts.current().map(Account::uuid).orElse(null);
         Optional<String> refresh = uuid == null ? Optional.empty() : this.secrets.get(refreshKey(uuid));
         if (refresh.isEmpty()) {
            refresh = this.secrets.get("msa-refresh-token");
         }

         if (refresh.isEmpty()) {
            if (this.accounts.current().isPresent()) {
               LauncherLog.warn("auth restore · no refresh token in " + this.secrets.describe());
            }

            return Optional.empty();
         } else {
            String recorded = this.accounts.current().map(Account::clientId).orElse(null);
            String first = recorded != null ? recorded : "00000000402b5328";
            String fallback = recorded == null && !first.equals(this.auth.endpoints().clientId()) ? this.auth.endpoints().clientId() : null;
            AuthException last = null;

            for (String clientId : fallback == null ? new String[]{first} : new String[]{first, fallback}) {
               try {
                  return Optional.of(this.restoreWith(clientId, refresh.get()));
               } catch (AuthException var12) {
                  last = var12;
                  if (!var12.retryable()) {
                     continue;
                  }
                  break;
               }
            }

            LauncherLog.warn(
               "auth restore failed · " + (last == null ? "?" : last.code()) + (last != null && last.retryable() ? " (transient, keeping account)" : "")
            );
            if (last != null && !last.retryable()) {
               this.secrets.delete("msa-refresh-token");
            }

            return Optional.empty();
         }
      }
   }

   private MinecraftSession restoreWith(String clientId, String refresh) throws AuthException {
      MicrosoftAuth issuer = clientId.equals(this.auth.endpoints().clientId()) ? this.auth : this.issuers.apply(clientId);
      AuthException last = null;

      for (int attempt = 1; attempt <= 2; attempt++) {
         try {
            MsaToken msa = issuer.refresh(refresh);
            this.session = issuer.complete(msa);
            if (msa.refreshToken() != null && !msa.refreshToken().isBlank()) {
               this.secrets.put(refreshKey(this.session.uuid().toString()), msa.refreshToken());
               this.secrets.delete("msa-refresh-token");
            }

            long since = this.accounts.current().map(Account::signedInAtEpochMs).orElse(System.currentTimeMillis());
            this.accounts.save(new Account(this.session.name(), this.session.uuid().toString(), this.session.xuid(), this.session.skinUrl(), since, clientId));
            LauncherLog.info(
               "auth restored" + (attempt > 1 ? " (attempt " + attempt + ")" : "") + ("00000000402b5328".equals(clientId) ? " · official launcher id" : "")
            );
            return this.session;
         } catch (AuthException var10) {
            last = var10;
            if (var10.retryable()) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var9) {
                  Thread.currentThread().interrupt();
                  break;
               }
               continue;
            }
            break;
         }
      }

      throw last;
   }

   public synchronized void cancel() {
      this.authorization = null;
      Thread t = this.polling;
      if (t != null && t.isAlive()) {
         t.interrupt();
      }

      this.polling = null;
   }

   public synchronized void logout() {
      this.accounts.current().map(Account::uuid).ifPresent(this::removeAccount);
   }

   public synchronized boolean selectAccount(String uuid) {
      this.cancel();
      if (!this.accounts.select(uuid)) {
         return false;
      } else {
         this.session = null;
         LauncherLog.info("auth select · " + uuid);
         return true;
      }
   }

   public synchronized void removeAccount(String uuid) {
      this.cancel();
      this.secrets.delete(refreshKey(uuid));
      this.secrets.delete("msa-refresh-token");
      boolean wasActive = this.accounts.current().map(a -> a.uuid().equals(uuid)).orElse(false);
      this.accounts.remove(uuid);
      if (wasActive) {
         this.session = null;
      }

      LauncherLog.info("auth remove · " + uuid);
   }

   public Identity identity(String offlineName) {
      return this.restore().map(AuthService::onlineIdentity).orElse(Identity.offline(offlineName));
   }

   public Identity playIdentity(String offlineName, boolean offlineAllowed) {
      Optional<MinecraftSession> s = this.restore();
      if (s.isPresent()) {
         return onlineIdentity(s.get());
      } else if (offlineAllowed) {
         return Identity.offline(offlineName);
      } else if (this.accounts.current().isPresent()) {
         throw new DaemonException(
            "account_unverified",
            "No se pudo verificar tu cuenta",
            "Pulsar necesita comprobar tu cuenta de Microsoft para jugar. Revisa tu conexión a internet y vuelve a intentarlo."
         );
      } else {
         throw new DaemonException("sign_in_required", "Inicia sesión para jugar", "Pulsar necesita tu cuenta de Microsoft con Minecraft: Java Edition.");
      }
   }

   private static Identity onlineIdentity(MinecraftSession m) {
      return new Identity(m.name(), m.uuid().toString(), m.accessToken(), m.xuid() == null ? "" : m.xuid(), true);
   }

   private void emit(String state, Account account, AuthException error) {
      Map<String, Object> payload = new LinkedHashMap<>();
      payload.put("state", state);
      if (account != null) {
         payload.put("account", account);
      }

      if (error != null) {
         payload.put("code", error.code());
         payload.put("title", error.title());
         payload.put("message", error.getMessage());
      }

      this.events.accept(Protocol.event("auth", payload));
   }
}

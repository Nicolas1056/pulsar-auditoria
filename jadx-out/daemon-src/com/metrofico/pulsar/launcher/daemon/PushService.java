package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metrofico.pulsar.launcher.auth.SecretStore;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class PushService {
   static final String TOKEN_KEY = "pulsar-launcher-token";
   private static final int RECENT = 20;
   private final String serviceUrl;
   private final SecretStore secrets;
   private final PreferencesStore preferences;
   private final PacksStore packs;
   private final AuthService auth;
   private final Consumer<String> events;
   private final Path stateFile;
   private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
   private final Deque<Map<String, Object>> recent = new ArrayDeque<>();
   private volatile String token;
   private volatile String since = "";
   private volatile boolean connected;
   private volatile String lastError;
   private volatile Runnable onSession = () -> {};
   private Thread thread;

   public PushService(
      String serviceUrl, SecretStore secrets, PreferencesStore preferences, PacksStore packs, AuthService auth, Consumer<String> events, Path dataDir
   ) {
      this.serviceUrl = serviceUrl.replaceAll("/+$", "");
      this.secrets = secrets;
      this.preferences = preferences;
      this.packs = packs;
      this.auth = auth;
      this.events = events;
      this.stateFile = dataDir.resolve("push.json");
      this.loadState();
   }

   public String token() {
      return this.token;
   }

   public void onSession(Runnable r) {
      this.onSession = r;
   }

   private void writeSessionFile() {
      try {
         Path f = this.stateFile.resolveSibling("emotes").resolve("session.json");
         Files.createDirectories(f.getParent());
         Files.writeString(f, "{\"token\":\"" + this.token + "\",\"serviceUrl\":\"" + this.serviceUrl + "\"}", StandardCharsets.UTF_8);

         try {
            Files.setPosixFilePermissions(f, PosixFilePermissions.fromString("rw-------"));
         } catch (UnsupportedOperationException var3) {
         }
      } catch (IOException var4) {
         LauncherLog.info("push · no se pudo guardar la sesión para el motor: " + var4.getMessage());
      }
   }

   private static boolean devMode() {
      return "off".equals(System.getenv("PULSAR_MOJANG_VERIFY"));
   }

   public synchronized void start() {
      if (this.thread == null || !this.thread.isAlive()) {
         if (!"1".equals(System.getenv("PULSAR_NO_AUTH")) || devMode()) {
            this.thread = new Thread(this::run, "pulsar-push");
            this.thread.setDaemon(true);
            this.thread.start();
         }
      }
   }

   private void run() {
      long backoff = 2000L;

      while (true) {
         while (true) {
            if (Thread.currentThread().isInterrupted()) {
               return;
            }

            try {
               if (!this.preferences.current().pushOn()) {
                  Thread.sleep(5000L);
               } else {
                  if (this.ensureSession()) {
                     this.writeSessionFile();
                     this.onSession.run();
                     this.syncSubscriptions();
                     this.stream();
                     backoff = 2000L;
                     break;
                  }

                  Thread.sleep(60000L);
               }
            } catch (InterruptedException var5) {
               return;
            } catch (Exception var6) {
               this.lastError = var6.getMessage();
               LauncherLog.info("push · " + var6.getMessage() + " · reintento en " + backoff / 1000L + " s");
               break;
            }
         }

         this.connected = false;

         try {
            Thread.sleep(backoff);
         } catch (InterruptedException var4) {
            return;
         }

         backoff = Math.min(backoff * 2L, 60000L);
      }
   }

   private boolean ensureSession() throws IOException, InterruptedException {
      if (this.token != null) {
         return true;
      } else {
         this.token = (String)this.secrets.get("pulsar-launcher-token").orElse(null);
         if (this.token != null) {
            return true;
         } else {
            Identity id = this.auth.identity(this.preferences.current().playerName());
            String uuid = id.uuid();
            if (!id.online()) {
               if (!devMode()) {
                  this.lastError = "sin cuenta Microsoft";
                  return false;
               }

               uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + id.name()).getBytes(StandardCharsets.UTF_8)).toString();
            }

            byte[] rnd = new byte[16];
            new SecureRandom().nextBytes(rnd);
            String challenge = HexFormat.of().formatHex(rnd);
            if (id.online()) {
               String join = "{\"accessToken\":\""
                  + id.accessToken()
                  + "\",\"selectedProfile\":\""
                  + uuid.replace("-", "")
                  + "\",\"serverId\":\""
                  + challenge
                  + "\"}";
               HttpResponse<String> r = this.http
                  .send(
                     HttpRequest.newBuilder(URI.create("https://sessionserver.mojang.com/session/minecraft/join"))
                        .header("Content-Type", "application/json")
                        .POST(BodyPublishers.ofString(join))
                        .build(),
                     BodyHandlers.ofString()
                  );
               if (r.statusCode() / 100 != 2) {
                  throw new IOException("Mojang join " + r.statusCode());
               }
            }

            String body = "{\"uuid\":\"" + uuid + "\",\"name\":\"" + id.name() + "\",\"challenge\":\"" + challenge + "\"}";
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/launcher/session"))
                     .header("Content-Type", "application/json")
                     .POST(BodyPublishers.ofString(body))
                     .build(),
                  BodyHandlers.ofString()
               );
            if (r.statusCode() != 200) {
               throw new IOException("sesión de launcher " + r.statusCode() + " " + r.body());
            } else {
               JsonObject j = JsonParser.parseString(r.body()).getAsJsonObject();
               this.token = j.get("token").getAsString();
               this.secrets.put("pulsar-launcher-token", this.token);
               LauncherLog.info("push · sesión de launcher creada (" + (id.online() ? "Mojang" : "dev") + ")");
               return true;
            }
         }
      }
   }

   private void forgetSession() {
      this.token = null;
      this.secrets.delete("pulsar-launcher-token");

      try {
         Files.deleteIfExists(this.stateFile.resolveSibling("emotes").resolve("session.json"));
      } catch (IOException var2) {
      }
   }

   List<String> subscribedServers() {
      Preferences p = this.preferences.current();
      List<String> out = new ArrayList<>();
      if (!p.pushOn()) {
         return out;
      } else {
         for (Object o : (List)this.packs.list().get("servers")) {
            Map<String, Object> m = (Map<String, Object>)o;
            String consent = String.valueOf(m.get("consent")).toUpperCase();
            String id = String.valueOf(m.get("serverId"));
            if ((consent.equals("FULL") || consent.equals("INFORMATIVE")) && !p.mutedServers().contains(id)) {
               out.add(id);
            }
         }

         return out;
      }
   }

   public void syncSubscriptions() {
      if (this.token != null) {
         try {
            List<String> servers = this.subscribedServers();
            StringBuilder sb = new StringBuilder("{\"servers\":[");

            for (int i = 0; i < servers.size(); i++) {
               sb.append(i > 0 ? "," : "").append('"').append(servers.get(i)).append('"');
            }

            sb.append("]}");
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/launcher/subscriptions"))
                     .header("Content-Type", "application/json")
                     .header("Authorization", "Bearer " + this.token)
                     .PUT(BodyPublishers.ofString(sb.toString()))
                     .build(),
                  BodyHandlers.ofString()
               );
            if (r.statusCode() == 401) {
               this.forgetSession();
            }
         } catch (InterruptedException | IOException var4) {
            this.lastError = var4.getMessage();
         }
      }
   }

   private void startResync() {
      Thread t = new Thread(() -> {
         while (this.connected) {
            try {
               Thread.sleep(120000L);
            } catch (InterruptedException var2) {
               return;
            }

            if (this.connected) {
               this.syncSubscriptions();
               this.onSession.run();
            }
         }
      }, "pulsar-push-resync");
      t.setDaemon(true);
      t.start();
   }

   private void stream() throws IOException, InterruptedException {
      HttpRequest req = HttpRequest.newBuilder(
            URI.create(
               this.serviceUrl + "/api/v1/launcher/stream" + (this.since.isEmpty() ? "" : "?since=" + URLEncoder.encode(this.since, StandardCharsets.UTF_8))
            )
         )
         .header("Accept", "text/event-stream")
         .header("Authorization", "Bearer " + this.token)
         .GET()
         .build();
      HttpResponse<Stream<String>> r = this.http.send(req, BodyHandlers.ofLines());
      if (r.statusCode() == 401) {
         this.forgetSession();
         throw new IOException("sesión caducada");
      } else if (r.statusCode() != 200) {
         throw new IOException("stream " + r.statusCode());
      } else {
         this.connected = true;
         this.lastError = null;
         this.startResync();
         String event = "";
         StringBuilder data = new StringBuilder();

         for (String line : r.body()::iterator) {
            if (line.startsWith("event:")) {
               event = line.substring(6).trim();
            } else if (line.startsWith("data:")) {
               data.append(line.substring(5).trim());
            } else if (line.isEmpty()) {
               if (event.equals("notification") && data.length() > 0) {
                  this.handle(data.toString());
               }

               event = "";
               data.setLength(0);
            }
         }

         this.connected = false;
      }
   }

   private void handle(String json) {
      JsonObject j = JsonParser.parseString(json).getAsJsonObject();
      Map<String, Object> n = new LinkedHashMap<>();

      for (String k : List.of("id", "server", "title", "body", "createdAt", "expiresAt")) {
         n.put(k, j.has(k) && !j.get(k).isJsonNull() ? j.get(k).getAsString() : null);
      }

      JsonElement action = j.get("action");
      if (action != null && action.isJsonObject()) {
         JsonObject a = action.getAsJsonObject();
         n.put("action", Map.of("type", a.get("type").getAsString(), "address", a.get("address").getAsString()));
      }

      n.put("receivedAt", System.currentTimeMillis());
      this.since = String.valueOf(n.get("createdAt"));
      synchronized (this.recent) {
         this.recent.addFirst(n);

         while (this.recent.size() > 20) {
            this.recent.removeLast();
         }
      }

      this.saveState();
      LauncherLog.info("push · " + n.get("server") + " · " + n.get("title"));
      this.events.accept(Protocol.event("notification", n));
   }

   public Map<String, Object> state() {
      Preferences p = this.preferences.current();
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("enabled", p.pushOn());
      m.put("muted", p.mutedServers());
      m.put("connected", this.connected);
      m.put("session", this.token != null);
      m.put("error", this.lastError);
      synchronized (this.recent) {
         m.put("recent", new ArrayList<>(this.recent));
         return m;
      }
   }

   public Map<String, Object> set(Boolean enabled, String server, Boolean muted) {
      this.preferences.update(p -> {
         List<String> m = new ArrayList<>(p.mutedServers());
         if (server != null && muted != null) {
            m.remove(server);
            if (muted) {
               m.add(server);
            }
         }

         return p.withPush(enabled != null ? enabled : p.pushOn(), m);
      });
      Thread t = new Thread(this::syncSubscriptions, "pulsar-push-sync");
      t.setDaemon(true);
      t.start();
      return this.state();
   }

   public void dismissAll() {
      synchronized (this.recent) {
         this.recent.clear();
      }

      this.saveState();
   }

   private void loadState() {
      try {
         if (Files.isRegularFile(this.stateFile)) {
            JsonObject j = JsonParser.parseString(Files.readString(this.stateFile)).getAsJsonObject();
            this.since = j.has("since") ? j.get("since").getAsString() : "";
         }
      } catch (Exception var2) {
         this.since = "";
      }
   }

   private void saveState() {
      try {
         Files.createDirectories(this.stateFile.getParent());
         Files.writeString(this.stateFile, "{\"since\":\"" + this.since + "\"}");
      } catch (IOException var2) {
         LauncherLog.info("push · no se pudo guardar el estado: " + var2.getMessage());
      }
   }
}

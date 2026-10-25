package com.metrofico.pulsar.api.legit;

import com.metrofico.pulsar.api.pack.Json;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class EntryPass {
   public static final String CLOUD_UNAVAILABLE = "cloud_unavailable";
   public static final String DECLINED_SENSORS = "declined_sensors";
   public static final String NOT_INTEGRAL = "not_integral";
   public static final String UNKNOWN_HOST = "unknown_host";
   public static final String NO_CHANNEL = "no_channel";
   private static volatile String activeHost;
   private final Supplier<LegitSession> session;
   private final SignalsChoice signals;
   private final Supplier<String> host;
   private final Supplier<String> keyHash;

   public EntryPass(Supplier<LegitSession> session, SignalsChoice signals) {
      this(session, signals, ConnectionIdentity::host, ConnectionIdentity::keyHash);
   }

   public EntryPass(Supplier<LegitSession> session, SignalsChoice signals, Supplier<String> host, Supplier<String> keyHash) {
      this.session = session;
      this.signals = signals;
      this.host = host;
      this.keyHash = keyHash;
   }

   public static SignalsChoice sharing(Predicate<String> sharesWith) {
      return (h, requested) -> CompletableFuture.completedFuture(sharesWith.test(h));
   }

   public static String activeHost() {
      return activeHost;
   }

   public static void left() {
      activeHost = null;
      ClickCap.clear();
   }

   public static boolean handles(String type) {
      return "legit.challenge".equals(type) || "legit.renew".equals(type);
   }

   public CompletableFuture<String> respond(String type, Map<String, Object> fields) {
      if (fields.get("nonce") instanceof String n && !n.isEmpty()) {
         ClickCap.apply(fields.get("cpsCap"), fields.get("cpsCapRight"));
         String h = this.host.get();
         if (h == null) {
            return CompletableFuture.completedFuture(error("unknown_host", n));
         } else {
            String k = this.keyHash.get();
            if (k == null) {
               return CompletableFuture.completedFuture(error("no_channel", n));
            } else if (!"legit.challenge".equals(type)) {
               return this.attest(h, k, n, h.equals(GameSignals.serverId()));
            } else {
               activeHost = h;
               boolean required = Boolean.TRUE.equals(fields.get("sensors"));
               return this.signals.shares(h, required).thenCompose(share -> {
                  if (required && !share) {
                     return CompletableFuture.completedFuture(error("declined_sensors", n));
                  } else {
                     if (share) {
                        GameSignals.start(h);
                     }

                     return this.attest(h, k, n, share);
                  }
               });
            }
         }
      } else {
         return CompletableFuture.completedFuture(error("not_integral", null));
      }
   }

   private CompletableFuture<String> attest(String h, String k, String n, boolean sensors) {
      LegitSession s = this.session.get();
      return s == null
         ? CompletableFuture.completedFuture(error("cloud_unavailable", n))
         : s.attest(h, k, n, sensors).thenApply(a -> a.token() != null ? reply("token", a.token(), n) : error(a.error(), n));
   }

   private static String error(String error, String nonce) {
      return reply("error", error, nonce);
   }

   private static String reply(String key, String value, String nonce) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("t", "legit.attestation");
      m.put(key, value);
      if (nonce != null) {
         m.put("nonce", nonce);
      }

      return Json.write(m);
   }
}

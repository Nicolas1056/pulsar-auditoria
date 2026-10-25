package com.metrofico.pulsar.api.staff;

import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.api.pack.PackVerifier;
import com.metrofico.pulsar.api.pack.ServerRecord;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class StaffXrayGate {
   static final long MAX_ORDER_S = 120L;
   static final long MAX_GRANT_S = 900L;
   static final long GRANT_SKEW_S = 300L;
   private static volatile long activeUntilMs;
   private static volatile String activeNetwork;
   private static volatile List<String> offered = List.of();
   private static final Set<String> SEEN = new HashSet<>();
   private static volatile int generation;

   private StaffXrayGate() {
   }

   public static boolean active(long nowMs) {
      return activeUntilMs > nowMs;
   }

   public static boolean active() {
      return active(System.currentTimeMillis());
   }

   public static List<String> offered() {
      return offered;
   }

   public static String network() {
      return activeNetwork;
   }

   public static int generation() {
      return generation;
   }

   public static synchronized boolean accept(StaffXrayGate.Order o, long arrivalMs, int forGeneration) {
      if (forGeneration != generation) {
         return false;
      } else {
         activeNetwork = o.network();
         offered = o.hide();
         activeUntilMs = arrivalMs + o.validMs();
         return true;
      }
   }

   public static synchronized void clear() {
      generation++;
      activeUntilMs = 0L;
      activeNetwork = null;
      offered = List.of();
      SEEN.clear();
   }

   static synchronized boolean fresh(String nonce) {
      return SEEN.add(nonce);
   }

   public static StaffXrayGate.Order verify(
      Map<String, Object> fields, StaffXrayGate.Records records, PublicKey pulsarKey, String account, String typedHost, String keyHash, long nowMs
   ) throws StaffXrayGate.Rejected {
      Map<String, Object> signedOrder = map(fields.get("order"), "order");
      Map<String, Object> order = map(signedOrder.get("order"), "order");
      String orderSig = string(signedOrder, "signature");
      if (Long.valueOf(1L).equals(order.get("v")) && "xray".equals(order.get("kind"))) {
         String network = string(order, "network");

         ServerRecord record;
         try {
            record = new PackVerifier(pulsarKey).verifyServer(records.registryJson(network));
         } catch (PackVerifier.RejectedException var35) {
            throw new StaffXrayGate.Rejected(var35.code(), var35.getMessage());
         } catch (Exception var36) {
            throw new StaffXrayGate.Rejected("registry_unavailable", "no se pudo leer el registro de la network: " + var36.getMessage());
         }

         if (!network.equals(record.id())) {
            throw new StaffXrayGate.Rejected("registry_other", "el registro es de otra network");
         } else {
            try {
               PackVerifier.verifyDestination(record, typedHost);
            } catch (PackVerifier.RejectedException var34) {
               throw new StaffXrayGate.Rejected("other_network", "el servidor al que se entró no es de esa network");
            }

            PublicKey packKey;
            try {
               packKey = PackVerifier.publicKeyFromBase64(record.publicKeyBase64());
            } catch (Exception var33) {
               throw new StaffXrayGate.Rejected("server_key", "clave de la network no válida");
            }

            if (!PackVerifier.verify(Json.canonical(order), orderSig, packKey)) {
               throw new StaffXrayGate.Rejected("order_unsigned", "la orden no está firmada por la network");
            } else {
               String uuid = uuid(string(order, "uuid"));
               if (account == null || !uuid.equals(uuid(account))) {
                  throw new StaffXrayGate.Rejected("order_other_player", "la orden es para otra cuenta");
               } else if (keyHash != null && keyHash.equalsIgnoreCase(string(order, "keyHash"))) {
                  long iat = number(order, "iat");
                  long exp = number(order, "exp");
                  if (exp > iat && exp - iat <= 120L) {
                     String nonce = string(order, "nonce");
                     Map<String, Object> signedGrant = map(fields.get("grant"), "grant");
                     Map<String, Object> grant = map(signedGrant.get("grant"), "grant");
                     if (!PackVerifier.verify(Json.canonical(grant), string(signedGrant, "signature"), pulsarKey)) {
                        throw new StaffXrayGate.Rejected("grant_unsigned", "el visto bueno no está firmado por Pulsar");
                     } else if (Long.valueOf(1L).equals(grant.get("v"))
                        && "staff".equals(grant.get("kind"))
                        && network.equals(grant.get("network"))
                        && uuid.equals(uuid(string(grant, "uuid")))) {
                        long gIat = number(grant, "iat");
                        long gExp = number(grant, "exp");
                        long nowS = nowMs / 1000L;
                        if (gExp <= gIat || gExp - gIat > 900L || gExp < nowS - 300L || gIat > nowS + 300L) {
                           throw new StaffXrayGate.Rejected("grant_expired", "el visto bueno caducó");
                        } else if (!fresh(nonce)) {
                           throw new StaffXrayGate.Rejected("order_replayed", "orden repetida");
                        } else {
                           List<String> hide = new ArrayList<>();
                           Object var30 = order.get("hide");
                           if (var30 instanceof List) {
                              for (Object o : (List)var30) {
                                 if (o instanceof String s && !s.isBlank() && hide.size() < 256) {
                                    hide.add(s.toLowerCase(Locale.ROOT));
                                 }
                              }
                           }

                           return new StaffXrayGate.Order(network, List.copyOf(hide), (exp - iat) * 1000L, nonce);
                        }
                     } else {
                        throw new StaffXrayGate.Rejected("grant_other", "el visto bueno es de otra cuenta o network");
                     }
                  } else {
                     throw new StaffXrayGate.Rejected("order_lifetime", "duración de la orden no válida");
                  }
               } else {
                  throw new StaffXrayGate.Rejected("order_other_connection", "la orden no es de esta conexión");
               }
            }
         }
      } else {
         throw new StaffXrayGate.Rejected("order_kind", "orden de otro tipo o versión");
      }
   }

   private static Map<String, Object> map(Object o, String what) throws StaffXrayGate.Rejected {
      try {
         return Json.object(o, what);
      } catch (RuntimeException var3) {
         throw new StaffXrayGate.Rejected("bad_message", "falta " + what);
      }
   }

   private static String string(Map<String, Object> o, String key) throws StaffXrayGate.Rejected {
      if (o.get(key) instanceof String s && !s.isEmpty()) {
         return s;
      } else {
         throw new StaffXrayGate.Rejected("bad_message", "falta " + key);
      }
   }

   private static long number(Map<String, Object> o, String key) throws StaffXrayGate.Rejected {
      if (o.get(key) instanceof Number n) {
         return n.longValue();
      } else {
         throw new StaffXrayGate.Rejected("bad_message", "falta " + key);
      }
   }

   private static String uuid(String raw) {
      return raw.replace("-", "").toLowerCase(Locale.ROOT);
   }

   public record Order(String network, List<String> hide, long validMs, String nonce) {
   }

   public interface Records {
      String registryJson(String var1) throws Exception;
   }

   public static final class Rejected extends Exception {
      private final String code;

      Rejected(String code, String message) {
         super(message);
         this.code = code;
      }

      public String code() {
         return this.code;
      }
   }
}

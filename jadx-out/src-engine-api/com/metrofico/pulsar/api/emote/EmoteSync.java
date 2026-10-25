package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Capability;
import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class EmoteSync {
   public static final int MAX_INLINE_BYTES = 20000;
   private static final long MAX_OBJECT_BYTES = 262144L;

   private EmoteSync() {
   }

   private static boolean granted() {
      PackClient rt = Emotes.pack();
      return rt.active() && rt.granted(Capability.XP_EMOTES);
   }

   public static void announcePlay(Emote emote, String sha256, boolean dev) {
      if (!granted()) {
         if (!dev) {
            EmoteRelay.get().play(emote, sha256);
         }
      } else {
         Map<String, Object> ev = new LinkedHashMap<>();
         ev.put("t", "event");
         ev.put("cap", Capability.XP_EMOTES.id());
         ev.put("action", "play");
         ev.put("id", emote.id());
         if (emote.loop()) {
            ev.put("loop", true);
         }

         if (dev) {
            String text = PemoteJson.write(emote);
            if (text.length() > 20000) {
               Emotes.log("emote: " + emote.id() + " (dev) es demasiado grande para retransmitirlo (" + text.length() + " bytes)");
               return;
            }

            ev.put("dev", true);
            ev.put("emote", Json.parse(text));
         } else {
            if (sha256 == null) {
               return;
            }

            ev.put("sha", sha256);
         }

         Emotes.pack().sendEvent(ev);
      }
   }

   public static void announceStop() {
      if (!granted()) {
         EmoteRelay.get().stop();
      } else {
         Map<String, Object> ev = new LinkedHashMap<>();
         ev.put("t", "event");
         ev.put("cap", Capability.XP_EMOTES.id());
         ev.put("action", "stop");
         Emotes.pack().sendEvent(ev);
      }
   }

   public static boolean apply(Map<String, Object> f) {
      String op = f.get("op") instanceof String s ? s : "";
      switch (op) {
         case "denied":
            Emotes.log("emote: el servidor denegó " + f.get("id") + " (" + f.get("reason") + ")");
            EmotePlayer.stopOwn();
            return true;
         case "stop":
            UUID player = uuid(f.get("player"));
            if (player == null) {
               return false;
            }

            EmotePlayer.stop(player);
            return true;
         case "play":
            UUID player = uuid(f.get("player"));
            String id = f.get("id") instanceof String sx ? sx : null;
            long elapsed;
            long var24 = elapsed = f.get("elapsedMs") instanceof Number n ? Math.max(0L, n.longValue()) : 0L;
            if (player != null && id != null) {
               if (Boolean.TRUE.equals(f.get("dev"))) {
                  if (f.get("emote") instanceof Map<?, ?> inline) {
                     try {
                        if (Json.write(inline).length() > 20000) {
                           return false;
                        }

                        Emote e = PemoteJson.from((Map<String, Object>)inline);
                        EmotePlayer.play(player, e, elapsed);
                        return true;
                     } catch (RuntimeException var12) {
                        Emotes.log("emote: dev inválido de " + player + ": " + var12.getMessage());
                        return false;
                     }
                  }

                  return false;
               }

               String sha = f.get("sha") instanceof String sxx && sxx.matches("[0-9a-f]{64}") ? sxx : null;
               if (sha == null) {
                  return false;
               }

               Emote cached = EmoteLibrary.bySha(sha);
               if (cached != null) {
                  prefetchSounds(cached);
                  EmotePlayer.play(player, cached, elapsed);
                  return true;
               }

               long requestedAt = System.currentTimeMillis();
               fetch(sha).thenAccept(e -> Emotes.host().runOnMain(() -> {
                  if (e != null) {
                     EmotePlayer.play(player, e, elapsed + (System.currentTimeMillis() - requestedAt));
                  }
               }));
               return true;
            }

            return false;
         default:
            return false;
      }
   }

   public static void prefetchSounds(Emote emote) {
      for (Emote.Sound s : emote.sounds()) {
         if (!EmoteSounds.available(s.sha256())) {
            String url = objectUrl(s.sha256());
            CompletableFuture.runAsync(() -> {
               try {
                  Emotes.pack().cache().fetchObject(s.sha256(), url, 260096L);
               } catch (Exception var3x) {
                  Emotes.log("emote: no se pudo bajar el sonido " + s.sha256().substring(0, 12) + ": " + var3x.getMessage());
               }
            });
         }
      }
   }

   public static String objectUrl(String sha256) {
      return Emotes.pack().serviceUrl() + "/downloads/objects/" + sha256.substring(0, 2) + "/" + sha256 + ".gz";
   }

   private static CompletableFuture<Emote> fetch(String sha256) {
      String url = objectUrl(sha256);
      if (url.startsWith("/")) {
         Emotes.log("emote: sin URL del servicio, no se puede descargar " + sha256.substring(0, 12));
         return CompletableFuture.completedFuture(null);
      } else {
         return CompletableFuture.supplyAsync(() -> {
            try {
               Emotes.pack().cache().fetchObject(sha256, url, 262144L);
               Emote fetched = EmoteLibrary.bySha(sha256);
               if (fetched != null) {
                  prefetchSounds(fetched);
               }

               return fetched;
            } catch (Exception var3) {
               Emotes.log("emote: no se pudo descargar " + sha256.substring(0, 12) + ": " + var3.getMessage());
               return null;
            }
         });
      }
   }

   private static UUID uuid(Object v) {
      if (v instanceof String s) {
         try {
            return UUID.fromString(s);
         } catch (IllegalArgumentException var3) {
            return null;
         }
      } else {
         return null;
      }
   }
}

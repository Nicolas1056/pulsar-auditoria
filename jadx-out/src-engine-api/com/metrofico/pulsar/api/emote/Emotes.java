package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.pack.PackClient;

public final class Emotes {
   private static volatile EmoteHost host;
   private static volatile PackClient pack;

   private Emotes() {
   }

   public static void install(EmoteHost h, PackClient p) {
      host = h;
      pack = p;
   }

   public static EmoteHost host() {
      EmoteHost h = host;
      if (h == null) {
         throw new IllegalStateException("emotes: host not installed");
      } else {
         return h;
      }
   }

   public static PackClient pack() {
      PackClient p = pack;
      if (p == null) {
         throw new IllegalStateException("emotes: pack client not installed");
      } else {
         return p;
      }
   }

   public static boolean installed() {
      return host != null && pack != null;
   }

   static void log(String message) {
      ClientOptions.LOG.accept(message);
   }
}

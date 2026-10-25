package com.metrofico.pulsar.api.legit;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GameSignals {
   static volatile boolean active;
   private static volatile String serverId;
   private static volatile boolean enabled = true;
   private static volatile long windowStart;

   private GameSignals() {
   }

   public static synchronized void start(String server) {
      ClickSensor.reset();
      AimSensor.reset();
      windowStart = System.nanoTime();
      serverId = server;
      active = enabled && server != null;
   }

   public static synchronized void stop() {
      active = false;
      serverId = null;
      ClickSensor.reset();
      AimSensor.reset();
   }

   public static synchronized void enabled(boolean on) {
      enabled = on;
      active = on && serverId != null;
   }

   public static boolean active() {
      return active;
   }

   static String serverId() {
      return serverId;
   }

   static synchronized Map<String, Object> drain() {
      String host = active ? serverId : LiveSignals.windowHost();
      Map<String, Object> live = LiveSignals.drainWindow();
      if (!active && live == null) {
         return null;
      } else {
         long now = System.nanoTime();
         Map<String, Object> d = new LinkedHashMap<>();
         d.put("host", host);
         d.put("windowMs", windowStart == 0L ? 10000L : (now - windowStart) / 1000000L);
         windowStart = now;
         if (active) {
            ClickSensor.drainInto(d);
            d.put("aim", AimSensor.drain());
         }

         if (live != null) {
            d.put("live", live);
         }

         return host == null ? null : d;
      }
   }
}

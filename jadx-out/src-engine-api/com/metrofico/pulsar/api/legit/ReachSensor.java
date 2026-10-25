package com.metrofico.pulsar.api.legit;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ReachSensor {
   static final double BUCKET = 0.05;
   static final int BUCKETS = 160;
   private static final Object LOCK = new Object();
   private static final int[] WINDOW = new int[160];
   private static int windowHits;
   private static double windowMax;
   private static int liveHits;
   private static double liveMax;
   private static double liveLast;

   private ReachSensor() {
   }

   public static void hit(double eyeX, double eyeY, double eyeZ, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      if (LegitWatch.host() != null) {
         record(distance(eyeX, eyeY, eyeZ, minX, minY, minZ, maxX, maxY, maxZ));
      }
   }

   static double distance(double x, double y, double z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      double dx = Math.max(0.0, Math.max(minX - x, x - maxX));
      double dy = Math.max(0.0, Math.max(minY - y, y - maxY));
      double dz = Math.max(0.0, Math.max(minZ - z, z - maxZ));
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
   }

   static void record(double d) {
      synchronized (LOCK) {
         WINDOW[Math.min(159, (int)(d / 0.05))]++;
         windowHits++;
         windowMax = Math.max(windowMax, d);
         liveHits++;
         liveMax = Math.max(liveMax, d);
         liveLast = d;
      }
   }

   static Map<String, Object> drainLive() {
      synchronized (LOCK) {
         if (liveHits == 0) {
            return null;
         } else {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("hits", liveHits);
            m.put("max", round(liveMax));
            m.put("last", round(liveLast));
            liveHits = 0;
            liveMax = 0.0;
            return m;
         }
      }
   }

   static Map<String, Object> drainWindow() {
      synchronized (LOCK) {
         if (windowHits == 0) {
            return null;
         } else {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("hits", windowHits);
            m.put("max", round(windowMax));
            m.put("p50", percentile(0.5));
            m.put("p95", percentile(0.95));
            Arrays.fill(WINDOW, 0);
            windowHits = 0;
            windowMax = 0.0;
            return m;
         }
      }
   }

   static void reset() {
      synchronized (LOCK) {
         Arrays.fill(WINDOW, 0);
         windowHits = 0;
         windowMax = 0.0;
         liveHits = 0;
         liveMax = 0.0;
         liveLast = 0.0;
      }
   }

   private static double percentile(double p) {
      int target = (int)Math.ceil(p * windowHits);
      int seen = 0;

      for (int i = 0; i < 160; i++) {
         seen += WINDOW[i];
         if (seen >= target) {
            return round(Math.min(windowMax, (i + 0.5) * 0.05));
         }
      }

      return round(windowMax);
   }

   private static double round(double v) {
      return Math.round(v * 100.0) / 100.0;
   }
}

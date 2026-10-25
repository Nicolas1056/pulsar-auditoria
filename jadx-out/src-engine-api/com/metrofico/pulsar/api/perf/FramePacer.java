package com.metrofico.pulsar.api.perf;

import java.util.Arrays;

public final class FramePacer {
   private static final double MARGIN = 1.35;
   private static final double FLOOR_MS = 4.0;
   private static final int RELAX_FRAMES = 20;
   private static final float[] RING = new float[256];
   private static final float[] SORTED = new float[RING.length];
   private static int ringCount;
   private static int ringPos;
   private static double medianMs = 8.0;
   private static double budgetMs = Math.max(4.0, medianMs * 1.35);
   private static int level;
   private static int overruns;
   private static int calm;
   private static int actionsThisSecond;
   private static int actionsPerSecond;
   private static long lastSecond = System.nanoTime();
   private static volatile boolean enabled = true;
   private static int actedMask;

   private FramePacer() {
   }

   public static void enabled(boolean on) {
      enabled = on;
      if (!on) {
         level = 0;
      }
   }

   public static void frame(double frameMs, double waitMs) {
      frame(Math.max(0.01, frameMs - Math.max(0.0, waitMs)));
   }

   public static void frame(double frameMs) {
      if (!(frameMs <= 0.0)) {
         actedMask = 0;
         RING[ringPos] = (float)frameMs;
         ringPos = (ringPos + 1) % RING.length;
         if (ringCount < RING.length) {
            ringCount++;
         }

         long now = System.nanoTime();
         if (now - lastSecond >= 1000000000L) {
            lastSecond = now;
            System.arraycopy(RING, 0, SORTED, 0, ringCount);
            Arrays.sort(SORTED, 0, ringCount);
            medianMs = SORTED[ringCount / 2];
            budgetMs = Math.max(4.0, medianMs * 1.35);
            actionsPerSecond = actionsThisSecond;
            actionsThisSecond = 0;
         }

         if (!enabled) {
            level = 0;
         } else {
            if (frameMs > budgetMs) {
               overruns++;
               calm = 0;
               int wanted = frameMs > budgetMs * 2.0 ? 3 : (!(frameMs > budgetMs * 1.5) && overruns < 2 ? 1 : 2);
               if (wanted > level) {
                  level = wanted;
               }
            } else {
               overruns = 0;
               if (++calm >= 20 && level > 0) {
                  level--;
                  calm = 0;
               }
            }
         }
      }
   }

   public static int level() {
      return level;
   }

   public static double budgetMs() {
      return budgetMs;
   }

   public static double medianMs() {
      return medianMs;
   }

   private static void acted(int bit) {
      if ((actedMask & bit) == 0) {
         actedMask |= bit;
         actionsThisSecond++;
      }
   }

   public static int chunkUploadCap() {
      if (level == 0) {
         return Integer.MAX_VALUE;
      } else {
         acted(1);
         return 1;
      }
   }

   public static double particleScale() {
      if (level >= 3) {
         acted(2);
         return 0.5;
      } else {
         return 1.0;
      }
   }

   public static double blockEntityScaleSq() {
      return 1.0;
   }

   public static int occlusionFactor() {
      return level >= 2 ? 2 : 1;
   }

   public static boolean skipAtlasTick() {
      if (level >= 3) {
         acted(8);
         return true;
      } else {
         return false;
      }
   }

   public static String line() {
      return enabled ? (level == 0 && actionsPerSecond == 0 ? "pacer ok" : "pacer L" + level + " · " + actionsPerSecond + " acciones/s") : "pacer off";
   }
}

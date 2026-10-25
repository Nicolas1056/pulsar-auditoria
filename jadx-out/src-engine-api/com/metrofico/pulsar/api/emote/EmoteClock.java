package com.metrofico.pulsar.api.emote;

public final class EmoteClock {
   private static long frameNanos = System.nanoTime();
   private static long pausedNanos;
   private static long pauseStart = -1L;

   private EmoteClock() {
   }

   public static void frame() {
      long now = System.nanoTime();
      boolean paused = Emotes.installed() && Emotes.host().paused();
      if (paused) {
         if (pauseStart < 0L) {
            pauseStart = now;
         }

         frameNanos = pauseStart - pausedNanos;
      } else {
         if (pauseStart >= 0L) {
            pausedNanos = pausedNanos + (now - pauseStart);
            pauseStart = -1L;
         }

         frameNanos = now - pausedNanos;
      }
   }

   public static long nanos() {
      return frameNanos;
   }

   public static double elapsed(long startNanos) {
      return (frameNanos - startNanos) / 1.0E9;
   }
}

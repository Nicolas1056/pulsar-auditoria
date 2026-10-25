package com.metrofico.pulsar.api.input;

import com.metrofico.pulsar.api.hub.ClientOptions;

public final class RawMouse {
   static final long ACTIVE_NS = 250000000L;
   private static final Object LOCK = new Object();
   private static long pendingX;
   private static long pendingY;
   private static long lastRaw = Long.MIN_VALUE;
   private static volatile boolean available;
   private static int frameY;
   private static int seenRevision = -1;
   private static boolean on;

   private RawMouse() {
   }

   public static boolean enabled() {
      int rev = ClientOptions.revision;
      if (rev != seenRevision) {
         on = ClientOptions.get().enabled("rawInput");
         seenRevision = rev;
      }

      return on;
   }

   public static void available(boolean value) {
      available = value;
   }

   public static boolean available() {
      return available;
   }

   public static void add(int dx, int dy, long nowNanos) {
      if (dx != 0 || dy != 0) {
         synchronized (LOCK) {
            pendingX += dx;
            pendingY += dy;
            lastRaw = nowNanos;
         }
      }
   }

   public static void reset() {
      synchronized (LOCK) {
         pendingX = 0L;
         pendingY = 0L;
      }
   }

   public static int takeX(int lwjglDx, int lwjglDy, boolean grabbed, long nowNanos) {
      if (grabbed && available && enabled()) {
         synchronized (LOCK) {
            if (lastRaw != Long.MIN_VALUE && nowNanos - lastRaw <= 250000000L) {
               int x = (int)Math.max(-2147483648L, Math.min(2147483647L, pendingX));
               frameY = (int)(-Math.max(-2147483647L, Math.min(2147483647L, pendingY)));
               pendingX = 0L;
               pendingY = 0L;
               return x;
            } else {
               pendingX = 0L;
               pendingY = 0L;
               frameY = lwjglDy;
               return lwjglDx;
            }
         }
      } else {
         reset();
         frameY = lwjglDy;
         return lwjglDx;
      }
   }

   static void clear() {
      synchronized (LOCK) {
         pendingX = 0L;
         pendingY = 0L;
         lastRaw = Long.MIN_VALUE;
         frameY = 0;
      }
   }

   public static int takeY() {
      return frameY;
   }
}

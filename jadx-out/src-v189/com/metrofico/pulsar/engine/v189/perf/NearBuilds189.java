package com.metrofico.pulsar.engine.v189.perf;

public final class NearBuilds189 {
   private static final int RING = 256;
   private static final long FRESH_NS = 1000000000L;
   private static final int[] X = new int[256];
   private static final int[] Y = new int[256];
   private static final int[] Z = new int[256];
   private static final long[] AT = new long[256];
   private static int next;
   private static int live;
   private static long floodUntil;
   private static int syncThisSecond;
   private static int deferredThisSecond;
   private static volatile int lastSync;
   private static volatile int lastDeferred;

   private NearBuilds189() {
   }

   public static void blockChanged(cj pos) {
      long now = System.nanoTime();
      if (live >= 256 && now - AT[next] < 1000000000L) {
         floodUntil = now + 1000000000L;
      }

      X[next] = pos.n();
      Y[next] = pos.o();
      Z[next] = pos.p();
      AT[next] = now;
      next = (next + 1) % 256;
      if (live < 256) {
         live++;
      }
   }

   public static boolean urgent(bht chunk) {
      long now = System.nanoTime();
      boolean urgent = now < floodUntil;
      if (!urgent) {
         cj o = chunk.j();
         int x0 = o.n() - 1;
         int y0 = o.o() - 1;
         int z0 = o.p() - 1;
         int x1 = x0 + 17;
         int y1 = y0 + 17;
         int z1 = z0 + 17;

         for (int i = 0; i < live; i++) {
            if (now - AT[i] < 1000000000L && X[i] >= x0 && X[i] <= x1 && Y[i] >= y0 && Y[i] <= y1 && Z[i] >= z0 && Z[i] <= z1) {
               urgent = true;
               break;
            }
         }
      }

      if (urgent) {
         syncThisSecond++;
      } else {
         deferredThisSecond++;
      }

      return urgent;
   }

   public static String line() {
      lastSync = syncThisSecond;
      lastDeferred = deferredThisSecond;
      deferredThisSecond = 0;
      syncThisSecond = 0;
      return lastSync + lastDeferred == 0 ? "" : " · cerca " + lastSync + " en render/" + lastDeferred + " a hilos";
   }

   public static void clear() {
      next = 0;
      live = 0;
      floodUntil = 0L;
   }
}

package com.metrofico.pulsar.api.legit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ClickSensor {
   static final int INTERVAL_BUCKETS = 80;
   static final int HOLD_BUCKETS = 40;
   private static final long BUCKET_NS = 5000000L;
   private static final long SECOND_NS = 1000000000L;
   static final int SEQUENCE = 6;
   static final int HISTORY = 128;
   static final long TOLERANCE_NS = 2000000L;
   static final long BURST_NS = 400000000L;
   private static final Object LOCK = new Object();
   private static final int[] INTERVALS = new int[80];
   private static final int[] HOLDS = new int[40];
   private static final long[] RECENT = new long[64];
   private static int count;
   private static int recentHead;
   private static int cpsMax;
   private static long lastPress = Long.MIN_VALUE;
   private static long pressedAt = Long.MIN_VALUE;
   private static final long[] SERIES = new long[128];
   private static int seriesHead;
   private static int seriesLength;
   private static int sequences;
   private static int repeats;

   private ClickSensor() {
   }

   public static void press(long nanos) {
      if (GameSignals.active) {
         synchronized (LOCK) {
            if (lastPress != Long.MIN_VALUE) {
               INTERVALS[bucket(nanos - lastPress, 80)]++;
               interval(nanos - lastPress);
            }

            lastPress = nanos;
            pressedAt = nanos;
            count++;
            RECENT[recentHead] = nanos;
            recentHead = recentHead + 1 & RECENT.length - 1;
            int cps = 0;

            for (long t : RECENT) {
               if (t != 0L && nanos - t < 1000000000L) {
                  cps++;
               }
            }

            cpsMax = Math.max(cpsMax, cps);
         }
      }
   }

   public static void release(long nanos) {
      if (GameSignals.active) {
         synchronized (LOCK) {
            if (pressedAt != Long.MIN_VALUE) {
               HOLDS[bucket(nanos - pressedAt, 40)]++;
               pressedAt = Long.MIN_VALUE;
            }
         }
      }
   }

   private static void interval(long ns) {
      if (ns <= 400000000L && ns >= 0L) {
         SERIES[seriesHead] = ns;
         seriesHead = seriesHead + 1 & 127;
         seriesLength = Math.min(128, seriesLength + 1);
         if (seriesLength >= 7) {
            sequences++;
            if (repeatsEarlier()) {
               repeats++;
            }
         }
      } else {
         seriesLength = 0;
      }
   }

   private static boolean repeatsEarlier() {
      int last = seriesHead - 1 & 127;

      for (int shift = 1; shift + 6 <= seriesLength; shift++) {
         boolean same = true;

         for (int k = 0; k < 6 && same; k++) {
            long a = SERIES[last - k & 127];
            long b = SERIES[last - k - shift & 127];
            same = Math.abs(a - b) <= 2000000L;
         }

         if (same) {
            return true;
         }
      }

      return false;
   }

   private static int bucket(long ns, int buckets) {
      long b = Math.max(0L, ns) / 5000000L;
      return (int)Math.min((long)(buckets - 1), b);
   }

   static void reset() {
      synchronized (LOCK) {
         Arrays.fill(INTERVALS, 0);
         Arrays.fill(HOLDS, 0);
         Arrays.fill(RECENT, 0L);
         Arrays.fill(SERIES, 0L);
         seriesHead = 0;
         seriesLength = 0;
         sequences = 0;
         repeats = 0;
         count = 0;
         recentHead = 0;
         cpsMax = 0;
         lastPress = Long.MIN_VALUE;
         pressedAt = Long.MIN_VALUE;
      }
   }

   static void drainInto(Map<String, Object> out) {
      synchronized (LOCK) {
         Map<String, Object> clicks = new LinkedHashMap<>();
         clicks.put("count", count);
         clicks.put("intervals", list(INTERVALS));
         clicks.put("holds", list(HOLDS));
         clicks.put("sequences", sequences);
         clicks.put("repeats", repeats);
         out.put("cpsMax", cpsMax);
         out.put("clicks", clicks);
         Arrays.fill(INTERVALS, 0);
         Arrays.fill(HOLDS, 0);
         count = 0;
         cpsMax = 0;
         sequences = 0;
         repeats = 0;
      }
   }

   private static List<Integer> list(int[] a) {
      List<Integer> l = new ArrayList<>(a.length);

      for (int v : a) {
         l.add(v);
      }

      return l;
   }
}

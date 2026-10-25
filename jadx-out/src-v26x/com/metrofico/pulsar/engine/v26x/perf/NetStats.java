package com.metrofico.pulsar.engine.v26x.perf;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class NetStats {
   private static final long GAP_NS = 100000000L;
   private static final AtomicLong LAST = new AtomicLong();
   private static final AtomicLong MAX_GAP = new AtomicLong();
   private static final AtomicInteger GAPS = new AtomicInteger();
   private static final AtomicLong PACKETS = new AtomicLong();
   private static volatile long lastMaxGapMs;
   private static volatile int lastGaps;
   private static volatile long lastPackets;
   private static volatile boolean connected;
   private static volatile long lastGapEndNanos;
   private static volatile long lastGapMs;
   private static final long[] RTT = new long[5];
   private static int rttCount;
   private static int rttNext;
   private static volatile long lastPongMs;

   private NetStats() {
   }

   public static synchronized void pong(long rttMs) {
      RTT[rttNext] = Math.max(0L, rttMs);
      rttNext = (rttNext + 1) % RTT.length;
      rttCount = Math.min(rttCount + 1, RTT.length);
      lastPongMs = System.currentTimeMillis();
   }

   public static synchronized long latencyMs() {
      if (rttCount != 0 && System.currentTimeMillis() - lastPongMs <= 10000L) {
         long sum = 0L;

         for (int i = 0; i < rttCount; i++) {
            sum += RTT[i];
         }

         return sum / rttCount;
      } else {
         return -1L;
      }
   }

   public static synchronized long[] latencyMinAvgMax() {
      if (rttCount != 0 && System.currentTimeMillis() - lastPongMs <= 10000L) {
         long min = Long.MAX_VALUE;
         long max = 0L;
         long sum = 0L;

         for (int i = 0; i < rttCount; i++) {
            min = Math.min(min, RTT[i]);
            max = Math.max(max, RTT[i]);
            sum += RTT[i];
         }

         return new long[]{min, sum / rttCount, max};
      } else {
         return null;
      }
   }

   public static void packetArrived() {
      long now = System.nanoTime();
      long prev = LAST.getAndSet(now);
      PACKETS.incrementAndGet();
      connected = true;
      if (prev != 0L) {
         long gap = now - prev;
         MAX_GAP.accumulateAndGet(gap, Math::max);
         if (gap > 100000000L) {
            GAPS.incrementAndGet();
            lastGapMs = gap / 1000000L;
            lastGapEndNanos = now;
         }
      }
   }

   public static void disconnected() {
      connected = false;
      LAST.set(0L);
   }

   public static long recentGapMs() {
      long last = LAST.get();
      if (last == 0L) {
         return 0L;
      } else {
         long now = System.nanoTime();
         long open = now - last;
         if (open > 100000000L) {
            return open / 1000000L;
         } else {
            return now - lastGapEndNanos < 3000000000L ? lastGapMs : 0L;
         }
      }
   }

   static void closeSecond() {
      long open = LAST.get() == 0L ? 0L : System.nanoTime() - LAST.get();
      lastMaxGapMs = Math.max(MAX_GAP.getAndSet(0L), open) / 1000000L;
      lastGaps = GAPS.getAndSet(0) + (open > 100000000L ? 1 : 0);
      lastPackets = PACKETS.getAndSet(0L);
   }

   static String line() {
      return !connected
         ? ""
         : " · red " + lastPackets + " pk/s · hueco máx " + lastMaxGapMs + " ms" + (lastGaps > 0 ? " (" + lastGaps + " huecos >100 ms)" : "");
   }
}

package com.metrofico.pulsar.api.net;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class NetMeter {
   static final long GAP_NS = 100000000L;
   public static final NetMeter CLIENT = new NetMeter();
   private final AtomicLong packets = new AtomicLong();
   private final AtomicLong bytes = new AtomicLong();
   private final AtomicLong compressed = new AtomicLong();
   private final AtomicLong nettyNanos = new AtomicLong();
   private final AtomicLong lastArrival = new AtomicLong();
   private final AtomicLong maxGap = new AtomicLong();
   private final AtomicInteger gaps = new AtomicInteger();
   private int deferredFrames;
   private volatile boolean connected;
   private long oldestPending;
   private long maxWait;
   private volatile NetMeter.Second last = NetMeter.Second.EMPTY;
   private final NetMeter.Window window = new NetMeter.Window();

   public void received(int frameBytes, long decodeNanos, long nowNanos) {
      this.packets.incrementAndGet();
      this.bytes.addAndGet(frameBytes);
      this.nettyNanos.addAndGet(decodeNanos);
      this.connected = true;
      long prev = this.lastArrival.getAndSet(nowNanos);
      if (prev != 0L) {
         long gap = nowNanos - prev;
         this.maxGap.accumulateAndGet(gap, Math::max);
         if (gap > 100000000L) {
            this.gaps.incrementAndGet();
         }
      }
   }

   public void inflated(int wireBytes, long inflateNanos) {
      this.compressed.addAndGet(wireBytes);
      this.nettyNanos.addAndGet(inflateNanos);
   }

   public void enqueued(long nowNanos) {
      if (this.oldestPending == 0L) {
         this.oldestPending = nowNanos;
      }
   }

   public long drained(long nowNanos) {
      long started = this.oldestPending;
      this.oldestPending = 0L;
      if (started == 0L) {
         return 0L;
      } else {
         long wait = Math.max(0L, nowNanos - started);
         this.maxWait = Math.max(this.maxWait, wait);
         return wait;
      }
   }

   public void requeued(long sinceNanos) {
      if (this.oldestPending == 0L || sinceNanos < this.oldestPending) {
         this.oldestPending = sinceNanos;
      }

      this.deferredFrames++;
   }

   public void disconnected() {
      this.connected = false;
      this.lastArrival.set(0L);
   }

   public NetMeter.Second closeSecond(long nowNanos) {
      long arrival = this.lastArrival.get();
      long open = this.connected && arrival != 0L ? nowNanos - arrival : 0L;
      NetMeter.Second s = new NetMeter.Second(
         this.packets.getAndSet(0L),
         this.bytes.getAndSet(0L),
         this.compressed.getAndSet(0L),
         this.nettyNanos.getAndSet(0L),
         Math.max(this.maxGap.getAndSet(0L), open),
         this.gaps.getAndSet(0),
         this.maxWait,
         this.deferredFrames,
         this.connected
      );
      this.maxWait = 0L;
      this.deferredFrames = 0;
      this.last = s;
      this.window.add(s);
      return s;
   }

   public NetMeter.Second last() {
      return this.last;
   }

   public String line() {
      return format(this.last, 1);
   }

   public String takeLogLine() {
      NetMeter.Second sum = this.window.take();
      return sum == null ? "" : format(sum, this.window.lastSeconds);
   }

   static String format(NetMeter.Second s, int seconds) {
      if (s.connected() && seconds > 0) {
         double per = seconds;
         StringBuilder sb = new StringBuilder(96);
         sb.append(" · red ")
            .append(Math.round(s.packets() / per))
            .append(" pk/s ")
            .append(String.format(Locale.ROOT, "%.1f", s.bytes() / per / 1000.0))
            .append(" kB/s");
         if (s.compressed() > 0L) {
            sb.append(" (comprimido ").append(String.format(Locale.ROOT, "%.1f", s.compressed() / per / 1000.0)).append(')');
         }

         sb.append(" · netty ")
            .append(String.format(Locale.ROOT, "%.2f", s.nettyNanos() / per / 1000000.0))
            .append(" ms/s")
            .append(" · cola máx ")
            .append(String.format(Locale.ROOT, "%.1f", s.maxWaitNanos() / 1000000.0))
            .append(" ms")
            .append(" · hueco máx ")
            .append(s.maxGapNanos() / 1000000L)
            .append(" ms");
         if (s.gaps() > 0) {
            sb.append(" (").append(s.gaps()).append(" >100 ms)");
         }

         if (s.deferredFrames() > 0) {
            sb.append(" · repartido en ").append(s.deferredFrames()).append(s.deferredFrames() == 1 ? " frame" : " frames");
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   public record Second(
      long packets, long bytes, long compressed, long nettyNanos, long maxGapNanos, int gaps, long maxWaitNanos, int deferredFrames, boolean connected
   ) {
      static final NetMeter.Second EMPTY = new NetMeter.Second(0L, 0L, 0L, 0L, 0L, 0, 0L, 0, false);
   }

   private static final class Window {
      private long packets;
      private long bytes;
      private long compressed;
      private long nettyNanos;
      private long maxGap;
      private long maxWait;
      private int gaps;
      private int deferredFrames;
      private int seconds;
      private int lastSeconds;
      private boolean connected;

      void add(NetMeter.Second s) {
         this.packets = this.packets + s.packets();
         this.bytes = this.bytes + s.bytes();
         this.compressed = this.compressed + s.compressed();
         this.nettyNanos = this.nettyNanos + s.nettyNanos();
         this.maxGap = Math.max(this.maxGap, s.maxGapNanos());
         this.maxWait = Math.max(this.maxWait, s.maxWaitNanos());
         this.gaps = this.gaps + s.gaps();
         this.deferredFrames = this.deferredFrames + s.deferredFrames();
         this.connected = this.connected | s.connected();
         this.seconds++;
      }

      NetMeter.Second take() {
         if (this.seconds == 0) {
            return null;
         } else {
            NetMeter.Second out = new NetMeter.Second(
               this.packets, this.bytes, this.compressed, this.nettyNanos, this.maxGap, this.gaps, this.maxWait, this.deferredFrames, this.connected
            );
            this.lastSeconds = this.seconds;
            this.packets = this.bytes = this.compressed = this.nettyNanos = this.maxGap = this.maxWait = 0L;
            this.gaps = this.deferredFrames = this.seconds = 0;
            this.connected = false;
            return out;
         }
      }
   }
}

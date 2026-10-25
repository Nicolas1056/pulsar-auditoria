package com.metrofico.pulsar.api.perf;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public final class HitchSampler {
   private static final int FRAMES = 8;
   private static final int GROUP_BY = 4;
   private static final int GROUPS = 2;
   private static final int MAX_SAMPLES = 256;
   private final long thresholdNanos;
   private final long intervalNanos;
   private final LongSupplier clock;
   private final Supplier<StackTraceElement[]> stack;
   private final BooleanSupplier active;
   private volatile long frameSeq;
   private volatile long frameStart;
   private long sampledSeq = -1L;
   private long nextSample;
   private final Object lock = new Object();
   private long worstNanos;
   private List<StackTraceElement[]> worstSamples;
   private List<StackTraceElement[]> current;
   private int hitches;

   public HitchSampler(long thresholdNanos, long intervalNanos, LongSupplier clock, Supplier<StackTraceElement[]> stack, BooleanSupplier active) {
      this.thresholdNanos = thresholdNanos;
      this.intervalNanos = intervalNanos;
      this.clock = clock;
      this.stack = stack;
      this.active = active;
   }

   public static HitchSampler watch(Thread thread, long thresholdNanos, long intervalNanos, BooleanSupplier active) {
      HitchSampler s = new HitchSampler(thresholdNanos, intervalNanos, System::nanoTime, thread::getStackTrace, active);
      Thread t = new Thread(s::loop, "pulsar-hitches");
      t.setDaemon(true);
      t.setPriority(10);
      t.start();
      return s;
   }

   public void frameStart(long nowNanos) {
      this.frameStart = nowNanos;
      this.frameSeq++;
   }

   private void loop() {
      while (true) {
         long wait = this.check(this.clock.getAsLong());
         LockSupport.parkNanos(Math.max(500000L, wait));
      }
   }

   long check(long nowNanos) {
      long seq = this.frameSeq;
      long start = this.frameStart;
      if (seq == 0L) {
         return this.thresholdNanos;
      } else {
         long elapsed = nowNanos - start;
         if (elapsed < this.thresholdNanos) {
            return this.thresholdNanos - elapsed;
         } else if (seq == this.sampledSeq && nowNanos < this.nextSample) {
            return this.nextSample - nowNanos;
         } else if (!this.active.getAsBoolean()) {
            return this.thresholdNanos;
         } else {
            StackTraceElement[] trace = this.stack.get();
            if (this.frameSeq != seq) {
               return this.thresholdNanos;
            } else {
               this.record(seq != this.sampledSeq, elapsed, trace);
               this.sampledSeq = seq;
               this.nextSample = nowNanos + this.intervalNanos;
               return this.intervalNanos;
            }
         }
      }
   }

   private void record(boolean newHitch, long elapsed, StackTraceElement[] trace) {
      synchronized (this.lock) {
         if (newHitch || this.current == null) {
            this.hitches++;
            this.current = new ArrayList<>();
         }

         if (this.current.size() < 256) {
            this.current.add(trace);
         }

         if (elapsed >= this.worstNanos) {
            this.worstNanos = elapsed;
            this.worstSamples = this.current;
         }
      }
   }

   public String takeLine() {
      long worst;
      List<StackTraceElement[]> samples;
      int count;
      synchronized (this.lock) {
         worst = this.worstNanos;
         samples = this.worstSamples == null ? null : new ArrayList<>(this.worstSamples);
         count = this.hitches;
         this.worstNanos = 0L;
         this.worstSamples = null;
         this.current = null;
         this.hitches = 0;
      }

      if (samples != null && !samples.isEmpty()) {
         Map<String, List<StackTraceElement[]>> groups = new LinkedHashMap<>();

         for (StackTraceElement[] t : samples) {
            groups.computeIfAbsent(key(t), k -> new ArrayList<>()).add(t);
         }

         List<List<StackTraceElement[]>> sorted = new ArrayList<>(groups.values());
         sorted.sort((a, b) -> b.size() - a.size());
         StringBuilder sb = new StringBuilder(384);
         sb.append(" · tirones ")
            .append(count)
            .append(" (peor ≥")
            .append(String.format(Locale.ROOT, "%.0f", worst / 1000000.0))
            .append(" ms, ")
            .append(samples.size())
            .append(samples.size() == 1 ? " foto: " : " fotos: ");

         for (int g = 0; g < Math.min(2, sorted.size()); g++) {
            List<StackTraceElement[]> group = sorted.get(g);
            sb.append(g == 0 ? "" : " · ").append(Math.round(group.size() * 100.0 / samples.size())).append("% en ");
            appendTrace(sb, group.get(group.size() - 1));
         }

         return sb.append(')').toString();
      } else {
         return "";
      }
   }

   private static String key(StackTraceElement[] t) {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < Math.min(4, t.length); i++) {
         sb.append(t[i].getClassName()).append('.').append(t[i].getMethodName()).append('<');
      }

      return sb.toString();
   }

   private static void appendTrace(StringBuilder sb, StackTraceElement[] trace) {
      if (trace.length == 0) {
         sb.append('?');
      } else {
         for (int i = 0; i < Math.min(8, trace.length); i++) {
            StackTraceElement e = trace[i];
            sb.append(i == 0 ? "" : " < ").append(e.getClassName()).append('.').append(e.getMethodName());
            if (e.getLineNumber() > 0) {
               sb.append(':').append(e.getLineNumber());
            }
         }
      }
   }
}

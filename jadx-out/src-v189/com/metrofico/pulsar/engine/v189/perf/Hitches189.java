package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.HitchSampler;

public final class Hitches189 {
   private static final long THRESHOLD_NS = 15000000L;
   private static final long INTERVAL_NS = 5000000L;
   private static HitchSampler sampler;

   private Hitches189() {
   }

   public static void frameStart() {
      HitchSampler s = sampler;
      if (s == null) {
         s = sampler = HitchSampler.watch(Thread.currentThread(), 15000000L, 5000000L, () -> ClientOptions.get().enabled("perf"));
      }

      s.frameStart(System.nanoTime());
   }

   static String takeLine() {
      HitchSampler s = sampler;
      return s == null ? "" : s.takeLine();
   }
}

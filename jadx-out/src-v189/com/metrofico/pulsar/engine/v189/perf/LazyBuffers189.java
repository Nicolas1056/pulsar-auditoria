package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;

public final class LazyBuffers189 {
   private LazyBuffers189() {
   }

   public static int gen() {
      return FrameFlags.lazyBuffers ? 0 : bqs.e();
   }
}

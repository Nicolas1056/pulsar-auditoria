package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.ConstantMatrix;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.FloatBuffer;

public final class SectionMatrix189 {
   public static final ConstantMatrix MATRIX = new ConstantMatrix();
   private static FloatBuffer shared;

   public static FloatBuffer buffer(int size) {
      if (FrameFlags.sectionMatrix && size == 16) {
         if (shared == null) {
            shared = avd.h(16);
         }

         return shared;
      } else {
         return avd.h(size);
      }
   }

   private SectionMatrix189() {
   }
}

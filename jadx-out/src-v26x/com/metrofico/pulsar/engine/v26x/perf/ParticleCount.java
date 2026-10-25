package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.engine.v26x.mixin.ParticleEngineAccessor;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;

public final class ParticleCount {
   private ParticleCount() {
   }

   public static int of(ParticleEngine engine) {
      int n = 0;

      for (ParticleGroup<?> g : ((ParticleEngineAccessor)engine).pulsar$particles().values()) {
         n += g.size();
      }

      return n;
   }
}

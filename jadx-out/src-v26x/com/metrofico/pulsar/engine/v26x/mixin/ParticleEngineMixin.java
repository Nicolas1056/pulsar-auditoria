package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v26x.perf.ParticleBudget;
import com.metrofico.pulsar.engine.v26x.perf.ParticleCount;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ParticleEngine.class})
abstract class ParticleEngineMixin {
   @Inject(
      method = {"createParticle"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noSweep(ParticleOptions options, double x, double y, double z, double dx, double dy, double dz, CallbackInfoReturnable<Particle> cir) {
      if (options.getType() == ParticleTypes.SWEEP_ATTACK && ClientOptions.get().classicPvp()) {
         cir.setReturnValue(null);
      }
   }

   @Inject(
      method = {"add"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$budget(Particle particle, CallbackInfo ci) {
      if (ClientOptions.get().enabled("perf")) {
         int count = ParticleCount.of((ParticleEngine)this);
         if (ParticleBudget.reject(particle, count)) {
            ci.cancel();
         }
      }
   }

   @Inject(
      method = {"extract"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$extractBegin(CallbackInfo ci) {
      PerfStats.begin(3);
   }

   @Inject(
      method = {"extract"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$extractEnd(CallbackInfo ci) {
      PerfStats.end(3);
   }
}

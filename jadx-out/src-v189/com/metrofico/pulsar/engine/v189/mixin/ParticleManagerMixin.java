package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.perf.ParticleBudget189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bec.class})
public abstract class ParticleManagerMixin {
   @Inject(
      method = {"a(Lpk;F)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$renderBegin(pk camera, float tickDelta, CallbackInfo ci) {
      ParticleBudget189.RENDER_BEGIN.run();
   }

   @Inject(
      method = {"a(Lpk;F)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$renderEnd(pk camera, float tickDelta, CallbackInfo ci) {
      ParticleBudget189.RENDER_END.run();
   }

   @Inject(
      method = {"a(Lbeb;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$budget(beb particle, CallbackInfo ci) {
      if (ClientOptions.get().enabled("perf")) {
         int count = 0;

         for (List<beb>[] layer : ((ParticleManagerAccessor)this).pulsar$particles()) {
            for (List<beb> list : layer) {
               count += list.size();
            }
         }

         if (ParticleBudget189.reject(particle, count)) {
            ci.cancel();
         }
      }
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderBuffers.class})
abstract class RenderBuffersMixin {
   @Inject(
      method = {"endFrame"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$endFrame(CallbackInfo ci) {
      ParallelFeatures.endFrame();
   }

   @Inject(
      method = {"close"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$close(CallbackInfo ci) {
      ParallelFeatures.close();
   }
}

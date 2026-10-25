package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.FastMath189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ns.class})
public abstract class MathHelperMixin {
   @Inject(
      method = {"a(F)F"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$sin(float value, CallbackInfoReturnable<Float> cir) {
      if (FrameFlags.fastMath) {
         cir.setReturnValue(FastMath189.sin(value));
      }
   }

   @Inject(
      method = {"b(F)F"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$cos(float value, CallbackInfoReturnable<Float> cir) {
      if (FrameFlags.fastMath) {
         cir.setReturnValue(FastMath189.cos(value));
      }
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({avo.class})
public abstract class VignetteMixin {
   @Inject(
      method = {"a(FLavr;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noVignette(float darkness, avr window, CallbackInfo ci) {
      if (!FrameFlags.vignette) {
         ci.cancel();
      }
   }
}

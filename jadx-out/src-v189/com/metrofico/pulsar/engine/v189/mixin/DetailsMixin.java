package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfk.class})
public abstract class DetailsMixin {
   @Inject(
      method = {"a(IF)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$fog(int mode, float tickDelta, CallbackInfo ci) {
      if (!FrameFlags.fog) {
         bfl.b(1000000.0F);
         bfl.c(1.0E7F);
      }
   }

   @Inject(
      method = {"c(F)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noWeather(float tickDelta, CallbackInfo ci) {
      if (!FrameFlags.weather) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"o"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noSplash(CallbackInfo ci) {
      if (!FrameFlags.weather) {
         ci.cancel();
      }
   }
}

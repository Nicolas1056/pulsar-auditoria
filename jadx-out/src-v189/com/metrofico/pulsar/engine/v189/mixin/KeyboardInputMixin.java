package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bev.class})
public abstract class KeyboardInputMixin {
   @Inject(
      method = {"a"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$freeze(CallbackInfo ci) {
      if (CameraDirector.get().active()) {
         bev in = (bev)this;
         in.b = 0.0F;
         in.a = 0.0F;
         in.c = false;
         in.d = false;
      }
   }
}

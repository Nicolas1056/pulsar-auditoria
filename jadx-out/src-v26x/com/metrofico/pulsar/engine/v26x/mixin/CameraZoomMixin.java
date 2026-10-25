package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hud.modules.Zoom;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Camera.class})
abstract class CameraZoomMixin {
   @Inject(
      method = {"calculateFov"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void pulsar$zoom(float partialTick, CallbackInfoReturnable<Float> cir) {
      if (Zoom.enabled() && Zoom.active()) {
         cir.setReturnValue((float)(((Float)cir.getReturnValue()).floatValue() / Zoom.factor()));
      }
   }
}

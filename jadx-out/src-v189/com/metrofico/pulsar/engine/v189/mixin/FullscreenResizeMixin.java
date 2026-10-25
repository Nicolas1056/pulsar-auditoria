package com.metrofico.pulsar.engine.v189.mixin;

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ave.class})
public abstract class FullscreenResizeMixin {
   @Shadow
   private boolean T;

   @Inject(
      method = {"q"},
      at = {@At(
         value = "INVOKE",
         target = "Lorg/lwjgl/opengl/Display;setFullscreen(Z)V",
         shift = Shift.AFTER,
         remap = false
      )},
      require = 1
   )
   private void pulsar$restoreResizable(CallbackInfo ci) {
      if (!this.T) {
         Display.setResizable(false);
         Display.setResizable(true);
      }
   }
}

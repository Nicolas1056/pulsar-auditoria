package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.input.RawMouse;
import com.metrofico.pulsar.engine.v189.input.RawMouse189;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({avf.class})
public abstract class MouseInputMixin {
   @Redirect(
      method = {"c"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/lwjgl/input/Mouse;getDX()I",
         remap = false
      ),
      require = 1
   )
   private int pulsar$rawX() {
      RawMouse189.ensureStarted();
      return RawMouse.takeX(Mouse.getDX(), Mouse.getDY(), Mouse.isGrabbed(), System.nanoTime());
   }

   @Redirect(
      method = {"c"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/lwjgl/input/Mouse;getDY()I",
         remap = false
      ),
      require = 1
   )
   private int pulsar$rawY() {
      return RawMouse.takeY();
   }

   @Inject(
      method = {"a", "b"},
      at = {@At("HEAD")},
      require = 2
   )
   private void pulsar$rawReset(CallbackInfo ci) {
      RawMouse.reset();
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.engine.PulsarVersion;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ave.class})
abstract class WindowTitleMixin {
   @Redirect(
      method = {"ap"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V",
         remap = false
      ),
      require = 1
   )
   private void pulsar$windowTitle(String vanilla) {
      Display.setTitle(PulsarVersion.full() + " · " + vanilla);
   }
}

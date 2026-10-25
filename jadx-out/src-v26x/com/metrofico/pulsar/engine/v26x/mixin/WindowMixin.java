package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.NoErrorContext;
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Window.class})
public abstract class WindowMixin {
   @Redirect(
      method = {"createGlfwWindow"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J"
      )
   )
   private static long pulsar$create(int width, int height, CharSequence title, long monitor, long share) {
      return NoErrorContext.create(width, height, title, monitor, share);
   }
}

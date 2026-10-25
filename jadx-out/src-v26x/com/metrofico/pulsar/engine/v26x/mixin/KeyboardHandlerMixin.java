package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.engine.v26x.keys.PulsarKeyInput;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.metrofico.pulsar.engine.v26x.ui.MenuScreen;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardHandler.class})
abstract class KeyboardHandlerMixin {
   private static final int GLFW_RELEASE = 0;
   private static final int GLFW_PRESS = 1;
   private static final int GLFW_REPEAT = 2;
   private static final int GLFW_KEY_ESCAPE = 256;

   @Inject(
      method = {"keyPress"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$hudEdit(long window, int action, KeyEvent event, CallbackInfo ci) {
      Minecraft mc = Minecraft.getInstance();
      if (window == mc.getWindow().handle() && mc.player != null) {
         if (PulsarKeyInput.handle(mc, KeyCode.fromGlfw(event.key()), action != 0, action == 2)) {
            ci.cancel();
         } else if (action == 1 && mc.gui.screen() == null) {
            PackClient runtime = PackRuntime.get();
            if (runtime.active()) {
               if (event.key() == 256 && CameraDirector.get().skip()) {
                  ci.cancel();
               } else {
                  String key = event.key() >= 65 && event.key() <= 90
                     ? String.valueOf((char)event.key())
                     : (event.key() >= 294 && event.key() <= 301 ? "F" + (event.key() - 289) : null);
                  if (key != null) {
                     runtime.screens().byKey(key).ifPresent(s -> {
                        mc.setScreenAndShow(new MenuScreen(s.id()));
                        ci.cancel();
                     });
                  }
               }
            }
         }
      }
   }
}

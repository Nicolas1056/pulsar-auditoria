package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.ClickCounter;
import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.legit.ClickCap;
import com.metrofico.pulsar.api.legit.ClickSensor;
import com.metrofico.pulsar.api.legit.GameSignals;
import com.metrofico.pulsar.engine.v189.controls.PulsarKeyInput189;
import com.metrofico.pulsar.engine.v189.ui.MouseLockScreen189;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ave.class})
public abstract class MinecraftClientClicksMixin {
   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/lwjgl/input/Mouse;next()Z",
         remap = false
      ),
      require = 1
   )
   private boolean pulsar$mouseEvent() {
      while (Mouse.next()) {
         int button = Mouse.getEventButton();
         boolean pressed = Mouse.getEventButtonState();
         boolean playing = button >= 0 && ave.A().m == null;
         if (!playing) {
            return true;
         }

         if (pressed) {
            if (MouseLock.suppressed()) {
               return false;
            }

            MouseLock.onGameClick();
         }

         String keyName = KeyCode.fromMouse(button);
         ave mc = ave.A();
         if (keyName == null || mc.h == null || !PulsarKeyInput189.handle(mc, keyName, pressed, false)) {
            long nanos = Mouse.getEventNanoseconds();
            if (button == 0 && GameSignals.active()) {
               if (pressed) {
                  ClickSensor.press(nanos);
               } else {
                  ClickSensor.release(nanos);
               }
            }

            if (!ClickCap.drop(button, pressed, nanos)) {
               if (pressed && button <= 1) {
                  ClickCounter.press(button);
               }

               return true;
            }
         }
      }

      return false;
   }

   @ModifyVariable(
      method = {"a(Laxu;)V"},
      at = @At("HEAD"),
      argsOnly = true,
      require = 1
   )
   private axu pulsar$mouseLockScreen(axu screen) {
      return MouseLockScreen189.enforce(screen);
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hud.modules.ClickCounter;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.legit.ClickCap;
import com.metrofico.pulsar.api.legit.ClickSensor;
import com.metrofico.pulsar.api.legit.GameSignals;
import com.metrofico.pulsar.api.ui.TabListView;
import com.metrofico.pulsar.engine.v26x.hud.Hud26;
import com.metrofico.pulsar.engine.v26x.keys.PulsarKeyInput;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MouseHandler.class})
abstract class MouseHandlerMixin {
   @Inject(
      method = {"turnPlayer"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noTurn(double sensitivity, CallbackInfo ci) {
      if (CameraDirector.get().active()) {
         ci.cancel();
      }
   }

   @Redirect(
      method = {"turnPlayer"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
      ),
      require = 1
   )
   private void pulsar$freecamTurn(LocalPlayer player, double yRot, double xRot) {
      if (Freecam.active()) {
         Freecam.turn((float)yRot * 0.15F, (float)xRot * 0.15F);
      } else {
         player.turn(yRot, xRot);
      }
   }

   @Inject(
      method = {"onButton"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cps(long window, MouseButtonInfo info, int action, CallbackInfo ci) {
      Minecraft mc = Minecraft.getInstance();
      if (window == mc.getWindow().handle() && mc.gui != null && mc.gui.screen() == null) {
         if (MouseLock.suppressed()) {
            ci.cancel();
         } else {
            long nanos = System.nanoTime();
            if (action == 1) {
               MouseLock.onGameClick();
            }

            String keyName = KeyCode.fromMouse(info.button());
            if (keyName != null && mc.player != null && (action == 1 || action == 0) && PulsarKeyInput.handle(mc, keyName, action == 1, false)) {
               ci.cancel();
            } else {
               if (info.button() == 0 && GameSignals.active()) {
                  if (action == 1) {
                     ClickSensor.press(nanos);
                  } else if (action == 0) {
                     ClickSensor.release(nanos);
                  }
               }

               if ((action == 1 || action == 0) && ClickCap.drop(info.button(), action == 1, nanos)) {
                  ci.cancel();
               } else {
                  if (action == 1) {
                     ClickCounter.press(info.button());
                  }
               }
            }
         }
      }
   }

   @Redirect(
      method = {"turnPlayer"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;",
         ordinal = 0
      ),
      require = 1
   )
   private Object pulsar$zoomSensitivity(OptionInstance<?> instance) {
      Object v = instance.get();
      return v instanceof Double d ? Zoom.sensitivity(d) : v;
   }

   @Inject(
      method = {"onScroll"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$tabScroll(long window, double dx, double dy, CallbackInfo ci) {
      if (Zoom.scroll(dy)) {
         ci.cancel();
      } else {
         TabListView tab = Hud26.TABLIST;
         if (tab != null && tab.scrollable()) {
            tab.scrollBy(-dy * 14.0);
            ci.cancel();
         }
      }
   }
}

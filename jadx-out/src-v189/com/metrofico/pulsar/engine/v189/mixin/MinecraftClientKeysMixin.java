package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.api.ui.TabListView;
import com.metrofico.pulsar.engine.v189.controls.PulsarKeyInput189;
import com.metrofico.pulsar.engine.v189.controls.ToggleKeys189;
import com.metrofico.pulsar.engine.v189.hud.Hud189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import com.metrofico.pulsar.engine.v189.ui.MenuScreen189;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ave.class})
public abstract class MinecraftClientKeysMixin {
   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lavb;a(I)V"
      ),
      require = 2
   )
   private void pulsar$togglePress(int code) {
      avb.a(code);
      ToggleKeys189.pressed(code);
   }

   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lwm;d(I)V"
      ),
      require = 1
   )
   private void pulsar$zoomScroll(wm inventory, int amount) {
      if (!Zoom.scroll(amount)) {
         TabListView tab = Hud189.TABLIST;
         if (tab.scrollable()) {
            tab.scrollBy(-amount * 14);
         } else {
            inventory.d(amount);
         }
      }
   }

   @Inject(
      method = {"s"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$mouseBoundViewKeys(CallbackInfo ci) {
      ave mc = (ave)this;
      avh o = mc.t;
      if (o != null && mc.m == null && mc.f != null) {
         if (o.ao.i() < 0) {
            for (; o.ao.f(); mc.g.m()) {
               o.aB++;
               if (o.aB > 2) {
                  o.aB = 0;
               }

               if (o.aB == 0) {
                  mc.o.a(mc.ac());
               } else if (o.aB == 1) {
                  mc.o.a(null);
               }
            }
         }

         if (o.ap.i() < 0) {
            while (o.ap.f()) {
               o.aG = !o.aG;
            }
         }
      }
   }

   @Inject(
      method = {"h"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$swapBegin(CallbackInfo ci) {
      PerfStats189.begin(6);
   }

   @Inject(
      method = {"h"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$swapEnd(CallbackInfo ci) {
      PerfStats189.end(6);
   }

   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lave;p()V"
      ),
      require = 1
   )
   private void pulsar$escSkips(ave mc) {
      if (!CameraDirector.get().skip()) {
         mc.p();
      }
   }

   @Inject(
      method = {"Z"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$keys(CallbackInfo ci) {
      ave mc = ave.A();
      if (mc.h != null) {
         int key = Keyboard.getEventKey();
         boolean pressed = Keyboard.getEventKeyState();
         if (!PulsarKeyInput189.handle(mc, KeyCode.fromLwjgl2(key), pressed, Keyboard.isRepeatEvent())) {
            if (pressed && mc.m == null) {
               PackClient pack = Pack189.get();
               if (pack.active()) {
                  String screenKey = MenuScreen189.keyName(key);
                  if (screenKey != null) {
                     pack.screens().byKey(screenKey).ifPresent(s -> mc.a(new MenuScreen189(s.id())));
                  }
               }
            }
         }
      }
   }
}

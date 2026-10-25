package com.metrofico.pulsar.engine.v189.mixin;

import auh.a;
import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ave.class})
public abstract class Visuals17SwingMixin {
   @Inject(
      method = {"s"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$blockHitLocalSwing(CallbackInfo ci) {
      ave mc = (ave)this;
      bew p = mc.h;
      if (p != null) {
         Visuals17.tickSneak(p.av());
         if (mc.m == null) {
            boolean onBlock = mc.s != null && mc.s.a == a.b;
            if (Visuals17.localSwing(p.bS(), mc.t.ai.d(), onBlock)) {
               int duration = ((LivingEntitySwingInvoker)p).pulsar$swingDuration();
               if (!p.ar || p.as >= duration / 2 || p.as < 0) {
                  p.as = -1;
                  p.ar = true;
               }
            }
         }
      }
   }
}

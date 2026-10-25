package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ave.class})
public abstract class UseWhileDigging17Mixin {
   @Shadow
   public bda c;
   @Unique
   private boolean pulsar$released;

   @Inject(
      method = {"ax"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$useWhileDigging(CallbackInfo ci) {
      if (Visuals17.useWhileDigging() && this.c != null && this.c.m()) {
         this.c.c();
         this.pulsar$released = true;
      }
   }

   @ModifyVariable(
      method = {"b(Z)V"},
      at = @At("HEAD"),
      argsOnly = true,
      require = 1
   )
   private boolean pulsar$releasedThisTick(boolean leftClick) {
      if (this.pulsar$released) {
         this.pulsar$released = false;
         return false;
      } else {
         return leftClick;
      }
   }
}

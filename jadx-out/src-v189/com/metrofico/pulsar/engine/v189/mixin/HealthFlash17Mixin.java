package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({avo.class})
public abstract class HealthFlash17Mixin {
   @ModifyVariable(
      method = {"d(Lavr;)V"},
      at = @At("STORE"),
      index = 4,
      require = 1
   )
   private boolean pulsar$flash17(boolean vanilla) {
      if (!Visuals17.healthFlashing()) {
         return vanilla;
      } else {
         pk camera = ave.A().ac();
         return camera instanceof wn && Visuals17.heartsFlash17(((wn)camera).Z);
      }
   }

   @ModifyVariable(
      method = {"d(Lavr;)V"},
      at = @At("STORE"),
      index = 5,
      require = 1
   )
   private int pulsar$noLostHearts17(int vanilla) {
      if (!Visuals17.healthFlashing()) {
         return vanilla;
      } else {
         pk camera = ave.A().ac();
         return camera instanceof wn ? ns.f(((wn)camera).bn()) : vanilla;
      }
   }
}

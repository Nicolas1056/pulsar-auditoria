package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({biz.class})
public abstract class FishingLine17Mixin {
   @ModifyConstant(
      method = {"a(Lur;DDDFF)V"},
      constant = {@Constant(
         doubleValue = -0.36
      )},
      require = 1
   )
   private double pulsar$lineX(double vanilla) {
      return Visuals17.fishingRod() ? -0.5 : vanilla;
   }

   @ModifyConstant(
      method = {"a(Lur;DDDFF)V"},
      constant = {@Constant(
         doubleValue = 0.35
      )},
      require = 1
   )
   private double pulsar$lineZ(double vanilla) {
      return Visuals17.fishingRod() ? 0.8 : vanilla;
   }
}

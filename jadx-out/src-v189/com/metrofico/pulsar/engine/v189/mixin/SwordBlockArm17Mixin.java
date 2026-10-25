package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({bbj.class})
public abstract class SwordBlockArm17Mixin {
   @Shadow
   public bct h;

   @ModifyConstant(
      method = {"a(FFFFFFLpk;)V"},
      constant = {@Constant(
         floatValue = (float) (-Math.PI / 6)
      )},
      require = 1
   )
   private float pulsar$blockArm17(float vanilla) {
      return Visuals17.swords() ? this.h.g : vanilla;
   }
}

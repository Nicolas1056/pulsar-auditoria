package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Player.class})
abstract class FlyBoostMixin {
   @Inject(
      method = {"getFlyingSpeed"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void pulsar$flyBoost(CallbackInfoReturnable<Float> cir) {
      Player self = (Player)this;
      if (self == Minecraft.getInstance().player) {
         float factor = ToggleSneakSprint.flySpeedFactor(self.getAbilities().flying, self.isSprinting());
         if (factor != 1.0F) {
            cir.setReturnValue((Float)cir.getReturnValue() * factor);
         }
      }
   }
}

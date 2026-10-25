package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Player.class})
abstract class PlayerAttackMixin {
   @Inject(
      method = {"getAttackStrengthScale"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$classicSwing(float partialTick, CallbackInfoReturnable<Float> cir) {
      if (ClientOptions.get().classicPvp() && this == Minecraft.getInstance().player) {
         cir.setReturnValue(1.0F);
      }
   }
}

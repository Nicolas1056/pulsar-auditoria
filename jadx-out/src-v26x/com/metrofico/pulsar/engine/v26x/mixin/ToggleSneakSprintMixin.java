package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LocalPlayer.class})
abstract class ToggleSneakSprintMixin {
   @Inject(
      method = {"aiStep"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$flyBoostTick(CallbackInfo ci) {
      LocalPlayer self = (LocalPlayer)this;
      ToggleSneakSprint.tick(self.getAbilities().flying, self.isSprinting());
   }

   @Redirect(
      method = {"aiStep"},
      at = @At(
         value = "FIELD",
         target = "Lnet/minecraft/client/player/LocalPlayer;sprintTriggerTime:I",
         opcode = 180
      ),
      require = 3
   )
   private int pulsar$doubleTap(LocalPlayer self) {
      return ToggleSneakSprint.doubleTap() ? ((LocalPlayerAccessor)self).pulsar$sprintTriggerTime() : 0;
   }

   @Redirect(
      method = {"aiStep"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/player/Abilities;getFlyingSpeed()F"
      ),
      require = 1
   )
   private float pulsar$flyBoostVertical(Abilities abilities) {
      LocalPlayer self = (LocalPlayer)this;
      return abilities.getFlyingSpeed() * ToggleSneakSprint.flySpeedFactor(abilities.flying, self.isSprinting());
   }
}

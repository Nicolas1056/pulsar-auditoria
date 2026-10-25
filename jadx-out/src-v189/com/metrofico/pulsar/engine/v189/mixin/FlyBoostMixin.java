package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({wn.class})
public abstract class FlyBoostMixin {
   @Redirect(
      method = {"g"},
      at = @At(
         value = "INVOKE",
         target = "Lwl;a()F"
      ),
      require = 1
   )
   private float pulsar$flyBoost(wl abilities) {
      wn self = (wn)this;
      float speed = abilities.a();
      return self == ave.A().h ? speed * ToggleSneakSprint.flySpeedFactor(abilities.b, self.aw()) : speed;
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bew.class})
public abstract class ToggleSneakSprintMixin {
   @Shadow
   protected int d;

   @Inject(
      method = {"m"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$flyBoostTick(CallbackInfo ci) {
      bew self = (bew)this;
      ToggleSneakSprint.tick(self.bA.b, self.aw());
   }

   @Redirect(
      method = {"m"},
      at = @At(
         value = "FIELD",
         target = "Lbew;d:I",
         opcode = 180
      ),
      require = 3
   )
   private int pulsar$doubleTap(bew self) {
      return ToggleSneakSprint.doubleTap() ? this.d : 0;
   }

   @Redirect(
      method = {"m"},
      at = @At(
         value = "INVOKE",
         target = "Lwl;a()F"
      ),
      require = 2
   )
   private float pulsar$flyBoostVertical(wl abilities) {
      bew self = (bew)this;
      return abilities.a() * ToggleSneakSprint.flySpeedFactor(abilities.b, self.aw());
   }
}

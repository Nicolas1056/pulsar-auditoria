package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bkp.class})
public abstract class CapeFeatureRendererMixin {
   @Shadow
   @Final
   private bln a;

   @Inject(
      method = {"a(Lbet;FFFFFFF)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lbfl;E()V",
         shift = Shift.AFTER
      )},
      require = 1
   )
   private void pulsar$followBody(
      bet player, float limbAngle, float limbDistance, float tickDelta, float age, float headYaw, float headPitch, float scale, CallbackInfo ci
   ) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(player.F());
         if (a != null && a.bones[1] != null) {
            this.a.g().g.c(0.0625F);
         }
      }
   }
}

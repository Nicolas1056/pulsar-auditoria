package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v189.emote.EmotePose189;
import com.metrofico.pulsar.engine.v189.emote.MeshSubdivider189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bbj.class})
public abstract class BiPedModelMixin {
   @Unique
   private boolean pulsar$posed;

   @Inject(
      method = {"a(FFFFFFLpk;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$rest(float limbAngle, float limbDistance, float age, float headYaw, float headPitch, float scale, pk entity, CallbackInfo ci) {
      if (EmotePose189.firstPersonHand) {
         ((bbj)this).q = false;
      }

      if (this.pulsar$posed) {
         this.pulsar$posed = false;
         EmotePose189.rest((bbj)this);
      }
   }

   @Inject(
      method = {"a(FFFFFFLpk;)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$emote(float limbAngle, float limbDistance, float age, float headYaw, float headPitch, float scale, pk entity, CallbackInfo ci) {
      bbj model = (bbj)this;
      MeshSubdivider189.syncOverlays(model);
      if (entity != null && EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(entity.F());
         if (a != null) {
            this.pulsar$posed = true;
            EmotePose189.apply(model, a);
         }
      }
   }
}

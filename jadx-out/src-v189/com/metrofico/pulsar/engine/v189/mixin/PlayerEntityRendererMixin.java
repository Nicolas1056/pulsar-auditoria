package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v189.emote.EmotePropLayer189;
import com.metrofico.pulsar.engine.v189.emote.EmoteRoot189;
import com.metrofico.pulsar.engine.v189.emote.MeshSubdivider189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bln.class})
public abstract class PlayerEntityRendererMixin {
   @Inject(
      method = {"<init>(Lbiu;Z)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$features(biu dispatcher, boolean thinArms, CallbackInfo ci) {
      LivingEntityRendererAccessor self = (LivingEntityRendererAccessor)this;
      self.pulsar$addFeature(new EmotePropLayer189((bln)this));

      for (blb<?> f : self.pulsar$features()) {
         if (f instanceof bkn) {
            ArmorFeatureRendererAccessor armor = (ArmorFeatureRendererAccessor)f;
            if (armor.pulsar$firstLayer() instanceof bbj b1) {
               MeshSubdivider189.subdivideArmor(b1);
            }

            if (armor.pulsar$secondLayer() instanceof bbj b2) {
               MeshSubdivider189.subdivideArmor(b2);
            }
         }
      }
   }

   @Inject(
      method = {"a(Lbet;FFF)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$rootMotion(bet player, float age, float yaw, float tickDelta, CallbackInfo ci) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(player.F());
         if (a != null) {
            EmoteRoot189.apply(a);
         }
      }
   }

   @Redirect(
      method = {"d"},
      at = @At(
         value = "INVOKE",
         target = "Lwm;h()Lzx;"
      ),
      require = 1
   )
   private zx pulsar$noArmPose(wm inventory) {
      zx held = inventory.h();
      if (held != null && EmotePlayer.anyActive()) {
         ave mc = ave.A();

         for (Object o : mc.f.j) {
            if (o instanceof wn p && p.bi == inventory) {
               Active a = EmotePlayer.active(p.F());
               return a != null && a.running() ? null : held;
            }
         }

         return held;
      } else {
         return held;
      }
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import bgr.b;
import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v189.visuals.ThirdPerson17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({bky.class})
public abstract class HeldItemRendererMixin {
   @Redirect(
      method = {"a(Lpr;FFFFFFF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lpr;bA()Lzx;"
      ),
      require = 1
   )
   private zx pulsar$noItem(pr entity) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(entity.F());
         if (a != null && a.running()) {
            return null;
         }
      }

      return ThirdPerson17.shown(entity, entity.bA());
   }

   @Redirect(
      method = {"a(Lpr;FFFFFFF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfn;a(Lpr;Lzx;Lbgr$b;)V"
      ),
      require = 1
   )
   private void pulsar$thirdPerson17(bfn held, pr entity, zx stack, b mode) {
      if (ThirdPerson17.applies(stack)) {
         ThirdPerson17.transform(entity, stack);
         mode = b.a;
      }

      held.a(entity, stack, mode);
   }
}

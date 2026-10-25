package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bmh.class})
public abstract class SpriteAtlasTextureMixin {
   @Shadow
   private List<bmi> i;

   @Inject(
      method = {"a(Lbni;Lbmb;)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$loaded(bni manager, bmb creator, CallbackInfo ci) {
      SpriteCulling189.atlasLoaded(this.i);
   }

   @Inject(
      method = {"d"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$tickBegin(CallbackInfo ci) {
      SpriteCulling189.tickBegin();
      if (FramePacer.skipAtlasTick()) {
         ci.cancel();
      }
   }

   @Redirect(
      method = {"d"},
      at = @At(
         value = "INVOKE",
         target = "Lbmi;j()V"
      ),
      require = 1
   )
   private void pulsar$tickSprite(bmi sprite) {
      if (SpriteCulling189.enabled() ? SpriteCulling189.shouldTick(sprite) : SpriteCulling189.animationAllowed(sprite.i())) {
         sprite.j();
      }
   }
}

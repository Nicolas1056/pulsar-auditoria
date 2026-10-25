package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.emote.MeshSubdivider189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bbr.class})
public abstract class PlayerEntityModelMixin {
   @Inject(
      method = {"<init>(FZ)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$subdivide(float scale, boolean thinArms, CallbackInfo ci) {
      MeshSubdivider189.subdividePlayer((bbr)this);
   }
}

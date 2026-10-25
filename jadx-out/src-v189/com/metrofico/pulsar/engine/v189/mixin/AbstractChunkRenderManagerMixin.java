package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfh.class})
public abstract class AbstractChunkRenderManagerMixin {
   @Inject(
      method = {"a(Lbht;Ladf;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$visible(bht chunk, adf layer, CallbackInfo ci) {
      if (SpriteCulling189.enabled()) {
         SpriteCulling189.sectionVisible((SpriteCulling189.SectionExt)chunk.g());
      }
   }
}

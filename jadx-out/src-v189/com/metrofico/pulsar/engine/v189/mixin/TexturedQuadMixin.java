package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({bbs.class})
public abstract class TexturedQuadMixin implements EntityBatch189.TexturedQuadInvert {
   @Shadow
   private boolean c;

   @Override
   public boolean pulsar$inverted() {
      return this.c;
   }
}

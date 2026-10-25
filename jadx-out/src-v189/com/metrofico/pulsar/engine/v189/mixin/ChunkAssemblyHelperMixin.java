package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({bhq.class})
public abstract class ChunkAssemblyHelperMixin implements SpriteCulling189.SectionExt {
   @Unique
   private volatile bmi[] pulsar$sprites;

   @Override
   public bmi[] pulsar$sprites() {
      return this.pulsar$sprites;
   }

   @Override
   public void pulsar$sprites(bmi[] sprites) {
      this.pulsar$sprites = sprites;
   }
}

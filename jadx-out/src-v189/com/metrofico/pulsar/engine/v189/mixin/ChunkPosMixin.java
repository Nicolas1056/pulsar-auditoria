package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({adg.class})
public abstract class ChunkPosMixin {
   @Shadow
   @Final
   public int a;
   @Shadow
   @Final
   public int b;

   @Overwrite
   @Override
   public int hashCode() {
      long h = ((long)this.a << 32 | this.b & 4294967295L) * -7046029254386353131L;
      return (int)(h >>> 32);
   }
}

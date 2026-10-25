package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bcr.class})
public interface ModelBoxAccessor {
   @Accessor("i")
   bbs[] pulsar$quads();

   @Accessor("i")
   void pulsar$quads(bbs[] var1);
}

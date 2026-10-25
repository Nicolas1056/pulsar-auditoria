package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bga.class})
public interface BuiltChunkStorageAccessor {
   @Accessor("d")
   int pulsar$sizeX();

   @Accessor("c")
   int pulsar$sizeY();

   @Accessor("e")
   int pulsar$sizeZ();
}

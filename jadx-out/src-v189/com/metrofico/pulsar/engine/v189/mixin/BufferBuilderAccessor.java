package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bfd.class})
public interface BufferBuilderAccessor {
   @Accessor("n")
   boolean pulsar$building();
}

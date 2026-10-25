package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bpz.class})
public interface SoundManagerAccessor {
   @Accessor("e")
   bqa pulsar$loadedSounds();
}

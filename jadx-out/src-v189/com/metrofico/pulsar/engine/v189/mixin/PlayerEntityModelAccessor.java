package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bbr.class})
public interface PlayerEntityModelAccessor {
   @Accessor("y")
   boolean pulsar$thinArms();
}

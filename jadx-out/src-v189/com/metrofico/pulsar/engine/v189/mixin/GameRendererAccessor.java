package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({bfk.class})
public interface GameRendererAccessor {
   @Invoker("a")
   float pulsar$nightVision(pr var1, float var2);
}

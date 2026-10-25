package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({pr.class})
public interface LivingEntitySwingInvoker {
   @Invoker("n")
   int pulsar$swingDuration();
}

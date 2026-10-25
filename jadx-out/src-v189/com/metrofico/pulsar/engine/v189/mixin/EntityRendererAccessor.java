package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({biv.class})
public interface EntityRendererAccessor {
   @Invoker("a")
   void pulsar$renderName(pk var1, double var2, double var4, double var6);
}

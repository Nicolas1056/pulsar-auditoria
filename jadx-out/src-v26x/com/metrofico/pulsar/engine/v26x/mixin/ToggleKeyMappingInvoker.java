package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({ToggleKeyMapping.class})
public interface ToggleKeyMappingInvoker {
   @Invoker("reset")
   void pulsar$reset();
}

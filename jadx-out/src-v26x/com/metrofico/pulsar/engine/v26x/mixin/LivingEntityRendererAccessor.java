package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({LivingEntityRenderer.class})
public interface LivingEntityRendererAccessor {
   @Invoker("addLayer")
   boolean pulsar$addLayer(RenderLayer<?, ?> var1);
}

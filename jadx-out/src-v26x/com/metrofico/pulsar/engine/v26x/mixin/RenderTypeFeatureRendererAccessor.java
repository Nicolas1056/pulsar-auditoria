package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({RenderTypeFeatureRenderer.class})
public interface RenderTypeFeatureRendererAccessor {
   @Invoker("getVertexBuilder")
   VertexConsumer pulsar$vertexBuilder(RenderType var1);
}

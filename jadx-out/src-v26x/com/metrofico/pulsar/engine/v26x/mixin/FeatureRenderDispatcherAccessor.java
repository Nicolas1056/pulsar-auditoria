package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.FeatureRendererMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({FeatureRenderDispatcher.class})
public interface FeatureRenderDispatcherAccessor {
   @Accessor("featureRenderers")
   FeatureRendererMap pulsar$featureRenderers();
}

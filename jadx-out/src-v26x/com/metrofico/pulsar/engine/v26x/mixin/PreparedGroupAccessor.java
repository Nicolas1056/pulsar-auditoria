package com.metrofico.pulsar.engine.v26x.mixin;

import java.util.List;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRendererMap;
import net.minecraft.client.renderer.feature.FeatureRendererType;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(
   targets = {"net.minecraft.client.renderer.feature.FeatureRenderDispatcher$PreparedGroup"}
)
public interface PreparedGroupAccessor {
   @Accessor("featureType")
   FeatureRendererType<?> pulsar$featureType();

   @Invoker("sliceUnchecked")
   List<?> pulsar$slice(List<SubmitNode> var1);

   @Invoker("prepare")
   void pulsar$prepare(FeatureFrameContext var1, FeatureRendererMap var2, List<SubmitNode> var3);
}

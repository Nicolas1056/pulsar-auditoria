package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import com.metrofico.pulsar.engine.v26x.perf.TagBatching;
import net.minecraft.client.renderer.StagedVertexBuffer;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({RenderTypeFeatureRenderer.class})
abstract class RenderTypeFeatureRendererMixin {
   @ModifyArg(
      method = {"prepareGroup"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/feature/RenderTypeFeatureRenderer$Group;<init>(Lnet/minecraft/client/renderer/StagedVertexBuffer;Z)V"
      ),
      index = 1,
      require = 1
   )
   private boolean pulsar$canReorder(boolean canReorder) {
      return TagBatching.canReorder((RenderTypeFeatureRenderer<?>)this, canReorder);
   }

   @Redirect(
      method = {"prepareGroup"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/feature/FeatureFrameContext;stagedVertexBuffer()Lnet/minecraft/client/renderer/StagedVertexBuffer;"
      ),
      require = 1
   )
   private StagedVertexBuffer pulsar$prepareBuffer(FeatureFrameContext context) {
      return ParallelFeatures.buffer(context, (RenderTypeFeatureRenderer<?>)this);
   }

   @Redirect(
      method = {"executeGroup"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/feature/FeatureFrameContext;stagedVertexBuffer()Lnet/minecraft/client/renderer/StagedVertexBuffer;"
      ),
      require = 1
   )
   private StagedVertexBuffer pulsar$executeBuffer(FeatureFrameContext context) {
      return ParallelFeatures.buffer(context, (RenderTypeFeatureRenderer<?>)this);
   }
}

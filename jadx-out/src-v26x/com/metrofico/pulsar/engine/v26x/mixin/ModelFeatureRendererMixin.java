package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.PoseCache26;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.Submit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelFeatureRenderer.class})
abstract class ModelFeatureRendererMixin {
   @Inject(
      method = {"prepareModel"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$poseBegin(Submit<?> submit, CallbackInfo ci) {
      VertexConsumer consumer = ((RenderTypeFeatureRendererAccessor)this).pulsar$vertexBuilder(submit.renderType());
      if (PoseCache26.beginModel(
         submit.state(),
         submit.model(),
         submit.renderType(),
         submit.sprite(),
         submit.sheetedDecalPose(),
         consumer,
         submit.lightCoords(),
         submit.overlayCoords(),
         submit.tintedColor()
      )) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"prepareModel"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$poseEnd(Submit<?> submit, CallbackInfo ci) {
      PoseCache26.endModel(((RenderTypeFeatureRendererAccessor)this).pulsar$vertexBuilder(submit.renderType()));
   }
}

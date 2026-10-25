package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import com.metrofico.pulsar.engine.v26x.perf.TagBatching;
import com.mojang.blaze3d.ProjectionType;
import net.minecraft.client.renderer.rendertype.PreparedRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   targets = {"net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer$Group"}
)
abstract class RenderTypeGroupMixin {
   @Redirect(
      method = {"getOrAddDraw"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/rendertype/RenderType;prepare()Lnet/minecraft/client/renderer/rendertype/PreparedRenderType;"
      ),
      require = 1
   )
   private PreparedRenderType pulsar$prepared(RenderType type) {
      return ParallelFeatures.prepared(type);
   }

   @Redirect(
      method = {"getOrAddDraw"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/rendertype/RenderType;sortOnUpload()Z"
      ),
      require = 1
   )
   private boolean pulsar$sortOnUpload(RenderType type) {
      return TagBatching.sortOnUpload(type);
   }

   @Redirect(
      method = {"getOrAddDraw"},
      at = @At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/RenderSystem;getProjectionType()Lcom/mojang/blaze3d/ProjectionType;"
      ),
      require = 1
   )
   private ProjectionType pulsar$projection() {
      return ParallelFeatures.projectionType();
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.engine.v26x.perf.OcclusionCuller;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockEntityRenderDispatcher.class})
abstract class BlockEntityRenderDispatcherMixin {
   @Inject(
      method = {"tryExtractRenderState"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private <E extends BlockEntity, S extends BlockEntityRenderState> void pulsar$cull(
      E be, float partialTick, CrumblingOverlay crumbling, boolean unused, CallbackInfoReturnable<S> cir
   ) {
      if (FrameFlags.cullBlockEntities) {
         PerfStats.blockEntitySeen();
         BlockEntityRenderer<E, ?> renderer = ((BlockEntityRenderDispatcher)this).getRenderer(be);
         if (renderer != null && !renderer.shouldRenderOffScreen()) {
            BlockPos pos = be.getBlockPos();
            Vec3 eye = OcclusionCuller.eye();
            double d = eye.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            if (d > FrameFlags.beDistanceSq * FramePacer.blockEntityScaleSq()) {
               PerfStats.blockEntityCulled();
               cir.setReturnValue(null);
            } else {
               AABB box = new AABB(pos);
               if (OcclusionCuller.candidate(box, eye)) {
                  if (OcclusionCuller.hidden(OcclusionCuller.blockKey(pos.asLong()), box)) {
                     PerfStats.blockEntityCulled();
                     cir.setReturnValue(null);
                  }
               }
            }
         }
      }
   }
}

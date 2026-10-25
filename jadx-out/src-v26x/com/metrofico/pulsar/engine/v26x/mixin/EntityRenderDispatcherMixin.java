package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.engine.v26x.perf.NameOnly26;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityRenderDispatcher.class})
abstract class EntityRenderDispatcherMixin {
   @Inject(
      method = {"submit"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private <S extends EntityRenderState> void pulsar$nameOnly(
      S state, CameraRenderState camera, double x, double y, double z, PoseStack pose, SubmitNodeCollector collector, CallbackInfo ci
   ) {
      if (state instanceof AvatarRenderState a && NameOnly26.active(a.id)) {
         EntityRenderer<?, ? super S> renderer = ((EntityRenderDispatcher)this).getRenderer(state);
         Vec3 off = renderer.getRenderOffset(state);
         pose.pushPose();
         pose.translate(x + off.x(), y + off.y(), z + off.z());
         ((EntityRendererInvoker)renderer).pulsar$submitNameDisplay(state, pose, collector, camera);
         pose.popPose();
         ci.cancel();
      }
   }

   @Inject(
      method = {"shouldRender"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private <E extends Entity> void pulsar$hideSelf(E entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
      if (CameraDirector.get().hideSelf() && entity == Minecraft.getInstance().player) {
         cir.setReturnValue(false);
      }
   }
}

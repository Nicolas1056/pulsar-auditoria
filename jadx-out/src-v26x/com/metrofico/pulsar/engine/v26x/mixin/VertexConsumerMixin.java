package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({VertexConsumer.class})
public interface VertexConsumerMixin {
   @Inject(
      method = {"putBakedQuad"},
      at = {@At("HEAD")}
   )
   private void pulsar$touchQuad(Pose pose, BakedQuad quad, QuadInstance instance, CallbackInfo ci) {
      SpriteCulling.touch(quad.materialInfo().sprite());
   }

   @Inject(
      method = {"putBlockBakedQuad"},
      at = {@At("HEAD")}
   )
   private void pulsar$touchBlockQuad(float x, float y, float z, BakedQuad quad, QuadInstance instance, CallbackInfo ci) {
      SpriteCulling.touch(quad.materialInfo().sprite());
   }
}

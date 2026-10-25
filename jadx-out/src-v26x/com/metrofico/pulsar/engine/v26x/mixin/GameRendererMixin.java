package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.emote.EmoteClock;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
abstract class GameRendererMixin {
   @Inject(
      method = {"render(Lnet/minecraft/client/DeltaTracker;Z)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frameClock(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
      EmoteClock.frame();
   }

   @Inject(
      method = {"renderItemInHand"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noHand(CameraRenderState camera, float partialTick, Matrix4fc projection, CallbackInfo ci) {
      if (CameraDirector.get().active()) {
         ci.cancel();
      }
   }
}

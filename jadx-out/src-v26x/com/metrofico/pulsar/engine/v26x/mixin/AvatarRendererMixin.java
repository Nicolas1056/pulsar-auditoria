package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v26x.emote.EmotePropLayer;
import com.metrofico.pulsar.engine.v26x.emote.EmoteRoot;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AvatarRenderer.class})
abstract class AvatarRendererMixin {
   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$propLayer(Context context, boolean slim, CallbackInfo ci) {
      ((LivingEntityRendererAccessor)this).pulsar$addLayer(new EmotePropLayer((AvatarRenderer)this));
   }

   @Inject(
      method = {"setupRotations(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$rootMotion(AvatarRenderState state, PoseStack pose, float bodyRot, float scale, CallbackInfo ci) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(state.id);
         if (a != null) {
            EmoteRoot.apply(pose, a);
         }
      }
   }

   @Inject(
      method = {"extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$lockYaw(Avatar entity, AvatarRenderState state, float partialTick, CallbackInfo ci) {
      if (EmotePlayer.anyActive()) {
         Active a = EmotePlayer.active(state.id);
         if (a != null && a.running()) {
            float headYaw = state.bodyRot + state.yRot;
            state.bodyRot = a.lockYaw;
            state.yRot = a.animatesHead() ? 0.0F : Mth.clamp(Mth.wrapDegrees(headYaw - a.lockYaw), -75.0F, 75.0F);
            state.rightHandItemState.clear();
            state.leftHandItemState.clear();
            state.rightHandItemStack = ItemStack.EMPTY;
            state.leftHandItemStack = ItemStack.EMPTY;
            state.rightArmPose = ArmPose.EMPTY;
            state.leftArmPose = ArmPose.EMPTY;
         }
      }
   }
}

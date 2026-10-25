package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerModel.class})
abstract class PlayerModelMixin {
   @Inject(
      method = {"translateToHand(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$handToForearm(AvatarRenderState state, HumanoidArm arm, PoseStack pose, CallbackInfo ci) {
      PlayerModel self = (PlayerModel)this;
      ModelPart armPart = self.getArm(arm);
      String name = arm == HumanoidArm.RIGHT ? "right_forearm" : "left_forearm";
      if (armPart.hasChild(name)) {
         ModelPart forearm = armPart.getChild(name);
         forearm.translateAndRotate(pose);
         PartPose init = forearm.getInitialPose();
         pose.translate(-init.x() / 16.0F, -init.y() / 16.0F, -init.z() / 16.0F);
      }
   }
}

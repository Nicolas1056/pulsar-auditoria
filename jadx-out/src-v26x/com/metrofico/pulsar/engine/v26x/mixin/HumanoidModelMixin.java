package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v26x.emote.EmotePose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HumanoidModel.class})
abstract class HumanoidModelMixin {
   @Inject(
      method = {"setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$emote(HumanoidRenderState state, CallbackInfo ci) {
      if (state instanceof AvatarRenderState s) {
         HumanoidModel<?> model = (HumanoidModel<?>)this;
         pulsar$syncLowerOverlays(model);
         if (EmotePlayer.anyActive()) {
            Active a = EmotePlayer.active(s.id);
            if (a != null) {
               EmotePose.apply(model, a);
            }
         }
      }
   }

   private static void pulsar$syncLowerOverlays(HumanoidModel<?> model) {
      if (model.body.hasChild("body_lower")) {
         pulsar$syncOverlay(model.body, "jacket", "body_lower");
         pulsar$syncOverlay(model.rightArm, "right_sleeve", "right_forearm");
         pulsar$syncOverlay(model.leftArm, "left_sleeve", "left_forearm");
         pulsar$syncOverlay(model.rightLeg, "right_pants", "right_shin");
         pulsar$syncOverlay(model.leftLeg, "left_pants", "left_shin");
      }
   }

   private static void pulsar$syncOverlay(ModelPart parent, String overlay, String joint) {
      if (parent.hasChild(overlay) && parent.hasChild(joint)) {
         ModelPart lower = parent.getChild(joint);
         String lowerName = overlay + "_lower";
         if (lower.hasChild(lowerName)) {
            lower.getChild(lowerName).visible = parent.getChild(overlay).visible;
         }
      }
   }
}

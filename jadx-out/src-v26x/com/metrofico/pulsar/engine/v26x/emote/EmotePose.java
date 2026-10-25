package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.Emote.Bone;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public final class EmotePose {
   private static final float DEG = (float) (Math.PI / 180.0);
   private static final float[] OUT = new float[9];

   private EmotePose() {
   }

   public static void apply(HumanoidModel<?> model, Active a) {
      double elapsed = a.elapsed();
      float w = EmoteEvaluator.weight(elapsed, a.endAt());
      if (!(w <= 0.0F)) {
         double t = EmoteEvaluator.time(a.emote, elapsed);
         Bone[] bones = a.bones;
         part(model.head, bones[0], t, w);
         part(model.body, bones[1], t, w);
         part(model.rightArm, bones[2], t, w);
         part(model.leftArm, bones[3], t, w);
         part(model.rightLeg, bones[4], t, w);
         part(model.leftLeg, bones[5], t, w);
         child(model.body, "body_lower", "jacket", bones[6], t, w);
         child(model.rightArm, "right_forearm", "right_sleeve", bones[7], t, w);
         child(model.leftArm, "left_forearm", "left_sleeve", bones[8], t, w);
         child(model.rightLeg, "right_shin", "right_pants", bones[9], t, w);
         child(model.leftLeg, "left_shin", "left_pants", bones[10], t, w);
      }
   }

   private static void child(ModelPart parent, String name, String overlay, Bone bone, double t, float w) {
      if (bone != null && parent.hasChild(name)) {
         ModelPart joint = parent.getChild(name);
         JointLink.link(parent, overlay, joint);
         part(joint, bone, t, w);
      }
   }

   private static void part(ModelPart part, Bone bone, double t, float w) {
      if (bone != null) {
         float[] o = OUT;
         int mask = EmoteEvaluator.sample(bone, t, o);
         PartPose init = part.getInitialPose();
         if ((mask & 1) != 0) {
            part.xRot = lerp(w, part.xRot, init.xRot() + o[0] * (float) (Math.PI / 180.0));
            part.yRot = lerp(w, part.yRot, init.yRot() + o[1] * (float) (Math.PI / 180.0));
            part.zRot = lerp(w, part.zRot, init.zRot() + o[2] * (float) (Math.PI / 180.0));
         }

         if ((mask & 2) != 0) {
            part.x = lerp(w, part.x, init.x() + o[3]);
            part.y = lerp(w, part.y, init.y() + o[4]);
            part.z = lerp(w, part.z, init.z() + o[5]);
         }

         if ((mask & 4) != 0) {
            part.xScale = lerp(w, part.xScale, init.xScale() * o[6]);
            part.yScale = lerp(w, part.yScale, init.yScale() * o[7]);
            part.zScale = lerp(w, part.zScale, init.zScale() * o[8]);
         }
      }
   }

   private static float lerp(float w, float from, float to) {
      return w >= 1.0F ? to : from + (to - from) * w;
   }
}

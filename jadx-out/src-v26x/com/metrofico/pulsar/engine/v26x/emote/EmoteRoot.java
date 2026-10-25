package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.Emote;
import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.Emote.Bone;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;

public final class EmoteRoot {
   private static final float[] BUF = new float[9];
   private static final Quaternionf Q = new Quaternionf();
   private static final float DEG = (float) (Math.PI / 180.0);
   private static final float PIVOT_Y = 0.75F;

   private EmoteRoot() {
   }

   public static void apply(PoseStack pose, Active a) {
      Bone root = a.bones[Emote.ROOT_INDEX];
      if (root != null) {
         double elapsed = a.elapsed();
         float w = EmoteEvaluator.weight(elapsed, a.endAt());
         if (!(w <= 0.0F)) {
            double t = EmoteEvaluator.time(a.emote, elapsed);
            int mask = EmoteEvaluator.sample(root, t, BUF);
            if ((mask & 2) != 0) {
               pose.translate(-BUF[3] / 16.0F * w, -BUF[4] / 16.0F * w, BUF[5] / 16.0F * w);
            }

            if ((mask & 1) != 0) {
               pose.translate(0.0F, 0.75F, 0.0F);
               pose.mulPose(
                  Q.rotationZYX(BUF[2] * (float) (Math.PI / 180.0) * w, -BUF[1] * (float) (Math.PI / 180.0) * w, -BUF[0] * (float) (Math.PI / 180.0) * w)
               );
               pose.translate(0.0F, -0.75F, 0.0F);
            }

            if ((mask & 4) != 0) {
               pose.translate(0.0F, 0.75F, 0.0F);
               pose.scale(1.0F + (BUF[6] - 1.0F) * w, 1.0F + (BUF[7] - 1.0F) * w, 1.0F + (BUF[8] - 1.0F) * w);
               pose.translate(0.0F, -0.75F, 0.0F);
            }
         }
      }
   }
}

package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.Emote;
import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.Emote.Bone;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;

public final class EmoteRoot189 {
   private static final float[] BUF = new float[9];
   private static final float PIVOT_Y = 0.75F;

   private EmoteRoot189() {
   }

   public static void apply(Active a) {
      Bone root = a.bones[Emote.ROOT_INDEX];
      if (root != null) {
         double elapsed = a.elapsed();
         float w = EmoteEvaluator.weight(elapsed, a.endAt());
         if (!(w <= 0.0F)) {
            double t = EmoteEvaluator.time(a.emote, elapsed);
            int mask = EmoteEvaluator.sample(root, t, BUF);
            if ((mask & 2) != 0) {
               bfl.b(-BUF[3] / 16.0F * w, -BUF[4] / 16.0F * w, BUF[5] / 16.0F * w);
            }

            if ((mask & 1) != 0) {
               bfl.b(0.0F, 0.75F, 0.0F);
               bfl.b(BUF[2] * w, 0.0F, 0.0F, 1.0F);
               bfl.b(-BUF[1] * w, 0.0F, 1.0F, 0.0F);
               bfl.b(-BUF[0] * w, 1.0F, 0.0F, 0.0F);
               bfl.b(0.0F, -0.75F, 0.0F);
            }

            if ((mask & 4) != 0) {
               bfl.b(0.0F, 0.75F, 0.0F);
               bfl.a(1.0F + (BUF[6] - 1.0F) * w, 1.0F + (BUF[7] - 1.0F) * w, 1.0F + (BUF[8] - 1.0F) * w);
               bfl.b(0.0F, -0.75F, 0.0F);
            }
         }
      }
   }
}

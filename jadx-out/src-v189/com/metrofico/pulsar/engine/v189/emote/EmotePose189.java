package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.EmoteEvaluator;
import com.metrofico.pulsar.api.emote.Emote.Bone;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.engine.v189.mixin.PlayerEntityModelAccessor;

public final class EmotePose189 {
   private static final float DEG = (float) (Math.PI / 180.0);
   private static final float[] OUT = new float[9];
   private static final float[][] PIVOT = new float[][]{
      {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {-5.0F, 2.0F, 0.0F}, {5.0F, 2.0F, 0.0F}, {-1.9F, 12.0F, 0.0F}, {1.9F, 12.0F, 0.0F}
   };
   private static final float[][] PIVOT_THIN = new float[][]{{-5.0F, 2.5F, 0.0F}, {5.0F, 2.5F, 0.0F}};
   public static boolean firstPersonHand;

   private EmotePose189() {
   }

   public static void apply(bbj model, Active a) {
      double elapsed = a.elapsed();
      float w = EmoteEvaluator.weight(elapsed, a.endAt());
      if (!(w <= 0.0F)) {
         double t = EmoteEvaluator.time(a.emote, elapsed);
         Bone[] bones = a.bones;
         boolean thin = model instanceof bbr && ((PlayerEntityModelAccessor)model).pulsar$thinArms();
         part(model.e, bones[0], PIVOT[0], t, w);
         part(model.g, bones[1], PIVOT[1], t, w);
         part(model.h, bones[2], thin ? PIVOT_THIN[0] : PIVOT[2], t, w);
         part(model.i, bones[3], thin ? PIVOT_THIN[1] : PIVOT[3], t, w);
         part(model.j, bones[4], PIVOT[4], t, w);
         part(model.k, bones[5], PIVOT[5], t, w);
         MeshSubdivider189.Joints j = MeshSubdivider189.joints(model);
         if (j != null) {
            for (int i = 0; i < 5; i++) {
               if (j.parts[i] != null) {
                  part(j.parts[i], bones[6 + i], j.pivots[i], t, w);
               }
            }
         }

         model.f.f = model.e.f;
         model.f.g = model.e.g;
         model.f.h = model.e.h;
         model.f.c = model.e.c;
         model.f.d = model.e.d;
         model.f.e = model.e.e;
      }
   }

   public static void rest(bbj model) {
      boolean thin = model instanceof bbr && ((PlayerEntityModelAccessor)model).pulsar$thinArms();
      pivot(model.e, PIVOT[0]);
      pivot(model.g, PIVOT[1]);
      pivot(model.h, thin ? PIVOT_THIN[0] : PIVOT[2]);
      pivot(model.i, thin ? PIVOT_THIN[1] : PIVOT[3]);
      pivot(model.j, PIVOT[4]);
      pivot(model.k, PIVOT[5]);
      MeshSubdivider189.Joints j = MeshSubdivider189.joints(model);
      if (j != null) {
         for (int i = 0; i < 5; i++) {
            bct p = j.parts[i];
            if (p != null) {
               p.f = p.g = p.h = 0.0F;
               pivot(p, j.pivots[i]);
            }
         }
      }
   }

   private static void pivot(bct part, float[] pivot) {
      part.c = pivot[0];
      part.d = pivot[1];
      part.e = pivot[2];
   }

   private static void part(bct part, Bone bone, float[] pivot, double t, float w) {
      if (bone != null) {
         float[] o = OUT;
         int mask = EmoteEvaluator.sample(bone, t, o);
         if ((mask & 1) != 0) {
            part.f = lerp(w, part.f, o[0] * (float) (Math.PI / 180.0));
            part.g = lerp(w, part.g, o[1] * (float) (Math.PI / 180.0));
            part.h = lerp(w, part.h, o[2] * (float) (Math.PI / 180.0));
         }

         if ((mask & 2) != 0) {
            part.c = lerp(w, part.c, pivot[0] + o[3]);
            part.d = lerp(w, part.d, pivot[1] + o[4]);
            part.e = lerp(w, part.e, pivot[2] + o[5]);
         }
      }
   }

   private static float lerp(float w, float from, float to) {
      return w >= 1.0F ? to : from + (to - from) * w;
   }
}

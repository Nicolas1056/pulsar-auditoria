package com.metrofico.pulsar.api.emote;

import java.util.List;

public final class EmoteEvaluator {
   public static final int ROT = 1;
   public static final int POS = 2;
   public static final int SCALE = 4;
   public static final double BLEND = 0.2;

   private EmoteEvaluator() {
   }

   public static double time(Emote e, double elapsedSeconds) {
      if (elapsedSeconds <= 0.0) {
         return 0.0;
      } else if (e.loop()) {
         double d = e.duration();
         return d <= 0.0 ? 0.0 : elapsedSeconds % d;
      } else {
         return Math.min(elapsedSeconds, e.duration());
      }
   }

   public static float weight(double elapsedSeconds, double endAt) {
      double in = Math.min(1.0, elapsedSeconds / 0.2);
      double out = Math.min(1.0, (endAt + 0.2 - elapsedSeconds) / 0.2);
      double w = Math.max(0.0, Math.min(in, out));
      return (float)(w * w * (3.0 - 2.0 * w));
   }

   public static boolean finished(double elapsedSeconds, double endAt) {
      return elapsedSeconds >= endAt + 0.2;
   }

   public static int sample(Emote.Bone bone, double t, float[] out) {
      int mask = 0;
      if (!bone.rot().isEmpty()) {
         channel(bone.rot(), t, out, 0);
         mask |= 1;
      }

      if (!bone.pos().isEmpty()) {
         channel(bone.pos(), t, out, 3);
         mask |= 2;
      }

      if (!bone.scale().isEmpty()) {
         channel(bone.scale(), t, out, 6);
         mask |= 4;
      }

      return mask;
   }

   private static void channel(List<Emote.Key> keys, double t, float[] out, int o) {
      int n = keys.size();
      Emote.Key first = keys.get(0);
      if (n != 1 && !(t <= first.t())) {
         Emote.Key last = keys.get(n - 1);
         if (t >= last.t()) {
            put(out, o, last);
         } else {
            int i = 0;

            while (i + 1 < n - 1 && keys.get(i + 1).t() <= t) {
               i++;
            }

            Emote.Key a = keys.get(i);
            Emote.Key b = keys.get(i + 1);
            double span = b.t() - a.t();
            double x = span <= 0.0 ? 1.0 : (t - a.t()) / span;
            Easing easing = a.easing();
            if (easing == Easing.STEP) {
               put(out, o, a);
            } else if (easing == Easing.CATMULLROM) {
               Emote.Key p0 = keys.get(Math.max(0, i - 1));
               Emote.Key p3 = keys.get(Math.min(n - 1, i + 2));
               out[o] = (float)catmull(p0.x(), a.x(), b.x(), p3.x(), x);
               out[o + 1] = (float)catmull(p0.y(), a.y(), b.y(), p3.y(), x);
               out[o + 2] = (float)catmull(p0.z(), a.z(), b.z(), p3.z(), x);
            } else {
               double k = easing.apply(x);
               out[o] = (float)(a.x() + (b.x() - a.x()) * k);
               out[o + 1] = (float)(a.y() + (b.y() - a.y()) * k);
               out[o + 2] = (float)(a.z() + (b.z() - a.z()) * k);
            }
         }
      } else {
         put(out, o, first);
      }
   }

   private static void put(float[] out, int o, Emote.Key k) {
      out[o] = k.x();
      out[o + 1] = k.y();
      out[o + 2] = k.z();
   }

   private static double catmull(double p0, double p1, double p2, double p3, double t) {
      double t2 = t * t;
      double t3 = t2 * t;
      return 0.5 * (2.0 * p1 + (-p0 + p2) * t + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * t2 + (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * t3);
   }
}

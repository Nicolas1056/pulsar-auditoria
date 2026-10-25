package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.util.Collections;
import java.util.List;

public final class SignText189 {
   private static final float MIN_GLYPH_PX = 3.0F;
   private static final double GLYPH_BLOCKS = 0.0833333375;
   private static int drawn;
   private static int skipped;

   private SignText189() {
   }

   public static boolean enabled() {
      return FrameFlags.signText;
   }

   public static List<eu> lines(SignText189.SignExt sign, eu[] texts, eu text, int width, avn renderer, double x, double y, double z) {
      if (tooFar(x, y, z)) {
         skipped++;
         return Collections.emptyList();
      } else {
         drawn++;
         eu[] source = sign.pulsar$source();

         for (int i = 0; i < 4 && i < texts.length; i++) {
            if (texts[i] == text) {
               if (source[i] != text) {
                  List<eu> l = avu.a(text, width, renderer, false, true);
                  sign.pulsar$lines()[i] = l == null ? Collections.emptyList() : l;
                  sign.pulsar$formatted()[i] = l != null && !l.isEmpty() ? l.get(0).d() : "";
                  source[i] = text;
               }

               return sign.pulsar$lines()[i];
            }
         }

         return avu.a(text, width, renderer, false, true);
      }
   }

   public static boolean blank(SignText189.SignExt sign, eu[] texts) {
      eu[] source = sign.pulsar$blankSource();
      boolean[] blank = sign.pulsar$blank();
      boolean same = true;

      for (int i = 0; i < 4; i++) {
         eu t = i < texts.length ? texts[i] : null;
         if (source[i] != t) {
            same = false;
            source[i] = t;
         }
      }

      if (!same) {
         boolean empty = true;

         for (int ix = 0; ix < 4 && empty; ix++) {
            empty = source[ix] == null || source[ix].c().isEmpty();
         }

         blank[0] = empty;
      }

      return blank[0];
   }

   public static String formatted(SignText189.SignExt sign, eu line) {
      List<eu>[] lines = sign.pulsar$lines();

      for (int i = 0; i < 4; i++) {
         List<eu> l = lines[i];
         if (l != null && !l.isEmpty() && l.get(0) == line) {
            return sign.pulsar$formatted()[i];
         }
      }

      return line.d();
   }

   public static boolean tooFar(double x, double y, double z) {
      return x * x + y * y + z * z > legibleDistanceSq();
   }

   public static double legibleDistanceSq() {
      ave mc = ave.A();
      double fov = Math.toRadians(Math.max(10.0F, Math.min(mc.t.aI, 110.0F)) / Zoom.currentFactor());
      double k = 0.0833333375 * (mc.e / (2.0 * Math.tan(fov / 2.0))) / 3.0;
      return k * k;
   }

   public static String line() {
      int d = drawn / 4;
      int s = skipped / 4;
      skipped = 0;
      drawn = 0;
      return d + s == 0 ? "" : " · carteles " + d + " con texto/" + s + " sin";
   }

   public interface SignExt {
      eu[] pulsar$source();

      List<eu>[] pulsar$lines();

      String[] pulsar$formatted();

      eu[] pulsar$blankSource();

      boolean[] pulsar$blank();
   }
}

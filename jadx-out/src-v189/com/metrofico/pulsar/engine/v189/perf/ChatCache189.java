package com.metrofico.pulsar.engine.v189.perf;

public final class ChatCache189 {
   public static final Object SLOT = new Object();
   public static final Runnable NO_FLUSH = () -> {};
   public static final int LIFE_TICKS = 200;
   public static final int FADE_FROM_TICKS = 180;

   private ChatCache189() {
   }

   public static int guiScale(ave mc) {
      int max = mc.t.aL == 0 ? 1000 : mc.t.aL;
      int scale = 1;

      while (scale < max && mc.d / (scale + 1) >= 320 && mc.e / (scale + 1) >= 240) {
         scale++;
      }

      if (mc.d() && scale % 2 != 0 && scale != 1) {
         scale--;
      }

      return scale;
   }

   public static long mix(long h, long v) {
      long z = h ^ v + -7046029254386353131L + (h << 6) + (h >>> 2);
      z = (z ^ z >>> 30) * -4658895280553007687L;
      z = (z ^ z >>> 27) * -7723592293110705685L;
      return z ^ z >>> 31;
   }

   public static int fadeState(int age) {
      return age >= 200 ? -1 : (age > 180 ? age : 0);
   }
}

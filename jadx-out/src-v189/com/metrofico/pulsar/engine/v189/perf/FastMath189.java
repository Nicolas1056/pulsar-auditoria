package com.metrofico.pulsar.engine.v189.perf;

public final class FastMath189 {
   private static final int BITS = 12;
   private static final int MASK = 4095;
   private static final float RAD_TO_INDEX = 651.8986F;
   private static final float[] SIN = new float[4096];
   private static final int COS_OFFSET = 1024;

   private FastMath189() {
   }

   public static float sin(float rad) {
      return SIN[(int)(rad * 651.8986F) & 4095];
   }

   public static float cos(float rad) {
      return SIN[(int)(rad * 651.8986F) + 1024 & 4095];
   }

   static {
      for (int i = 0; i < SIN.length; i++) {
         SIN[i] = (float)Math.sin((i + 0.5) / SIN.length * Math.PI * 2.0);
      }

      for (int i = 0; i < 360; i += 90) {
         SIN[(int)(i * (SIN.length / 360.0F)) & 4095] = (float)Math.sin(Math.toRadians(i));
      }
   }
}

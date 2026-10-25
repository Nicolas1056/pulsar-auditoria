package com.metrofico.pulsar.api.perf;

public final class CompactCoords {
   public static final float POS_STEPS_PER_BLOCK = 2048.0F;

   private CompactCoords() {
   }

   public static int encodePos(float local) {
      return Math.max(0, Math.min(65535, Math.round((local + 8.0F) * 2048.0F)));
   }

   public static float decodePos(int u16) {
      return u16 / 2048.0F - 8.0F;
   }

   public static int encodeUvInward(float value, float quadMin, float quadMax) {
      float scaled = value * 65535.0F;
      int q = value <= quadMin ? (int)Math.ceil(scaled) : (value >= quadMax ? (int)Math.floor(scaled) : Math.round(scaled));
      return Math.max(0, Math.min(65535, q));
   }
}

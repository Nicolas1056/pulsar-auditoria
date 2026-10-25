package com.metrofico.pulsar.api.perf;

import java.nio.FloatBuffer;

public final class ConstantMatrix {
   private final float[] value = new float[16];
   private boolean known;

   public boolean fill(FloatBuffer out) {
      if (!this.known) {
         return false;
      } else {
         for (int i = 0; i < 16; i++) {
            out.put(i, this.value[i]);
         }

         return true;
      }
   }

   public void learn(FloatBuffer from) {
      if (!this.known) {
         float[] m = new float[16];

         for (int i = 0; i < 16; i++) {
            m[i] = from.get(i);
         }

         if (plausible(m)) {
            System.arraycopy(m, 0, this.value, 0, 16);
            this.known = true;
         }
      }
   }

   static boolean plausible(float[] m) {
      for (int c = 0; c < 4; c++) {
         for (int r = 0; r < 4; r++) {
            float v = m[c * 4 + r];
            if (Float.isNaN(v) || Float.isInfinite(v)) {
               return false;
            }

            if (c == r) {
               if (Math.abs(v - 1.0F) > 0.001F) {
                  return false;
               }
            } else if (c == 3) {
               if (Math.abs(v) > 0.01F) {
                  return false;
               }
            } else if (v != 0.0F) {
               return false;
            }
         }
      }

      return true;
   }

   public void forget() {
      this.known = false;
   }
}

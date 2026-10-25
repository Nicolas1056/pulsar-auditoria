package com.metrofico.pulsar.api.gfx;

public final class AtlasPixels {
   private AtlasPixels() {
   }

   public static int[] halve(int[] src, int width, int height) {
      int w = width / 2;
      int h = height / 2;
      if (w >= 8 && h >= 8) {
         int[] out = new int[w * h];

         for (int y = 0; y < h; y++) {
            int row0 = y * 2 * width;
            int row1 = row0 + width;

            for (int x = 0; x < w; x++) {
               int a = src[row0 + x * 2];
               int b = src[row0 + x * 2 + 1];
               int c = src[row1 + x * 2];
               int d = src[row1 + x * 2 + 1];
               int px = 0;

               for (int shift = 0; shift <= 24; shift += 8) {
                  int sum = (a >>> shift & 0xFF) + (b >>> shift & 0xFF) + (c >>> shift & 0xFF) + (d >>> shift & 0xFF);
                  px |= (sum + 2) / 4 << shift;
               }

               out[y * w + x] = px;
            }
         }

         return out;
      } else {
         return null;
      }
   }
}

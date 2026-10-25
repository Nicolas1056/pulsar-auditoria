package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;

public final class PixelInfinity {
   public static final String SYMBOL = "∞";
   public static final int WIDTH = 10;
   private static final String[] ROWS = new String[]{".##...##.", "#..#.#..#", "#...#...#", "#..#.#..#", ".##...##."};

   private PixelInfinity() {
   }

   public static void draw(Canvas c, int x, int y, int argb, boolean shadow) {
      if (shadow) {
         int dark = argb & 0xFF000000 | (argb & 16579836) >> 2;
         rows(c, x + 1, y + 2, dark);
      }

      rows(c, x, y + 1, argb);
   }

   private static void rows(Canvas c, int x, int y, int argb) {
      for (int r = 0; r < ROWS.length; r++) {
         String row = ROWS[r];
         int i = 0;

         while (i < row.length()) {
            if (row.charAt(i) != '#') {
               i++;
            } else {
               int j = i;

               while (j < row.length() && row.charAt(j) == '#') {
                  j++;
               }

               c.fill(x + i, y + r, x + j, y + r + 1, argb);
               i = j;
            }
         }
      }
   }
}

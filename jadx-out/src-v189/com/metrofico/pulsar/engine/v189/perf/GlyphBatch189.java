package com.metrofico.pulsar.engine.v189.perf;

import java.util.Arrays;
import org.lwjgl.opengl.GL11;

public final class GlyphBatch189 {
   private static final int MAX_GROUPS = 256;
   private static float[] verts = new float[65536];
   private static int glyphs;
   private static final jy[] TEX = new jy[256];
   private static final int[] START = new int[256];
   private static final int[] COLOR = new int[256];
   private static int groups;
   private static int color = -1;

   private GlyphBatch189() {
   }

   public static void color(float r, float g, float b, float a) {
      color = Math.round(a * 255.0F) << 24 | Math.round(b * 255.0F) << 16 | Math.round(g * 255.0F) << 8 | Math.round(r * 255.0F);
   }

   public static void add(
      jy tex,
      float x0,
      float y0,
      float u0,
      float v0,
      float x1,
      float y1,
      float u1,
      float v1,
      float x2,
      float y2,
      float u2,
      float v2,
      float x3,
      float y3,
      float u3,
      float v3
   ) {
      if (LabelBatch189.capturing()) {
         LabelBatch189.glyph(tex, color, x0, y0, u0, v0, x1, y1, u1, v1, x2, y2, u2, v2, x3, y3, u3, v3);
      } else {
         if (groups == 0 || TEX[groups - 1] != tex || COLOR[groups - 1] != color) {
            if (groups == 256) {
               flush();
            }

            TEX[groups] = tex;
            COLOR[groups] = color;
            START[groups] = glyphs;
            groups++;
         }

         if ((glyphs + 1) * 16 > verts.length) {
            verts = Arrays.copyOf(verts, verts.length * 2);
         }

         int o = glyphs * 16;
         verts[o] = x0;
         verts[o + 1] = y0;
         verts[o + 2] = u0;
         verts[o + 3] = v0;
         verts[o + 4] = x1;
         verts[o + 5] = y1;
         verts[o + 6] = u1;
         verts[o + 7] = v1;
         verts[o + 8] = x3;
         verts[o + 9] = y3;
         verts[o + 10] = u3;
         verts[o + 11] = v3;
         verts[o + 12] = x2;
         verts[o + 13] = y2;
         verts[o + 14] = u2;
         verts[o + 15] = v2;
         glyphs++;
      }
   }

   public static void flush() {
      if (groups != 0) {
         bfx tess = bfx.a();
         bfd buf = tess.c();

         for (int gi = 0; gi < groups; gi++) {
            int from = START[gi];
            int to = gi + 1 < groups ? START[gi + 1] : glyphs;
            if (to > from) {
               ave.A().P().a(TEX[gi]);
               int c = COLOR[gi];
               int r = c & 0xFF;
               int g = c >> 8 & 0xFF;
               int b = c >> 16 & 0xFF;
               int a = c >>> 24;
               buf.a(7, bms.i);

               for (int i = from * 16; i < to * 16; i += 4) {
                  buf.b(verts[i], verts[i + 1], 0.0).a(verts[i + 2], verts[i + 3]).b(r, g, b, a).d();
               }

               tess.b();
            }
         }

         groups = 0;
         glyphs = 0;
         GL11.glColor4f((color & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color >> 16 & 0xFF) / 255.0F, (color >>> 24) / 255.0F);
      }
   }
}

package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.gfx.PulsarAtlases;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class LabelBatch189 {
   private static final int STRIDE = 28;
   private static final Map<String, LabelBatch189.Layout> LAYOUTS = new HashMap<>();
   private static final List<LabelBatch189.Group> GROUPS = new ArrayList<>();
   private static int groupCount;
   private static final RawStream189 BG = new RawStream189(4096);
   private static final jy MARK = new jy("pulsar", "mark");
   private static final int MARK_SIZE = 7;
   private static final int MARK_GAP = 2;
   private static final float MARK_SHIFT = -4.5F;
   private static int markTexture;
   private static LabelBatch189.Layout recording;
   private static int pass;
   private static final FloatBuffer MATRIX = BufferUtils.createFloatBuffer(16);
   private static final float[] M = new float[16];
   private static int lightS = 240;
   private static int lightT = 240;
   private static int frameLabels;
   private static int frameDraws;
   static volatile int lastLabels;
   static volatile int lastDraws;

   private static int markTexture() {
      if (markTexture == 0) {
         markTexture = PulsarAtlases.mark();
      }

      return markTexture;
   }

   static boolean ownsVbo(int id) {
      if (id == BG.vbo()) {
         return true;
      } else {
         for (LabelBatch189.Group g : GROUPS) {
            if (g.stream.vbo() == id) {
               return true;
            }
         }

         return false;
      }
   }

   public static void light(float s, float t) {
      lightS = Math.round(s);
      lightT = Math.round(t);
   }

   private LabelBatch189() {
   }

   public static boolean enabled() {
      return FrameFlags.labelBatch;
   }

   public static boolean capturing() {
      return recording != null;
   }

   public static void capture(avn font, String text, double d, double e, double f, float height, float yaw, float pitch, int k) {
      capture(font, text, d, e, f, height, yaw, pitch, k, false);
   }

   public static void capture(avn font, String text, double d, double e, double f, float height, float yaw, float pitch, int k, boolean mark) {
      LabelBatch189.Layout layout = text.indexOf("§k") >= 0 ? null : LAYOUTS.get(text);
      if (layout == null) {
         layout = new LabelBatch189.Layout();
         layout.halfWidth = font.a(text) / 2;
         recording = layout;
         pass = 1;
         font.a(text, -font.a(text) / 2, 0, 553648127);
         pass = 2;
         font.a(text, -font.a(text) / 2, 0, -1);
         recording = null;
         if (text.indexOf("§k") < 0) {
            if (LAYOUTS.size() > 4096) {
               LAYOUTS.clear();
            }

            LAYOUTS.put(text, layout);
         }
      }

      float j = 0.02666667F;
      System.arraycopy(MatrixMirror189.matrix(), 0, M, 0, 16);
      float tx = (float)d + 0.0F;
      float ty = (float)e + height + 0.5F;
      float tz = (float)f;
      M[12] = M[12] + (M[0] * tx + M[4] * ty + M[8] * tz);
      M[13] = M[13] + (M[1] * tx + M[5] * ty + M[9] * tz);
      M[14] = M[14] + (M[2] * tx + M[6] * ty + M[10] * tz);
      M[15] = M[15] + (M[3] * tx + M[7] * ty + M[11] * tz);
      MatrixMirror189.rotateInto(M, -yaw, 0.0F, 1.0F, 0.0F);
      MatrixMirror189.rotateInto(M, pitch, 1.0F, 0.0F, 0.0F);
      M[0] = M[0] * -j;
      M[1] = M[1] * -j;
      M[2] = M[2] * -j;
      M[3] = M[3] * -j;
      M[4] = M[4] * -j;
      M[5] = M[5] * -j;
      M[6] = M[6] * -j;
      M[7] = M[7] * -j;
      M[8] = M[8] * j;
      M[9] = M[9] * j;
      M[10] = M[10] * j;
      M[11] = M[11] * j;
      int lmS = lightS;
      int lmT = lightT;
      int l = layout.halfWidth;
      int markTex = mark ? markTexture() : 0;
      float dx = markTex != 0 ? -4.5F : 0.0F;
      float half = markTex != 0 ? l - -4.5F : l;
      int at = BG.reserve(16);
      int[] bgOut = BG.data;
      bg(bgOut, at, -half - 1.0F, -1 + k);
      bg(bgOut, at + 4, -half - 1.0F, 8 + k);
      bg(bgOut, at + 8, half + 1.0F, 8 + k);
      bg(bgOut, at + 12, half + 1.0F, -1 + k);
      float m0 = M[0];
      float m1 = M[1];
      float m2 = M[2];
      float m4 = M[4];
      float m5 = M[5];
      float m6 = M[6];
      float m12 = M[12];
      float m13 = M[13];
      float m14 = M[14];
      List<LabelBatch189.Run> runs = layout.runs;
      int r = 0;

      for (int n = runs.size(); r < n; r++) {
         LabelBatch189.Run run = runs.get(r);
         if (run.pass == 1) {
            LabelBatch189.Run twin = twin(runs, run);
            LabelBatch189.Group g1 = group(1, run.tex, lmS, lmT);
            int o1 = g1.stream.reserve(run.glyphs * 4 * 7);
            LabelBatch189.Group g2 = twin == null ? null : group(2, run.tex, lmS, lmT);
            int o2 = g2 == null ? 0 : g2.stream.reserve(run.glyphs * 4 * 7);
            int[] out1 = g1.stream.data;
            int[] out2 = g2 == null ? null : g2.stream.data;
            float[] v = run.v;
            int light = lmS & 65535 | (lmT & 65535) << 16;

            for (int gi = 0; gi < run.glyphs; gi++) {
               int c1 = run.color[gi];
               int c2 = twin == null ? 0 : twin.color[gi];

               for (int q = 0; q < 4; q++) {
                  int i = (gi * 4 + q) * 4;
                  float x = v[i] + dx;
                  float y = v[i + 1] + k;
                  int ex = Float.floatToRawIntBits(m0 * x + m4 * y + m12);
                  int ey = Float.floatToRawIntBits(m1 * x + m5 * y + m13);
                  int ez = Float.floatToRawIntBits(m2 * x + m6 * y + m14);
                  int tu = Float.floatToRawIntBits(v[i + 2]);
                  int tv = Float.floatToRawIntBits(v[i + 3]);
                  out1[o1] = ex;
                  out1[o1 + 1] = ey;
                  out1[o1 + 2] = ez;
                  out1[o1 + 3] = c1;
                  out1[o1 + 4] = tu;
                  out1[o1 + 5] = tv;
                  out1[o1 + 6] = light;
                  o1 += 7;
                  if (out2 != null) {
                     out2[o2] = ex;
                     out2[o2 + 1] = ey;
                     out2[o2 + 2] = ez;
                     out2[o2 + 3] = c2;
                     out2[o2 + 4] = tu;
                     out2[o2 + 5] = tv;
                     out2[o2 + 6] = light;
                     o2 += 7;
                  }
               }
            }
         }
      }

      if (markTex != 0) {
         mark(l + dx + 2.0F, k + 0.5F, lmS, lmT);
      }

      frameLabels++;
   }

   private static void mark(float x0, float y0, int lmS, int lmT) {
      float x1 = x0 + 7.0F;
      float y1 = y0 + 7.0F;
      int light = lmS & 65535 | (lmT & 65535) << 16;
      markQuad(group(1, MARK, lmS, lmT), x0, y0, x1, y1, 553648127, light);
      markQuad(group(2, MARK, lmS, lmT), x0, y0, x1, y1, -1, light);
   }

   private static void markQuad(LabelBatch189.Group g, float x0, float y0, float x1, float y1, int color, int light) {
      int o = g.stream.reserve(28);
      int[] out = g.stream.data;
      o = markVertex(out, o, x0, y0, 0.0F, 0.0F, color, light);
      o = markVertex(out, o, x0, y1, 0.0F, 1.0F, color, light);
      o = markVertex(out, o, x1, y1, 1.0F, 1.0F, color, light);
      markVertex(out, o, x1, y0, 1.0F, 0.0F, color, light);
   }

   private static int markVertex(int[] out, int o, float x, float y, float u, float v, int color, int light) {
      out[o] = Float.floatToRawIntBits(M[0] * x + M[4] * y + M[12]);
      out[o + 1] = Float.floatToRawIntBits(M[1] * x + M[5] * y + M[13]);
      out[o + 2] = Float.floatToRawIntBits(M[2] * x + M[6] * y + M[14]);
      out[o + 3] = color;
      out[o + 4] = Float.floatToRawIntBits(u);
      out[o + 5] = Float.floatToRawIntBits(v);
      out[o + 6] = light;
      return o + 7;
   }

   private static LabelBatch189.Run twin(List<LabelBatch189.Run> runs, LabelBatch189.Run first) {
      int i = 0;

      for (int n = runs.size(); i < n; i++) {
         LabelBatch189.Run c = runs.get(i);
         if (c.pass == 2 && c.tex == first.tex && c.glyphs == first.glyphs) {
            return c;
         }
      }

      return null;
   }

   private static void bg(int[] b, int at, float x, float y) {
      b[at] = Float.floatToRawIntBits(M[0] * x + M[4] * y + M[12]);
      b[at + 1] = Float.floatToRawIntBits(M[1] * x + M[5] * y + M[13]);
      b[at + 2] = Float.floatToRawIntBits(M[2] * x + M[6] * y + M[14]);
      b[at + 3] = 1073741824;
   }

   private static LabelBatch189.Group group(int pass, jy tex, int lmS, int lmT) {
      for (int i = groupCount - 1; i >= 0; i--) {
         LabelBatch189.Group c = GROUPS.get(i);
         if (c.pass == pass && c.tex == tex && c.lmS == lmS && c.lmT == lmT) {
            return c;
         }
      }

      if (groupCount == GROUPS.size()) {
         GROUPS.add(new LabelBatch189.Group());
      }

      LabelBatch189.Group g = GROUPS.get(groupCount++);
      g.pass = pass;
      g.tex = tex;
      g.lmS = lmS;
      g.lmT = lmT;
      g.stream.reset();
      return g;
   }

   public static void glyph(
      jy tex,
      int color,
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
      LabelBatch189.Layout layout = recording;
      LabelBatch189.Run run = null;

      for (int i = layout.runs.size() - 1; i >= 0; i--) {
         LabelBatch189.Run c = layout.runs.get(i);
         if (c.pass == pass && c.tex == tex) {
            run = c;
            break;
         }
      }

      if (run == null) {
         run = new LabelBatch189.Run();
         run.pass = pass;
         run.tex = tex;
         layout.runs.add(run);
      }

      if ((run.glyphs + 1) * 16 > run.v.length) {
         run.v = Arrays.copyOf(run.v, run.v.length * 2);
         run.color = Arrays.copyOf(run.color, run.color.length * 2);
      }

      float[] v = run.v;
      int o = run.glyphs * 16;
      v[o] = x0;
      v[o + 1] = y0;
      v[o + 2] = u0;
      v[o + 3] = v0;
      v[o + 4] = x1;
      v[o + 5] = y1;
      v[o + 6] = u1;
      v[o + 7] = v1;
      v[o + 8] = x3;
      v[o + 9] = y3;
      v[o + 10] = u3;
      v[o + 11] = v3;
      v[o + 12] = x2;
      v[o + 13] = y2;
      v[o + 14] = u2;
      v[o + 15] = v2;
      run.color[run.glyphs++] = color;
   }

   public static void flush() {
      if (BG.size != 0 || groupCount != 0) {
         if (FrameFlags.clientArrays) {
            ArrayBufferGuard189.beforeClientArrays();
         }

         bfl.E();
         bfl.D();
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         bfl.f();
         bfl.a(false);
         bfl.i();
         bfl.l();
         bfl.a(770, 771, 1, 0);
         GL11.glEnableClientState(32884);
         GL11.glEnableClientState(32886);
         if (BG.size > 0) {
            bfl.x();
            int vertices = BG.size / 4;
            if (FrameFlags.clientArrays) {
               ByteBuffer b = BG.clientBytes();
               GL11.glVertexPointer(3, 5126, 16, b);
               b.position(12);
               GL11.glColorPointer(4, 5121, 16, b);
            } else {
               BG.bindAndUpload();
               GL11.glVertexPointer(3, 5126, 16, 0L);
               GL11.glColorPointer(4, 5121, 16, 12L);
            }

            GL11.glDrawArrays(7, 0, vertices);
            bfl.w();
            BG.reset();
            frameDraws++;
         }

         GL11.glEnableClientState(32888);
         bqs.l(bqs.r);
         GL11.glEnableClientState(32888);
         bqs.l(bqs.q);
         drawPass(1);
         bfl.j();
         bfl.a(true);
         drawPass(2);
         bqs.l(bqs.r);
         GL11.glDisableClientState(32888);
         bqs.l(bqs.q);
         GL11.glDisableClientState(32888);
         GL11.glDisableClientState(32886);
         GL11.glDisableClientState(32884);
         bqs.g(bqs.R, 0);
         bfl.e();
         bfl.k();
         bfl.G();
         bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
         bfl.F();
         lastLabels = frameLabels;
         lastDraws = frameDraws;
         frameLabels = 0;
         frameDraws = 0;
         groupCount = 0;
      }
   }

   private static void drawPass(int which) {
      bmj textures = ave.A().P();

      for (int gi = 0; gi < groupCount; gi++) {
         LabelBatch189.Group g = GROUPS.get(gi);
         if (g.pass == which && g.stream.size != 0) {
            if (g.tex == MARK) {
               bfl.i(markTexture());
            } else {
               textures.a(g.tex);
            }

            int vertices = g.stream.size / 7;
            if (FrameFlags.clientArrays) {
               ByteBuffer b = g.stream.clientBytes();
               GL11.glVertexPointer(3, 5126, 28, b);
               b.position(12);
               GL11.glColorPointer(4, 5121, 28, b);
               b.position(16);
               GL11.glTexCoordPointer(2, 5126, 28, b);
               bqs.l(bqs.r);
               b.position(24);
               GL11.glTexCoordPointer(2, 5122, 28, b);
               bqs.l(bqs.q);
            } else {
               g.stream.bindAndUpload();
               TerrainRegions189.pointers();
            }

            GL11.glDrawArrays(7, 0, vertices);
            frameDraws++;
         }
      }
   }

   public static void clear() {
      LAYOUTS.clear();
   }

   private static final class Group {
      int pass;
      jy tex;
      int lmS;
      int lmT;
      final RawStream189 stream = new RawStream189(16384);
   }

   private static final class Layout {
      int halfWidth;
      final List<LabelBatch189.Run> runs = new ArrayList<>();
   }

   private static final class Run {
      int pass;
      jy tex;
      float[] v = new float[128];
      int[] color = new int[8];
      int glyphs;
   }
}

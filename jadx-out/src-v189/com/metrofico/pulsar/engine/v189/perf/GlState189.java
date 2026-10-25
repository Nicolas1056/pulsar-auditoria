package com.metrofico.pulsar.engine.v189.perf;

import java.util.Arrays;
import org.lwjgl.opengl.GL11;

public final class GlState189 {
   public static final int SIZE = 24;
   private static final int TEX = 0;
   private static final int R = 1;
   private static final int G = 2;
   private static final int B = 3;
   private static final int A = 4;
   private static final int BLEND = 5;
   private static final int SRC = 6;
   private static final int DST = 7;
   private static final int SRCA = 8;
   private static final int DSTA = 9;
   private static final int ALPHA = 10;
   private static final int AFUNC = 11;
   private static final int AREF = 12;
   private static final int CULL = 13;
   private static final int LIGHT = 14;
   private static final int DEPTH = 15;
   private static final int DMASK = 16;
   private static final int DFUNC = 17;
   private static final int RESCALE = 18;
   private static final int NORMALIZE = 19;
   private static final int TEX2D = 20;
   private static final int POLY = 21;
   private static final int PFACTOR = 22;
   private static final int PUNITS = 23;
   private static final int[] cur = new int[24];
   public static int activeUnit;

   private GlState189() {
   }

   public static int boundTexture() {
      return cur[0];
   }

   public static int packedColor() {
      return channel(cur[4]) << 24 | channel(cur[3]) << 16 | channel(cur[2]) << 8 | channel(cur[1]);
   }

   private static int channel(int bits) {
      float f = Float.intBitsToFloat(bits);
      return f < 0.0F ? 255 : Math.min(255, Math.round(f * 255.0F));
   }

   public static boolean lightingEnabled() {
      return cur[14] != 0;
   }

   public static boolean depthTestEnabled() {
      return cur[15] != 0;
   }

   public static boolean blendEnabled() {
      return cur[5] != 0;
   }

   public static boolean depthMaskEnabled() {
      return cur[16] != 0;
   }

   public static int blendSrc() {
      return cur[6];
   }

   public static int blendDst() {
      return cur[7];
   }

   public static int blendSrcAlpha() {
      return cur[8];
   }

   public static int blendDstAlpha() {
      return cur[9];
   }

   public static void snapshot(int[] into) {
      System.arraycopy(cur, 0, into, 0, 24);
   }

   public static boolean opaque(int[] s) {
      return s[5] == 0 && s[15] != 0 && s[16] != 0;
   }

   public static boolean equalDepthOverlay(int[] s) {
      return s[5] != 0 && s[15] != 0 && s[16] == 0 && s[17] == 514;
   }

   public static String describe(int[] s) {
      return "blend " + s[5] + " (" + s[6] + "," + s[7] + ") depth " + s[15] + " mask " + s[16] + " func " + s[17] + " tex " + s[0];
   }

   public static boolean same(int[] snap) {
      return Arrays.equals(cur, snap);
   }

   public static void apply(int[] s) {
      bfl.i(s[0]);
      bfl.c(Float.intBitsToFloat(s[1]), Float.intBitsToFloat(s[2]), Float.intBitsToFloat(s[3]), Float.intBitsToFloat(s[4]));
      if (s[5] != 0) {
         bfl.l();
      } else {
         bfl.k();
      }

      bfl.a(s[6], s[7], s[8], s[9]);
      if (s[10] != 0) {
         bfl.d();
      } else {
         bfl.c();
      }

      bfl.a(s[11], Float.intBitsToFloat(s[12]));
      if (s[13] != 0) {
         bfl.o();
      } else {
         bfl.p();
      }

      if (s[14] != 0) {
         bfl.e();
      } else {
         bfl.f();
      }

      if (s[15] != 0) {
         bfl.j();
      } else {
         bfl.i();
      }

      bfl.a(s[16] != 0);
      bfl.c(s[17]);
      if (s[18] != 0) {
         bfl.B();
      } else {
         bfl.C();
      }

      if (s[19] != 0) {
         bfl.z();
      } else {
         bfl.A();
      }

      if (s[20] != 0) {
         bfl.w();
      } else {
         bfl.x();
      }

      if (s[21] != 0) {
         bfl.q();
      } else {
         bfl.r();
      }

      bfl.a(Float.intBitsToFloat(s[22]), Float.intBitsToFloat(s[23]));
   }

   public static void activeTexture(int unit) {
      activeUnit = unit;
   }

   public static void bindTexture(int id) {
      if (unit0()) {
         cur[0] = id;
      }
   }

   public static void color(float r, float g, float b, float a) {
      cur[1] = Float.floatToRawIntBits(r);
      cur[2] = Float.floatToRawIntBits(g);
      cur[3] = Float.floatToRawIntBits(b);
      cur[4] = Float.floatToRawIntBits(a);
   }

   public static void clearColor() {
      color(-1.0F, -1.0F, -1.0F, -1.0F);
   }

   public static void reaffirmColor() {
      float r = Float.intBitsToFloat(cur[1]);
      if (r < 0.0F) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         GL11.glColor4f(r, Float.intBitsToFloat(cur[2]), Float.intBitsToFloat(cur[3]), Float.intBitsToFloat(cur[4]));
      }
   }

   public static void blend(boolean on) {
      cur[5] = on ? 1 : 0;
   }

   public static void blendFunc(int src, int dst, int srcA, int dstA) {
      cur[6] = src;
      cur[7] = dst;
      cur[8] = srcA;
      cur[9] = dstA;
   }

   public static void alphaTest(boolean on) {
      cur[10] = on ? 1 : 0;
   }

   public static void alphaFunc(int func, float ref) {
      cur[11] = func;
      cur[12] = Float.floatToRawIntBits(ref);
   }

   public static void cull(boolean on) {
      cur[13] = on ? 1 : 0;
   }

   public static void lighting(boolean on) {
      cur[14] = on ? 1 : 0;
   }

   public static void depthTest(boolean on) {
      cur[15] = on ? 1 : 0;
   }

   public static void depthMask(boolean on) {
      cur[16] = on ? 1 : 0;
   }

   public static void depthFunc(int func) {
      cur[17] = func;
   }

   public static void rescale(boolean on) {
      cur[18] = on ? 1 : 0;
   }

   public static void normalize(boolean on) {
      cur[19] = on ? 1 : 0;
   }

   public static void texture2d(boolean on) {
      if (unit0()) {
         cur[20] = on ? 1 : 0;
      } else if (!on) {
         EntityCache189.unsupported("textura apagada en otra unidad");
      }
   }

   public static void polyOffset(boolean on) {
      cur[21] = on ? 1 : 0;
   }

   public static void polygonOffset(float factor, float units) {
      cur[22] = Float.floatToRawIntBits(factor);
      cur[23] = Float.floatToRawIntBits(units);
   }

   private static boolean unit0() {
      return activeUnit == bqs.q || activeUnit == 0;
   }

   static {
      cur[1] = cur[2] = cur[3] = cur[4] = Float.floatToRawIntBits(1.0F);
      cur[6] = 1;
      cur[7] = 0;
      cur[8] = 1;
      cur[9] = 0;
      cur[11] = 519;
      cur[12] = Float.floatToRawIntBits(-1.0F);
      cur[16] = 1;
      cur[17] = 513;
      cur[20] = 1;
   }
}

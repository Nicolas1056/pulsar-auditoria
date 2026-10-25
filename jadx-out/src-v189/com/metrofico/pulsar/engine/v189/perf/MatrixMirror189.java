package com.metrofico.pulsar.engine.v189.perf;

import java.nio.FloatBuffer;
import java.util.Arrays;
import org.lwjgl.BufferUtils;

public final class MatrixMirror189 {
   private static final int MODELVIEW = 5888;
   private static float[] cur = new float[16];
   private static final float[][] STACK = new float[64][16];
   private static int sp;
   private static int mode = 5888;
   private static boolean valid;
   private static final FloatBuffer SYNC = BufferUtils.createFloatBuffer(16);
   static volatile int syncs;

   private MatrixMirror189() {
   }

   public static float[] matrix() {
      if (!valid) {
         sync();
      }

      return cur;
   }

   private static void sync() {
      SYNC.clear();
      bfl.a(2982, SYNC);
      SYNC.position(0);
      SYNC.get(cur);
      sp = 0;
      valid = true;
      syncs++;
   }

   public static void frame() {
      valid = false;
   }

   public static void matrixMode(int m) {
      mode = m;
   }

   public static void push() {
      if (mode == 5888 && valid) {
         if (sp == STACK.length) {
            valid = false;
         } else {
            System.arraycopy(cur, 0, STACK[sp++], 0, 16);
         }
      }
   }

   public static void pop() {
      if (mode == 5888 && valid) {
         if (sp == 0) {
            valid = false;
         } else {
            System.arraycopy(STACK[--sp], 0, cur, 0, 16);
         }
      }
   }

   public static void loadIdentity() {
      if (mode == 5888) {
         Arrays.fill(cur, 0.0F);
         cur[0] = cur[5] = cur[10] = cur[15] = 1.0F;
         if (!valid) {
            sp = 0;
            valid = true;
         }
      }
   }

   public static void translate(float x, float y, float z) {
      if (mode == 5888 && valid) {
         float[] m = cur;
         m[12] += m[0] * x + m[4] * y + m[8] * z;
         m[13] += m[1] * x + m[5] * y + m[9] * z;
         m[14] += m[2] * x + m[6] * y + m[10] * z;
         m[15] += m[3] * x + m[7] * y + m[11] * z;
      }
   }

   public static void scale(float x, float y, float z) {
      if (mode == 5888 && valid) {
         float[] m = cur;
         m[0] *= x;
         m[1] *= x;
         m[2] *= x;
         m[3] *= x;
         m[4] *= y;
         m[5] *= y;
         m[6] *= y;
         m[7] *= y;
         m[8] *= z;
         m[9] *= z;
         m[10] *= z;
         m[11] *= z;
      }
   }

   public static void rotate(float angle, float x, float y, float z) {
      if (mode == 5888 && valid) {
         rotateInto(cur, angle, x, y, z);
      }
   }

   static void rotateInto(float[] m, float angle, float x, float y, float z) {
      float len = (float)Math.sqrt(x * x + y * y + z * z);
      if (!(len < 1.0E-12F)) {
         x /= len;
         y /= len;
         z /= len;
         double rad = Math.toRadians(angle);
         float c = (float)Math.cos(rad);
         float s = (float)Math.sin(rad);
         float t = 1.0F - c;
         float r00 = x * x * t + c;
         float r01 = x * y * t - z * s;
         float r02 = x * z * t + y * s;
         float r10 = y * x * t + z * s;
         float r11 = y * y * t + c;
         float r12 = y * z * t - x * s;
         float r20 = z * x * t - y * s;
         float r21 = z * y * t + x * s;
         float r22 = z * z * t + c;

         for (int i = 0; i < 4; i++) {
            float a = m[i];
            float b = m[4 + i];
            float d = m[8 + i];
            m[i] = a * r00 + b * r10 + d * r20;
            m[4 + i] = a * r01 + b * r11 + d * r21;
            m[8 + i] = a * r02 + b * r12 + d * r22;
         }
      }
   }

   public static void mult(FloatBuffer other) {
      if (mode == 5888 && valid) {
         float[] o = new float[16];
         int p = other.position();

         for (int i = 0; i < 16; i++) {
            o[i] = other.get(p + i);
         }

         float[] m = cur;
         float[] r = new float[16];

         for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
               r[col * 4 + row] = m[row] * o[col * 4] + m[4 + row] * o[col * 4 + 1] + m[8 + row] * o[col * 4 + 2] + m[12 + row] * o[col * 4 + 3];
            }
         }

         cur = r;
      }
   }

   public static void unknown() {
      if (mode == 5888) {
         valid = false;
      }
   }
}

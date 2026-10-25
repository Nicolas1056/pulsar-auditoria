package com.metrofico.pulsar.api.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class CompactVertex {
   public static final int STRIDE = 16;
   public static final float POS_MIN = -8.0F;
   public static final float POS_RANGE = 32.0F;
   public static final int POS_UNITS = 2048;

   private CompactVertex() {
   }

   public static int encodePos(float v) {
      int q = Math.round((v - -8.0F) * 2048.0F);
      return q < 0 ? 0 : Math.min(q, 65535);
   }

   public static float decodePos(int q) {
      return q / 2048.0F + -8.0F;
   }

   public static int encodeUv(float u, float c) {
      float q = u * 65535.0F;
      int r = u < c ? (int)Math.ceil(q - 1.0E-4F) : (int)Math.floor(q + 1.0E-4F);
      return r < 0 ? 0 : Math.min(r, 65535);
   }

   public static float decodeUv(int q) {
      return q / 65535.0F;
   }

   public static int encode(ByteBuffer buffer, int vertexCount, int stride, int posOff, int colorOff, int uvOff, int uv2Off) {
      if (vertexCount % 4 == 0 && stride >= 16) {
         ByteBuffer b = buffer.duplicate().order(ByteOrder.nativeOrder());
         int base = b.position();
         int[] px = new int[4];
         int[] py = new int[4];
         int[] pz = new int[4];
         int[] light = new int[4];
         int[] color = new int[4];
         int[] pu = new int[4];
         int[] pv = new int[4];
         float[] u = new float[4];
         float[] v = new float[4];

         for (int q = 0; q < vertexCount / 4; q++) {
            float cu = 0.0F;
            float cv = 0.0F;

            for (int i = 0; i < 4; i++) {
               int o = base + (q * 4 + i) * stride;
               px[i] = encodePos(b.getFloat(o + posOff));
               py[i] = encodePos(b.getFloat(o + posOff + 4));
               pz[i] = encodePos(b.getFloat(o + posOff + 8));
               color[i] = b.getInt(o + colorOff);
               u[i] = b.getFloat(o + uvOff);
               v[i] = b.getFloat(o + uvOff + 4);
               cu += u[i];
               cv += v[i];
               int lb = b.getShort(o + uv2Off) & '\uffff';
               int ls = b.getShort(o + uv2Off + 2) & '\uffff';
               light[i] = Math.min(lb, 255) | Math.min(ls, 255) << 8;
            }

            cu *= 0.25F;
            cv *= 0.25F;

            for (int i = 0; i < 4; i++) {
               pu[i] = encodeUv(u[i], cu);
               pv[i] = encodeUv(v[i], cv);
            }

            for (int i = 0; i < 4; i++) {
               int o = base + (q * 4 + i) * 16;
               b.putShort(o, (short)px[i]);
               b.putShort(o + 2, (short)py[i]);
               b.putShort(o + 4, (short)pz[i]);
               b.putShort(o + 6, (short)light[i]);
               b.putInt(o + 8, color[i]);
               b.putShort(o + 12, (short)pu[i]);
               b.putShort(o + 14, (short)pv[i]);
            }
         }

         return vertexCount * 16;
      } else {
         throw new IllegalArgumentException("mesh de quads con stride >= 16 esperada");
      }
   }
}

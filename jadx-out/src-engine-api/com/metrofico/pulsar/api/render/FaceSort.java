package com.metrofico.pulsar.api.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class FaceSort {
   public static final int GROUPS = 7;
   public static final int WEST = 0;
   public static final int EAST = 1;
   public static final int DOWN = 2;
   public static final int UP = 3;
   public static final int NORTH = 4;
   public static final int SOUTH = 5;
   public static final int UNASSIGNED = 6;
   private static final float MIN_AXIS_DOMINANCE = 0.9F;

   private FaceSort() {
   }

   public static int group(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2) {
      float ax = x1 - x0;
      float ay = y1 - y0;
      float az = z1 - z0;
      float bx = x2 - x0;
      float by = y2 - y0;
      float bz = z2 - z0;
      float nx = ay * bz - az * by;
      float ny = az * bx - ax * bz;
      float nz = ax * by - ay * bx;
      float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
      if (len < 1.0E-6F) {
         return 6;
      } else {
         float ax_ = Math.abs(nx);
         float ay_ = Math.abs(ny);
         float az_ = Math.abs(nz);
         float dominance = 0.9F * len;
         if (ax_ >= dominance) {
            return nx > 0.0F ? 1 : 0;
         } else if (ay_ >= dominance) {
            return ny > 0.0F ? 3 : 2;
         } else if (az_ >= dominance) {
            return nz > 0.0F ? 5 : 4;
         } else {
            return 6;
         }
      }
   }

   public static int[] sort(ByteBuffer buffer, int vertexCount, int stride) {
      if (vertexCount > 0 && vertexCount % 4 == 0 && stride >= 12) {
         int quads = vertexCount / 4;
         int quadBytes = stride * 4;
         int base = buffer.position();
         ByteBuffer b = buffer.duplicate().order(ByteOrder.nativeOrder());
         int[] groupOf = new int[quads];
         int[] counts = new int[7];

         for (int q = 0; q < quads; q++) {
            int o = base + q * quadBytes;
            int g = group(
               b.getFloat(o),
               b.getFloat(o + 4),
               b.getFloat(o + 8),
               b.getFloat(o + stride),
               b.getFloat(o + stride + 4),
               b.getFloat(o + stride + 8),
               b.getFloat(o + 2 * stride),
               b.getFloat(o + 2 * stride + 4),
               b.getFloat(o + 2 * stride + 8)
            );
            groupOf[q] = g;
            counts[g]++;
         }

         boolean ordered = true;

         for (int q = 1; q < quads && ordered; q++) {
            ordered = groupOf[q] >= groupOf[q - 1];
         }

         if (ordered) {
            return counts;
         } else {
            int[] start = new int[7];

            for (int g = 1; g < 7; g++) {
               start[g] = start[g - 1] + counts[g - 1];
            }

            byte[] tmp = new byte[quads * quadBytes];
            int[] next = (int[])start.clone();

            for (int q = 0; q < quads; q++) {
               int dst = next[groupOf[q]]++ * quadBytes;
               b.get(base + q * quadBytes, tmp, dst, quadBytes);
            }

            b.put(base, tmp, 0, tmp.length);
            return counts;
         }
      } else {
         return null;
      }
   }

   public static int visibleMask(double camX, double camY, double camZ, int ox, int oy, int oz) {
      int mask = 64;
      if (camX > ox) {
         mask |= 2;
      }

      if (camX < ox + 16) {
         mask |= 1;
      }

      if (camY > oy) {
         mask |= 8;
      }

      if (camY < oy + 16) {
         mask |= 4;
      }

      if (camZ > oz) {
         mask |= 32;
      }

      if (camZ < oz + 16) {
         mask |= 16;
      }

      return mask;
   }
}

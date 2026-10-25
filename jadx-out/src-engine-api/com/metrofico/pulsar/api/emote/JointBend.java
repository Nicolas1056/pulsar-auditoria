package com.metrofico.pulsar.api.emote;

public final class JointBend {
   private JointBend() {
   }

   public static boolean bent(float xRot, float yRot, float zRot) {
      return xRot != 0.0F || yRot != 0.0F || zRot != 0.0F;
   }

   public static void half(float xRot, float yRot, float zRot, float[] h) {
      double sa = Math.sin(xRot / 2.0F);
      double ca = Math.cos(xRot / 2.0F);
      double sb = Math.sin(yRot / 2.0F);
      double cb = Math.cos(yRot / 2.0F);
      double sc = Math.sin(zRot / 2.0F);
      double cc = Math.cos(zRot / 2.0F);
      double ax = -sc * sb;
      double ay = cc * sb;
      double az = cb * sc;
      double aw = cc * cb;
      double x = aw * sa + ca * ax;
      double y = ca * ay + az * sa;
      double z = ca * az - ay * sa;
      double w = aw * ca - ax * sa;
      if (w < 0.0) {
         x = -x;
         y = -y;
         z = -z;
         w = -w;
      }

      double len = Math.sqrt(x * x + y * y + z * z + ++w * w);
      x /= len;
      y /= len;
      z /= len;
      w /= len;
      h[0] = (float)(1.0 - 2.0 * (y * y + z * z));
      h[1] = (float)(2.0 * (x * y - z * w));
      h[2] = (float)(2.0 * (x * z + y * w));
      h[3] = (float)(2.0 * (x * y + z * w));
      h[4] = (float)(1.0 - 2.0 * (x * x + z * z));
      h[5] = (float)(2.0 * (y * z - x * w));
      h[6] = (float)(2.0 * (x * z - y * w));
      h[7] = (float)(2.0 * (y * z + x * w));
      h[8] = (float)(1.0 - 2.0 * (x * x + y * y));
   }

   public static void upper(float[] h, float px, float py, float pz, float x, float y, float z, float[] out, int o) {
      float dx = x - px;
      float dy = y - py;
      float dz = z - pz;
      out[o] = px + h[0] * dx + h[1] * dy + h[2] * dz;
      out[o + 1] = py + h[3] * dx + h[4] * dy + h[5] * dz;
      out[o + 2] = pz + h[6] * dx + h[7] * dy + h[8] * dz;
   }

   public static void lower(float[] h, float x, float y, float z, float[] out, int o) {
      out[o] = h[0] * x + h[3] * y + h[6] * z;
      out[o + 1] = h[1] * x + h[4] * y + h[7] * z;
      out[o + 2] = h[2] * x + h[5] * y + h[8] * z;
   }
}

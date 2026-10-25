package com.metrofico.pulsar.api.perf;

public final class FrustumMath {
   private FrustumMath() {
   }

   public static boolean inside(double[] planes, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      for (int p = 0; p < 24; p += 4) {
         double a = planes[p];
         double b = planes[p + 1];
         double c = planes[p + 2];
         double px = a >= 0.0 ? maxX : minX;
         double py = b >= 0.0 ? maxY : minY;
         double pz = c >= 0.0 ? maxZ : minZ;
         if (!(a * px + b * py + c * pz + planes[p + 3] > 0.0)) {
            return false;
         }
      }

      return true;
   }

   public static boolean insideEightCorners(double[] planes, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      for (int p = 0; p < 24; p += 4) {
         boolean any = false;

         for (int i = 0; i < 8 && !any; i++) {
            double x = (i & 1) == 0 ? minX : maxX;
            double y = (i & 2) == 0 ? minY : maxY;
            double z = (i & 4) == 0 ? minZ : maxZ;
            any = planes[p] * x + planes[p + 1] * y + planes[p + 2] * z + planes[p + 3] > 0.0;
         }

         if (!any) {
            return false;
         }
      }

      return true;
   }
}

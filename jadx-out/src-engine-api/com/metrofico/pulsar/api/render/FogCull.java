package com.metrofico.pulsar.api.render;

public final class FogCull {
   private FogCull() {
   }

   static double axis(double c, double lo, double hi) {
      return c < lo ? lo - c : (c > hi ? c - hi : 0.0);
   }

   public static boolean fullyFogged(
      double camX, double camY, double camZ, int ox, int oy, int oz, int size, float renderDistanceEnd, float environmentalEnd, float fogAlpha, float margin
   ) {
      if (!(fogAlpha >= 0.999F)) {
         return false;
      } else {
         double dx = axis(camX, ox, ox + size);
         double dy = axis(camY, oy, oy + size);
         double dz = axis(camZ, oz, oz + size);
         double cylindrical = Math.max(Math.sqrt(dx * dx + dz * dz), dy);
         if (renderDistanceEnd > 0.0F && cylindrical >= renderDistanceEnd + margin) {
            return true;
         } else {
            double spherical = Math.sqrt(dx * dx + dy * dy + dz * dz);
            return environmentalEnd > 0.0F && spherical >= environmentalEnd + margin;
         }
      }
   }
}

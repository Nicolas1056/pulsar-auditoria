package com.metrofico.pulsar.api.world;

import java.util.Optional;

public final class Projection {
   private Projection() {
   }

   public static Optional<Projection.Point> project(
      double px,
      double py,
      double pz,
      double camX,
      double camY,
      double camZ,
      double qx,
      double qy,
      double qz,
      double qw,
      double fovDegrees,
      int width,
      int height
   ) {
      double dx = px - camX;
      double dy = py - camY;
      double dz = pz - camZ;
      double cx = -qx;
      double cy = -qy;
      double cz = -qz;
      double tx = 2.0 * (cy * dz - cz * dy);
      double ty = 2.0 * (cz * dx - cx * dz);
      double tz = 2.0 * (cx * dy - cy * dx);
      double vx = dx + qw * tx + (cy * tz - cz * ty);
      double vy = dy + qw * ty + (cz * tx - cx * tz);
      double vz = dz + qw * tz + (cx * ty - cy * tx);
      if (vz >= -0.05) {
         return Optional.empty();
      } else {
         double f = height / 2.0 / Math.tan(Math.toRadians(fovDegrees) / 2.0);
         double sx = width / 2.0 + vx / -vz * f;
         double sy = height / 2.0 - vy / -vz * f;
         return Optional.of(new Projection.Point(sx, sy, Math.sqrt(dx * dx + dy * dy + dz * dz)));
      }
   }

   public static double yawTo(double camX, double camZ, double px, double pz) {
      return Math.toDegrees(Math.atan2(-(px - camX), pz - camZ));
   }

   public static double wrap(double degrees) {
      double d = degrees % 360.0;
      if (d > 180.0) {
         d -= 360.0;
      } else if (d <= -180.0) {
         d += 360.0;
      }

      return d;
   }

   public record Point(double x, double y, double distance) {
   }
}

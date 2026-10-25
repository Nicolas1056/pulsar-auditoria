package com.metrofico.pulsar.api.camera;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record CameraPath(String id, List<CameraPath.Key> keys, long durationMs, boolean skippable, boolean hideHud, boolean hideSelf, String ease) {
   public static final int MAX_KEYS = 128;
   public static final long MAX_DURATION_MS = 120000L;

   public static CameraPath from(Map<String, Object> f) {
      String id = f.get("cinematic") instanceof String s && !s.isEmpty() ? s : null;
      if (id != null && f.get("keys") instanceof List<?> rawKeys && !rawKeys.isEmpty()) {
         long duration = f.get("duration") instanceof Number n ? Math.max(100L, Math.min(120000L, n.longValue())) : 5000L;
         List<CameraPath.Key> keys = new ArrayList<>();

         for (Object o : rawKeys) {
            if (o instanceof Map<?, ?> m && keys.size() < 128) {
               if (!(m.get("x") instanceof Number kx) || !(m.get("y") instanceof Number ky) || !(m.get("z") instanceof Number kz)) {
                  return null;
               }

               Double yaw = m.get("yaw") instanceof Number nx ? nx.doubleValue() : null;
               Double pitch = m.get("pitch") instanceof Number nxx ? nxx.doubleValue() : null;
               double[] lookAt = null;
               if (m.get("lookAt") instanceof List<?> la && la.size() == 3 && la.stream().allMatch(v -> v instanceof Number)) {
                  lookAt = new double[]{((Number)la.get(0)).doubleValue(), ((Number)la.get(1)).doubleValue(), ((Number)la.get(2)).doubleValue()};
               }

               double t = m.get("t") instanceof Number nxxx ? Math.max(0.0, Math.min(1.0, nxxx.doubleValue())) : -1.0;
               keys.add(new CameraPath.Key(kx.doubleValue(), ky.doubleValue(), kz.doubleValue(), yaw, pitch, lookAt, t));
            }
         }

         if (keys.size() < 2) {
            return null;
         } else {
            List<CameraPath.Key> timed = new ArrayList<>(keys.size());

            for (int i = 0; i < keys.size(); i++) {
               CameraPath.Key k = keys.get(i);
               double t = k.t() >= 0.0 ? k.t() : (double)i / (keys.size() - 1);
               if (!timed.isEmpty() && t <= timed.get(timed.size() - 1).t()) {
                  t = timed.get(timed.size() - 1).t() + 1.0E-6;
               }

               timed.add(new CameraPath.Key(k.x(), k.y(), k.z(), k.yaw(), k.pitch(), k.lookAt(), Math.min(1.0, t)));
            }

            boolean skippable = !(f.get("skippable") instanceof Boolean b && !b);
            boolean hideHud = !(f.get("hideHud") instanceof Boolean bx && !bx);
            boolean hideSelf = f.get("hideSelf") instanceof Boolean bxx && bxx;
            String ease = f.get("ease") instanceof String sx ? sx : "smooth";
            return new CameraPath(id, List.copyOf(timed), duration, skippable, hideHud, hideSelf, ease);
         }
      } else {
         return null;
      }
   }

   public double[] at(double progress) {
      double p = this.ease.equals("linear") ? progress : progress * progress * (3.0 - 2.0 * progress);
      int n = this.keys.size();
      int seg = 0;

      while (seg < n - 2 && p > this.keys.get(seg + 1).t()) {
         seg++;
      }

      CameraPath.Key a = this.keys.get(seg);
      CameraPath.Key b = this.keys.get(seg + 1);
      double span = Math.max(1.0E-9, b.t() - a.t());
      double u = Math.max(0.0, Math.min(1.0, (p - a.t()) / span));
      CameraPath.Key p0 = this.keys.get(Math.max(0, seg - 1));
      CameraPath.Key p3 = this.keys.get(Math.min(n - 1, seg + 2));
      double x = catmull(p0.x(), a.x(), b.x(), p3.x(), u);
      double y = catmull(p0.y(), a.y(), b.y(), p3.y(), u);
      double z = catmull(p0.z(), a.z(), b.z(), p3.z(), u);
      double[] lookA = angles(a, x, y, z);
      double[] lookB = angles(b, x, y, z);
      double yaw = lerpAngle(lookA[0], lookB[0], u);
      double pitch = lerp(lookA[1], lookB[1], u);
      if (a.lookAt() != null && b.lookAt() != null) {
         double tx = lerp(a.lookAt()[0], b.lookAt()[0], u);
         double ty = lerp(a.lookAt()[1], b.lookAt()[1], u);
         double tz = lerp(a.lookAt()[2], b.lookAt()[2], u);
         double[] la = lookAngles(x, y, z, tx, ty, tz);
         yaw = la[0];
         pitch = la[1];
      }

      return new double[]{x, y, z, yaw, pitch};
   }

   private static double[] angles(CameraPath.Key k, double fromX, double fromY, double fromZ) {
      return k.lookAt() != null
         ? lookAngles(k.x(), k.y(), k.z(), k.lookAt()[0], k.lookAt()[1], k.lookAt()[2])
         : new double[]{k.yaw() != null ? k.yaw() : 0.0, k.pitch() != null ? k.pitch() : 0.0};
   }

   public static double[] lookAngles(double x, double y, double z, double tx, double ty, double tz) {
      double dx = tx - x;
      double dy = ty - y;
      double dz = tz - z;
      double yaw = Math.toDegrees(Math.atan2(-dx, dz));
      double dist = Math.sqrt(dx * dx + dz * dz);
      double pitch = Math.toDegrees(-Math.atan2(dy, dist));
      return new double[]{yaw, pitch};
   }

   private static double catmull(double p0, double p1, double p2, double p3, double t) {
      return 0.5 * (2.0 * p1 + (-p0 + p2) * t + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * t * t + (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * t * t * t);
   }

   private static double lerp(double a, double b, double t) {
      return a + (b - a) * t;
   }

   public static double lerpAngle(double a, double b, double t) {
      double d = ((b - a) % 360.0 + 540.0) % 360.0 - 180.0;
      double r = a + d * t;
      return (r % 360.0 + 540.0) % 360.0 - 180.0;
   }

   public record Key(double x, double y, double z, Double yaw, Double pitch, double[] lookAt, double t) {
   }
}

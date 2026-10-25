package com.metrofico.pulsar.api.legit;

import java.util.LinkedHashMap;
import java.util.Map;

public final class AimSensor {
   static final float SNAP_DEGREES = 30.0F;
   static final float STILL_DEGREES = 0.001F;
   private static final Object LOCK = new Object();
   private static int ticks;
   private static int snaps;
   private static double yawMean;
   private static double yawM2;
   private static double pitchMean;
   private static double pitchM2;
   private static float lastYaw;
   private static float lastPitch;
   private static boolean hasLast;
   private static boolean lastWasBig;

   private AimSensor() {
   }

   public static void tick(float yaw, float pitch) {
      if (GameSignals.active) {
         synchronized (LOCK) {
            if (hasLast) {
               float dy = wrap(yaw - lastYaw);
               float dp = pitch - lastPitch;
               ticks++;
               double d1 = dy - yawMean;
               yawMean = yawMean + d1 / ticks;
               yawM2 = yawM2 + d1 * (dy - yawMean);
               double d2 = dp - pitchMean;
               pitchMean = pitchMean + d2 / ticks;
               pitchM2 = pitchM2 + d2 * (dp - pitchMean);
               float turn = (float)Math.sqrt(dy * dy + dp * dp);
               if (lastWasBig && turn < 0.001F) {
                  snaps++;
               }

               lastWasBig = turn > 30.0F;
            }

            lastYaw = yaw;
            lastPitch = pitch;
            hasLast = true;
         }
      }
   }

   private static float wrap(float degrees) {
      float d = degrees % 360.0F;
      if (d > 180.0F) {
         d -= 360.0F;
      } else if (d < -180.0F) {
         d += 360.0F;
      }

      return d;
   }

   static void reset() {
      synchronized (LOCK) {
         ticks = 0;
         snaps = 0;
         pitchM2 = 0.0;
         pitchMean = 0.0;
         yawM2 = 0.0;
         yawMean = 0.0;
         hasLast = false;
         lastWasBig = false;
      }
   }

   static Map<String, Object> drain() {
      synchronized (LOCK) {
         Map<String, Object> aim = new LinkedHashMap<>();
         aim.put("ticks", ticks);
         aim.put("yawVariance", ticks > 1 ? round(yawM2 / (ticks - 1)) : 0.0);
         aim.put("pitchVariance", ticks > 1 ? round(pitchM2 / (ticks - 1)) : 0.0);
         aim.put("snaps", snaps);
         ticks = 0;
         snaps = 0;
         pitchM2 = 0.0;
         pitchMean = 0.0;
         yawM2 = 0.0;
         yawMean = 0.0;
         return aim;
      }
   }

   private static double round(double v) {
      return Math.round(v * 1000.0) / 1000.0;
   }
}

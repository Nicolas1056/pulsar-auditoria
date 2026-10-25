package com.metrofico.pulsar.engine.v189.perf;

public final class FovWatch189 {
   private static final float REVEAL_RATIO = 1.5F;
   private static float observedFov = Float.NaN;
   private static float frameFov = Float.NaN;
   private static float occlusionFov = Float.NaN;

   private FovWatch189() {
   }

   public static void observe(float fov) {
      observedFov = fov;
   }

   public static boolean changedSinceLastFrame() {
      if (!Float.isNaN(observedFov) && observedFov != frameFov) {
         boolean firstFrame = Float.isNaN(frameFov);
         frameFov = observedFov;
         return !firstFrame;
      } else {
         return false;
      }
   }

   public static boolean openedWide() {
      if (Float.isNaN(occlusionFov) || frameFov < occlusionFov) {
         occlusionFov = frameFov;
         return false;
      } else if (frameFov > occlusionFov * 1.5F) {
         occlusionFov = frameFov;
         return true;
      } else {
         return false;
      }
   }
}

package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;

public final class EntityLod189 {
   private static final float REDUCE_PX = 96.0F;
   private static boolean reduce;
   private static int frameReduced;
   static volatile int lastReduced;

   private EntityLod189() {
   }

   public static void begin(pk entity, double x, double y, double z) {
      reduce = false;
      if (FrameFlags.entityLod && !Zoom.active()) {
         ave mc = ave.A();
         double dist = Math.sqrt(x * x + y * y + z * z);
         if (!(dist < 6.0)) {
            double fov = Math.toRadians(Math.max(10.0F, Math.min(mc.t.aI, 110.0F)));
            double px = entity.K / dist * (mc.e / (2.0 * Math.tan(fov / 2.0)));
            if (px < 96.0) {
               reduce = true;
               frameReduced++;
            }
         }
      }
   }

   public static void end() {
      reduce = false;
   }

   public static boolean reduce() {
      return reduce;
   }

   public static void frame() {
      lastReduced = frameReduced;
      frameReduced = 0;
   }
}

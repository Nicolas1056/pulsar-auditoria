package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;

public final class HudText {
   private HudText() {
   }

   public static float crispRatio(Canvas g, double moduleScale, double ratio) {
      if (ratio >= 1.0) {
         return 1.0F;
      } else {
         int gui = Math.max(1, g.guiScale());
         int full = (int)Math.max(1L, Math.round(moduleScale * gui));
         int small = (int)Math.max(1L, Math.round(full * ratio));
         return (float)small / full;
      }
   }
}

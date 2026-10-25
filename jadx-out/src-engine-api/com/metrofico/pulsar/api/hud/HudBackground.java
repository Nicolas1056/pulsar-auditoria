package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.hub.ClientOptions;

public final class HudBackground {
   public static final int DEFAULT_OPACITY_PERCENT = 40;

   private HudBackground() {
   }

   public static String option(String module) {
      return module + ".opacity";
   }

   public static int color(String option, int rgb) {
      int percent = Math.max(0, Math.min(100, ClientOptions.get().integer(option, 40)));
      return Math.round(percent * 255.0F / 100.0F) << 24 | rgb & 16777215;
   }

   public static int color(String option) {
      return color(option, 0);
   }
}

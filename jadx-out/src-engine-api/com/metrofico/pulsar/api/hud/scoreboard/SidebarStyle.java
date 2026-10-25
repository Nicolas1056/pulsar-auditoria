package com.metrofico.pulsar.api.hud.scoreboard;

import com.metrofico.pulsar.api.hub.ClientOptions;

public record SidebarStyle(boolean background, int bodyAlpha, int headerAlpha, boolean scores, boolean shadow) {
   private static final int DARK_HEADER_STEP = 80;
   private static final int DEFAULT_OPACITY_PERCENT = 31;

   public static SidebarStyle fromOptions() {
      ClientOptions options = ClientOptions.get();
      int bodyAlpha = Math.round(options.integer("scoreboard.opacity", 31) * 255.0F / 100.0F);
      int headerStep = options.bool("scoreboard.darkHeader", false) ? 80 : 0;
      return new SidebarStyle(
         options.bool("scoreboard.background", true),
         clampAlpha(bodyAlpha),
         clampAlpha(bodyAlpha + headerStep),
         options.bool("scoreboard.scores", true),
         options.bool("scoreboard.shadow", false)
      );
   }

   private static int clampAlpha(int alpha) {
      return Math.max(0, Math.min(255, alpha));
   }

   public int bodyColor() {
      return this.bodyAlpha << 24;
   }

   public int headerColor() {
      return this.headerAlpha << 24;
   }
}

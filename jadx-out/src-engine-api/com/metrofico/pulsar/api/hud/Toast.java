package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.pack.ImageRef;
import java.util.Map;

public record Toast(String id, String title, String text, String image, Zone zone, int background, int border, int color, long durationMs, long createdAt) {
   public static final int WIDTH = 160;
   public static final long MIN_DURATION = 1000L;
   public static final long MAX_DURATION = 15000L;
   public static final long SLIDE_MS = 300L;

   public static Toast from(Map<String, Object> f, String id, long now) {
      String title = f.get("title") instanceof String s ? PanelDefinition.clip(s) : "";
      String text = f.get("text") instanceof String sx ? (sx.length() > 512 ? sx.substring(0, 512) : sx) : "";
      String image = ImageRef.parse(f.get("image")).isPresent() ? (String)f.get("image") : null;
      Zone zone = f.get("zone") instanceof String sxx ? Zone.byId(sxx).orElse(Zone.TOP_RIGHT) : Zone.TOP_RIGHT;
      Map<String, Object> style = (Map<String, Object>)(f.get("style") instanceof Map<?, ?> m ? m : Map.of());
      int background = PanelDefinition.color(style.get("background"), -267380704);
      int border = PanelDefinition.color(style.get("border"), -11930);
      int color = PanelDefinition.color(style.get("color"), -855310);
      long duration = f.get("duration") instanceof Number n ? Math.max(1000L, Math.min(15000L, n.longValue())) : 5000L;
      return new Toast(id, title, text, image, zone, background, border, color, duration, now);
   }

   public long expiresAt() {
      return this.createdAt + this.durationMs;
   }

   public double progress(long now) {
      long age = now - this.createdAt;
      if (age < 300L) {
         return Math.max(0.0, age / 300.0);
      } else {
         long left = this.expiresAt() - now;
         return left < 300L ? Math.max(0.0, left / 300.0) : 1.0;
      }
   }
}

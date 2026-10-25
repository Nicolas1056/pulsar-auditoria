package com.metrofico.pulsar.api.hud;

import java.util.Map;

public final class HudDefaults {
   private static final Map<String, HudDefaults.Layout> LAYOUT = Map.of(
      "fps",
      layout("top-left:4:12", 1.6),
      "effects",
      layout("top-left:4:31", 0.95),
      "cps",
      layout("top-left:4:56", 1.3),
      "armor",
      layout("top-left:4:74", 1.2),
      "coords",
      layout("top-right:-4:4", 1.0),
      "ping",
      layout("top-right:-4:17", 1.1),
      "toggles",
      layout("top-right:-4:36", 1.2),
      "legit",
      layout("bottom-right:-4:-4", 1.0)
   );

   private HudDefaults() {
   }

   private static HudDefaults.Layout layout(String pos, double scale) {
      return new HudDefaults.Layout(Placement.parse(pos).orElseThrow(), scale);
   }

   public static Placement placement(String id) {
      HudDefaults.Layout l = LAYOUT.get(id);
      return l == null ? null : l.placement();
   }

   public static Zone zone(String id, Zone fallback) {
      HudDefaults.Layout l = LAYOUT.get(id);
      return l == null ? fallback : l.placement().anchor();
   }

   public static double scale(String id) {
      HudDefaults.Layout l = LAYOUT.get(id);
      return l == null ? 1.0 : l.scale();
   }

   private record Layout(Placement placement, double scale) {
   }
}

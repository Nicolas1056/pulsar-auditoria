package com.metrofico.pulsar.api.hud;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public record CompassDefinition(
   String id,
   Zone zone,
   CompassDefinition.Form form,
   int size,
   int span,
   boolean cardinals,
   boolean distances,
   String target,
   int color,
   int accent,
   int background
) {
   public static final String DEFAULT_ID = "compass";
   public static final int MIN_SIZE = 24;
   public static final int MAX_SIZE = 400;

   public static CompassDefinition defaults() {
      return new CompassDefinition("compass", Zone.TOP, CompassDefinition.Form.BAR, 180, 90, true, false, null, -855638017, -8587872, -2146430960);
   }

   public static CompassDefinition merge(CompassDefinition base, Map<String, Object> o) {
      String id = o.get("widgetId") instanceof String s && !s.isEmpty() ? s : base.id();
      Zone zone = o.get("zone") instanceof String sx ? Zone.byId(sx).orElse(base.zone()) : base.zone();
      CompassDefinition.Form form = base.form();
      if (o.get("form") instanceof String sxx) {
         try {
            form = CompassDefinition.Form.valueOf(sxx.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var14) {
         }
      }

      int size = clamp(o.get("size"), base.size(), 24, 400);
      int span = clamp(o.get("span"), base.span(), 30, 180);
      boolean cardinals = o.get("cardinals") instanceof Boolean b ? b : base.cardinals();
      boolean distances = o.get("distances") instanceof Boolean bx ? bx : base.distances();
      String target = o.containsKey("target") ? (o.get("target") instanceof String sxx && !sxx.isEmpty() ? sxx : null) : base.target();
      Map<String, Object> style = (Map<String, Object>)(o.get("style") instanceof Map<?, ?> m ? m : Map.of());
      int color = PanelDefinition.color(style.get("color"), base.color());
      int accent = PanelDefinition.color(style.get("accent"), base.accent());
      int background = PanelDefinition.color(style.get("background"), base.background());
      return new CompassDefinition(id, zone, form, size, span, cardinals, distances, target, color, accent, background);
   }

   public static boolean isCompass(Map<String, Object> o) {
      return "compass".equals(o.get("widget"));
   }

   public int width() {
      return this.form == CompassDefinition.Form.BAR ? this.size : (this.form == CompassDefinition.Form.RING ? this.size : Math.max(this.size, 40));
   }

   public int height() {
      return this.form == CompassDefinition.Form.BAR
         ? 14
         : (this.form == CompassDefinition.Form.RING ? this.size : Math.max(this.size, 40) + (this.distances ? 10 : 0));
   }

   public Map<String, Object> toMap() {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("widgetId", this.id);
      m.put("zone", this.zone.id());
      m.put("form", this.form.name().toLowerCase(Locale.ROOT));
      m.put("size", (long)this.size);
      m.put("span", (long)this.span);
      return m;
   }

   private static int clamp(Object v, int fallback, int min, int max) {
      return v instanceof Number n ? Math.max(min, Math.min(max, n.intValue())) : fallback;
   }

   public static enum Form {
      BAR,
      RING,
      NEEDLE;
   }
}

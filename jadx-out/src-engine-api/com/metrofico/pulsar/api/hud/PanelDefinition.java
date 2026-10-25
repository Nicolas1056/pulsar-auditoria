package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.pack.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record PanelDefinition(String id, Zone zone, String title, List<String> lines, PanelDefinition.Align align, int color, int background) {
   public static final int DEFAULT_COLOR = -855310;
   public static final int DEFAULT_BACKGROUND = -2146430960;
   public static final int MAX_LINES = 12;
   public static final int MAX_LINE_LENGTH = 96;

   public PanelDefinition(String id, Zone zone, String title, List<String> lines, PanelDefinition.Align align, int color, int background) {
      lines = List.copyOf(lines);
      this.id = id;
      this.zone = zone;
      this.title = title;
      this.lines = lines;
      this.align = align;
      this.color = color;
      this.background = background;
   }

   public static PanelDefinition parse(String json) {
      return parse(Json.object(Json.parse(json), "panel"));
   }

   public static PanelDefinition parse(Map<String, Object> o) {
      String id = Json.string(o, "panel", "panel");
      Zone zone = Zone.byId(String.valueOf(o.getOrDefault("zone", "top-left")))
         .orElseThrow(() -> new IllegalArgumentException("panel: unknown zone " + o.get("zone")));
      String title = o.get("title") instanceof String s ? s : "";
      List<String> lines = lines(o.get("lines"));
      Map<String, Object> style = (Map<String, Object>)(o.get("style") instanceof Map<?, ?> m ? m : Map.of());
      String var12 = String.valueOf(style.getOrDefault("align", "left"));

      PanelDefinition.Align align = switch (var12) {
         case "center" -> PanelDefinition.Align.CENTER;
         case "right" -> PanelDefinition.Align.RIGHT;
         default -> PanelDefinition.Align.LEFT;
      };
      int color = color(style.get("color"), -855310);
      int background = color(style.get("background"), -2146430960);
      return new PanelDefinition(id, zone, clip(title), lines, align, color, background);
   }

   public static List<String> lines(Object v) {
      if (v == null) {
         return List.of();
      } else {
         List<String> out = new ArrayList<>();

         for (Object l : Json.array(v, "panel.lines")) {
            if (out.size() == 12) {
               break;
            }

            out.add(clip(String.valueOf(l)));
         }

         return out;
      }
   }

   public static int color(Object v, int fallback) {
      if (v instanceof String s && s.startsWith("#")) {
         try {
            long n = Long.parseLong(s.substring(1), 16);
            return s.length() == 7 ? (int)(4278190080L | n) : (int)n;
         } catch (NumberFormatException var5) {
            return fallback;
         }
      } else {
         return fallback;
      }
   }

   static String clip(String s) {
      return s.length() > 96 ? s.substring(0, 96) : s;
   }

   public static enum Align {
      LEFT,
      CENTER,
      RIGHT;
   }
}

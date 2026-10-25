package com.metrofico.pulsar.api.map;

import com.metrofico.pulsar.api.hud.PanelDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MapDefinition(
   String title,
   String image,
   double minX,
   double minZ,
   double maxX,
   double maxZ,
   int background,
   boolean grid,
   int gridStep,
   boolean ownPosition,
   double minZoom,
   double maxZoom,
   int fogColor
) {
   public static final int MAX_SPAN = 100000;
   public static final int MAX_TILES = 4096;
   public static final int MAX_INLINE_PNG_CHARS = 400000;

   public static MapDefinition empty() {
      return new MapDefinition("Mapa", null, 0.0, 0.0, 0.0, 0.0, -14998480, true, 16, true, 0.25, 8.0, -2569821);
   }

   public boolean defined() {
      return this.maxX > this.minX && this.maxZ > this.minZ;
   }

   public double width() {
      return this.maxX - this.minX;
   }

   public double height() {
      return this.maxZ - this.minZ;
   }

   public static MapDefinition merge(MapDefinition base, Map<String, Object> o) {
      String title = o.get("title") instanceof String s ? MapDefinition.PanelDefinitionClip.clip(s) : base.title();
      String image = o.containsKey("image") ? (o.get("image") instanceof String sx && !sx.isEmpty() ? sx : null) : base.image();
      double minX = base.minX();
      double minZ = base.minZ();
      double maxX = base.maxX();
      double maxZ = base.maxZ();
      if (o.get("bounds") instanceof List<?> b && b.size() == 4 && b.stream().allMatch(v -> v instanceof Number)) {
         double x1 = ((Number)b.get(0)).doubleValue();
         double z1 = ((Number)b.get(1)).doubleValue();
         double x2 = ((Number)b.get(2)).doubleValue();
         double z2 = ((Number)b.get(3)).doubleValue();
         minX = Math.min(x1, x2);
         maxX = Math.max(x1, x2);
         minZ = Math.min(z1, z2);
         maxZ = Math.max(z1, z2);
         if (maxX - minX > 100000.0 || maxZ - minZ > 100000.0) {
            minX = base.minX();
            maxX = base.maxX();
            minZ = base.minZ();
            maxZ = base.maxZ();
         }
      }

      Map<String, Object> style = (Map<String, Object>)(o.get("style") instanceof Map<?, ?> m ? m : Map.of());
      int background = PanelDefinition.color(style.get("background"), base.background());
      int fogColor = PanelDefinition.color(style.get("fog"), base.fogColor());
      boolean grid = o.get("grid") instanceof Boolean v ? v : base.grid();
      int gridStep = o.get("gridStep") instanceof Number n ? Math.max(1, Math.min(4096, n.intValue())) : base.gridStep();
      boolean own = o.get("ownPosition") instanceof Boolean vx ? vx : base.ownPosition();
      double minZoom = o.get("minZoom") instanceof Number nx ? Math.max(0.05, nx.doubleValue()) : base.minZoom();
      double maxZoom = o.get("maxZoom") instanceof Number nxx ? Math.min(64.0, nxx.doubleValue()) : base.maxZoom();
      return new MapDefinition(title, image, minX, minZ, maxX, maxZ, background, grid, gridStep, own, minZoom, Math.max(minZoom, maxZoom), fogColor);
   }

   public static List<MapDefinition.Tile> tiles(Map<String, Object> props) {
      List<MapDefinition.Tile> out = new ArrayList<>();
      Object var3 = props.get("tiles");
      if (var3 instanceof List) {
         for (Object o : (List)var3) {
            if (o instanceof Map<?, ?> t
               && t.get("image") instanceof String img
               && t.get("bounds") instanceof List<?> b
               && b.size() == 4
               && b.stream().allMatch(v -> v instanceof Number)
               && out.size() < 4096) {
               double x1 = ((Number)b.get(0)).doubleValue();
               double z1 = ((Number)b.get(1)).doubleValue();
               double x2 = ((Number)b.get(2)).doubleValue();
               double z2 = ((Number)b.get(3)).doubleValue();
               String png = t.get("png") instanceof String p && !p.isEmpty() && p.length() <= 400000 ? p : null;
               if (x2 > x1 && z2 > z1) {
                  out.add(new MapDefinition.Tile(img, Math.min(x1, x2), Math.min(z1, z2), Math.max(x1, x2), Math.max(z1, z2), png));
               }
            }
         }
      }

      return out;
   }

   static final class PanelDefinitionClip {
      private PanelDefinitionClip() {
      }

      static String clip(String s) {
         return s.length() > 96 ? s.substring(0, 96) : s;
      }
   }

   public record Tile(String image, double minX, double minZ, double maxX, double maxZ, String png) {
   }
}

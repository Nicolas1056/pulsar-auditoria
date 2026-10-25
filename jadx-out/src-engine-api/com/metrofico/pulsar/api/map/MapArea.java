package com.metrofico.pulsar.api.map;

import com.metrofico.pulsar.api.hud.PanelDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MapArea(String id, List<double[]> points, int fill, int outline, String label) {
   public static final int MAX_POINTS = 64;
   public static final int DEFAULT_FILL = 1442840575;
   public static final int DEFAULT_OUTLINE = -855638017;

   public MapArea(String id, List<double[]> points, int fill, int outline, String label) {
      points = List.copyOf(points);
      this.id = id;
      this.points = points;
      this.fill = fill;
      this.outline = outline;
      this.label = label;
   }

   public static MapArea from(Map<String, Object> f, MapArea previous) {
      String id = String.valueOf(f.get("area"));
      List<double[]> pts = previous == null ? List.of() : previous.points();
      if (f.get("rect") instanceof List<?> r && r.size() == 4 && r.stream().allMatch(v -> v instanceof Number)) {
         double x1 = ((Number)r.get(0)).doubleValue();
         double z1 = ((Number)r.get(1)).doubleValue();
         double x2 = ((Number)r.get(2)).doubleValue();
         double z2 = ((Number)r.get(3)).doubleValue();
         pts = List.of(new double[]{x1, z1}, new double[]{x2, z1}, new double[]{x2, z2}, new double[]{x1, z2});
      } else if (f.get("points") instanceof List<?> list) {
         List<double[]> out = new ArrayList<>();

         for (Object o : list) {
            if (o instanceof List<?> p && p.size() == 2 && p.get(0) instanceof Number a && p.get(1) instanceof Number b && out.size() < 64) {
               out.add(new double[]{a.doubleValue(), b.doubleValue()});
            }
         }

         if (out.size() >= 3) {
            pts = out;
         }
      }

      int fill = PanelDefinition.color(f.get("fill"), previous == null ? 1442840575 : previous.fill());
      int outline = PanelDefinition.color(f.get("outline"), previous == null ? -855638017 : previous.outline());
      String label = f.get("label") instanceof String s ? (s.length() > 48 ? s.substring(0, 48) : s) : (previous == null ? "" : previous.label());
      return new MapArea(id, pts, fill, outline, label);
   }

   public boolean valid() {
      return this.points.size() >= 3;
   }

   public double[] center() {
      double x = 0.0;
      double z = 0.0;

      for (double[] p : this.points) {
         x += p[0];
         z += p[1];
      }

      return new double[]{x / this.points.size(), z / this.points.size()};
   }
}

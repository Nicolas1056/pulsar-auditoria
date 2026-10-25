package com.metrofico.pulsar.api.world;

import com.metrofico.pulsar.api.hud.PanelDefinition;
import com.metrofico.pulsar.api.pack.ImageRef;
import java.util.Locale;
import java.util.Map;

public record Marker(String id, double x, double y, double z, String label, Marker.Icon icon, int color, boolean compass, String image) {
   public static final int MAX_LABEL = 48;
   public static final int DEFAULT_COLOR = -11930;

   public static Marker from(Map<String, Object> f, Marker previous) {
      String id = String.valueOf(f.get("marker"));
      double x = number(f.get("x"), previous == null ? 0.0 : previous.x());
      double y = number(f.get("y"), previous == null ? 0.0 : previous.y());
      double z = number(f.get("z"), previous == null ? 0.0 : previous.z());
      String label = f.get("label") instanceof String s ? (s.length() > 48 ? s.substring(0, 48) : s) : (previous == null ? "" : previous.label());
      Marker.Icon icon = previous == null ? Marker.Icon.PIN : previous.icon();
      if (f.get("icon") instanceof String sx) {
         try {
            icon = Marker.Icon.valueOf(sx.toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var15) {
         }
      }

      int color = PanelDefinition.color(f.get("color"), previous == null ? -11930 : previous.color());
      boolean compass = f.get("compass") instanceof Boolean b ? b : previous == null || previous.compass();
      String image = f.containsKey("image")
         ? (ImageRef.parse(f.get("image")).isPresent() ? (String)f.get("image") : null)
         : (previous == null ? null : previous.image());
      return new Marker(id, x, y, z, label, icon, color, compass, image);
   }

   private static double number(Object v, double fallback) {
      return v instanceof Number n ? n.doubleValue() : fallback;
   }

   public double distanceTo(double px, double py, double pz) {
      double dx = this.x - px;
      double dy = this.y - py;
      double dz = this.z - pz;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
   }

   public static enum Icon {
      FLAG,
      PIN,
      DOT,
      STAR;
   }
}

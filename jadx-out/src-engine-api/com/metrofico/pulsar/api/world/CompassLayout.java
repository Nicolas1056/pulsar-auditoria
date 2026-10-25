package com.metrofico.pulsar.api.world;

import com.metrofico.pulsar.api.hud.CompassDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public final class CompassLayout {
   private CompassLayout() {
   }

   public static List<CompassLayout.Item> items(List<Marker> markers, double camX, double camY, double camZ, double playerYaw) {
      List<CompassLayout.Item> out = new ArrayList<>();

      for (Marker m : markers) {
         if (m.compass()) {
            double rel = Projection.wrap(Projection.yawTo(camX, camZ, m.x(), m.z()) - playerYaw);
            out.add(new CompassLayout.Item(m, rel, m.distanceTo(camX, camY, camZ)));
         }
      }

      return out;
   }

   public static List<CompassLayout.Cardinal> cardinals(double playerYaw) {
      String[] labels = new String[]{"S", "O", "N", "E"};
      List<CompassLayout.Cardinal> out = new ArrayList<>(4);

      for (int i = 0; i < 4; i++) {
         out.add(new CompassLayout.Cardinal(labels[i], Projection.wrap(i * 90 - playerYaw)));
      }

      return out;
   }

   public static OptionalInt barOffset(double relativeYaw, int width, int span) {
      return Math.abs(relativeYaw) > span ? OptionalInt.empty() : OptionalInt.of((int)Math.round(relativeYaw / span * (width / 2.0)));
   }

   public static double[] ringOffset(double relativeYaw, double radius) {
      double a = Math.toRadians(relativeYaw);
      return new double[]{Math.sin(a) * radius, -Math.cos(a) * radius};
   }

   public static CompassLayout.Item needleTarget(List<CompassLayout.Item> items, String target) {
      CompassLayout.Item best = null;

      for (CompassLayout.Item i : items) {
         if (target != null && target.equals(i.marker().id())) {
            return i;
         }

         if (best == null || i.distance() < best.distance()) {
            best = i;
         }
      }

      return target != null ? null : best;
   }

   public static CompassDefinition.Form form(CompassDefinition d) {
      return d.form();
   }

   public record Cardinal(String label, double relativeYaw) {
   }

   public record Item(Marker marker, double relativeYaw, double distance) {
   }
}

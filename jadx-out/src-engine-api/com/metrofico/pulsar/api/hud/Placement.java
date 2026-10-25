package com.metrofico.pulsar.api.hud;

import java.util.Optional;

public record Placement(Zone anchor, Integer dx, Integer dy) {
   public static Placement zone(Zone zone) {
      return new Placement(zone, null, null);
   }

   public boolean free() {
      return this.dx != null && this.dy != null;
   }

   public static Placement of(int x, int y, int w, int h, int guiWidth, int guiHeight) {
      Zone anchor = PanelLayout.nearestZone(x + w / 2.0, y + h / 2.0, guiWidth, guiHeight);
      return new Placement(anchor, x - anchorX(anchor, w, guiWidth), y - anchorY(anchor, h, guiHeight));
   }

   public int x(int w, int guiWidth) {
      return anchorX(this.anchor, w, guiWidth) + (this.dx == null ? 0 : this.dx);
   }

   public int y(int h, int guiHeight) {
      return anchorY(this.anchor, h, guiHeight) + (this.dy == null ? 0 : this.dy);
   }

   static int anchorX(Zone z, int w, int guiWidth) {
      return switch (z.column()) {
         case 0 -> 0;
         case 1 -> (guiWidth - w) / 2;
         default -> guiWidth - w;
      };
   }

   static int anchorY(Zone z, int h, int guiHeight) {
      return switch (z.row()) {
         case 0 -> 0;
         case 1 -> (guiHeight - h) / 2;
         default -> guiHeight - h;
      };
   }

   public String serialize() {
      return this.free() ? this.anchor.id() + ":" + this.dx + ":" + this.dy : this.anchor.id();
   }

   public static Optional<Placement> parse(String s) {
      if (s == null) {
         return Optional.empty();
      } else {
         String[] parts = s.split(":");
         Optional<Zone> zone = Zone.byId(parts[0]);
         if (zone.isEmpty()) {
            return Optional.empty();
         } else if (parts.length == 3) {
            try {
               return Optional.of(new Placement(zone.get(), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            } catch (NumberFormatException var4) {
               return Optional.empty();
            }
         } else {
            return Optional.of(zone(zone.get()));
         }
      }
   }
}

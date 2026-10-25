package com.metrofico.pulsar.api.hud;

import java.util.Optional;

public enum Zone {
   TOP_LEFT("top-left", 0, 0),
   TOP("top", 1, 0),
   TOP_RIGHT("top-right", 2, 0),
   LEFT("left", 0, 1),
   RIGHT("right", 2, 1),
   BOTTOM_LEFT("bottom-left", 0, 2),
   BOTTOM("bottom", 1, 2),
   BOTTOM_RIGHT("bottom-right", 2, 2);

   private final String id;
   private final int column;
   private final int row;

   private Zone(String id, int column, int row) {
      this.id = id;
      this.column = column;
      this.row = row;
   }

   public String id() {
      return this.id;
   }

   public int column() {
      return this.column;
   }

   public int row() {
      return this.row;
   }

   public static Optional<Zone> byId(String id) {
      for (Zone z : values()) {
         if (z.id.equals(id)) {
            return Optional.of(z);
         }
      }

      return Optional.empty();
   }
}

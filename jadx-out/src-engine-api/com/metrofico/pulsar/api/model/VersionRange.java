package com.metrofico.pulsar.api.model;

import java.util.Objects;

public record VersionRange(GameVersion min, GameVersion max) {
   public VersionRange(GameVersion min, GameVersion max) {
      Objects.requireNonNull(min, "min");
      Objects.requireNonNull(max, "max");
      if (min.compareTo(max) > 0) {
         throw new IllegalArgumentException("Invalid version range: " + min + " > " + max);
      } else {
         this.min = min;
         this.max = max;
      }
   }

   public static VersionRange of(String min, String max) {
      return new VersionRange(GameVersion.parse(min), GameVersion.parse(max));
   }

   public static VersionRange only(String version) {
      GameVersion v = GameVersion.parse(version);
      return new VersionRange(v, v);
   }

   public boolean contains(GameVersion version) {
      return this.min.compareTo(version) <= 0 && version.compareTo(this.max) <= 0;
   }

   @Override
   public String toString() {
      return this.min.equals(this.max) ? this.min.toString() : "[" + this.min + ", " + this.max + "]";
   }
}

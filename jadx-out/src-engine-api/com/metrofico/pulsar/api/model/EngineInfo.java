package com.metrofico.pulsar.api.model;

import java.util.Objects;

public record EngineInfo(String backendId, GameVersion gameVersion, int javaFeatureVersion) {
   public EngineInfo(String backendId, GameVersion gameVersion, int javaFeatureVersion) {
      Objects.requireNonNull(backendId, "backendId");
      Objects.requireNonNull(gameVersion, "gameVersion");
      if (backendId.isBlank()) {
         throw new IllegalArgumentException("backendId must not be blank");
      } else {
         this.backendId = backendId;
         this.gameVersion = gameVersion;
         this.javaFeatureVersion = javaFeatureVersion;
      }
   }

   @Override
   public String toString() {
      return this.backendId + " for Minecraft " + this.gameVersion + " on Java " + this.javaFeatureVersion;
   }
}

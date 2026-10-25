package com.metrofico.pulsar.launcher.auth;

import java.util.UUID;

public record MinecraftSession(String name, UUID uuid, String accessToken, long expiresAtEpochMs, String xuid, String skinUrl) {
   public boolean expired(long nowEpochMs) {
      return nowEpochMs >= this.expiresAtEpochMs - 60000L;
   }
}

package com.metrofico.pulsar.launcher.launch;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public record LaunchSession(String playerName, UUID uuid, String accessToken, String userType, String xuid, String clientId) {
   public static LaunchSession developmentOffline(String playerName) {
      UUID offline = UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
      return new LaunchSession(playerName, offline, "0", "legacy", "", "");
   }
}

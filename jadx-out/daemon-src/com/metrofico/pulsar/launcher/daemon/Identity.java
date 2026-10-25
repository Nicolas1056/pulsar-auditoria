package com.metrofico.pulsar.launcher.daemon;

public record Identity(String name, String uuid, String accessToken, String xuid, boolean online) {
   public static Identity offline(String playerName) {
      return new Identity(playerName, null, null, "", false);
   }
}

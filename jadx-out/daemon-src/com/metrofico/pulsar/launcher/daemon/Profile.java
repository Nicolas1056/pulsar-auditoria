package com.metrofico.pulsar.launcher.daemon;

import java.util.UUID;

public record Profile(String id, String name, String version, Integer memoryMb, String javaPath, Long lastPlayedEpochMs, String gameDir) {
   public static Profile create(String name, String version) {
      return new Profile(UUID.randomUUID().toString(), name, version, null, null, null, null);
   }

   public Profile played(long at) {
      return new Profile(this.id, this.name, this.version, this.memoryMb, this.javaPath, at, this.gameDir);
   }
}

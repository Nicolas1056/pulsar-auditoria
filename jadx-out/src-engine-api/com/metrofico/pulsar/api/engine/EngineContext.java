package com.metrofico.pulsar.api.engine;

import com.metrofico.pulsar.api.model.GameVersion;
import java.nio.file.Path;
import java.util.Objects;

public record EngineContext(GameVersion gameVersion, String os, String arch, Path dataDir, EngineLogger logger) {
   public EngineContext(GameVersion gameVersion, String os, String arch, Path dataDir, EngineLogger logger) {
      Objects.requireNonNull(gameVersion, "gameVersion");
      Objects.requireNonNull(os, "os");
      Objects.requireNonNull(arch, "arch");
      Objects.requireNonNull(dataDir, "dataDir");
      Objects.requireNonNull(logger, "logger");
      this.gameVersion = gameVersion;
      this.os = os;
      this.arch = arch;
      this.dataDir = dataDir;
      this.logger = logger;
   }
}

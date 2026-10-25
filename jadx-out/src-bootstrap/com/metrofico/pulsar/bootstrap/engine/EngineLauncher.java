package com.metrofico.pulsar.bootstrap.engine;

import com.metrofico.pulsar.api.engine.EngineBackend;
import com.metrofico.pulsar.api.engine.EngineBackends;
import com.metrofico.pulsar.api.engine.EngineContext;
import com.metrofico.pulsar.api.model.GameVersion;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public final class EngineLauncher {
   public static final String PROPERTY_VERSION = "pulsar.game.version";
   public static final String PROPERTY_DATA_DIR = "pulsar.data.dir";

   public static EngineLauncher.Selection select(ClassLoader gameLoader, GameVersion version, String os, String arch, Path dataDir) {
      List<EngineBackend> found = EngineBackends.discover(gameLoader);
      EngineContext context = new EngineContext(version, os, arch, dataDir, new BootstrapEngineLogger());
      if (found.isEmpty()) {
         BootstrapLog.info("WARN no engine backend on the game classpath: running without engine (mixins only)");
         return new EngineLauncher.Selection(Optional.empty(), context);
      } else {
         EngineBackend backend = EngineBackends.select(found, version);
         BootstrapLog.info("Engine backend " + backend.id() + " selected for Minecraft " + version + " (" + backend.mixinConfigs().size() + " mixin config(s))");
         PulsarClient.install(backend, context);
         return new EngineLauncher.Selection(Optional.of(backend), context);
      }
   }

   public static GameVersion versionFromSystemProperties() {
      String id = System.getProperty("pulsar.game.version");
      if (id != null && !id.isBlank()) {
         return GameVersion.parse(id);
      } else {
         throw new IllegalStateException("The launcher must set -Dpulsar.game.version");
      }
   }

   private EngineLauncher() {
   }

   public record Selection(Optional<EngineBackend> backend, EngineContext context) {
      public List<String> mixinConfigs() {
         return this.backend.<List<String>>map(EngineBackend::mixinConfigs).orElse(List.of());
      }
   }
}

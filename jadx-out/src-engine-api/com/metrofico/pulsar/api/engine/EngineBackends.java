package com.metrofico.pulsar.api.engine;

import com.metrofico.pulsar.api.model.GameVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public final class EngineBackends {
   public static List<EngineBackend> discover(ClassLoader loader) {
      List<EngineBackend> found = new ArrayList<>();

      try {
         for (EngineBackend backend : ServiceLoader.load(EngineBackend.class, loader)) {
            found.add(backend);
         }
      } catch (ServiceConfigurationError var4) {
         throw new IllegalStateException(
            "An engine backend is registered but cannot be loaded: "
               + var4.getMessage()
               + " (backend classes must live on the game classpath, outside com.metrofico.pulsar.api.*)",
            var4
         );
      }

      return List.copyOf(found);
   }

   public static EngineBackend select(List<EngineBackend> candidates, GameVersion version) {
      List<EngineBackend> matching = candidates.stream().filter(b -> b.supports(version)).toList();
      if (matching.size() == 1) {
         return matching.get(0);
      } else {
         String available = candidates.isEmpty()
            ? "none"
            : candidates.stream().map(b -> b.id() + " " + b.supportedVersions()).collect(Collectors.joining(", "));
         if (matching.isEmpty()) {
            throw new IllegalStateException("No engine backend supports Minecraft " + version + " (available: " + available + ")");
         } else {
            throw new IllegalStateException(
               "Ambiguous engine backends for Minecraft " + version + ": " + matching.stream().map(EngineBackend::id).collect(Collectors.joining(", "))
            );
         }
      }
   }

   private EngineBackends() {
   }
}

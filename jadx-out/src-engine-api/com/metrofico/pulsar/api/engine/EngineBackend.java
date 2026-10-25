package com.metrofico.pulsar.api.engine;

import com.metrofico.pulsar.api.model.GameVersion;
import com.metrofico.pulsar.api.model.VersionRange;
import com.metrofico.pulsar.api.pack.Capability;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface EngineBackend {
   String id();

   VersionRange supportedVersions();

   default boolean supports(GameVersion version) {
      return this.supportedVersions().contains(version);
   }

   List<String> mixinConfigs();

   Engine createEngine(EngineContext var1);

   default Set<Capability> capabilities() {
      return EnumSet.allOf(Capability.class);
   }
}

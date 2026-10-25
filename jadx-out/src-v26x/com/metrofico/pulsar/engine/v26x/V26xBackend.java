package com.metrofico.pulsar.engine.v26x;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.engine.EngineBackend;
import com.metrofico.pulsar.api.engine.EngineContext;
import com.metrofico.pulsar.api.model.VersionRange;
import java.util.List;

public final class V26xBackend implements EngineBackend {
   public static final String ID = "engine-v26x";

   public String id() {
      return "engine-v26x";
   }

   public VersionRange supportedVersions() {
      return VersionRange.only("26.2");
   }

   public List<String> mixinConfigs() {
      return List.of("pulsar-v26x.mixins.json");
   }

   public Engine createEngine(EngineContext context) {
      return new V26xEngine(this, context);
   }
}

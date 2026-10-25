package com.metrofico.pulsar.engine.v189;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.engine.EngineBackend;
import com.metrofico.pulsar.api.engine.EngineContext;
import com.metrofico.pulsar.api.model.VersionRange;
import com.metrofico.pulsar.api.pack.Capability;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public final class V189Backend implements EngineBackend {
   public static final String ID = "engine-v1_8_9";
   public static final Set<Capability> CAPABILITIES = Collections.unmodifiableSet(
      EnumSet.of(
         Capability.UI_PANELS,
         Capability.UI_TABLIST,
         Capability.UI_COMPASS,
         Capability.UI_MENU,
         Capability.UI_MAP,
         Capability.UI_NOTIFICATIONS,
         Capability.WORLD_MARKERS,
         Capability.XP_KEYBINDS,
         Capability.CAMERA_CINEMATIC,
         Capability.XP_COMBAT,
         Capability.XP_EMOTES
      )
   );
   public static final Set<String> PERF_PARAMS = Set.of(
      "perf.particleBudget",
      "perf.particleDistance",
      "perf.pacer",
      "perf.diagnostics",
      "perf.asyncNearChunks",
      "perf.smartLeaves",
      "perf.tabCache",
      "perf.sectionMatrix",
      "perf.textureUpload",
      "perf.batchDeletes",
      "perf.lazyBuffers",
      "perf.signText",
      "perf.signBatch",
      "perf.animDistance",
      "perf.textCache",
      "perf.terrainRegions",
      "perf.faceCulling",
      "perf.terrainMultidraw",
      "perf.compactVertices",
      "perf.gpuOcclusion",
      "perf.entityBatch",
      "perf.uiBatch",
      "perf.cloudCache",
      "perf.visibility",
      "perf.labelBatch",
      "perf.entityLod",
      "perf.entityCache",
      "perf.entityCacheMaps",
      "perf.spriteCulling",
      "perf.fastMath",
      "perf.chunkUpdates",
      "perf.dynamicUpdates",
      "perf.chunkThreads",
      "perf.fog",
      "perf.sky",
      "perf.sunMoon",
      "perf.stars",
      "perf.vignette",
      "perf.weather",
      "perf.animWater",
      "perf.animLava",
      "perf.animFire",
      "perf.animPortal",
      "perf.cullEntities",
      "perf.cullPlayers",
      "perf.cullBlockEntities",
      "perf.itemLod",
      "perf.itemLodDistance",
      "perf.itemLodCell",
      "perf.itemRange",
      "perf.beDistance",
      "perf.particles",
      "perf.shadows",
      "perf.clouds",
      "perf.limitDistance",
      "perf.renderDistance",
      "perf.smoothLighting",
      "perf.vsync",
      "perf.fpsLimit"
   );

   public String id() {
      return "engine-v1_8_9";
   }

   public VersionRange supportedVersions() {
      return VersionRange.of("1.8.9", "1.8.9");
   }

   public List<String> mixinConfigs() {
      return List.of("pulsar-v1_8_9.mixins.json");
   }

   public Engine createEngine(EngineContext context) {
      return new Engine189(this, context);
   }

   public Set<Capability> capabilities() {
      return CAPABILITIES;
   }
}

package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.GpuDeviceAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class PerfStats {
   static final AtomicInteger ENTITIES_SEEN = new AtomicInteger();
   static final AtomicInteger ENTITIES_CULLED = new AtomicInteger();
   static final AtomicInteger BLOCK_ENTITIES_SEEN = new AtomicInteger();
   static final AtomicInteger BLOCK_ENTITIES_CULLED = new AtomicInteger();
   static final AtomicLong RAYS = new AtomicLong();
   private static volatile int terrainSections;
   private static volatile int terrainDraws;
   private static int lastTerrainSections;
   private static int lastTerrainDraws;
   private static volatile long terrainQuads;
   private static volatile long terrainCulledQuads;
   private static long lastTerrainQuads;
   private static long lastTerrainCulledQuads;
   private static volatile int terrainCompact;
   private static int lastTerrainCompact;
   private static volatile int terrainFogCulled;
   private static int lastTerrainFogCulled;
   static final AtomicInteger PARTICLES_DROPPED = new AtomicInteger();
   private static volatile PerfStats.Snapshot snapshot = new PerfStats.Snapshot(0, 0, 0, 0, 0L, 0, 0, 0.0);
   private static long lastSecond = System.nanoTime();
   private static int secondsLogged;
   private static int frameEntitiesSeen;
   private static int frameEntitiesCulled;
   private static int frameBeSeen;
   private static int frameBeCulled;
   private static float[] frameTimes = new float[2048];
   private static int frameTimeCount;
   private static volatile double p99Ms;
   private static volatile double worstMs;
   private static volatile int hitchesPerSecond;
   private static final List<GarbageCollectorMXBean> GCS = ManagementFactory.getGarbageCollectorMXBeans();
   private static long gcCountBase = -1L;
   private static long gcTimeBase;
   private static volatile int gcPerSecond;
   private static volatile long gcMsPerSecond;
   private static long presentStart;
   private static volatile double lastPresentMs;
   public static final String[] PHASES = new String[]{"extract", "level", "gui", "particles", "upload", "entes", "rasgos", "dibujo", "letreros", "modelos"};
   private static final long[] PHASE_START = new long[PHASES.length];
   private static final double[] PHASE_ACC = new double[PHASES.length];
   private static final double[] PHASE_AVG = new double[PHASES.length];

   private PerfStats() {
   }

   public static void terrain(int sections, int draws, long quads, long culledQuads, int compactSections, int fogCulled) {
      terrainFogCulled = fogCulled;
      terrainCompact = compactSections;
      terrainSections = sections;
      terrainDraws = draws;
      terrainQuads = quads;
      terrainCulledQuads = culledQuads;
   }

   public static double p99Ms() {
      return p99Ms;
   }

   public static double worstMs() {
      return worstMs;
   }

   public static int hitchesPerSecond() {
      return hitchesPerSecond;
   }

   private static void sampleGc() {
      long count = 0L;
      long time = 0L;

      for (GarbageCollectorMXBean gc : GCS) {
         count += Math.max(0L, gc.getCollectionCount());
         time += Math.max(0L, gc.getCollectionTime());
      }

      if (gcCountBase >= 0L) {
         gcPerSecond = (int)(count - gcCountBase);
         gcMsPerSecond = time - gcTimeBase;
      }

      gcCountBase = count;
      gcTimeBase = time;
   }

   public static int gcPerSecond() {
      return gcPerSecond;
   }

   public static long gcMsPerSecond() {
      return gcMsPerSecond;
   }

   private static void recordFrameTime(double frameMs, boolean closeSecond) {
      if (frameTimeCount == frameTimes.length) {
         frameTimes = Arrays.copyOf(frameTimes, frameTimes.length * 2);
      }

      frameTimes[frameTimeCount++] = (float)frameMs;
      if (closeSecond && frameTimeCount >= 2) {
         float[] sorted = Arrays.copyOf(frameTimes, frameTimeCount);
         Arrays.sort(sorted);
         double median = sorted[frameTimeCount / 2];
         p99Ms = sorted[Math.min(frameTimeCount - 1, (int)Math.ceil(frameTimeCount * 0.99) - 1)];
         worstMs = sorted[frameTimeCount - 1];
         int hitches = 0;

         for (int i = frameTimeCount - 1; i >= 0 && sorted[i] > median * 3.0; i--) {
            hitches++;
         }

         hitchesPerSecond = hitches;
         frameTimeCount = 0;
      }
   }

   public static void presentBegin() {
      presentStart = System.nanoTime();
   }

   public static void presentEnd() {
      if (presentStart != 0L) {
         lastPresentMs = (System.nanoTime() - presentStart) / 1000000.0;
         presentStart = 0L;
      }
   }

   public static void frame(int particles, double frameMs) {
      closePhases();
      FramePacer.frame(frameMs, lastPresentMs);
      lastTerrainSections = terrainSections;
      lastTerrainDraws = terrainDraws;
      lastTerrainQuads = terrainQuads;
      lastTerrainCompact = terrainCompact;
      lastTerrainFogCulled = terrainFogCulled;
      terrainFogCulled = 0;
      terrainCompact = 0;
      lastTerrainCulledQuads = terrainCulledQuads;
      terrainQuads = 0L;
      terrainCulledQuads = 0L;
      terrainSections = 0;
      terrainDraws = 0;
      frameEntitiesSeen = ENTITIES_SEEN.getAndSet(0);
      frameEntitiesCulled = ENTITIES_CULLED.getAndSet(0);
      frameBeSeen = BLOCK_ENTITIES_SEEN.getAndSet(0);
      frameBeCulled = BLOCK_ENTITIES_CULLED.getAndSet(0);
      long now = System.nanoTime();
      PerfStats.Snapshot s = snapshot;
      long rays = s.raysPerSecond();
      int dropped = s.particlesDroppedPerSecond();
      boolean second = now - lastSecond >= 1000000000L;
      recordFrameTime(frameMs, second);
      if (second) {
         lastSecond = now;
         sampleGc();
         NetStats.closeSecond();
         if (++secondsLogged % 5 == 0) {
            BootstrapLog.info("perf: " + line() + " | " + phasesLine());
         }

         rays = RAYS.getAndSet(0L);
         dropped = PARTICLES_DROPPED.getAndSet(0);
      }

      snapshot = new PerfStats.Snapshot(frameEntitiesSeen, frameEntitiesCulled, frameBeSeen, frameBeCulled, rays, dropped, particles, frameMs);
   }

   public static void entitySeen() {
      ENTITIES_SEEN.incrementAndGet();
   }

   public static void entityCulled() {
      ENTITIES_CULLED.incrementAndGet();
   }

   public static void blockEntitySeen() {
      BLOCK_ENTITIES_SEEN.incrementAndGet();
   }

   public static void blockEntityCulled() {
      BLOCK_ENTITIES_CULLED.incrementAndGet();
   }

   public static void begin(int phase) {
      if (RenderSystem.isOnRenderThread()) {
         PHASE_START[phase] = System.nanoTime();
      }
   }

   public static void end(int phase) {
      if (PHASE_START[phase] != 0L && RenderSystem.isOnRenderThread()) {
         PHASE_ACC[phase] = PHASE_ACC[phase] + (System.nanoTime() - PHASE_START[phase]) / 1000000.0;
         PHASE_START[phase] = 0L;
      }
   }

   private static void closePhases() {
      for (int i = 0; i < PHASES.length; i++) {
         PHASE_AVG[i] = PHASE_AVG[i] + (PHASE_ACC[i] - PHASE_AVG[i]) / 32.0;
         PHASE_ACC[i] = 0.0;
      }
   }

   public static String phasesLine() {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < PHASES.length; i++) {
         sb.append(i > 0 ? " · " : "").append(PHASES[i]).append(' ').append(String.format(Locale.ROOT, "%.2f", PHASE_AVG[i]));
      }

      return sb.append(" ms").toString();
   }

   public static PerfStats.Snapshot snapshot() {
      return snapshot;
   }

   public static String backendClass() {
      Object device = RenderSystem.getDevice();
      return device instanceof GpuDeviceAccessor a && a.pulsar$backend() != null ? a.pulsar$backend().getClass().getName() : device.getClass().getName();
   }

   public static String backend() {
      try {
         String n = backendClass();
         return n.contains(".vulkan.") ? "VK" : (n.contains(".opengl.") ? "GL" : "?");
      } catch (Throwable var1) {
         return "?";
      }
   }

   public static String line() {
      PerfStats.Snapshot s = snapshot;
      String rays = s.raysPerSecond() >= 1000L ? String.format(Locale.ROOT, "%.1fk", s.raysPerSecond() / 1000.0) : Long.toString(s.raysPerSecond());
      return backend()
         + " · E "
         + (s.entitiesSeen() - s.entitiesCulled())
         + "/"
         + s.entitiesSeen()
         + " · BE "
         + (s.blockEntitiesSeen() - s.blockEntitiesCulled())
         + "/"
         + s.blockEntitiesSeen()
         + " · P "
         + s.particles()
         + (s.particlesDroppedPerSecond() > 0 ? " (−" + s.particlesDroppedPerSecond() + "/s)" : "")
         + (lastTerrainDraws > 0 ? " · T " + lastTerrainSections + "→" + lastTerrainDraws + " draws" : " · T vanilla")
         + (lastTerrainQuads > 0L ? " · caras −" + lastTerrainCulledQuads * 100L / lastTerrainQuads + "%" : "")
         + (lastTerrainCompact > 0 ? " · 16B " + lastTerrainCompact * 100 / Math.max(1, lastTerrainSections) + "%" : "")
         + (lastTerrainFogCulled > 0 ? " · niebla −" + lastTerrainFogCulled : "")
         + (
            SpriteCulling.lastTicked() + SpriteCulling.lastSkipped() > 0
               ? " · S " + SpriteCulling.lastTicked() + "/" + (SpriteCulling.lastTicked() + SpriteCulling.lastSkipped())
               : ""
         )
         + PoseCache26.line()
         + NameOnly26.line()
         + ParallelFeatures.lanes((int)(5000.0 / Math.max(0.05, s.frameMs())))
         + (ItemLod.seen() > 0 ? " · I " + (ItemLod.seen() - ItemLod.grouped()) + "/" + ItemLod.seen() : "")
         + " · "
         + rays
         + " rayos/s · "
         + String.format(Locale.ROOT, "%.2f", s.frameMs())
         + " ms"
         + String.format(Locale.ROOT, " (p99 %.1f · peor %.1f", p99Ms, worstMs)
         + (hitchesPerSecond > 0 ? " · " + hitchesPerSecond + " tirones/s)" : ")")
         + NetStats.line()
         + (gcPerSecond > 0 ? " · GC " + gcPerSecond + "×/s " + gcMsPerSecond + " ms" : "")
         + " · "
         + FramePacer.line();
   }

   public record Snapshot(
      int entitiesSeen,
      int entitiesCulled,
      int blockEntitiesSeen,
      int blockEntitiesCulled,
      long raysPerSecond,
      int particlesDroppedPerSecond,
      int particles,
      double frameMs
   ) {
   }
}

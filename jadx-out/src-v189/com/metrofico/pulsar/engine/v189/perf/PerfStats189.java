package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.net.NetMeter;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.api.perf.FrameStats;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import org.lwjgl.opengl.Display;

public final class PerfStats189 {
   public static final AtomicInteger ENTITIES_SEEN = new AtomicInteger();
   public static final AtomicInteger ENTITIES_CULLED = new AtomicInteger();
   public static final AtomicInteger PLAYERS_NAME_ONLY = new AtomicInteger();
   public static final AtomicInteger BLOCK_ENTITIES_SEEN = new AtomicInteger();
   public static final AtomicInteger BLOCK_ENTITIES_CULLED = new AtomicInteger();
   private static volatile int particles;
   private static volatile int seen;
   private static volatile int culled;
   private static volatile int beSeen;
   private static volatile int beCulled;
   private static volatile int nameOnly;
   private static volatile int droppedPerSecond;
   private static volatile long raysPerSecond;
   private static long droppedBase;
   private static long raysBase;
   private static int seconds;
   public static final String[] PHASES = new String[]{
      "terreno", "entes", "partículas", "cielo", "chunks", "hud", "swap", "tick", "visibilidad", "nubes", "mano", "paquetes", "resto"
   };
   public static final int PACKETS = 11;
   private static final int RESTO = 12;
   private static final long[] PHASE_START = new long[PHASES.length];
   private static final long[] PHASE_ACC = new long[PHASES.length];
   private static final double[] PHASE_MS = new double[PHASES.length];
   public static volatile double lastSwapMs;
   private static final long[] WORST_NOW = new long[PHASES.length];
   private static final long[] LAST_FRAME = new long[PHASES.length];
   private static volatile String worstLine = "";
   private static double windowWorstMs;
   private static String windowWorstLine = "";
   public static final String[] TICK_SUB = new String[]{"red", "entes", "mundo", "luz"};
   private static final long[] SUB_START = new long[TICK_SUB.length];
   private static final long[] SUB_ACC = new long[TICK_SUB.length];
   private static final long[] SUB_LAST = new long[TICK_SUB.length];
   private static final long[] SUB_WORST = new long[TICK_SUB.length];
   private static long hudPulsarNanos;
   private static long hudFrames;
   private static List<String> cachedLines = List.of();
   private static long cachedAt;

   private PerfStats189() {
   }

   public static void frame(int particleCount, double frameMs) {
      closePhases(frameMs);
      TerrainRegions189.frame();
      particles = particleCount;
      seen = ENTITIES_SEEN.getAndSet(0);
      culled = ENTITIES_CULLED.getAndSet(0);
      nameOnly = PLAYERS_NAME_ONLY.getAndSet(0);
      beSeen = BLOCK_ENTITIES_SEEN.getAndSet(0);
      beCulled = BLOCK_ENTITIES_CULLED.getAndSet(0);
      boolean closedSecond = FrameStats.frame(frameMs);
      trackWorst(closedSecond);
      if (closedSecond) {
         FrameStats.focus(Display.isActive());
         long dropped = ParticleBudget189.DROPPED.get();
         droppedPerSecond = (int)(dropped - droppedBase);
         droppedBase = dropped;
         long rays = OcclusionCuller189.RAYS.get();
         raysPerSecond = rays - raysBase;
         raysBase = rays;
         NetMeter.CLIENT.closeSecond(System.nanoTime());
         if (++seconds % 5 == 0) {
            String net = NetMeter.CLIENT.takeLogLine();
            String hitches = Hitches189.takeLine();
            if (ClientOptions.get().enabled("perf")) {
               ClientOptions.LOG.accept("perf: " + line() + net + hitches + " | " + phasesLine(windowWorstLine, windowWorstMs) + " ms");
            }

            windowWorstMs = 0.0;
            windowWorstLine = "";
         }
      }
   }

   public static String line() {
      String rays = raysPerSecond >= 1000L ? String.format(Locale.ROOT, "%.1fk", raysPerSecond / 1000.0) : Long.toString(raysPerSecond);
      return "1.8.9 · E "
         + (seen - culled)
         + "/"
         + seen
         + (nameOnly > 0 ? " (solo nombre " + nameOnly + ")" : "")
         + " · BE "
         + (beSeen - beCulled)
         + "/"
         + beSeen
         + " · P "
         + particles
         + (droppedPerSecond > 0 ? " (−" + droppedPerSecond + "/s)" : "")
         + (EntityBatch189.lastParts > 0 ? " · piezas " + EntityBatch189.lastParts + "→" + EntityBatch189.lastFlushes : "")
         + (
            EntityCache189.lastReplayed + EntityCache189.lastCaptured > 0
               ? " · caché "
                  + EntityCache189.lastReplayed
                  + " repro/"
                  + EntityCache189.lastCaptured
                  + " capt/"
                  + EntityCache189.lastLive
                  + " vivo · "
                  + EntityCache189.lastBatches
                  + " lotes · "
                  + EntityCache189.averages()
                  + (EntityCache189.lastLive > 0 ? " [" + EntityCache189.lastWhy + "]" : "")
               : ""
         )
         + (EntityLod189.lastReduced > 0 ? " · lod " + EntityLod189.lastReduced + " (−" + EntityBatch189.lastQuadsCulledPct + "% caras)" : "")
         + (LabelBatch189.lastLabels > 0 ? " · letreros " + LabelBatch189.lastLabels + "→" + LabelBatch189.lastDraws : "")
         + (Visibility189.lastVisible > 0 ? " · V " + Visibility189.lastVisible + Visibility189.searchLine(1) : "")
         + (Clouds189.rebuilds > 0 ? " · nubes " + Clouds189.rebuilds + " mallas" : "")
         + (Canvas189.lastQuads > 0 ? " · gui " + Canvas189.lastQuads + "→" + Canvas189.lastDraws : "")
         + (ItemLod189.seen() > 0 ? " · I " + (ItemLod189.seen() - ItemLod189.grouped()) + "/" + ItemLod189.seen() : "")
         + (
            TerrainRegions189.lastSections > 0
               ? " · T "
                  + TerrainRegions189.lastSections
                  + "→"
                  + TerrainRegions189.lastBinds
                  + " binds"
                  + (TerrainRegions189.lastIndirectCalls > 0 ? " · " + TerrainRegions189.lastIndirectCalls + " MDI" : "")
                  + (GpuOcclusion189.lastHidden > 0 ? " · oclusión −" + GpuOcclusion189.lastHidden : "")
                  + (
                     TerrainRegions189.lastCompactSections > 0
                        ? " · 16B " + TerrainRegions189.lastCompactSections * 100 / Math.max(1, TerrainRegions189.lastSections) + "%"
                        : ""
                  )
                  + (TerrainRegions189.lastFacesCulledPct > 0 ? " · caras −" + TerrainRegions189.lastFacesCulledPct + "%" : "")
               : " · T vanilla"
         )
         + (
            SpriteCulling189.lastTicked() + SpriteCulling189.lastSkipped() > 0
               ? " · S " + SpriteCulling189.lastTicked() + "/" + (SpriteCulling189.lastTicked() + SpriteCulling189.lastSkipped())
               : ""
         )
         + " · "
         + rays
         + " rayos/s · "
         + FrameStats.line()
         + NearBuilds189.line()
         + SectionDirt189.line()
         + SignText189.line()
         + SignBoards189.line()
         + " · "
         + FramePacer.line();
   }

   public static void begin(int phase) {
      PHASE_START[phase] = System.nanoTime();
   }

   public static void end(int phase) {
      if (PHASE_START[phase] != 0L) {
         if (phase == 6) {
            lastSwapMs = (System.nanoTime() - PHASE_START[phase]) / 1000000.0;
         }

         PHASE_ACC[phase] = PHASE_ACC[phase] + (System.nanoTime() - PHASE_START[phase]);
         PHASE_START[phase] = 0L;
      }
   }

   public static void subBegin(int i) {
      SUB_START[i] = System.nanoTime();
   }

   public static void subEnd(int i) {
      if (SUB_START[i] != 0L) {
         SUB_ACC[i] = SUB_ACC[i] + (System.nanoTime() - SUB_START[i]);
         SUB_START[i] = 0L;
      }
   }

   private static void trackWorst(boolean closedSecond) {
      if (FrameStats.worstFrameSoFar()) {
         System.arraycopy(LAST_FRAME, 0, WORST_NOW, 0, LAST_FRAME.length);
         System.arraycopy(SUB_LAST, 0, SUB_WORST, 0, SUB_LAST.length);
      }

      if (closedSecond) {
         StringBuilder sb = new StringBuilder();

         for (int pass = 0; pass < 3; pass++) {
            int best = -1;

            for (int i = 0; i < PHASES.length; i++) {
               if (WORST_NOW[i] > 0L && (best < 0 || WORST_NOW[i] > WORST_NOW[best])) {
                  best = i;
               }
            }

            if (best < 0) {
               break;
            }

            sb.append(pass == 0 ? "" : " · ").append(PHASES[best]).append(' ').append(String.format(Locale.ROOT, "%.1f", WORST_NOW[best] / 1000000.0));
            WORST_NOW[best] = 0L;
         }

         long subTotal = 0L;

         for (long v : SUB_WORST) {
            subTotal += v;
         }

         if (subTotal > 500000L) {
            sb.append(" (tick: ");

            for (int ix = 0; ix < TICK_SUB.length; ix++) {
               sb.append(ix == 0 ? "" : " · ").append(TICK_SUB[ix]).append(' ').append(String.format(Locale.ROOT, "%.1f", SUB_WORST[ix] / 1000000.0));
            }

            sb.append(')');
         }

         worstLine = sb.toString();
         double wall = FrameStats.worstFrameWallMs();
         if (wall >= windowWorstMs) {
            windowWorstMs = wall;
            windowWorstLine = worstLine;
         }

         Arrays.fill(WORST_NOW, 0L);
         Arrays.fill(SUB_WORST, 0L);
      }
   }

   public static String worstLine() {
      return worstLine;
   }

   private static void closePhases(double frameMs) {
      long measured = 0L;

      for (int i = 0; i < 12; i++) {
         measured += PHASE_ACC[i];
      }

      PHASE_ACC[12] = Math.max(0L, (long)(frameMs * 1000000.0) - measured);
      System.arraycopy(PHASE_ACC, 0, LAST_FRAME, 0, PHASE_ACC.length);
      System.arraycopy(SUB_ACC, 0, SUB_LAST, 0, SUB_ACC.length);
      Arrays.fill(SUB_ACC, 0L);

      for (int i = 0; i < PHASES.length; i++) {
         PHASE_MS[i] = PHASE_MS[i] * 0.9 + PHASE_ACC[i] / 1000000.0 * 0.1;
         PHASE_ACC[i] = 0L;
      }
   }

   public static void hudPulsar(long nanos) {
      hudPulsarNanos += nanos;
      hudFrames++;
   }

   public static String phasesLine() {
      return phasesLine(worstLine, -1.0);
   }

   private static String phasesLine(String worst, double worstMs) {
      StringBuilder sb = new StringBuilder();
      if (worstMs >= 0.0) {
         sb.append("peor frame en 5 s: ")
            .append(String.format(Locale.ROOT, "%.1f", worstMs))
            .append(" ms")
            .append(worst.isEmpty() ? "" : " → " + worst + " ms")
            .append(" || ");
      } else if (!worst.isEmpty()) {
         sb.append("peor frame: ").append(worst).append(" ms || ");
      }

      for (int i = 0; i < PHASES.length; i++) {
         if (i > 0) {
            sb.append(" · ");
         }

         sb.append(PHASES[i]).append(' ').append(String.format(Locale.ROOT, "%.2f", PHASE_MS[i]));
         if (i == 5 && hudFrames > 0L) {
            sb.append(hudLine());
         }
      }

      return sb.toString();
   }

   private static String hudLine() {
      double frames = hudFrames;
      StringBuilder sb = new StringBuilder(String.format(Locale.ROOT, " (pulsar %.2f", hudPulsarNanos / 1000000.0 / frames));
      Map<String, Long> modules = HudModules.takeDrawNanos();
      String sep = ": ";

      for (Entry<String, Long> e : modules.entrySet().stream().sorted(Entry.<String, Long>comparingByValue().reversed()).limit(3L).toList()) {
         sb.append(sep).append(e.getKey()).append(' ').append(String.format(Locale.ROOT, "%.2f", e.getValue().longValue() / 1000000.0 / frames));
         sep = " · ";
      }

      hudPulsarNanos = 0L;
      hudFrames = 0L;
      return sb.append(')').toString();
   }

   public static List<String> lines() {
      long now = System.nanoTime();
      if (now - cachedAt > 250000000L) {
         cachedAt = now;
         String net = NetMeter.CLIENT.line();
         cachedLines = net.isEmpty() ? List.of(line(), phasesLine()) : List.of(line(), phasesLine(), net.substring(3));
      }

      return cachedLines;
   }
}

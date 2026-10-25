package com.metrofico.pulsar.api.perf;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class FrameStats {
   private static float[] frameTimes = new float[2048];
   private static int frameTimeCount;
   private static volatile double p99Ms;
   private static volatile double worstMs;
   private static volatile int hitchesPerSecond;
   private static volatile double lastFrameMs;
   private static long lastSecond = System.nanoTime();
   private static long lastFrameNanos = -1L;
   private static final List<GarbageCollectorMXBean> GCS = ManagementFactory.getGarbageCollectorMXBeans();
   private static long gcCountBase = -1L;
   private static long gcTimeBase;
   private static long gcCountAtStart = -1L;
   private static long gcTimeAtStart;
   private static volatile int gcPerSecond;
   private static volatile long gcMsPerSecond;
   private static final ThreadMXBean THREADS;
   private static long allocBase = -1L;
   private static long allocSecondFrames;
   private static final boolean CPU_TIME;
   private static final OperatingSystemMXBean OS;
   private static long cpuLast = -1L;
   private static long cpuSecondBase = -1L;
   private static double worstWallThisSecond;
   private static double worstCpuThisSecond;
   private static volatile double cpuMsPerFrame;
   private static volatile double worstFrameCpuMs;
   private static volatile double worstFrameWallMs;
   private static volatile int processCpuPct = -1;
   private static volatile int systemCpuPct = -1;
   private static volatile boolean worstFrameNow;
   private static final boolean[] UNFOCUSED = new boolean[5];
   private static final int[] GPU_BUSY = new int[]{-1, -1, -1, -1, -1};
   private static int focusSlot;
   private static int gpuSlot;
   private static volatile boolean gpuStarted;
   private static final int SESSION_CAP = 7200;
   private static float[] sessionMedians = new float[7200];
   private static int sessionMediansCount;
   private static double sessionP99Ms;
   private static double sessionWorstMs;
   private static long sessionStartMs = System.currentTimeMillis();
   private static long sessionGcCount;
   private static long sessionGcMs;
   private static volatile long allocKbPerFrame;

   public static boolean worstFrameSoFar() {
      return worstFrameNow;
   }

   public static double worstFrameCpuMs() {
      return worstFrameCpuMs;
   }

   public static double worstFrameWallMs() {
      return worstFrameWallMs;
   }

   private FrameStats() {
   }

   public static double frameStart() {
      long now = System.nanoTime();
      double ms = lastFrameNanos < 0L ? 0.0 : (now - lastFrameNanos) / 1000000.0;
      lastFrameNanos = now;
      return ms;
   }

   public static boolean frame(double frameMs) {
      lastFrameMs = frameMs;
      long now = System.nanoTime();
      boolean second = now - lastSecond >= 1000000000L;
      if (frameTimeCount == frameTimes.length) {
         frameTimes = Arrays.copyOf(frameTimes, frameTimes.length * 2);
      }

      frameTimes[frameTimeCount++] = (float)frameMs;
      worstFrameNow = false;
      if (CPU_TIME) {
         long cpu = THREADS.getCurrentThreadCpuTime();
         double cpuMs = cpuLast < 0L ? 0.0 : (cpu - cpuLast) / 1000000.0;
         cpuLast = cpu;
         if (frameMs > worstWallThisSecond) {
            worstWallThisSecond = frameMs;
            worstCpuThisSecond = cpuMs;
            worstFrameNow = true;
         }

         if (second) {
            if (cpuSecondBase >= 0L && frameTimeCount > 0) {
               cpuMsPerFrame = (cpu - cpuSecondBase) / 1000000.0 / frameTimeCount;
            }

            cpuSecondBase = cpu;
            worstFrameCpuMs = worstCpuThisSecond;
            worstFrameWallMs = worstWallThisSecond;
            worstWallThisSecond = 0.0;
            worstCpuThisSecond = 0.0;
            if (OS != null) {
               double proc = OS.getProcessCpuLoad();
               double sys = OS.getCpuLoad();
               int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
               processCpuPct = proc < 0.0 ? -1 : (int)Math.round(proc * cores * 100.0);
               systemCpuPct = sys < 0.0 ? -1 : (int)Math.round(sys * 100.0);
            }
         }
      }

      if (second) {
         lastSecond = now;
         if (THREADS != null) {
            long alloc = THREADS.getCurrentThreadAllocatedBytes();
            if (allocBase >= 0L && frameTimeCount > 0) {
               allocKbPerFrame = (alloc - allocBase) / 1024L / frameTimeCount;
            }

            allocBase = alloc;
         }

         if (frameTimeCount >= 2) {
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
            if (sessionMediansCount < 7200) {
               sessionMedians[sessionMediansCount++] = (float)median;
            }

            if (p99Ms > sessionP99Ms) {
               sessionP99Ms = p99Ms;
            }

            if (worstMs > sessionWorstMs) {
               sessionWorstMs = worstMs;
            }
         }

         frameTimeCount = 0;
         sampleGc();
      }

      return second;
   }

   private static void sampleGc() {
      long count = 0L;
      long time = 0L;

      for (GarbageCollectorMXBean gc : GCS) {
         count += Math.max(0L, gc.getCollectionCount());
         time += Math.max(0L, gc.getCollectionTime());
      }

      if (gcCountBase >= 0L && count >= gcCountBase && time >= gcTimeBase) {
         sessionGcCount = count - firstGcCount();
         sessionGcMs = time - firstGcTime();
      }

      if (gcCountBase >= 0L) {
         gcPerSecond = (int)(count - gcCountBase);
         gcMsPerSecond = time - gcTimeBase;
      }

      gcCountBase = count;
      gcTimeBase = time;
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

   public static int gcPerSecond() {
      return gcPerSecond;
   }

   public static long gcMsPerSecond() {
      return gcMsPerSecond;
   }

   private static long firstGcCount() {
      return gcCountAtStart < 0L ? 0L : gcCountAtStart;
   }

   private static long firstGcTime() {
      return gcTimeAtStart < 0L ? 0L : gcTimeAtStart;
   }

   public static Map<String, Object> sessionSummary() {
      int n = sessionMediansCount;
      Map<String, Object> fps = new LinkedHashMap<>();
      if (n == 0) {
         fps.put("median", 0);
         fps.put("p99", 0);
         fps.put("p1", 0);
         fps.put("worstFrameMs", 0);
      } else {
         float[] sorted = Arrays.copyOf(sessionMedians, n);
         Arrays.sort(sorted);
         double medianMs = sorted[n / 2];
         double p99Ms2 = sessionP99Ms;
         double p1Ms = sorted[n - 1];
         fps.put("median", medianMs > 0.0 ? (int)Math.round(1000.0 / medianMs) : 0);
         fps.put("p99", p99Ms2 > 0.0 ? (int)Math.round(1000.0 / p99Ms2) : 0);
         fps.put("p1", p1Ms > 0.0 ? (int)Math.round(1000.0 / p1Ms) : 0);
         fps.put("worstFrameMs", Math.round(sessionWorstMs * 10.0) / 10.0);
      }

      long durationMin = Math.max(0L, (System.currentTimeMillis() - sessionStartMs) / 60000L);
      double gcMsPerSec = Math.max(1.0, (System.currentTimeMillis() - sessionStartMs) / 1000.0);
      Map<String, Object> out = new LinkedHashMap<>();
      out.put("durationMin", (int)durationMin);
      out.put("fps", fps);
      out.put("gcMsPerSec", Math.round(sessionGcMs / gcMsPerSec * 100.0) / 100.0);
      return out;
   }

   public static void focus(boolean active) {
      synchronized (UNFOCUSED) {
         UNFOCUSED[focusSlot] = !active;
         focusSlot = (focusSlot + 1) % UNFOCUSED.length;
      }
   }

   private static int unfocusedSeconds() {
      int n = 0;
      synchronized (UNFOCUSED) {
         for (boolean u : UNFOCUSED) {
            n += u ? 1 : 0;
         }

         return n;
      }
   }

   private static int gpuBusy() {
      startGpuSampler();
      int sum = 0;
      int n = 0;
      synchronized (GPU_BUSY) {
         for (int v : GPU_BUSY) {
            if (v >= 0) {
               sum += v;
               n++;
            }
         }
      }

      return n == 0 ? -1 : Math.round((float)sum / n);
   }

   private static void startGpuSampler() {
      if (!gpuStarted) {
         gpuStarted = true;
         Path file = gpuBusyFile();
         if (file != null) {
            Thread t = new Thread(() -> {
               while (true) {
                  int v;
                  try {
                     v = Integer.parseInt(Files.readString(file).trim());
                  } catch (Exception var6) {
                     return;
                  }

                  synchronized (GPU_BUSY) {
                     GPU_BUSY[gpuSlot] = v;
                     gpuSlot = (gpuSlot + 1) % GPU_BUSY.length;
                  }

                  try {
                     Thread.sleep(1000L);
                  } catch (InterruptedException var4) {
                     return;
                  }
               }
            }, "pulsar-gpu-busy");
            t.setDaemon(true);
            t.setPriority(1);
            t.start();
         }
      }
   }

   private static Path gpuBusyFile() {
      Path best = null;
      int bestValue = -1;

      try {
         try (DirectoryStream<Path> cards = Files.newDirectoryStream(Path.of("/sys/class/drm"), "card[0-9]*")) {
            for (Path card : cards) {
               Path f = card.resolve("device/gpu_busy_percent");

               try {
                  int v = Integer.parseInt(Files.readString(f).trim());
                  if (v > bestValue) {
                     bestValue = v;
                     best = f;
                  }
               } catch (Exception var8) {
               }
            }
         }

         return best;
      } catch (Exception var10) {
         return null;
      }
   }

   public static String line() {
      int unfocused = unfocusedSeconds();
      int gpu = gpuBusy();
      return String.format(Locale.ROOT, "%.2f ms (p99 %.1f · peor %.1f", lastFrameMs, p99Ms, worstMs)
         + (hitchesPerSecond > 0 ? " · " + hitchesPerSecond + " tirones/s)" : ")")
         + (gcPerSecond > 0 ? " · GC " + gcPerSecond + "×/s " + gcMsPerSecond + " ms" : "")
         + (allocKbPerFrame > 0L ? " · basura " + allocKbPerFrame + " KB/frame" : "")
         + (
            CPU_TIME
               ? String.format(Locale.ROOT, " · CPU %.2f ms/frame (peor frame: %.1f ms de CPU en %.1f ms)", cpuMsPerFrame, worstFrameCpuMs, worstFrameWallMs)
               : ""
         )
         + (processCpuPct >= 0 ? " · proceso " + processCpuPct + " % · sistema " + systemCpuPct + " %" : "")
         + (gpu >= 0 ? " · GPU " + gpu + " %" : "")
         + (unfocused > 0 ? " · sin foco " + unfocused + " s" : "");
   }

   static {
      THREADS = ManagementFactory.getThreadMXBean() instanceof ThreadMXBean t && t.isThreadAllocatedMemorySupported() ? t : null;
      CPU_TIME = THREADS != null && THREADS.isCurrentThreadCpuTimeSupported();
      OS = ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean o ? o : null;
   }
}

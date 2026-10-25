package com.metrofico.pulsar.launcher.launch;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record LaunchOptions(
   Path javaExecutable,
   int maxMemoryMb,
   Integer width,
   Integer height,
   List<String> extraJvmArgs,
   List<Path> extraClasspath,
   String mainClassOverride,
   List<Path> extraGameClasspath,
   String quickPlayServer
) {
   public LaunchOptions(
      Path javaExecutable,
      int maxMemoryMb,
      Integer width,
      Integer height,
      List<String> extraJvmArgs,
      List<Path> extraClasspath,
      String mainClassOverride,
      List<Path> extraGameClasspath
   ) {
      this(javaExecutable, maxMemoryMb, width, height, extraJvmArgs, extraClasspath, mainClassOverride, extraGameClasspath, null);
   }

   public LaunchOptions withQuickPlay(String server) {
      return new LaunchOptions(
         this.javaExecutable,
         this.maxMemoryMb,
         this.width,
         this.height,
         this.extraJvmArgs,
         this.extraClasspath,
         this.mainClassOverride,
         this.extraGameClasspath,
         server
      );
   }

   public static List<String> performanceFlags() {
      return performanceFlags(null);
   }

   public static List<String> performanceFlags(String gc) {
      String custom = System.getenv("PULSAR_JVM_FLAGS");
      if (custom != null && !custom.isBlank()) {
         return List.of(custom.trim().split("\\s+"));
      } else {
         List<String> flags = new ArrayList<>(
            List.of(
               "-XX:+IgnoreUnrecognizedVMOptions",
               "-XX:+UnlockExperimentalVMOptions",
               "-XX:+DisableExplicitGC",
               "-XX:+PerfDisableSharedMem",
               "-XX:+UseStringDeduplication",
               "-XX:+UseCompactObjectHeaders",
               "-XX:+AlwaysPreTouch",
               "-XX:+UseTransparentHugePages"
            )
         );
         String mode = System.getenv("PULSAR_GC");
         if (mode == null || mode.isBlank()) {
            mode = gc != null && !gc.isBlank() ? gc : "zgc";
         }

         if (!"zgc".equalsIgnoreCase(mode)) {
            flags.addAll(
               List.of(
                  "-XX:+UseG1GC",
                  "-XX:MaxGCPauseMillis=8",
                  "-XX:G1NewSizePercent=30",
                  "-XX:G1MaxNewSizePercent=60",
                  "-XX:G1HeapRegionSize=8M",
                  "-XX:G1ReservePercent=15",
                  "-XX:+ParallelRefProcEnabled"
               )
            );
         } else {
            flags.addAll(List.of("-XX:+UseZGC", "-XX:+ZGenerational"));
         }

         return List.copyOf(flags);
      }
   }

   public static int defaultMemoryMb() {
      try {
         OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
         long totalMb = os.getTotalMemorySize() / 1048576L;
         return defaultMemoryMb(totalMb, availableMemoryMb(os));
      } catch (Throwable var3) {
         return 4096;
      }
   }

   static int defaultMemoryMb(long totalMb, long availableMb) {
      long tier;
      if (totalMb <= 4096L) {
         tier = 2048L;
      } else if (totalMb <= 8192L) {
         tier = 4096L;
      } else if (totalMb <= 12288L) {
         tier = 6144L;
      } else if (totalMb <= 16384L) {
         tier = 8192L;
      } else if (totalMb <= 24576L) {
         tier = 10240L;
      } else if (totalMb <= 32768L) {
         tier = 12288L;
      } else {
         tier = 16384L;
      }

      long reserve = Math.max(3072L, totalMb / 8L);
      long byAvailable = availableMb > 0L ? availableMb - reserve : tier;
      return (int)Math.max(2048L, Math.min(tier, byAvailable));
   }

   static long availableMemoryMb(OperatingSystemMXBean os) {
      if (!isWindows()) {
         try {
            for (String line : Files.readAllLines(Path.of("/proc/meminfo"))) {
               if (line.startsWith("MemAvailable:")) {
                  return Long.parseLong(line.replaceAll("[^0-9]", "")) / 1024L;
               }
            }
         } catch (Exception var3) {
         }
      }

      return os.getFreeMemorySize() / 1048576L;
   }

   public static LaunchOptions defaults() {
      return defaults(null);
   }

   public static LaunchOptions defaults(String gc) {
      return new LaunchOptions(currentJava(), defaultMemoryMb(), null, null, performanceFlags(gc), List.of(), null, List.of());
   }

   public static Path currentJava() {
      return Path.of(System.getProperty("java.home"), "bin", isWindows() ? "java.exe" : "java");
   }

   private static boolean isWindows() {
      return System.getProperty("os.name").toLowerCase().contains("win");
   }

   public boolean hasCustomResolution() {
      return this.width != null && this.height != null;
   }

   public LaunchOptions withJava(Path java) {
      return new LaunchOptions(
         java,
         this.maxMemoryMb,
         this.width,
         this.height,
         this.extraJvmArgs,
         this.extraClasspath,
         this.mainClassOverride,
         this.extraGameClasspath,
         this.quickPlayServer
      );
   }

   public LaunchOptions withBootstrap(List<Path> jars, String mainClass) {
      return new LaunchOptions(
         this.javaExecutable,
         this.maxMemoryMb,
         this.width,
         this.height,
         this.extraJvmArgs,
         List.copyOf(jars),
         mainClass,
         this.extraGameClasspath,
         this.quickPlayServer
      );
   }

   public LaunchOptions withGameClasspath(List<Path> jars) {
      return new LaunchOptions(
         this.javaExecutable,
         this.maxMemoryMb,
         this.width,
         this.height,
         this.extraJvmArgs,
         this.extraClasspath,
         this.mainClassOverride,
         List.copyOf(jars),
         this.quickPlayServer
      );
   }

   public LaunchOptions withExtraJvmArgs(List<String> more) {
      List<String> all = new ArrayList<>(this.extraJvmArgs);
      all.addAll(more);
      return new LaunchOptions(
         this.javaExecutable,
         this.maxMemoryMb,
         this.width,
         this.height,
         List.copyOf(all),
         this.extraClasspath,
         this.mainClassOverride,
         this.extraGameClasspath,
         this.quickPlayServer
      );
   }

   public LaunchOptions withResolution(int w, int h) {
      return new LaunchOptions(
         this.javaExecutable,
         this.maxMemoryMb,
         w,
         h,
         this.extraJvmArgs,
         this.extraClasspath,
         this.mainClassOverride,
         this.extraGameClasspath,
         this.quickPlayServer
      );
   }
}

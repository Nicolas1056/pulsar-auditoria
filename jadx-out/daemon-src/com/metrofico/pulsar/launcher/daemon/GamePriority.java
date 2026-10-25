package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

public final class GamePriority {
   public static final Path SYSTEMD_RUN = Path.of("/usr/bin/systemd-run");
   public static final Path CGROUP2 = Path.of("/sys/fs/cgroup/cgroup.controllers");
   public static final int CPU_WEIGHT = 1000;

   private GamePriority() {
   }

   public static GamePriority.Applied wrap(List<String> commandLine, String unit) {
      return wrap(commandLine, unit, System.getProperty("os.name", ""), Files::isExecutable, x$0 -> Files.exists(x$0), System.getenv());
   }

   static GamePriority.Applied wrap(
      List<String> commandLine, String unit, String osName, Predicate<Path> executable, Predicate<Path> exists, Map<String, String> env
   ) {
      if ("off".equalsIgnoreCase(env.getOrDefault("PULSAR_GAME_PRIORITY", ""))) {
         return new GamePriority.Applied(List.copyOf(commandLine), "off (PULSAR_GAME_PRIORITY)");
      } else {
         String os = osName.toLowerCase(Locale.ROOT);
         boolean linux = os.contains("linux");
         if (!linux) {
            return new GamePriority.Applied(List.copyOf(commandLine), os.contains("win") ? "windows: High priority class after launch" : "none");
         } else if (executable.test(SYSTEMD_RUN) && exists.test(CGROUP2) && !env.getOrDefault("XDG_RUNTIME_DIR", "").isBlank()) {
            List<String> line = new ArrayList<>(commandLine.size() + 8);
            line.add(SYSTEMD_RUN.toString());
            line.add("--user");
            line.add("--scope");
            line.add("--quiet");
            line.add("--collect");
            line.add("--unit=" + unit);
            line.add("-p");
            line.add("CPUWeight=1000");
            line.add("-p");
            line.add("IOWeight=1000");
            line.addAll(commandLine);
            return new GamePriority.Applied(List.copyOf(line), "linux: systemd scope " + unit + " CPUWeight=1000");
         } else {
            return new GamePriority.Applied(List.copyOf(commandLine), "none (systemd-run/cgroup v2/user session not available)");
         }
      }
   }

   public static void boostWindows(long pid) {
      if (System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
         try {
            new ProcessBuilder(
                  "powershell", "-NoProfile", "-NonInteractive", "-WindowStyle", "Hidden", "-Command", "(Get-Process -Id " + pid + ").PriorityClass = 'High'"
               )
               .redirectErrorStream(true)
               .redirectOutput(Redirect.DISCARD)
               .start();
         } catch (IOException var3) {
         }
      }
   }

   public record Applied(List<String> commandLine, String mode) {
   }
}

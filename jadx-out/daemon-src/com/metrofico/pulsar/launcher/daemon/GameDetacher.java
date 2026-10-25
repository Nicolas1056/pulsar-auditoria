package com.metrofico.pulsar.launcher.daemon;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public final class GameDetacher {
   public static final Path SETSID = Path.of("/usr/bin/setsid");

   public static List<String> detach(List<String> commandLine) {
      return detach(commandLine, System.getProperty("os.name", ""), Files::isExecutable);
   }

   static List<String> detach(List<String> commandLine, String osName, Predicate<Path> executable) {
      String os = osName.toLowerCase(Locale.ROOT);
      boolean linux = os.contains("linux") || !os.contains("win") && !os.contains("mac") && !os.contains("darwin");
      if (linux && executable.test(SETSID)) {
         List<String> line = new ArrayList<>(commandLine.size() + 1);
         line.add(SETSID.toString());
         line.addAll(commandLine);
         return List.copyOf(line);
      } else {
         return List.copyOf(commandLine);
      }
   }

   private GameDetacher() {
   }
}

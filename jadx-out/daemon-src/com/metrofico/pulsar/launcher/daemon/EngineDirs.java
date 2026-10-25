package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

final class EngineDirs {
   static final List<String> SUPPORTED = List.of("26.2", "1.8.9");

   private EngineDirs() {
   }

   static boolean supported(String version) {
      return SUPPORTED.contains(version);
   }

   static String supportedFor(String version) {
      if (supported(version)) {
         return version;
      } else {
         String backend = backendFor(version);
         return SUPPORTED.stream().filter(v -> backendFor(v).equals(backend)).findFirst().orElse(SUPPORTED.getFirst());
      }
   }

   static String backendFor(String version) {
      return version != null && version.startsWith("1.8") ? "v1_8_9" : "v26x";
   }

   static Path resolve(Path engineRoot, Path repo, String version) {
      String backend = backendFor(version);
      if (engineRoot != null && Files.isDirectory(engineRoot)) {
         Path sub = engineRoot.resolve(backend);
         if (hasJars(sub)) {
            return sub.toAbsolutePath();
         } else {
            Path dev = sub.resolve("build/install/engine");
            if (hasJars(dev)) {
               return dev.toAbsolutePath();
            } else {
               return backend.equals("v26x") && hasJars(engineRoot) ? engineRoot.toAbsolutePath() : null;
            }
         }
      } else if (repo != null) {
         Path dev = repo.resolve("engine").resolve(backend).resolve("build/install/engine");
         return hasJars(dev) ? dev.toAbsolutePath() : null;
      } else {
         return null;
      }
   }

   private static boolean hasJars(Path dir) {
      if (!Files.isDirectory(dir)) {
         return false;
      } else {
         try {
            boolean var2;
            try (Stream<Path> s = Files.list(dir)) {
               var2 = s.anyMatch(p -> p.getFileName().toString().endsWith(".jar"));
            }

            return var2;
         } catch (IOException var6) {
            return false;
         }
      }
   }
}

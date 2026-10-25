package com.metrofico.pulsar.launcher.runtime;

import com.metrofico.pulsar.launcher.rules.Platform;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class JavaDetector {
   private final Platform platform;
   private final Path bundledHome;
   private final RuntimeDirectories managed;
   private final List<Path> systemRoots;
   private final Map<String, String> env;

   public JavaDetector(Platform platform, Path bundledHome, RuntimeDirectories managed, List<Path> systemRoots, Map<String, String> env) {
      this.platform = platform;
      this.bundledHome = bundledHome;
      this.managed = managed;
      this.systemRoots = List.copyOf(systemRoots);
      this.env = Map.copyOf(env);
   }

   public static JavaDetector current(RuntimeDirectories managed) {
      Platform platform = Platform.current();
      return new JavaDetector(platform, Path.of(System.getProperty("java.home")), managed, defaultSystemRoots(platform), System.getenv());
   }

   static List<Path> defaultSystemRoots(Platform platform) {
      String var1 = platform.os();

      return switch (var1) {
         case "windows" -> List.of(
            Path.of("C:\\Program Files\\Java"),
            Path.of("C:\\Program Files\\Eclipse Adoptium"),
            Path.of("C:\\Program Files\\Microsoft"),
            Path.of("C:\\Program Files\\Zulu")
         );
         case "osx" -> List.of(Path.of("/Library/Java/JavaVirtualMachines"), Path.of(System.getProperty("user.home"), "Library/Java/JavaVirtualMachines"));
         default -> List.of(
            Path.of("/usr/lib/jvm"), Path.of("/usr/java"), Path.of("/opt/java"), Path.of(System.getProperty("user.home"), ".sdkman/candidates/java")
         );
      };
   }

   public Optional<JavaRuntime> inspect(Path candidate, JavaRuntime.Source source) {
      Path home = this.normalizeHome(candidate);
      return home != null && Files.isRegularFile(this.executable(home))
         ? ReleaseFile.read(home).flatMap(r -> r.major().map(m -> new JavaRuntime(home, m, r.implementor(), source)))
         : Optional.empty();
   }

   public Optional<JavaRuntime> bundled() {
      return this.bundledHome == null ? Optional.empty() : this.inspect(this.bundledHome, JavaRuntime.Source.BUNDLED);
   }

   public List<JavaRuntime> managed() {
      List<JavaRuntime> found = new ArrayList<>();

      for (Path dir : children(this.managed.runtimes())) {
         if (Files.isRegularFile(RuntimeDirectories.marker(dir))) {
            this.inspect(dir, JavaRuntime.Source.MANAGED).ifPresent(found::add);
         }
      }

      return found;
   }

   public List<JavaRuntime> system() {
      Map<Path, JavaRuntime> found = new LinkedHashMap<>();
      List<Path> candidates = new ArrayList<>();
      String javaHome = this.env.get("JAVA_HOME");
      if (javaHome != null && !javaHome.isBlank()) {
         candidates.add(Path.of(javaHome));
      }

      String path = this.env.get("PATH");
      if (path != null) {
         for (String entry : path.split(Pattern.quote(File.pathSeparator))) {
            if (!entry.isBlank()) {
               candidates.add(Path.of(entry).resolve(this.executableName()));
            }
         }
      }

      for (Path root : this.systemRoots) {
         candidates.addAll(children(root));
      }

      for (Path c : candidates) {
         this.inspect(c, JavaRuntime.Source.SYSTEM).ifPresent(r -> found.putIfAbsent(realPath(r.home()), r));
      }

      return List.copyOf(found.values());
   }

   public List<JavaRuntime> all() {
      Map<Path, JavaRuntime> all = new LinkedHashMap<>();

      for (JavaRuntime r : this.managed()) {
         all.putIfAbsent(realPath(r.home()), r);
      }

      this.bundled().ifPresent(rx -> all.putIfAbsent(realPath(rx.home()), rx));

      for (JavaRuntime r : this.system()) {
         all.putIfAbsent(realPath(r.home()), r);
      }

      return List.copyOf(all.values());
   }

   private Path normalizeHome(Path candidate) {
      if (candidate == null) {
         return null;
      } else {
         Path p = candidate;
         if (Files.isRegularFile(candidate)
            && candidate.getFileName().toString().startsWith("java")
            && candidate.getParent() != null
            && "bin".equals(candidate.getParent().getFileName().toString())) {
            p = candidate.getParent().getParent();
         }

         Path macHome = p.resolve("Contents").resolve("Home");
         if (Files.isDirectory(macHome)) {
            p = macHome;
         }

         return p;
      }
   }

   private String executableName() {
      return "windows".equals(this.platform.os()) ? "java.exe" : "java";
   }

   private Path executable(Path home) {
      return home.resolve("bin").resolve(this.executableName());
   }

   private static List<Path> children(Path dir) {
      if (!Files.isDirectory(dir)) {
         return List.of();
      } else {
         try {
            List var2;
            try (Stream<Path> s = Files.list(dir)) {
               var2 = s.filter(x$0 -> Files.isDirectory(x$0)).sorted().toList();
            }

            return var2;
         } catch (IOException var6) {
            return List.of();
         }
      }
   }

   private static Path realPath(Path p) {
      try {
         return p.toRealPath();
      } catch (IOException var2) {
         return p.toAbsolutePath().normalize();
      }
   }
}

package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.api.legit.ProbeEngine;
import com.metrofico.pulsar.api.legit.LegitSession.Exam;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.jar.JarFile;

final class BootSeal {
   private static final List<String> INJECTING_ARGS = List.of(
      "-javaagent", "-agentlib", "-agentpath", "-Xbootclasspath", "--patch-module", "-Djava.system.class.loader"
   );

   private BootSeal() {
   }

   static Exam exam(String mcVersion, List<Path> gameClasspath, Class<?> backend) {
      try {
         List<Path> pulsarJars = new ArrayList<>();
         addJar(pulsarJars, BootSeal.class.getProtectionDomain().getCodeSource());
         Path api = addJar(pulsarJars, ProbeEngine.class.getProtectionDomain().getCodeSource());
         if (backend != null) {
            addJar(pulsarJars, backend.getProtectionDomain().getCodeSource());
         }

         Path game = gameClasspath.stream()
            .filter(p -> p.getFileName() != null && p.getFileName().toString().equals(mcVersion + ".jar"))
            .findFirst()
            .orElse(null);
         String gameSha1 = game == null ? "" : sha1(game);
         List<String> origins = injectingArgs();
         String expected = System.getProperty("pulsar.game.sha1", "");
         if (!expected.isEmpty() && !expected.equalsIgnoreCase(gameSha1)) {
            origins.add("game-sha1-mismatch");
         }

         return new Exam(System.getProperty("pulsar.client.version", "dev"), mcVersion, buildId(api), os(), ProbeEngine.bootHash(pulsarJars), gameSha1, origins);
      } catch (IOException var9) {
         throw new UncheckedIOException(var9);
      }
   }

   static String buildId(Path apiJar) throws IOException {
      if (apiJar == null) {
         return "dev";
      } else {
         String var3;
         try (JarFile jar = new JarFile(apiJar.toFile())) {
            String b = jar.getManifest() == null ? null : jar.getManifest().getMainAttributes().getValue("Pulsar-Build");
            var3 = b == null ? "dev" : b;
         }

         return var3;
      }
   }

   static String os() {
      String n = System.getProperty("os.name").toLowerCase();
      return n.contains("win") ? "windows" : (n.contains("mac") ? "macos" : "linux");
   }

   static List<String> injectingArgs() {
      return injectingArgs(ManagementFactory.getRuntimeMXBean().getInputArguments(), ownJar());
   }

   static List<String> injectingArgs(List<String> jvmArgs, Path ownJar) {
      List<String> out = new ArrayList<>();

      for (String arg : jvmArgs) {
         if (ownJar == null || !isAgentOf(arg, ownJar)) {
            for (String prefix : INJECTING_ARGS) {
               if (arg.startsWith(prefix)) {
                  out.add(arg);
                  break;
               }
            }
         }
      }

      return out;
   }

   static boolean isAgentOf(String arg, Path jar) {
      if (!arg.startsWith("-javaagent:")) {
         return false;
      } else {
         String spec = arg.substring("-javaagent:".length());
         int options = spec.indexOf(61);

         try {
            return Path.of(options >= 0 ? spec.substring(0, options) : spec).toAbsolutePath().normalize().equals(jar.toAbsolutePath().normalize());
         } catch (InvalidPathException var5) {
            return false;
         }
      }
   }

   private static Path ownJar() {
      CodeSource source = BootSeal.class.getProtectionDomain().getCodeSource();

      try {
         return source != null && source.getLocation() != null ? Path.of(source.getLocation().toURI()) : null;
      } catch (IllegalArgumentException | URISyntaxException var2) {
         return null;
      }
   }

   private static String sha1(Path file) throws IOException {
      try {
         String var8;
         try (InputStream in = Files.newInputStream(file)) {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] buf = new byte[65536];

            while ((n = in.read(buf)) > 0) {
               sha.update(buf, 0, n);
            }

            var8 = HexFormat.of().formatHex(sha.digest());
         }

         return var8;
      } catch (NoSuchAlgorithmException var7) {
         throw new IllegalStateException(var7);
      }
   }

   private static Path addJar(List<Path> jars, CodeSource source) {
      if (source != null && source.getLocation() != null) {
         try {
            Path p = Path.of(source.getLocation().toURI());
            if (p.getFileName() != null && p.getFileName().toString().endsWith(".jar") && !jars.contains(p)) {
               jars.add(p);
               return p;
            }
         } catch (IllegalArgumentException | URISyntaxException var3) {
            BootstrapLog.info("legit: code source skipped: " + source.getLocation());
         }

         return null;
      } else {
         return null;
      }
   }
}

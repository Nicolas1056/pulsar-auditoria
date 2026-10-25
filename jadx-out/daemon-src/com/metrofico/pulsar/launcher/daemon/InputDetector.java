package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

final class InputDetector {
   static final int EXIT_NO_PERMISSION = 3;
   private final Path binary;
   static final String RULE_FILE = "70-pulsar-input.rules";

   private InputDetector(Path binary) {
      this.binary = binary;
   }

   static InputDetector locate(Path repo) {
      String name = windows() ? "pulsar-input.exe" : "pulsar-input";
      String dev = System.getenv("PULSAR_INPUT");
      if (dev != null && !dev.isBlank()) {
         return existing(Path.of(dev));
      } else {
         String exe = System.getenv("PULSAR_EXECUTABLE");
         if (exe != null && !exe.isBlank()) {
            InputDetector d = existing(Path.of(exe).toAbsolutePath().resolveSibling(name));
            if (d != null) {
               return d;
            }
         }

         return existing(repo.resolve("launcher-shell/build/Release").resolve(name));
      }
   }

   private static InputDetector existing(Path p) {
      return Files.isRegularFile(p) ? new InputDetector(p.toAbsolutePath()) : null;
   }

   Path binary() {
      return this.binary;
   }

   String missingPermission() {
      if (!linux()) {
         return null;
      } else {
         try {
            Process p = new ProcessBuilder(this.binary.toString(), "--check").redirectErrorStream(true).start();
            String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            if (!p.waitFor(5L, TimeUnit.SECONDS)) {
               p.destroyForcibly();
               return "pulsar-input --check no respondió";
            } else {
               return p.exitValue() == 3 ? out : null;
            }
         } catch (IOException var3) {
            return "pulsar-input no se pudo ejecutar: " + var3.getMessage();
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
            return "interrumpido";
         }
      }
   }

   String grantPermission() {
      if (!linux()) {
         return null;
      } else {
         try {
            String rule = Files.readString(this.binary.resolveSibling("70-pulsar-input.rules"), StandardCharsets.UTF_8);
            String script = "printf '%s' \"$1\" > /etc/udev/rules.d/70-pulsar-input.rules && chmod 644 /etc/udev/rules.d/70-pulsar-input.rules && udevadm control --reload && udevadm trigger --subsystem-match=input --action=change && udevadm settle --timeout=5";
            Process p = new ProcessBuilder("pkexec", "/bin/sh", "-c", script, "sh", rule).redirectErrorStream(true).start();
            String out = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
            if (!p.waitFor(120L, TimeUnit.SECONDS)) {
               p.destroyForcibly();
               return "pkexec no respondió";
            } else {
               return p.exitValue() == 0 ? null : (p.exitValue() != 126 && p.exitValue() != 127 ? out : "cancelled");
            }
         } catch (IOException var5) {
            return var5.getMessage();
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            return "interrumpido";
         }
      }
   }

   static boolean requiredHere() {
      return linux() || windows();
   }

   List<String> prepare(Path sessionDir, Path logFile) throws IOException {
      Path target = Files.createDirectories(sessionDir).resolve(this.binary.getFileName());
      Files.copy(this.binary, target, StandardCopyOption.REPLACE_EXISTING);
      if (!windows()) {
         target.toFile().setExecutable(true, true);
      }

      return List.of(target.toString(), "--log", logFile.toString());
   }

   static List<String> jvmArgs(Path copiedBinary, Path logFile, boolean published) {
      List<String> args = new ArrayList<>(List.of("-Dpulsar.input=" + copiedBinary, "-Dpulsar.input.log=" + logFile));
      if (!published && "off".equals(System.getenv("PULSAR_INPUT_ENFORCE"))) {
         args.add("-Dpulsar.input.enforce=off");
      }

      return List.copyOf(args);
   }

   private static boolean windows() {
      return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
   }

   private static boolean linux() {
      return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("linux");
   }
}

package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class DiagnosticReport {
   private final Path reportsDir;
   private final Path logsDir;
   private final Path crashReportsDir;
   private final List<String> secrets;
   private static final Pattern SESSION_ID = Pattern.compile("token:[A-Za-z0-9._-]+(:[0-9a-fA-F-]+)?");
   private static final Pattern JWT = Pattern.compile("eyJ[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+");

   public DiagnosticReport(Path reportsDir, Path logsDir, Path crashReportsDir, String playerName) {
      this.reportsDir = reportsDir;
      this.logsDir = logsDir;
      this.crashReportsDir = crashReportsDir;
      List<String> s = new ArrayList<>();
      if (playerName != null && playerName.length() >= 3) {
         s.add(playerName);
      }

      String user = System.getProperty("user.name", "");
      if (user.length() >= 3) {
         s.add(user);
      }

      this.secrets = List.copyOf(s);
   }

   public DiagnosticReport.Created create(Map<String, Object> system, Object profiles, Object preferences) throws IOException {
      Files.createDirectories(this.reportsDir);
      String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
      Path zip = this.reportsDir.resolve("pulsar-report-" + stamp + ".zip");
      List<String> entries = new ArrayList<>();

      try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(zip), StandardCharsets.UTF_8)) {
         this.add(out, entries, "README.txt", readme());
         this.add(
            out,
            entries,
            "system.json",
            Protocol.GSON.toJson(Map.of("system", system, "profiles", (Map<String, Object>)profiles, "preferences", (Map<String, Object>)preferences))
         );
         Optional<Path> launcherLog = newest(this.logsDir, "launcher.", ".log");
         if (launcherLog.isPresent()) {
            this.add(out, entries, "launcher.log", Files.readString(launcherLog.get(), StandardCharsets.UTF_8));
         }

         Optional<Path> gameLog = newest(this.logsDir, "game-", ".log");
         if (gameLog.isPresent()) {
            this.add(out, entries, "game.log", Files.readString(gameLog.get(), StandardCharsets.UTF_8));
         }

         Optional<Path> crash = newest(this.crashReportsDir, "crash-", ".txt");
         if (crash.isPresent()) {
            this.add(out, entries, "crash-report.txt", Files.readString(crash.get(), StandardCharsets.UTF_8));
         }
      }

      return new DiagnosticReport.Created(zip.toString(), this.reportsDir.toString(), List.copyOf(entries));
   }

   private void add(ZipOutputStream out, List<String> entries, String name, String text) throws IOException {
      out.putNextEntry(new ZipEntry(name));
      out.write(this.redact(text).getBytes(StandardCharsets.UTF_8));
      out.closeEntry();
      entries.add(name);
   }

   public String redact(String text) {
      String result = text;
      String home = System.getProperty("user.home", "");
      if (home.length() > 3) {
         result = text.replace(home, "~");
      }

      for (String secret : this.secrets) {
         result = result.replace(secret, "<redactado>");
      }

      result = SESSION_ID.matcher(result).replaceAll("token:<redactado>");
      return JWT.matcher(result).replaceAll("<redactado>");
   }

   private static Optional<Path> newest(Path dir, String prefix, String suffix) throws IOException {
      if (dir != null && Files.isDirectory(dir)) {
         Optional var4;
         try (Stream<Path> s = Files.list(dir)) {
            var4 = s.filter(p -> p.getFileName().toString().startsWith(prefix) && p.getFileName().toString().endsWith(suffix))
               .max(Comparator.comparingLong(p -> p.toFile().lastModified()));
         }

         return var4;
      } else {
         return Optional.empty();
      }
   }

   private static String readme() {
      return "Paquete de diagnóstico de Pulsar Launcher\n=========================================\nContiene solo lo necesario para entender un problema:\n  system.json      sistema operativo, versión del launcher, runtimes Java, perfiles (versión, memoria) y preferencias\n  launcher.log     registro del launcher (comandos y errores; sin argumentos)\n  game.log         último registro del juego\n  crash-report.txt último crash report de Minecraft (si existe)\nDatos sustituidos antes de guardar: tu nombre de jugador, tu usuario del sistema y tu carpeta personal.\nNada se envía automáticamente: tú decides dónde adjuntar este archivo.\n";
   }

   public record Created(String path, String dir, List<String> entries) {
   }
}

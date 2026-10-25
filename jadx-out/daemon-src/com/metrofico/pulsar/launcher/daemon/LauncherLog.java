package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class LauncherLog {
   public static final String PATTERN = "launcher.%g.log";
   public static final int DEFAULT_LIMIT = 1048576;
   public static final int DEFAULT_COUNT = 5;
   public static final int GAME_LOGS_KEPT = 10;
   private static final Logger LOG = Logger.getLogger("pulsar.launcher");
   private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
   private static FileHandler handler;

   public static synchronized void init(Path logsDir, int limitBytes, int count) {
      close();

      try {
         Files.createDirectories(logsDir);
         handler = new FileHandler(logsDir.resolve("launcher.%g.log").toString(), limitBytes, count, true);
         handler.setFormatter(
            new Formatter() {
               @Override
               public String format(LogRecord r) {
                  StringBuilder sb = new StringBuilder(LauncherLog.TIME.format(Instant.ofEpochMilli(r.getMillis())))
                     .append(' ')
                     .append(String.format("%-7s", r.getLevel().getName()))
                     .append(this.formatMessage(r))
                     .append('\n');

                  for (Throwable t = r.getThrown(); t != null; t = t.getCause() == t ? null : t.getCause()) {
                     sb.append("    caused by ").append(t.getClass().getName()).append(": ").append(t.getMessage()).append('\n');

                     for (StackTraceElement e : t.getStackTrace()) {
                        if (e.getClassName().startsWith("com.metrofico")) {
                           sb.append("        at ").append(e).append('\n');
                        }
                     }
                  }

                  return sb.toString();
               }
            }
         );
         handler.setLevel(Level.ALL);
         LOG.addHandler(handler);
      } catch (IOException var4) {
         handler = null;
      }
   }

   public static void init(Path logsDir) {
      init(logsDir, 1048576, 5);
   }

   public static synchronized void close() {
      if (handler != null) {
         LOG.removeHandler(handler);
         handler.close();
         handler = null;
      }
   }

   public static void info(String message) {
      LOG.info(message);
   }

   public static void warn(String message) {
      LOG.warning(message);
   }

   public static void error(String message, Throwable t) {
      LOG.log(Level.SEVERE, message, t);
   }

   public static Path current(Path logsDir) {
      return logsDir.resolve("launcher.0.log");
   }

   public static List<Path> retainGameLogs(Path logsDir, int keep) throws IOException {
      if (!Files.isDirectory(logsDir)) {
         return List.of();
      } else {
         List<Path> logs;
         try (Stream<Path> s = Files.list(logsDir)) {
            logs = s.filter(px -> px.getFileName().toString().startsWith("game-") && px.getFileName().toString().endsWith(".log"))
               .sorted(Comparator.<Path>comparingLong(px -> px.toFile().lastModified()).reversed())
               .toList();
         }

         List<Path> removed = logs.size() > keep ? logs.subList(keep, logs.size()) : List.of();

         for (Path p : removed) {
            Files.deleteIfExists(p);
         }

         return removed;
      }
   }

   private LauncherLog() {
   }

   static {
      LOG.setUseParentHandlers(false);
      LOG.setLevel(Level.ALL);
   }
}

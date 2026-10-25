package com.metrofico.pulsar.bootstrap;

import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class BootstrapLog {
   private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
   private static final PrintStream OUT = System.out;

   public static void info(String message) {
      log("INFO", message);
   }

   public static void error(String message, Throwable cause) {
      log("ERROR", message);
      if (cause != null) {
         cause.printStackTrace(OUT);
      }
   }

   private static void log(String level, String message) {
      OUT.println("[" + LocalTime.now().format(TIME) + "] [Pulsar bootstrap/" + level + "]: " + message);
   }

   private BootstrapLog() {
   }
}

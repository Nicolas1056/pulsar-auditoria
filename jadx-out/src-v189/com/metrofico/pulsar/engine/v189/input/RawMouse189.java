package com.metrofico.pulsar.engine.v189.input;

import com.metrofico.pulsar.api.input.RawMouse;
import java.util.Locale;

public final class RawMouse189 {
   private static boolean started;

   private RawMouse189() {
   }

   public static void ensureStarted() {
      if (!started && RawMouse.enabled()) {
         started = true;
         String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
         if (os.contains("win")) {
            WindowsRawMouse189.start();
         } else if (os.contains("linux")) {
            LinuxRawMouse189.start();
         }
      }
   }
}

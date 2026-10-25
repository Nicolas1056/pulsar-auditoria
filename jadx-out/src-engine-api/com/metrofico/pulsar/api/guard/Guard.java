package com.metrofico.pulsar.api.guard;

import java.util.List;
import java.util.Locale;
import java.util.ServiceLoader;

public interface Guard {
   GuardReport scan();

   static Guard current() {
      for (Guard g : ServiceLoader.load(Guard.class, Guard.class.getClassLoader())) {
         if (g.supported()) {
            return g;
         }
      }

      return noop();
   }

   default boolean supported() {
      return true;
   }

   static Guard noop() {
      return () -> GuardReport.EMPTY;
   }

   static List<String> knownModuleNames() {
      return KnownModules.NAMES;
   }

   static String osName() {
      String n = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
      return n.contains("win") ? "windows" : (n.contains("mac") ? "macos" : (n.contains("linux") ? "linux" : "other"));
   }
}

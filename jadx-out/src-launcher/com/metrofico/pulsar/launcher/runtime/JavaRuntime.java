package com.metrofico.pulsar.launcher.runtime;

import java.nio.file.Path;

public record JavaRuntime(Path home, int major, String implementor, JavaRuntime.Source source) {
   public Path executable() {
      return this.home.resolve("bin").resolve(isWindows() ? "java.exe" : "java");
   }

   public static boolean isWindows() {
      return System.getProperty("os.name", "").toLowerCase().contains("win");
   }

   public JavaRuntime withSource(JavaRuntime.Source s) {
      return new JavaRuntime(this.home, this.major, this.implementor, s);
   }

   public static enum Source {
      PROFILE,
      MANAGED,
      BUNDLED,
      SYSTEM;
   }
}

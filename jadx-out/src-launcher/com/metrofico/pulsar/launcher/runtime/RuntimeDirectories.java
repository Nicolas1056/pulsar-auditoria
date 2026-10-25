package com.metrofico.pulsar.launcher.runtime;

import com.metrofico.pulsar.launcher.rules.Platform;
import java.nio.file.Path;

public record RuntimeDirectories(Path root) {
   public static final String MARKER = ".pulsar-runtime.json";

   public Path runtimes() {
      return this.root.resolve("runtimes");
   }

   public Path managed(String vendor, int major, Platform platform) {
      return this.runtimes().resolve(vendor + "-" + major + "-" + platform.os() + "-" + platform.arch());
   }

   public static Path marker(Path runtimeDir) {
      return runtimeDir.resolve(".pulsar-runtime.json");
   }
}

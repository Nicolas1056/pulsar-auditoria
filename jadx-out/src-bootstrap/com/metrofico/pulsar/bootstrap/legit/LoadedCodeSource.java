package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.api.legit.ProbeEngine;
import com.metrofico.pulsar.api.legit.ProbeEngine.Source;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;

public final class LoadedCodeSource implements Source {
   private final GameClassLoader loader;
   private final Map<String, Path> jars;

   private LoadedCodeSource(GameClassLoader loader, Map<String, Path> jars) {
      this.loader = loader;
      this.jars = Map.copyOf(jars);
   }

   public static void install(GameClassLoader loader, URL[] gameClasspath) {
      Map<String, Path> jars = new HashMap<>();

      for (URL url : gameClasspath) {
         add(jars, url);
      }

      add(jars, LoadedCodeSource.class.getProtectionDomain().getCodeSource());
      add(jars, ProbeEngine.class.getProtectionDomain().getCodeSource());
      ProbeEngine.install(new LoadedCodeSource(loader, jars));
   }

   public byte[] classBytes(String className) throws ClassNotFoundException {
      return this.loader.definedClassBytes(className);
   }

   public Path jar(String fileName) {
      return this.jars.get(fileName);
   }

   private static void add(Map<String, Path> jars, CodeSource source) {
      if (source != null && source.getLocation() != null) {
         add(jars, source.getLocation());
      }
   }

   private static void add(Map<String, Path> jars, URL url) {
      try {
         Path p = Path.of(url.toURI());
         if (p.getFileName() != null && p.getFileName().toString().endsWith(".jar")) {
            jars.put(p.getFileName().toString(), p);
         }
      } catch (IllegalArgumentException | URISyntaxException var3) {
         BootstrapLog.info("legit: classpath entry skipped: " + url);
      }
   }
}

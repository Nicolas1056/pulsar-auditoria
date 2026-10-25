package com.metrofico.pulsar.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record GameEntry(List<Path> classpath, String mainClass) {
   public static final String PROPERTY_CLASSPATH = "pulsar.game.classpath";
   public static final String PROPERTY_MAIN = "pulsar.game.main";

   public static GameEntry fromSystemProperties() {
      String cp = System.getProperty("pulsar.game.classpath");
      String main = System.getProperty("pulsar.game.main");
      if (cp != null && !cp.isBlank() && main != null && !main.isBlank()) {
         List<Path> paths = new ArrayList<>();

         for (String entry : cp.split(File.pathSeparator)) {
            if (!entry.isBlank()) {
               Path path = Path.of(entry);
               if (!Files.exists(path)) {
                  throw new IllegalStateException("Game classpath entry does not exist: " + path);
               }

               paths.add(path);
            }
         }

         if (paths.isEmpty()) {
            throw new IllegalStateException("Game classpath is empty");
         } else {
            return new GameEntry(List.copyOf(paths), main);
         }
      } else {
         throw new IllegalStateException("The launcher must set -Dpulsar.game.classpath and -Dpulsar.game.main");
      }
   }

   public URL[] urls() {
      URL[] urls = new URL[this.classpath.size()];

      for (int i = 0; i < urls.length; i++) {
         try {
            urls[i] = this.classpath.get(i).toUri().toURL();
         } catch (MalformedURLException var4) {
            throw new IllegalStateException("Bad classpath entry: " + this.classpath.get(i), var4);
         }
      }

      return urls;
   }
}

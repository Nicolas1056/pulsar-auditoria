package com.metrofico.pulsar.launcher.runtime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public record ReleaseFile(Map<String, String> values) {
   public static Optional<ReleaseFile> read(Path home) {
      Path file = home.resolve("release");
      if (!Files.isRegularFile(file)) {
         return Optional.empty();
      } else {
         try {
            return Optional.of(parse(Files.readString(file)));
         } catch (IOException var3) {
            return Optional.empty();
         }
      }
   }

   public static ReleaseFile parse(String text) {
      Map<String, String> values = new LinkedHashMap<>();

      for (String line : text.split("\\R")) {
         int eq = line.indexOf(61);
         if (eq > 0) {
            String value = line.substring(eq + 1).trim();
            if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
               value = value.substring(1, value.length() - 1);
            }

            values.put(line.substring(0, eq).trim(), value);
         }
      }

      return new ReleaseFile(Map.copyOf(values));
   }

   public Optional<Integer> major() {
      String v = this.values.get("JAVA_VERSION");
      if (v != null && !v.isBlank()) {
         String[] parts = v.split("[.\\-_+]");

         try {
            int first = Integer.parseInt(parts[0]);
            return first == 1 && parts.length > 1 ? Optional.of(Integer.parseInt(parts[1])) : Optional.of(first);
         } catch (NumberFormatException var4) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   public String implementor() {
      return this.values.getOrDefault("IMPLEMENTOR", "");
   }
}

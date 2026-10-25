package com.metrofico.pulsar.launcher.install;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class NativesExtractor {
   public int extract(List<ResolvedLibrary> legacyNatives, Path nativesDir) throws IOException {
      Files.createDirectories(nativesDir);
      int extracted = 0;

      for (ResolvedLibrary library : legacyNatives) {
         List<String> exclude = library.library().extract() == null ? List.of("META-INF/") : library.library().extract().exclude();

         ZipEntry entry;
         try (ZipInputStream zip = new ZipInputStream(Files.newInputStream(library.target()))) {
            while ((entry = zip.getNextEntry()) != null) {
               if (!entry.isDirectory() && !isExcluded(entry.getName(), exclude)) {
                  Path out = nativesDir.resolve(entry.getName()).normalize();
                  if (!out.startsWith(nativesDir)) {
                     throw new IOException("Zip entry escapes natives dir: " + entry.getName());
                  }

                  Files.createDirectories(out.getParent());
                  Files.copy(zip, out, StandardCopyOption.REPLACE_EXISTING);
                  extracted++;
               }
            }
         }
      }

      return extracted;
   }

   private static boolean isExcluded(String name, List<String> exclude) {
      return exclude.stream().anyMatch(name::startsWith);
   }
}

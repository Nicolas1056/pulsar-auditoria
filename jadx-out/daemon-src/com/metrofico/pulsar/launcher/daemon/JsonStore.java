package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Supplier;

final class JsonStore<T> {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final Path file;
   private final Class<T> type;
   private final Supplier<T> defaults;

   JsonStore(Path file, Class<T> type, Supplier<T> defaults) {
      this.file = file;
      this.type = type;
      this.defaults = defaults;
   }

   T load() {
      if (!Files.exists(this.file)) {
         return this.defaults.get();
      } else {
         try {
            T value = (T)GSON.fromJson(Files.readString(this.file), this.type);
            return value != null ? value : this.defaults.get();
         } catch (IOException | JsonParseException var4) {
            try {
               Files.move(this.file, this.file.resolveSibling(this.file.getFileName() + ".corrupt"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException var3) {
            }

            return this.defaults.get();
         }
      }
   }

   void save(T value) {
      try {
         Files.createDirectories(this.file.getParent());
         Path tmp = this.file.resolveSibling(this.file.getFileName() + ".tmp");
         Files.writeString(tmp, GSON.toJson(value));
         Files.move(tmp, this.file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (IOException var3) {
         throw new UncheckedIOException(var3);
      }
   }
}

package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.pack.Json;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class KeyBindings {
   private final Path file;

   public KeyBindings(Path dataDir) {
      this.file = dataDir.resolve("packs").resolve("keys.json");
   }

   public Map<String, String> forServer(String serverId) {
      Map<String, String> out = new LinkedHashMap<>();
      if (this.all().get(serverId) instanceof Map<?, ?> m) {
         for (Entry<?, ?> e : m.entrySet()) {
            String k = ScreenDefinition.normalizeKey(e.getValue());
            if (k != null) {
               out.put(String.valueOf(e.getKey()), k);
            }
         }
      }

      return out;
   }

   public synchronized void bind(String serverId, String screenId, String key) {
      Map<String, Object> all = this.all();
      Map<String, Object> server = new LinkedHashMap<>();
      if (all.get(serverId) instanceof Map<?, ?> m) {
         m.forEach((k, v) -> server.put(String.valueOf(k), v));
      }

      if (key == null) {
         server.remove(screenId);
      } else {
         server.put(screenId, key);
      }

      if (server.isEmpty()) {
         all.remove(serverId);
      } else {
         all.put(serverId, server);
      }

      this.save(all);
   }

   public synchronized void reset(String serverId) {
      Map<String, Object> all = this.all();
      if (all.remove(serverId) != null) {
         this.save(all);
      }
   }

   public static String effective(ScreenDefinition s, Map<String, String> chosen) {
      String k = chosen.get(s.id());
      return k != null ? k : s.key();
   }

   private Map<String, Object> all() {
      if (!Files.isRegularFile(this.file)) {
         return new LinkedHashMap<>();
      } else {
         try {
            Map<String, Object> root = Json.object(Json.parse(Files.readString(this.file, StandardCharsets.UTF_8)), "keys");
            return new LinkedHashMap<>(Json.object(root.getOrDefault("servers", Map.of()), "keys.servers"));
         } catch (RuntimeException | IOException var2) {
            return new LinkedHashMap<>();
         }
      }
   }

   private void save(Map<String, Object> servers) {
      try {
         Files.createDirectories(this.file.getParent());
         Files.writeString(this.file, Json.write(Map.of("version", 1L, "servers", servers)), StandardCharsets.UTF_8);
      } catch (IOException var3) {
         throw new IllegalStateException("cannot save keys", var3);
      }
   }
}

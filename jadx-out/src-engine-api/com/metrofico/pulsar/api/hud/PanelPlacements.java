package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.pack.Json;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class PanelPlacements {
   private final Path file;

   public PanelPlacements(Path dataDir) {
      this.file = dataDir.resolve("packs").resolve("layout.json");
   }

   public Path file() {
      return this.file;
   }

   public Map<String, Placement> forServer(String serverId) {
      Map<String, Placement> out = new LinkedHashMap<>();
      if (this.all().get(serverId) instanceof Map<?, ?> m) {
         for (Entry<?, ?> e : m.entrySet()) {
            Placement.parse(String.valueOf(e.getValue())).ifPresent(p -> out.put(String.valueOf(e.getKey()), p));
         }
      }

      return out;
   }

   public static Map<String, Zone> zones(Map<String, Placement> placements) {
      Map<String, Zone> out = new LinkedHashMap<>();
      placements.forEach((k, v) -> out.put(k, v.anchor()));
      return out;
   }

   public synchronized void move(String serverId, String panelId, Zone zone) {
      this.move(serverId, panelId, zone == null ? null : Placement.zone(zone));
   }

   public synchronized void move(String serverId, String panelId, Placement placement) {
      Map<String, Object> all = this.all();
      Map<String, Object> server = new LinkedHashMap<>();
      if (all.get(serverId) instanceof Map<?, ?> m) {
         m.forEach((k, v) -> server.put(String.valueOf(k), v));
      }

      if (placement == null) {
         server.remove(panelId);
      } else {
         server.put(panelId, placement.serialize());
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

   private Map<String, Object> all() {
      if (!Files.isRegularFile(this.file)) {
         return new LinkedHashMap<>();
      } else {
         try {
            Map<String, Object> root = Json.object(Json.parse(Files.readString(this.file, StandardCharsets.UTF_8)), "layout");
            return new LinkedHashMap<>(Json.object(root.getOrDefault("servers", Map.of()), "layout.servers"));
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
         throw new IllegalStateException("cannot save layout", var3);
      }
   }
}

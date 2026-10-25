package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

final class PacksStore {
   private final Path consent;
   private final Path layout;
   private final Path keys;
   private final Path cache;

   PacksStore(Path dataDir) {
      this.consent = dataDir.resolve("packs").resolve("consent.json");
      this.layout = dataDir.resolve("packs").resolve("layout.json");
      this.keys = dataDir.resolve("packs").resolve("keys.json");
      this.cache = dataDir.resolve("packs").resolve("cache");
   }

   Map<String, Object> list() {
      List<Object> servers = new ArrayList<>();

      for (JsonElement e : this.read()) {
         JsonObject d = e.getAsJsonObject();
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("serverId", str(d, "serverId"));
         m.put("serverName", str(d, "serverName"));
         m.put("packVersion", d.has("packVersion") ? d.get("packVersion").getAsLong() : 0L);
         List<String> caps = new ArrayList<>();
         if (d.has("requested")) {
            d.getAsJsonArray("requested").forEach(c -> caps.add(c.getAsString()));
         }

         m.put("requested", caps);
         m.put("consent", str(d, "consent"));
         m.put("decidedAt", str(d, "decidedAt"));
         servers.add(m);
      }

      long[] stats = this.cacheStats();
      return Map.of("servers", servers, "cacheFiles", stats[0], "cacheBytes", stats[1]);
   }

   Map<String, Object> revoke(String serverId) {
      JsonArray kept = new JsonArray();
      boolean found = false;

      for (JsonElement e : this.read()) {
         if (serverId.equals(str(e.getAsJsonObject(), "serverId"))) {
            found = true;
         } else {
            kept.add(e);
         }
      }

      if (!found) {
         throw new DaemonException("not_found", "Servidor desconocido", "No hay una decisión guardada para " + serverId);
      } else {
         JsonObject root = new JsonObject();
         root.addProperty("version", 1);
         root.add("servers", kept);

         try {
            Files.createDirectories(this.consent.getParent());
            Path tmp = this.consent.resolveSibling("consent.json.tmp");
            Files.writeString(tmp, root.toString(), StandardCharsets.UTF_8);
            Files.move(tmp, this.consent, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } catch (IOException var6) {
            throw new UncheckedIOException(var6);
         }

         this.forgetLayout(serverId);
         forgetIn(this.keys, serverId);
         return this.list();
      }
   }

   private void forgetLayout(String serverId) {
      forgetIn(this.layout, serverId);
   }

   private static void forgetIn(Path file, String serverId) {
      if (Files.isRegularFile(file)) {
         try {
            JsonElement root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8));
            if (root.isJsonObject() && root.getAsJsonObject().has("servers") && root.getAsJsonObject().getAsJsonObject("servers").remove(serverId) != null) {
               Files.writeString(file, root.toString(), StandardCharsets.UTF_8);
            }
         } catch (JsonParseException | IllegalStateException | IOException var3) {
         }
      }
   }

   Map<String, Object> clearCache() {
      if (Files.isDirectory(this.cache)) {
         try (Stream<Path> files = Files.list(this.cache)) {
            for (Path p : files.toList()) {
               if (Files.isRegularFile(p)) {
                  Files.deleteIfExists(p);
               }
            }
         } catch (IOException var6) {
            throw new UncheckedIOException(var6);
         }
      }

      return this.list();
   }

   private JsonArray read() {
      if (!Files.isRegularFile(this.consent)) {
         return new JsonArray();
      } else {
         try {
            JsonElement root = JsonParser.parseString(Files.readString(this.consent, StandardCharsets.UTF_8));
            if (root.isJsonObject() && root.getAsJsonObject().has("servers")) {
               return root.getAsJsonObject().getAsJsonArray("servers");
            }
         } catch (JsonParseException | IllegalStateException | IOException var2) {
         }

         return new JsonArray();
      }
   }

   private long[] cacheStats() {
      long files = 0L;
      long bytes = 0L;
      if (Files.isDirectory(this.cache)) {
         try (Stream<Path> s = Files.list(this.cache)) {
            for (Path p : s.toList()) {
               if (Files.isRegularFile(p)) {
                  files++;
                  bytes += Files.size(p);
               }
            }
         } catch (IOException var10) {
         }
      }

      return new long[]{files, bytes};
   }

   private static String str(JsonObject o, String key) {
      return o.has(key) && !o.get(key).isJsonNull() ? o.get(key).getAsString() : "";
   }
}

package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;

public final class EmoteService {
   private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
   private static final long MAX_OBJECT_BYTES = 262144L;
   private final String serviceUrl;
   private final Supplier<String> token;
   private final Supplier<Boolean> gameRunning;
   private final Consumer<String> events;
   private final Path dataDir;
   private final HttpClient http;
   private volatile List<Map<String, Object>> wardrobe = List.of();
   private volatile String lastError;
   private volatile long updatedAt;
   private volatile int cached;
   private Thread worker;

   public EmoteService(String serviceUrl, Supplier<String> token, Supplier<Boolean> gameRunning, Consumer<String> events, Path dataDir, HttpClient http) {
      this.serviceUrl = serviceUrl.replaceAll("/+$", "");
      this.token = token;
      this.gameRunning = gameRunning;
      this.events = events;
      this.dataDir = dataDir;
      this.http = http != null ? http : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
      this.loadLocal();
   }

   public Path wardrobeFile() {
      return this.dataDir.resolve("emotes").resolve("wardrobe.json");
   }

   private Path cacheFile(String sha) {
      return this.dataDir.resolve("packs").resolve("cache").resolve(sha);
   }

   private void loadLocal() {
      try {
         if (Files.isRegularFile(this.wardrobeFile())) {
            this.parse(Files.readString(this.wardrobeFile(), StandardCharsets.UTF_8));
            this.updatedAt = Files.getLastModifiedTime(this.wardrobeFile()).toMillis();
         }
      } catch (Exception var2) {
         this.lastError = "guardarropa local ilegible: " + var2.getMessage();
      }
   }

   private void parse(String json) {
      JsonObject root = JsonParser.parseString(json).getAsJsonObject();
      JsonObject w = root.getAsJsonObject("wardrobe");
      List<Map<String, Object>> list = new ArrayList<>();
      int have = 0;

      for (JsonElement e : w.getAsJsonArray("emotes")) {
         JsonObject o = e.getAsJsonObject();
         Map<String, Object> m = new LinkedHashMap<>();
         String sha = o.get("sha256").getAsString();
         m.put("id", o.get("id").getAsString());
         m.put("name", o.has("name") ? o.get("name").getAsString() : o.get("id").getAsString());
         m.put("sha256", sha);
         m.put("visibility", o.has("visibility") ? o.get("visibility").getAsString() : "public");
         boolean present = Files.isRegularFile(this.cacheFile(sha));
         m.put("cached", present);
         if (present) {
            have++;
         }

         list.add(m);
      }

      this.wardrobe = list;
      this.cached = have;
   }

   public synchronized void refreshAsync() {
      if (this.worker == null || !this.worker.isAlive()) {
         this.worker = new Thread(this::refresh, "pulsar-emotes");
         this.worker.setDaemon(true);
         this.worker.start();
      }
   }

   public Map<String, Object> refresh() {
      String t = this.token.get();
      if (t == null) {
         this.lastError = "sin sesión de launcher";
         return this.state();
      } else {
         try {
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/launcher/wardrobe"))
                     .timeout(Duration.ofSeconds(20L))
                     .header("Authorization", "Bearer " + t)
                     .GET()
                     .build(),
                  BodyHandlers.ofString(StandardCharsets.UTF_8)
               );
            if (r.statusCode() != 200) {
               throw new IOException("guardarropa " + r.statusCode());
            }

            JsonParser.parseString(r.body()).getAsJsonObject().getAsJsonObject("wardrobe");
            Files.createDirectories(this.wardrobeFile().getParent());
            Path tmp = this.wardrobeFile().resolveSibling("wardrobe.json.part");
            Files.writeString(tmp, r.body(), StandardCharsets.UTF_8);
            Files.move(tmp, this.wardrobeFile(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            this.parse(r.body());
            this.updatedAt = System.currentTimeMillis();
            this.lastError = null;
            this.precache();
            LauncherLog.info("emotes · guardarropa: " + this.wardrobe.size() + " emotes, " + this.cached + " en caché");
            this.events.accept(Protocol.event("emotes", Map.of("state", "updated", "count", this.wardrobe.size(), "cached", this.cached)));
         } catch (Exception var4) {
            this.lastError = var4.getMessage();
            LauncherLog.info("emotes · " + var4.getMessage());
         }

         return this.state();
      }
   }

   private void precache() {
      int have = 0;
      List<Map<String, Object>> updated = new ArrayList<>();

      for (Map<String, Object> m : this.wardrobe) {
         String sha = (String)m.get("sha256");
         boolean present = Files.isRegularFile(this.cacheFile(sha));
         if (!present) {
            try {
               this.fetchObject(sha, 262144L);
               present = true;
            } catch (Exception var8) {
               LauncherLog.info("emotes · no se pudo precargar " + sha.substring(0, 12) + ": " + var8.getMessage());
            }
         }

         if (present) {
            this.prefetchSounds(sha);
         }

         Map<String, Object> copy = new LinkedHashMap<>(m);
         copy.put("cached", present);
         updated.add(copy);
         if (present) {
            have++;
         }
      }

      this.wardrobe = updated;
      this.cached = have;
   }

   private void prefetchSounds(String pemoteSha) {
      try {
         String text = Files.readString(this.cacheFile(pemoteSha), StandardCharsets.UTF_8);
         if (JsonParser.parseString(text) instanceof JsonObject o && o.has("sounds")) {
            for (JsonElement e : o.getAsJsonArray("sounds")) {
               String sha = e.getAsJsonObject().get("sha256").getAsString();
               if (!Files.isRegularFile(this.cacheFile(sha))) {
                  this.fetchObject(sha, 266240L);
               }
            }
         }
      } catch (Exception var8) {
         LauncherLog.info("emotes · sonidos de " + pemoteSha.substring(0, 12) + ": " + var8.getMessage());
      }
   }

   void fetchObject(String sha, long maxBytes) throws IOException, InterruptedException {
      Path target = this.cacheFile(sha);
      Files.createDirectories(target.getParent());
      String url = this.serviceUrl + "/downloads/objects/" + sha.substring(0, 2) + "/" + sha + ".gz";
      HttpResponse<InputStream> r = this.http
         .send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(30L)).GET().build(), BodyHandlers.ofInputStream());
      if (r.statusCode() != 200) {
         r.body().close();
         throw new IOException("HTTP " + r.statusCode());
      } else {
         MessageDigest md;
         try {
            md = MessageDigest.getInstance("SHA-256");
         } catch (NoSuchAlgorithmException var17) {
            throw new IllegalStateException(var17);
         }

         Path part = target.resolveSibling(sha + ".part");
         long written = 0L;

         try (
            InputStream in = new DigestInputStream(new GZIPInputStream(r.body()), md);
            OutputStream out = Files.newOutputStream(part);
         ) {
            byte[] buf = new byte[32768];

            int n;
            while ((n = in.read(buf)) != -1) {
               written += n;
               if (written > maxBytes) {
                  throw new IOException("objeto demasiado grande");
               }

               out.write(buf, 0, n);
            }
         } catch (IOException var20) {
            Files.deleteIfExists(part);
            throw var20;
         }

         if (!HexFormat.of().formatHex(md.digest()).equals(sha)) {
            Files.deleteIfExists(part);
            throw new IOException("el hash no coincide");
         } else {
            Files.move(part, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         }
      }
   }

   public Map<String, Object> state() {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("session", this.token.get() != null);
      m.put("emotes", this.wardrobe);
      m.put("cached", this.cached);
      m.put("updatedAt", this.updatedAt);
      m.put("error", this.lastError);
      return m;
   }

   public Map<String, Object> claim(String id) {
      String t = this.token.get();
      if (t == null) {
         return Map.of("ok", false, "error", "no_session", "message", "Inicia sesión con Microsoft para obtener emotes");
      } else if (!id.matches("[a-z0-9][a-z0-9-]{1,63}")) {
         return Map.of("ok", false, "error", "bad_request", "message", "id inválido");
      } else {
         try {
            HttpResponse<String> r = this.http
               .send(
                  HttpRequest.newBuilder(URI.create(this.serviceUrl + "/api/v1/launcher/emotes/" + id + "/claim"))
                     .timeout(Duration.ofSeconds(20L))
                     .header("Authorization", "Bearer " + t)
                     .POST(BodyPublishers.noBody())
                     .build(),
                  BodyHandlers.ofString(StandardCharsets.UTF_8)
               );
            if (r.statusCode() == 201) {
               this.refresh();
               return Map.of("ok", true, "state", this.state());
            } else {
               JsonObject j;
               try {
                  j = JsonParser.parseString(r.body()).getAsJsonObject();
               } catch (RuntimeException var6) {
                  j = new JsonObject();
               }

               return Map.of(
                  "ok",
                  false,
                  "error",
                  j.has("error") ? j.get("error").getAsString() : "http_" + r.statusCode(),
                  "message",
                  j.has("message") ? j.get("message").getAsString() : "No se pudo obtener el emote"
               );
            }
         } catch (Exception var7) {
            return Map.of("ok", false, "error", "network", "message", var7.getMessage() == null ? "sin conexión" : var7.getMessage());
         }
      }
   }

   private Path optionsFile() {
      return this.dataDir.resolve("client-options.json");
   }

   private JsonObject readOptions() {
      try {
         if (Files.isRegularFile(this.optionsFile())) {
            return JsonParser.parseString(Files.readString(this.optionsFile(), StandardCharsets.UTF_8)).getAsJsonObject();
         }
      } catch (Exception var2) {
         LauncherLog.info("emotes · client-options ilegible: " + var2.getMessage());
      }

      return new JsonObject();
   }

   public Map<String, Object> options() {
      JsonObject o = this.readOptions();
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("showMine", !o.has("emotes.enabled") || o.get("emotes.enabled").getAsBoolean());
      m.put("seeOthers", !o.has("emotes.others") || o.get("emotes.others").getAsBoolean());
      m.put("key", o.has("emotes.key") ? o.get("emotes.key").getAsString() : "G");
      List<String> wheel = new ArrayList<>();
      if (o.has("emotes.wheel") && o.get("emotes.wheel").isJsonArray()) {
         for (JsonElement e : o.getAsJsonArray("emotes.wheel")) {
            wheel.add(e.isJsonPrimitive() ? e.getAsString() : "");
         }
      }

      m.put("wheel", wheel);
      m.put("gameRunning", this.gameRunning.get());
      return m;
   }

   public Map<String, Object> setOptions(Boolean showMine, Boolean seeOthers, List<String> wheel) {
      if (this.gameRunning.get()) {
         Map<String, Object> m = new LinkedHashMap<>(this.options());
         m.put("error", "game_running");
         return m;
      } else {
         JsonObject o = this.readOptions();
         if (showMine != null) {
            o.addProperty("emotes.enabled", showMine);
         }

         if (seeOthers != null) {
            o.addProperty("emotes.others", seeOthers);
         }

         if (wheel != null) {
            JsonArray a = new JsonArray();

            for (int i = 0; i < 8; i++) {
               a.add(i < wheel.size() && wheel.get(i) != null ? wheel.get(i) : "");
            }

            o.add("emotes.wheel", a);
         }

         try {
            Files.createDirectories(this.optionsFile().getParent());
            Path tmp = this.optionsFile().resolveSibling("client-options.json.part");
            Files.writeString(tmp, GSON.toJson(o), StandardCharsets.UTF_8);
            Files.move(tmp, this.optionsFile(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } catch (IOException var7) {
            Map<String, Object> m = new LinkedHashMap<>(this.options());
            m.put("error", "write_failed");
            return m;
         }

         return this.options();
      }
   }
}

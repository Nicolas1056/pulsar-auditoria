package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class LegitService {
   private final String serviceUrl;
   private final Supplier<String> token;
   private final Supplier<Boolean> gameRunning;
   private final Path dataDir;
   private final HttpClient http;

   public LegitService(String serviceUrl, Supplier<String> token, Supplier<Boolean> gameRunning, Path dataDir, HttpClient http) {
      this.serviceUrl = serviceUrl.replaceAll("/+$", "");
      this.token = token;
      this.gameRunning = gameRunning;
      this.dataDir = dataDir;
      this.http = http != null ? http : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
   }

   public Map<String, Object> status() {
      Map<String, Object> out = new LinkedHashMap<>();
      boolean running = this.gameRunning.get();
      out.put("gameRunning", running);
      out.put("state", "none");
      Path f = this.dataDir.resolve("legit-state.json");
      if (running && Files.isRegularFile(f)) {
         try {
            JsonObject o = JsonParser.parseString(Files.readString(f, StandardCharsets.UTF_8)).getAsJsonObject();
            out.put("state", o.get("state").getAsString());
            out.put("score", o.has("score") ? o.get("score").getAsInt() : 0);
            out.put("updatedAt", o.has("updatedAt") ? o.get("updatedAt").getAsString() : "");
         } catch (RuntimeException | IOException var5) {
            LauncherLog.info("legit · estado ilegible: " + var5.getMessage());
         }
      }

      return out;
   }

   public Map<String, Object> mark() {
      return this.call("GET", "/api/v1/legit/mark", null);
   }

   public Map<String, Object> appeal(String caseId, String text) {
      JsonObject body = new JsonObject();
      body.addProperty("caseId", caseId);
      body.addProperty("text", text);
      return this.call("POST", "/api/v1/legit/appeal", body.toString());
   }

   private Map<String, Object> call(String method, String path, String body) {
      String t = this.token.get();
      if (t == null) {
         throw new DaemonException("no_session", "Sin sesión de Pulsar", "Inicia sesión para ver el estado de tu cuenta");
      } else {
         try {
            Builder b = HttpRequest.newBuilder(URI.create(this.serviceUrl + path)).timeout(Duration.ofSeconds(20L)).header("Authorization", "Bearer " + t);
            b = body == null ? b.GET() : b.header("Content-Type", "application/json").POST(BodyPublishers.ofString(body));
            HttpResponse<String> r = this.http.send(b.build(), BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (r.statusCode() == 409) {
               throw new DaemonException("already_appealed", "Ya apelaste", "Esta marca ya tiene una apelación");
            } else if (r.statusCode() != 200) {
               throw new DaemonException("http_" + r.statusCode(), "Pulsar no respondió", path + " → " + r.statusCode());
            } else {
               JsonObject o = JsonParser.parseString(r.body()).getAsJsonObject();
               Map<String, Object> out = new LinkedHashMap<>();
               JsonElement mark = o.get("mark");
               out.put("mark", mark != null && !mark.isJsonNull() ? new Gson().fromJson(mark, Map.class) : null);
               return out;
            }
         } catch (IOException var10) {
            throw new DaemonException("offline", "Sin conexión con Pulsar", var10.getMessage());
         } catch (InterruptedException var11) {
            Thread.currentThread().interrupt();
            throw new DaemonException("interrupted", "Cancelado", path);
         }
      }
   }
}

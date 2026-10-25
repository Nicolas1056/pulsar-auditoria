package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Capability;
import com.metrofico.pulsar.api.pack.Json;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public final class EmoteRelay {
   private static final EmoteRelay INSTANCE = new EmoteRelay();
   private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();
   private volatile String room;
   private volatile Thread thread;
   private volatile boolean connected;

   private EmoteRelay() {
   }

   public static EmoteRelay get() {
      return INSTANCE;
   }

   private EmoteRelay.Session session() {
      Path f = Emotes.pack().dataDir().resolve("emotes").resolve("session.json");

      try {
         if (!Files.isRegularFile(f)) {
            return null;
         } else {
            Map<String, Object> o = Json.object(Json.parse(Files.readString(f, StandardCharsets.UTF_8)), "session");
            String token = o.get("token") instanceof String t && !t.isEmpty() ? t : null;
            String url = o.get("serviceUrl") instanceof String u && !u.isEmpty() ? u : Emotes.pack().serviceUrl();
            return token != null && !url.isEmpty() ? new EmoteRelay.Session(token, url.replaceAll("/+$", "")) : null;
         }
      } catch (Exception var7) {
         return null;
      }
   }

   public static String currentRoom() {
      return Emotes.host().currentServer();
   }

   public synchronized void joined() {
      String r = currentRoom();
      if (r != null && EmoteWheel.enabled()) {
         if (!r.equals(this.room) || this.thread == null || !this.thread.isAlive()) {
            this.left();
            this.room = r;
            Thread t = new Thread(() -> this.loop(r), "pulsar-emote-relay");
            t.setDaemon(true);
            this.thread = t;
            t.start();
         }
      }
   }

   public synchronized void left() {
      this.room = null;
      this.connected = false;
      Thread t = this.thread;
      this.thread = null;
      if (t != null) {
         t.interrupt();
      }
   }

   public boolean connected() {
      return this.connected;
   }

   private void loop(String r) {
      long backoff = 2000L;

      while (r.equals(this.room) && !Thread.currentThread().isInterrupted()) {
         try {
            if (Emotes.pack().granted(Capability.XP_EMOTES)) {
               Thread.sleep(10000L);
               continue;
            }

            EmoteRelay.Session s = this.session();
            if (s == null) {
               Thread.sleep(30000L);
               continue;
            }

            this.stream(s, r);
            backoff = 2000L;
         } catch (InterruptedException var6) {
            return;
         } catch (Exception var7) {
            Emotes.log("emote relay: " + var7.getMessage() + " · reintento en " + backoff / 1000L + " s");
         }

         this.connected = false;

         try {
            Thread.sleep(backoff);
         } catch (InterruptedException var5) {
            return;
         }

         backoff = Math.min(backoff * 2L, 60000L);
      }
   }

   private void stream(EmoteRelay.Session s, String r) throws IOException, InterruptedException {
      HttpRequest req = HttpRequest.newBuilder(
            URI.create(s.serviceUrl() + "/api/v1/launcher/emotes/stream?server=" + URLEncoder.encode(r, StandardCharsets.UTF_8))
         )
         .header("Accept", "text/event-stream")
         .header("Authorization", "Bearer " + s.token())
         .GET()
         .build();
      HttpResponse<Stream<String>> res = this.http.send(req, BodyHandlers.ofLines());
      if (res.statusCode() != 200) {
         throw new IOException("stream " + res.statusCode());
      } else {
         this.connected = true;
         Emotes.log("emote relay: en la sala " + r);
         String event = "";
         StringBuilder data = new StringBuilder();

         for (String line : res.body()::iterator) {
            if (!r.equals(this.room)) {
               break;
            }

            if (line.startsWith("event:")) {
               event = line.substring(6).trim();
            } else if (line.startsWith("data:")) {
               data.append(line.substring(5).trim());
            } else if (line.isEmpty()) {
               if (event.equals("emote") && data.length() > 0) {
                  this.handle(data.toString());
               }

               event = "";
               data.setLength(0);
            }
         }

         this.connected = false;
      }
   }

   private void handle(String json) {
      try {
         Map<String, Object> f = Json.object(Json.parse(json), "emote");
         Emotes.host().runOnMain(() -> EmoteSync.apply(f));
      } catch (RuntimeException var3) {
         Emotes.log("emote relay: mensaje ilegible");
      }
   }

   public void play(Emote emote, String sha256) {
      String r = this.room;
      EmoteRelay.Session s = this.session();
      if (r != null && s != null && sha256 != null) {
         this.post(s, "/api/v1/launcher/emotes/play", Map.of("server", r, "id", emote.id(), "loop", emote.loop()));
      }
   }

   public void stop() {
      String r = this.room;
      EmoteRelay.Session s = this.session();
      if (r != null && s != null) {
         this.post(s, "/api/v1/launcher/emotes/stop", Map.of("server", r));
      }
   }

   private void post(EmoteRelay.Session s, String path, Map<String, Object> body) {
      Map<String, Object> b = new LinkedHashMap<>(body);
      this.http
         .sendAsync(
            HttpRequest.newBuilder(URI.create(s.serviceUrl() + path))
               .timeout(Duration.ofSeconds(10L))
               .header("Content-Type", "application/json")
               .header("Authorization", "Bearer " + s.token())
               .POST(BodyPublishers.ofString(Json.write(b), StandardCharsets.UTF_8))
               .build(),
            BodyHandlers.ofString()
         )
         .whenComplete((res, err) -> {
            if (err != null) {
               Emotes.log("emote relay: " + err.getMessage());
            } else if (res.statusCode() >= 400) {
               Emotes.log("emote relay: " + path + " → " + res.statusCode() + " " + res.body());
            }
         });
   }

   static UUID uuid(String s) {
      try {
         return UUID.fromString(s);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   private record Session(String token, String serviceUrl) {
   }
}

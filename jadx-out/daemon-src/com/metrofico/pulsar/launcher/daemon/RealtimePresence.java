package com.metrofico.pulsar.launcher.daemon;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public final class RealtimePresence {
   private static final long NO_SESSION_WAIT_MS = 15000L;
   private static final long MIN_BACKOFF_MS = 5000L;
   private static final long MAX_BACKOFF_MS = 60000L;
   private final URI uri;
   private final Supplier<String> token;
   private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10L)).build();

   public RealtimePresence(String gameRealtimeUrl, String version, Supplier<String> token) {
      this.uri = launcherUri(gameRealtimeUrl, version);
      this.token = token;
   }

   static URI launcherUri(String gameRealtimeUrl, String version) {
      if (gameRealtimeUrl != null && !gameRealtimeUrl.isBlank()) {
         String base = gameRealtimeUrl.trim();
         int q = base.indexOf(63);
         String path = q < 0 ? base : base.substring(0, q);
         String query = "client=launcher";
         if (version != null && !version.isBlank()) {
            query = query + "&v=" + URLEncoder.encode(version.trim(), StandardCharsets.UTF_8);
         }

         return URI.create(path + "?" + query);
      } else {
         return null;
      }
   }

   public void start() {
      if (this.uri != null) {
         Thread t = new Thread(this::run, "pulsar-realtime-presence");
         t.setDaemon(true);
         t.start();
      }
   }

   private void run() {
      long backoff = 5000L;

      while (!Thread.currentThread().isInterrupted()) {
         try {
            String t = this.token.get();
            if (t == null) {
               Thread.sleep(15000L);
            } else {
               long connectedAt = System.currentTimeMillis();
               String reason = this.connectOnce(t).join();
               LauncherLog.info("realtime · presencia cerrada (" + reason + ")");
               backoff = System.currentTimeMillis() - connectedAt > 60000L ? 5000L : Math.min(backoff * 2L, 60000L);
               Thread.sleep(backoff);
            }
         } catch (InterruptedException var8) {
            Thread.currentThread().interrupt();
         } catch (RuntimeException var9) {
            LauncherLog.info("realtime · presencia sin conexión: " + var9.getMessage());

            try {
               Thread.sleep(backoff);
            } catch (InterruptedException var7) {
               Thread.currentThread().interrupt();
            }

            backoff = Math.min(backoff * 2L, 60000L);
         }
      }
   }

   private CompletableFuture<String> connectOnce(String sessionToken) {
      final CompletableFuture<String> closed = new CompletableFuture<>();
      this.http.newWebSocketBuilder().header("Authorization", "Bearer " + sessionToken).buildAsync(this.uri, new Listener() {
         {
            Objects.requireNonNull(RealtimePresence.this);
         }

         @Override
         public CompletionStage<?> onText(WebSocket ws, CharSequence data, boolean last) {
            ws.request(1L);
            return null;
         }

         @Override
         public CompletionStage<?> onClose(WebSocket ws, int status, String reason) {
            closed.complete(status + " " + reason);
            return null;
         }

         @Override
         public void onError(WebSocket ws, Throwable error) {
            closed.complete("error: " + error.getMessage());
         }
      }).whenComplete((ws, error) -> {
         if (error != null) {
            closed.complete("no abre: " + error.getMessage());
         }
      });
      return closed;
   }
}

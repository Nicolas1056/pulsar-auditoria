package com.metrofico.pulsar.api.legit;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public final class RealtimeCloudLink implements CloudLink {
   private final URI uri;
   private final String token;
   private volatile WebSocket socket;

   public RealtimeCloudLink(URI uri, String token) {
      this.uri = uri;
      this.token = token;
   }

   public static URI endpoint() {
      String url = System.getProperty("pulsar.realtime.url", "").trim();
      return url.isEmpty() ? null : withVersion(url, System.getProperty("pulsar.client.version", ""));
   }

   static URI withVersion(String url, String version) {
      if (version != null && !version.isBlank()) {
         String v = URLEncoder.encode(version.trim(), StandardCharsets.UTF_8);
         return URI.create(url + (url.contains("?") ? "&" : "?") + "v=" + v);
      } else {
         return URI.create(url);
      }
   }

   @Override
   public void open(final CloudLink.Listener listener) throws Exception {
      this.socket = HttpClient.newBuilder()
         .connectTimeout(Duration.ofSeconds(10L))
         .build()
         .newWebSocketBuilder()
         .header("Authorization", "Bearer " + this.token)
         .buildAsync(this.uri, new WebSocket.Listener() {
            private final StringBuilder partial;

            {
               Objects.requireNonNull(RealtimeCloudLink.this);
               this.partial = new StringBuilder();
            }

            @Override
            public CompletionStage<?> onText(WebSocket ws, CharSequence data, boolean last) {
               this.partial.append(data);
               if (last) {
                  String frame = this.partial.toString();
                  this.partial.setLength(0);
                  listener.onText(frame);
               }

               ws.request(1L);
               return null;
            }

            @Override
            public CompletionStage<?> onClose(WebSocket ws, int status, String reason) {
               listener.onClosed(status + " " + reason);
               return null;
            }

            @Override
            public void onError(WebSocket ws, Throwable error) {
               listener.onClosed(String.valueOf(error));
            }
         })
         .get(15L, TimeUnit.SECONDS);
   }

   @Override
   public void send(String frame) {
      WebSocket s = this.socket;
      if (s != null) {
         synchronized (this) {
            s.sendText(frame, true).join();
         }
      }
   }

   @Override
   public void close() {
      WebSocket s = this.socket;
      if (s != null) {
         s.sendClose(1000, "bye");
      }
   }
}

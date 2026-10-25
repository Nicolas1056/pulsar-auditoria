package com.metrofico.pulsar.launcher.daemon;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public final class WebSocketTransport extends WebSocketServer {
   private final CoreService core;
   private final Path dataDir;

   public WebSocketTransport(int port, CoreService core, Path dataDir) {
      super(new InetSocketAddress("127.0.0.1", port));
      this.core = core;
      this.dataDir = dataDir;
      this.setReuseAddr(true);
   }

   public static void serve(int port, CoreService core, Path dataDir) throws InterruptedException {
      WebSocketTransport server = new WebSocketTransport(port, core, dataDir);
      server.start();
      System.err.println("Pulsar daemon listening on ws://127.0.0.1:" + port + " (development mode)");
      Thread.currentThread().join();
   }

   public void onOpen(WebSocket conn, ClientHandshake handshake) {
      conn.setAttachment(new Daemon(this.core, this.dataDir, line -> {
         if (conn.isOpen()) {
            conn.send(line);
         }
      }));
   }

   public void onMessage(WebSocket conn, String message) {
      Daemon daemon = (Daemon)conn.getAttachment();
      if (!daemon.handle(message)) {
         conn.close(1000, "shutdown");
      }
   }

   public void onClose(WebSocket conn, int code, String reason, boolean remote) {
      Daemon daemon = (Daemon)conn.getAttachment();
      if (daemon != null) {
         daemon.close();
      }
   }

   public void onError(WebSocket conn, Exception ex) {
      System.err.println("WebSocket error: " + ex);
   }

   public void onStart() {
   }
}

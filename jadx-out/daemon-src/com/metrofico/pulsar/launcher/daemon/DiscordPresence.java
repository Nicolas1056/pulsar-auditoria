package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public final class DiscordPresence {
   static final String APPLICATION_ID = "1559417420295184384";
   static final long RETRY_MS = 15000L;
   private static final int OP_HANDSHAKE = 0;
   private static final int OP_FRAME = 1;
   private static final int OP_CLOSE = 2;
   private static final int MAX_FRAME = 65536;
   private final Supplier<Boolean> enabled;
   private final Supplier<String> language;
   private final Supplier<String[]> player;
   private final String serviceUrl;
   private final Object lock = new Object();
   private DiscordPresence.Activity wanted;
   private DiscordPresence.Activity shown;
   private DiscordPresence.Pipe pipe;

   public DiscordPresence(Supplier<Boolean> enabled, Supplier<String> language, Supplier<String[]> player, String serviceUrl) {
      this.enabled = enabled;
      this.language = language;
      this.player = player;
      this.serviceUrl = serviceUrl;
      this.wanted = new DiscordPresence.Activity(null, System.currentTimeMillis());
   }

   public void start() {
      Thread t = new Thread(this::run, "pulsar-discord");
      t.setDaemon(true);
      t.start();
   }

   public void inLauncher() {
      this.set(new DiscordPresence.Activity(null, System.currentTimeMillis()));
   }

   public void playing(String version) {
      this.set(new DiscordPresence.Activity(version, System.currentTimeMillis()));
   }

   public void settingChanged() {
      synchronized (this.lock) {
         this.shown = null;
         this.lock.notifyAll();
      }
   }

   private void set(DiscordPresence.Activity activity) {
      synchronized (this.lock) {
         this.wanted = activity;
         this.lock.notifyAll();
      }
   }

   private void run() {
      while (true) {
         DiscordPresence.Activity target;
         synchronized (this.lock) {
            target = this.wanted;
         }

         String[] who = this.player.get();
         target = target.withPlayer(who == null ? null : who[0], who == null ? null : who[1]);

         try {
            if (!Boolean.TRUE.equals(this.enabled.get())) {
               if (this.pipe != null) {
                  this.send(null);
                  this.disconnect();
                  LauncherLog.info("discord · presencia apagada por el jugador");
               }
            } else {
               if (this.pipe == null && !this.connect()) {
                  target = null;
               }

               if (this.pipe != null && target != null && !target.equals(this.shown)) {
                  this.send(target);
                  this.shown = target;
               }
            }
         } catch (RuntimeException | IOException var7) {
            LauncherLog.info("discord · canal cerrado (" + var7.getMessage() + ")");
            this.disconnect();
         }

         synchronized (this.lock) {
            if (target != null
               && this.wanted.startMillis() == target.startMillis()
               && Objects.equals(this.wanted.version(), target.version())
               && (this.pipe == null || target.equals(this.shown))) {
               try {
                  this.lock.wait(15000L);
               } catch (InterruptedException var6) {
                  Thread.currentThread().interrupt();
                  return;
               }
            }
         }
      }
   }

   private boolean connect() throws IOException {
      for (Path candidate : candidates(System.getProperty("os.name", ""), System.getenv())) {
         DiscordPresence.Pipe p;
         try {
            p = open(candidate);
         } catch (RuntimeException | IOException var8) {
            continue;
         }

         try {
            JsonObject hello = new JsonObject();
            hello.addProperty("v", 1);
            hello.addProperty("client_id", "1559417420295184384");
            p.write(frame(0, hello.toString()));
            read(p);
            this.pipe = p;
            this.shown = null;
            LauncherLog.info("discord · conectado (" + candidate.getFileName() + ")");
            return true;
         } catch (IOException var7) {
            try {
               p.close();
            } catch (IOException var6) {
            }
         }
      }

      return false;
   }

   private void disconnect() {
      if (this.pipe != null) {
         try {
            this.pipe.close();
         } catch (IOException var2) {
         }

         this.pipe = null;
      }

      this.shown = null;
   }

   private void send(DiscordPresence.Activity activity) throws IOException {
      JsonObject args = new JsonObject();
      args.addProperty("pid", ProcessHandle.current().pid());
      if (activity != null) {
         args.add("activity", activityJson(activity, this.language.get(), this.serviceUrl));
      }

      JsonObject command = new JsonObject();
      command.addProperty("cmd", "SET_ACTIVITY");
      command.add("args", args);
      command.addProperty("nonce", UUID.randomUUID().toString());
      this.pipe.write(frame(1, command.toString()));
      read(this.pipe);
   }

   static JsonObject activityJson(DiscordPresence.Activity a, String language, String serviceUrl) {
      boolean english = "en".equals(language);
      JsonObject activity = new JsonObject();
      if (a.version() == null) {
         activity.addProperty("details", english ? "In the launcher" : "En el launcher");
      } else {
         activity.addProperty("details", "Minecraft " + a.version());
      }

      JsonObject timestamps = new JsonObject();
      timestamps.addProperty("start", a.startMillis() / 1000L);
      activity.add("timestamps", timestamps);
      JsonObject assets = new JsonObject();
      assets.addProperty("large_image", "pulsar");
      assets.addProperty("large_text", "Pulsar Launcher");
      if (a.playerUuid() != null && serviceUrl != null && serviceUrl.startsWith("https://")) {
         assets.addProperty("small_image", serviceUrl.replaceAll("/+$", "") + "/api/v1/heads/" + a.playerUuid().replace("-", "") + ".png");
         if (a.playerName() != null) {
            assets.addProperty("small_text", a.playerName());
         }
      }

      activity.add("assets", assets);
      activity.addProperty("instance", false);
      return activity;
   }

   static byte[] frame(int op, String json) {
      byte[] body = json.getBytes(StandardCharsets.UTF_8);
      return ByteBuffer.allocate(8 + body.length).order(ByteOrder.LITTLE_ENDIAN).putInt(op).putInt(body.length).put(body).array();
   }

   private static void read(DiscordPresence.Pipe p) throws IOException {
      byte[] header = new byte[8];
      p.readFully(header);
      ByteBuffer h = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);
      int op = h.getInt();
      int length = h.getInt();
      if (length >= 0 && length <= 65536) {
         byte[] body = new byte[length];
         p.readFully(body);
         if (op == 2) {
            throw new IOException("discord closed: " + new String(body, StandardCharsets.UTF_8));
         }
      } else {
         throw new IOException("bad frame length " + length);
      }
   }

   static List<Path> candidates(String osName, Map<String, String> env) {
      List<Path> out = new ArrayList<>();
      if (osName.toLowerCase(Locale.ROOT).contains("win")) {
         for (int i = 0; i < 10; i++) {
            out.add(Path.of("\\\\.\\pipe\\discord-ipc-" + i));
         }

         return out;
      } else {
         List<String> bases = new ArrayList<>();

         for (String key : new String[]{"XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP"}) {
            String v = env.get(key);
            if (v != null && !v.isBlank() && !bases.contains(v)) {
               bases.add(v);
            }
         }

         if (!bases.contains("/tmp")) {
            bases.add("/tmp");
         }

         String[] apps = new String[]{
            "", "app/com.discordapp.Discord", "app/com.discordapp.DiscordCanary", "snap.discord", "snap.discord-canary", ".flatpak/dev.vencord.Vesktop/xdg-run"
         };

         for (String base : bases) {
            for (String app : apps) {
               Path dir = Path.of(base, app);
               if (Files.isDirectory(dir)) {
                  for (int i = 0; i < 10; i++) {
                     out.add(dir.resolve("discord-ipc-" + i));
                  }
               }
            }
         }

         return out;
      }
   }

   private static DiscordPresence.Pipe open(Path path) throws IOException {
      if (path.toString().startsWith("\\\\.\\pipe\\")) {
         final RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
         return new DiscordPresence.Pipe() {
            @Override
            public void write(byte[] bytes) throws IOException {
               file.write(bytes);
            }

            @Override
            public void readFully(byte[] into) throws IOException {
               file.readFully(into);
            }

            @Override
            public void close() throws IOException {
               file.close();
            }
         };
      } else if (!Files.exists(path)) {
         throw new IOException("no socket");
      } else {
         final SocketChannel channel = SocketChannel.open(UnixDomainSocketAddress.of(path));
         return new DiscordPresence.Pipe() {
            @Override
            public void write(byte[] bytes) throws IOException {
               ByteBuffer b = ByteBuffer.wrap(bytes);

               while (b.hasRemaining()) {
                  channel.write(b);
               }
            }

            @Override
            public void readFully(byte[] into) throws IOException {
               ByteBuffer b = ByteBuffer.wrap(into);

               while (b.hasRemaining()) {
                  if (channel.read(b) < 0) {
                     throw new IOException("closed");
                  }
               }
            }

            @Override
            public void close() throws IOException {
               channel.close();
            }
         };
      }
   }

   record Activity(String version, long startMillis, String playerUuid, String playerName) {
      Activity(String version, long startMillis) {
         this(version, startMillis, null, null);
      }

      DiscordPresence.Activity withPlayer(String uuid, String name) {
         return new DiscordPresence.Activity(this.version, this.startMillis, uuid, name);
      }
   }

   interface Pipe extends AutoCloseable {
      void write(byte[] var1) throws IOException;

      void readFully(byte[] var1) throws IOException;

      @Override
      void close() throws IOException;
   }
}

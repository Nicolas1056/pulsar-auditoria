package com.metrofico.pulsar.engine.v189.input;

import com.metrofico.pulsar.api.input.RawMouse;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class LinuxRawMouse189 {
   private static final int EVENT_SIZE = 24;
   private static final int EV_REL = 2;
   private static final int REL_X = 0;
   private static final int REL_Y = 1;
   private static final int EV_SYN = 0;
   private static final Path SYS = Path.of("/sys/class/input");
   private static final Set<String> OPEN = ConcurrentHashMap.newKeySet();

   private LinuxRawMouse189() {
   }

   static void start() {
      Thread scan = new Thread(LinuxRawMouse189::scanLoop, "pulsar-rawmouse-scan");
      scan.setDaemon(true);
      scan.setPriority(1);
      scan.start();
   }

   private static void scanLoop() {
      boolean logged = false;

      while (true) {
         int opened = scan();
         if (!logged) {
            logged = true;
            BootstrapLog.info(OPEN.isEmpty() ? "raw input: no readable evdev mouse, using the cursor" : "raw input: evdev active (" + OPEN.size() + " mice)");
         } else if (opened > 0) {
            BootstrapLog.info("raw input: " + opened + " new evdev mouse");
         }

         try {
            Thread.sleep(2000L);
         } catch (InterruptedException var3) {
            return;
         }
      }
   }

   private static int scan() {
      int opened = 0;

      try (Stream<Path> nodes = Files.list(SYS)) {
         for (Path node : nodes::iterator) {
            String name = node.getFileName().toString();
            if (name.startsWith("event") && !OPEN.contains(name) && relativeXY(node)) {
               Path dev = Path.of("/dev/input", name);
               if (Files.isReadable(dev)) {
                  OPEN.add(name);
                  Thread t = new Thread(() -> read(name, dev), "pulsar-rawmouse-" + name);
                  t.setDaemon(true);
                  t.start();
                  opened++;
               }
            }
         }
      } catch (RuntimeException | IOException var9) {
      }

      RawMouse.available(!OPEN.isEmpty());
      return opened;
   }

   static boolean relativeXY(Path node) {
      try {
         String[] words = Files.readString(node.resolve("device/capabilities/rel")).trim().split("\\s+");
         long mask = Long.parseUnsignedLong(words[words.length - 1], 16);
         return (mask & 3L) == 3L;
      } catch (RuntimeException | IOException var4) {
         return false;
      }
   }

   static void read(String name, Path dev) {
      byte[] buffer = new byte[1536];
      ByteBuffer events = ByteBuffer.wrap(buffer).order(ByteOrder.nativeOrder());

      try (InputStream in = new FileInputStream(dev.toFile())) {
         int dx = 0;
         int dy = 0;

         while (true) {
            int n = in.read(buffer);
            if (n < 0) {
               break;
            }

            for (int o = 0; o + 24 <= n; o += 24) {
               int type = events.getShort(o + 16) & '\uffff';
               int code = events.getShort(o + 18) & '\uffff';
               int value = events.getInt(o + 20);
               if (type == 2 && code == 0) {
                  dx += value;
               } else if (type == 2 && code == 1) {
                  dy += value;
               } else if (type == 0 && (dx != 0 || dy != 0)) {
                  RawMouse.add(dx, dy, System.nanoTime());
                  dx = 0;
                  dy = 0;
               }
            }
         }
      } catch (IOException var19) {
      } finally {
         OPEN.remove(name);
         RawMouse.available(!OPEN.isEmpty());
      }
   }
}

package com.metrofico.pulsar.api.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;
import java.util.function.LongSupplier;

public final class InputPipe {
   public static final String PROPERTY = "pulsar.input";
   public static final String LOG_PROPERTY = "pulsar.input.log";
   public static final String ENFORCE_PROPERTY = "pulsar.input.enforce";
   private static final long WATCH_MS = 500L;
   private static volatile String binarySha256 = "";
   private static final DateTimeFormatter STAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   private InputPipe() {
   }

   public static boolean enforcing() {
      return !"off".equals(System.getProperty("pulsar.input.enforce"));
   }

   public static String binarySha256() {
      return binarySha256;
   }

   public static MouseLock startFromSystem(MouseLock lock) {
      String bin = System.getProperty("pulsar.input");
      if (bin != null && !bin.isBlank()) {
         String log = System.getProperty("pulsar.input.log");
         if (log != null && !log.isBlank()) {
            Path file = Path.of(log);
            lock.localLog(line -> append(file, line));
         }

         Thread.ofPlatform().daemon().name("pulsar-input-hash").start(() -> binarySha256 = sha256(Path.of(bin)));
         start(System.in, lock, MouseLock::clockMs);
         return lock;
      } else {
         return null;
      }
   }

   public static void start(InputStream in, MouseLock lock, LongSupplier clock) {
      MouseLock.install(lock);
      Thread reader = Thread.ofPlatform().daemon().name("pulsar-input").unstarted(() -> {
         String line;
         try (BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            while ((line = r.readLine()) != null) {
               lock.onLine(line, clock.getAsLong());
            }
         } catch (IOException var8) {
         }

         lock.detectorGone();
      });
      reader.setPriority(1);
      reader.start();
      Thread watch = Thread.ofPlatform().daemon().name("pulsar-input-watch").unstarted(() -> {
         while (!lock.blocked()) {
            try {
               Thread.sleep(500L);
            } catch (InterruptedException var3x) {
               return;
            }

            lock.tick(clock.getAsLong());
         }
      });
      watch.setPriority(1);
      watch.start();
   }

   private static void append(Path file, String line) {
      try {
         Files.writeString(
            file, LocalDateTime.now().format(STAMP) + " " + line + "\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND
         );
      } catch (IOException var3) {
      }
   }

   private static String sha256(Path file) {
      try {
         String var8;
         try (InputStream in = Files.newInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[65536];

            while ((n = in.read(buf)) > 0) {
               md.update(buf, 0, n);
            }

            var8 = HexFormat.of().formatHex(md.digest());
         }

         return var8;
      } catch (Exception var7) {
         return "";
      }
   }
}

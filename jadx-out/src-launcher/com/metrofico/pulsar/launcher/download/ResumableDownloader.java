package com.metrofico.pulsar.launcher.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Locale;

public final class ResumableDownloader {
   private static final int BUFFER = 65536;
   private final HttpClient http;
   private final Duration timeout;

   public ResumableDownloader(HttpClient http) {
      this(http, Duration.ofSeconds(30L));
   }

   public ResumableDownloader(HttpClient http, Duration timeout) {
      this.http = http;
      this.timeout = timeout;
   }

   public long download(String url, Path target, String sha256Hex, long size, ResumableDownloader.Progress progress) throws IOException, InterruptedException {
      String expected = sha256Hex.toLowerCase(Locale.ROOT);
      if (Files.isRegularFile(target) && Files.size(target) == size && hashOf(target).equals(expected)) {
         progress.update(size, size);
         return 0L;
      } else {
         Files.createDirectories(target.toAbsolutePath().getParent());
         Path part = target.resolveSibling(target.getFileName() + ".part");
         Path meta = target.resolveSibling(target.getFileName() + ".part.meta");
         if (!Files.isRegularFile(meta) || !Files.readString(meta).trim().equals(expected)) {
            Files.deleteIfExists(part);
            Files.writeString(meta, expected);
         }

         long have = Files.isRegularFile(part) ? Files.size(part) : 0L;
         if (have > size) {
            Files.deleteIfExists(part);
            have = 0L;
         }

         MessageDigest md = sha256();
         if (have > 0L) {
            digestInto(md, part);
         }

         long got = 0L;
         if (have < size) {
            Builder b = HttpRequest.newBuilder(URI.create(url)).timeout(this.timeout).GET();
            if (have > 0L) {
               b.header("Range", "bytes=" + have + "-");
            }

            HttpResponse<InputStream> r = this.http.send(b.build(), BodyHandlers.ofInputStream());
            boolean append;
            if (r.statusCode() == 206 && have > 0L) {
               append = true;
            } else {
               if (r.statusCode() != 200) {
                  r.body().close();
                  throw new IOException("HTTP " + r.statusCode() + " al descargar " + url);
               }

               append = false;
               have = 0L;
               md = sha256();
            }

            try (
               InputStream in = r.body();
               OutputStream out = Files.newOutputStream(
                  part,
                  append
                     ? new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND}
                     : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE}
               );
            ) {
               byte[] buf = new byte[65536];

               int n;
               while ((n = in.read(buf)) > 0) {
                  out.write(buf, 0, n);
                  md.update(buf, 0, n);
                  have += n;
                  got += n;
                  if (have > size) {
                     throw new IOException("más bytes de los anunciados (" + have + " > " + size + ")");
                  }

                  progress.update(have, size);
               }
            }
         }

         if (have != size) {
            throw new IOException("descarga incompleta: " + have + "/" + size + " bytes");
         } else {
            String actual = HexFormat.of().formatHex(md.digest());
            if (!actual.equals(expected)) {
               Files.deleteIfExists(part);
               Files.deleteIfExists(meta);
               throw new IOException("SHA-256 no coincide (" + actual + " ≠ " + expected + ")");
            } else {
               Files.move(part, target, StandardCopyOption.REPLACE_EXISTING);
               Files.deleteIfExists(meta);
               progress.update(size, size);
               return got;
            }
         }
      }
   }

   private static MessageDigest sha256() {
      try {
         return MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var1) {
         throw new IllegalStateException(var1);
      }
   }

   private static void digestInto(MessageDigest md, Path file) throws IOException {
      try (InputStream in = Files.newInputStream(file)) {
         byte[] buf = new byte[65536];

         int n;
         while ((n = in.read(buf)) > 0) {
            md.update(buf, 0, n);
         }
      }
   }

   static String hashOf(Path file) throws IOException {
      MessageDigest md = sha256();
      digestInto(md, file);
      return HexFormat.of().formatHex(md.digest());
   }

   public interface Progress {
      void update(long var1, long var3);
   }
}

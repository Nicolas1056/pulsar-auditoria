package com.metrofico.pulsar.api.pack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.zip.GZIPInputStream;

public final class ResourceCache {
   private final Path dir;
   private final HttpClient http;

   public ResourceCache(Path dataDir, HttpClient http) {
      this.dir = dataDir.resolve("packs").resolve("cache");
      this.http = http;
   }

   public Path pathFor(String sha256) {
      return this.dir.resolve(sha256);
   }

   public boolean has(PackManifest.Resource r) {
      Path p = this.pathFor(r.sha256());

      try {
         return Files.isRegularFile(p) && Files.size(p) == r.size();
      } catch (IOException var4) {
         return false;
      }
   }

   public void fetchAll(List<PackManifest.Resource> resources, BiConsumer<Integer, Integer> progress) throws IOException, InterruptedException {
      Files.createDirectories(this.dir);
      int done = 0;

      for (PackManifest.Resource r : resources) {
         if (!this.has(r)) {
            this.fetch(r);
         }

         progress.accept(++done, resources.size());
      }
   }

   public Path fetchObject(String sha256, String url, long maxBytes) throws IOException, InterruptedException {
      Path target = this.pathFor(sha256);
      if (Files.isRegularFile(target)) {
         return target;
      } else {
         Files.createDirectories(this.dir);
         HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
         HttpResponse<InputStream> res = this.http.send(req, BodyHandlers.ofInputStream());
         if (res.statusCode() != 200) {
            res.body().close();
            throw new IOException("HTTP " + res.statusCode() + " for object " + sha256.substring(0, 12));
         } else {
            Path part = this.pathFor(sha256 + ".part");

            MessageDigest md;
            try {
               md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException var23) {
               throw new IllegalStateException(var23);
            }

            long written = 0L;
            boolean gzip = url.endsWith(".gz") || res.headers().firstValue("Content-Encoding").map("gzip"::equalsIgnoreCase).orElse(false);

            try (
               InputStream raw = res.body();
               InputStream body = (InputStream)(gzip ? new GZIPInputStream(raw) : raw);
               InputStream in = new DigestInputStream(body, md);
               OutputStream out = Files.newOutputStream(part);
            ) {
               byte[] buf = new byte[65536];

               int n;
               while ((n = in.read(buf)) != -1) {
                  written += n;
                  if (written > maxBytes) {
                     throw new IOException("object larger than " + maxBytes + " bytes: " + sha256.substring(0, 12));
                  }

                  out.write(buf, 0, n);
               }
            } catch (IOException var28) {
               Files.deleteIfExists(part);
               throw var28;
            }

            String actual = HexFormat.of().formatHex(md.digest());
            if (!actual.equals(sha256)) {
               Files.deleteIfExists(part);
               throw new IOException("object failed verification: " + sha256.substring(0, 12));
            } else {
               Files.move(part, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
               return target;
            }
         }
      }
   }

   void fetch(PackManifest.Resource r) throws IOException, InterruptedException {
      HttpRequest req = HttpRequest.newBuilder(URI.create(r.url())).GET().build();
      HttpResponse<InputStream> res = this.http.send(req, BodyHandlers.ofInputStream());
      if (res.statusCode() != 200) {
         throw new IOException("HTTP " + res.statusCode() + " for " + r.path());
      } else {
         Path part = this.pathFor(r.sha256() + ".part");

         MessageDigest md;
         try {
            md = MessageDigest.getInstance("SHA-256");
         } catch (NoSuchAlgorithmException var14) {
            throw new IllegalStateException(var14);
         }

         long written = 0L;

         try (
            InputStream in = new DigestInputStream(res.body(), md);
            OutputStream out = Files.newOutputStream(part);
         ) {
            byte[] buf = new byte[65536];

            int n;
            while ((n = in.read(buf)) != -1) {
               written += n;
               if (written > r.size()) {
                  throw new IOException("resource larger than declared: " + r.path());
               }

               out.write(buf, 0, n);
            }
         } catch (IOException var17) {
            Files.deleteIfExists(part);
            throw var17;
         }

         String actual = HexFormat.of().formatHex(md.digest());
         if (written == r.size() && actual.equals(r.sha256())) {
            Files.move(part, this.pathFor(r.sha256()), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } else {
            Files.deleteIfExists(part);
            throw new IOException("resource failed verification: " + r.path());
         }
      }
   }
}

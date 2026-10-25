package com.metrofico.pulsar.launcher.download;

import com.metrofico.pulsar.launcher.integrity.Sha1;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public final class VerifiedDownloader {
   private static final String PART_SUFFIX = ".part";
   private final HttpClient http;
   private final int maxConcurrent;
   private final int maxAttempts;
   private final Duration retryBackoff;

   public VerifiedDownloader(HttpClient http, int maxConcurrent, int maxAttempts, Duration retryBackoff) {
      if (maxConcurrent >= 1 && maxAttempts >= 1) {
         this.http = http;
         this.maxConcurrent = maxConcurrent;
         this.maxAttempts = maxAttempts;
         this.retryBackoff = retryBackoff;
      } else {
         throw new IllegalArgumentException("maxConcurrent and maxAttempts must be >= 1");
      }
   }

   public static VerifiedDownloader createDefault(HttpClient http) {
      return new VerifiedDownloader(http, 8, 3, Duration.ofMillis(500L));
   }

   public static boolean isValid(Path target, String sha1, long size) throws IOException {
      return isValid(target, sha1, Sha1.digest(), size);
   }

   public static boolean isValid(DownloadRequest request) throws IOException {
      return isValid(request.target(), request.expectedHash(), request.newDigest(), request.size());
   }

   private static boolean isValid(Path target, String expectedHex, MessageDigest digest, long size) throws IOException {
      if (!Files.isRegularFile(target)) {
         return false;
      } else if (size >= 0L && Files.size(target) != size) {
         return false;
      } else if (expectedHex == null) {
         return true;
      } else {
         try (InputStream in = Files.newInputStream(target)) {
            byte[] buffer = new byte[65536];

            int read;
            while ((read = in.read(buffer)) != -1) {
               digest.update(buffer, 0, read);
            }
         }

         return HexFormat.of().formatHex(digest.digest()).equalsIgnoreCase(expectedHex);
      }
   }

   public DownloadResult download(DownloadRequest request) throws DownloadFailedException, InterruptedException {
      try {
         if (isValid(request)) {
            return new DownloadResult(request, DownloadResult.Outcome.CACHED, Files.size(request.target()));
         }
      } catch (IOException var7) {
         throw new DownloadFailedException(request, 0, "could not inspect existing file " + request.target(), var7);
      }

      Exception last = null;

      for (int attempt = 1; attempt <= this.maxAttempts; attempt++) {
         try {
            long bytes = this.fetchAndVerify(request);
            return new DownloadResult(request, DownloadResult.Outcome.DOWNLOADED, bytes);
         } catch (IOException var6) {
            last = var6;
            if (attempt < this.maxAttempts && !this.retryBackoff.isZero()) {
               Thread.sleep(this.retryBackoff.multipliedBy(attempt));
            }
         }
      }

      throw new DownloadFailedException(request, this.maxAttempts, last.getMessage(), last);
   }

   public List<DownloadResult> downloadAll(Collection<DownloadRequest> requests, ProgressListener listener) throws DownloadFailedException, InterruptedException {
      Semaphore slots = new Semaphore(this.maxConcurrent);
      AtomicInteger done = new AtomicInteger();
      int total = requests.size();
      List<Future<DownloadResult>> futures = new ArrayList<>(total);

      Object var19;
      try (ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor()) {
         for (DownloadRequest request : requests) {
            futures.add(pool.submit(() -> {
               slots.acquire();

               DownloadResult var7x;
               try {
                  DownloadResult result = this.download(request);
                  listener.onFileCompleted(result, done.incrementAndGet(), total);
                  var7x = result;
               } finally {
                  slots.release();
               }

               return var7x;
            }));
         }

         List<DownloadResult> results = new ArrayList<>(total);

         for (Future<DownloadResult> future : futures) {
            try {
               results.add(future.get());
            } catch (ExecutionException var15) {
               futures.forEach(f -> f.cancel(true));
               if (var15.getCause() instanceof DownloadFailedException failed) {
                  throw failed;
               }

               throw new IllegalStateException("Unexpected download failure", var15.getCause());
            }
         }

         var19 = results;
      }

      return (List<DownloadResult>)var19;
   }

   private long fetchAndVerify(DownloadRequest request) throws IOException, InterruptedException {
      Path target = request.target();
      Files.createDirectories(target.toAbsolutePath().getParent());
      Path part = target.resolveSibling(target.getFileName() + ".part");
      HttpRequest httpRequest = HttpRequest.newBuilder(request.url()).GET().build();
      HttpResponse<InputStream> response = this.http.send(httpRequest, BodyHandlers.ofInputStream());
      if (response.statusCode() != 200) {
         try (InputStream body = response.body()) {
            body.skip(Long.MAX_VALUE);
         }

         throw new IOException("HTTP " + response.statusCode());
      } else {
         MessageDigest digest = request.newDigest();
         long written = 0L;

         try (
            InputStream in = response.body();
            OutputStream out = new DigestOutputStream(Files.newOutputStream(part), digest);
         ) {
            byte[] buffer = new byte[65536];

            int read;
            while ((read = in.read(buffer)) != -1) {
               out.write(buffer, 0, read);
               written += read;
            }
         } catch (IOException var19) {
            Files.deleteIfExists(part);
            throw var19;
         }

         String actual = HexFormat.of().formatHex(digest.digest());
         if (request.size() >= 0L && written != request.size()) {
            Files.deleteIfExists(part);
            throw new IOException("size mismatch: expected " + request.size() + " bytes, got " + written);
         } else if (request.expectedHash() != null && !actual.equalsIgnoreCase(request.expectedHash())) {
            Files.deleteIfExists(part);
            throw new IOException(request.algorithm() + " mismatch: expected " + request.expectedHash() + ", got " + actual);
         } else {
            Files.move(part, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            return written;
         }
      }
   }
}

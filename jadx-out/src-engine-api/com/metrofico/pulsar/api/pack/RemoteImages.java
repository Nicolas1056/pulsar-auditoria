package com.metrofico.pulsar.api.pack;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class RemoteImages {
   private static final HttpClient HTTP = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private static final Map<String, CompletableFuture<Path>> DOWNLOADS = new ConcurrentHashMap<>();

   private RemoteImages() {
   }

   public static Path remote(Path dataDir, String url, Consumer<String> log) {
      Path dir = dataDir.resolve("packs").resolve("images");
      Path file = dir.resolve(sha256(url) + ".png");
      if (Files.isRegularFile(file)) {
         return file;
      } else {
         DOWNLOADS.computeIfAbsent(
            url,
            u -> CompletableFuture.supplyAsync(
               () -> {
                  try {
                     Files.createDirectories(dir);
                     HttpResponse<byte[]> res = HTTP.send(
                        HttpRequest.newBuilder(URI.create(u)).timeout(Duration.ofSeconds(20L)).GET().build(), BodyHandlers.ofByteArray()
                     );
                     if (res.statusCode() == 200 && res.body().length <= 2097152) {
                        Path tmp = file.resolveSibling(file.getFileName() + ".part");
                        Files.write(tmp, res.body());
                        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
                        return file;
                     } else {
                        throw new IllegalStateException("HTTP " + res.statusCode() + ", " + res.body().length + " bytes");
                     }
                  } catch (Exception var6) {
                     log.accept("pulsar:v1 remote image failed " + u + ": " + var6);
                     throw new IllegalStateException(var6);
                  }
               }
            )
         );
         return null;
      }
   }

   public static String sha256(String s) {
      try {
         return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException(var2);
      }
   }
}

package com.metrofico.pulsar.launcher.meta;

import com.metrofico.pulsar.launcher.integrity.Sha1;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public final class PistonMetaClient {
   public static final URI VERSION_MANIFEST_URI = URI.create("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json");
   private final HttpClient http;

   public PistonMetaClient(HttpClient http) {
      this.http = http;
   }

   public static PistonMetaClient createDefault() {
      return new PistonMetaClient(HttpClient.newBuilder().followRedirects(Redirect.NORMAL).connectTimeout(Duration.ofSeconds(10L)).build());
   }

   public VersionManifest fetchVersionManifest() throws IOException, InterruptedException {
      HttpRequest request = HttpRequest.newBuilder(VERSION_MANIFEST_URI).GET().build();
      HttpResponse<String> response = this.http.send(request, BodyHandlers.ofString());
      if (response.statusCode() != 200) {
         throw new IOException("piston-meta returned HTTP " + response.statusCode() + " for " + VERSION_MANIFEST_URI);
      } else {
         return VersionManifest.fromJson(response.body());
      }
   }

   public VersionDetail fetchVersionDetail(VersionManifest.Version version) throws IOException, InterruptedException {
      return VersionDetail.fromJson(this.fetchVersionDetailJson(version));
   }

   public String fetchVersionDetailJson(VersionManifest.Version version) throws IOException, InterruptedException {
      URI uri = URI.create(version.url());
      HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
      HttpResponse<byte[]> response = this.http.send(request, BodyHandlers.ofByteArray());
      if (response.statusCode() != 200) {
         throw new IOException("piston-meta returned HTTP " + response.statusCode() + " for " + uri);
      } else {
         byte[] body = response.body();
         if (version.sha1() != null && !Sha1.matches(body, version.sha1())) {
            throw new IOException(
               "Version detail for " + version.id() + " failed SHA-1 verification (expected " + version.sha1() + ", got " + Sha1.hex(body) + ")"
            );
         } else {
            return new String(body, StandardCharsets.UTF_8);
         }
      }
   }
}

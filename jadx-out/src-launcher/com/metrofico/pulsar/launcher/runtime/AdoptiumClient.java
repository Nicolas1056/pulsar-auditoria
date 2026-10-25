package com.metrofico.pulsar.launcher.runtime;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metrofico.pulsar.launcher.rules.Platform;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public final class AdoptiumClient implements RuntimeSource {
   public static final String DEFAULT_BASE = "https://api.adoptium.net";
   public static final String VENDOR = "temurin";
   private final HttpClient http;
   private final String base;

   public AdoptiumClient(HttpClient http, String base) {
      this.http = http;
      this.base = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
   }

   public static AdoptiumClient createDefault() {
      return new AdoptiumClient(
         HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15L)).followRedirects(Redirect.NORMAL).build(), "https://api.adoptium.net"
      );
   }

   @Override
   public String vendor() {
      return "temurin";
   }

   @Override
   public RuntimeRelease latest(int major, Platform platform) throws IOException, InterruptedException {
      URI uri = URI.create(
         this.base + "/v3/assets/latest/" + major + "/hotspot?os=" + os(platform) + "&architecture=" + arch(platform) + "&image_type=jre&vendor=eclipse"
      );
      HttpResponse<String> response = this.http.send(HttpRequest.newBuilder(uri).GET().header("Accept", "application/json").build(), BodyHandlers.ofString());
      if (response.statusCode() != 200) {
         throw new IOException("Adoptium HTTP " + response.statusCode() + " for Java " + major + " (" + os(platform) + "/" + arch(platform) + ")");
      } else {
         return parse(major, response.body());
      }
   }

   public static RuntimeRelease parse(int major, String json) throws IOException {
      JsonArray assets = JsonParser.parseString(json).getAsJsonArray();
      if (assets.isEmpty()) {
         throw new IOException("Adoptium has no JRE " + major + " for this platform");
      } else {
         JsonObject asset = assets.get(0).getAsJsonObject();
         JsonObject pkg = asset.getAsJsonObject("binary").getAsJsonObject("package");
         String name = pkg.get("name").getAsString();
         return new RuntimeRelease(
            major,
            asset.get("release_name").getAsString(),
            name,
            pkg.get("link").getAsString(),
            pkg.get("checksum").getAsString(),
            pkg.get("size").getAsLong(),
            ArchiveExtractor.formatOf(name)
         );
      }
   }

   static String os(Platform p) {
      String var1 = p.os();

      return switch (var1) {
         case "windows" -> "windows";
         case "osx" -> "mac";
         default -> "linux";
      };
   }

   static String arch(Platform p) {
      String var1 = p.arch();

      return switch (var1) {
         case "arm64" -> "aarch64";
         case "x86" -> "x86";
         default -> "x64";
      };
   }
}

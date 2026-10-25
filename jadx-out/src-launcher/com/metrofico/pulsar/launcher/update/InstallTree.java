package com.metrofico.pulsar.launcher.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public record InstallTree(
   String version,
   String channel,
   String os,
   String arch,
   String publishedAt,
   String notes,
   String minVersion,
   List<InstallTree.Entry> files,
   long totalSize,
   long totalObjectSize,
   boolean signed,
   String rawJson
) {
   public static InstallTree parse(String json, PublicKey publicKey) {
      JsonObject root = JsonParser.parseString(json).getAsJsonObject();
      if (root.has("version") && root.get("version").getAsInt() == 1 && root.has("tree")) {
         JsonObject t = root.getAsJsonObject("tree");
         boolean signed = root.has("signature")
            && publicKey != null
            && ReleaseManifest.verify(ReleaseManifest.canonical(t), root.get("signature").getAsString(), publicKey);
         List<InstallTree.Entry> files = new ArrayList<>();

         for (JsonElement e : t.getAsJsonArray("files")) {
            JsonObject f = e.getAsJsonObject();
            String path = f.get("path").getAsString();
            if (path.isBlank() || path.startsWith("/") || path.contains("\\") || path.contains("..")) {
               throw new IllegalArgumentException("tree: ruta no permitida " + path);
            }

            files.add(
               new InstallTree.Entry(
                  path,
                  f.get("size").getAsLong(),
                  f.get("mode").getAsInt(),
                  f.get("sha256").getAsString().toLowerCase(),
                  f.get("object").getAsString(),
                  f.get("objectSize").getAsLong(),
                  f.get("objectSha256").getAsString().toLowerCase()
               )
            );
         }

         String v = t.get("version").getAsString();
         Semver.parse(v);
         return new InstallTree(
            v,
            t.get("channel").getAsString(),
            t.get("os").getAsString(),
            t.get("arch").getAsString(),
            t.get("publishedAt").getAsString(),
            t.has("notes") ? t.get("notes").getAsString() : "",
            t.has("minVersion") ? t.get("minVersion").getAsString() : null,
            List.copyOf(files),
            t.get("totalSize").getAsLong(),
            t.get("totalObjectSize").getAsLong(),
            signed,
            json
         );
      } else {
         throw new IllegalArgumentException("tree: version must be 1 and tree present");
      }
   }

   public record Entry(String path, long size, int mode, String sha256, String object, long objectSize, String objectSha256) {
      public boolean executable() {
         return (this.mode & 64) != 0;
      }
   }
}

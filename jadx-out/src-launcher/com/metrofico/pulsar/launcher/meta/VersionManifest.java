package com.metrofico.pulsar.launcher.meta;

import java.util.List;
import java.util.Optional;

public record VersionManifest(VersionManifest.Latest latest, List<VersionManifest.Version> versions) {
   public static VersionManifest fromJson(String json) {
      VersionManifest manifest = MojangJson.parse(json, VersionManifest.class, "Version manifest");
      if (manifest != null && manifest.latest != null && manifest.versions != null) {
         return new VersionManifest(manifest.latest, List.copyOf(manifest.versions));
      } else {
         throw new IllegalArgumentException("Version manifest is missing required fields (latest/versions)");
      }
   }

   public Optional<VersionManifest.Version> findVersion(String id) {
      return this.versions.stream().filter(v -> v.id().equals(id)).findFirst();
   }

   public record Latest(String release, String snapshot) {
   }

   public record Version(String id, String type, String url, String sha1) {
   }
}

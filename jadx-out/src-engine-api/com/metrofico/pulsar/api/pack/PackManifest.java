package com.metrofico.pulsar.api.pack;

import java.util.List;
import java.util.Set;

public record PackManifest(
   String id,
   long packVersion,
   String serverId,
   String name,
   String minClient,
   Set<Capability> capabilities,
   List<PackManifest.Resource> resources,
   String baseUrl,
   List<String> locales
) {
   public long totalSize() {
      return this.resources.stream().mapToLong(PackManifest.Resource::size).sum();
   }

   public record Resource(String path, String sha256, long size, String url) {
   }
}

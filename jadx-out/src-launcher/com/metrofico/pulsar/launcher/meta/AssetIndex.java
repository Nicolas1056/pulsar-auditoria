package com.metrofico.pulsar.launcher.meta;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public record AssetIndex(Map<String, AssetIndex.AssetObject> objects, Boolean virtual, Boolean mapToResources) {
   public static AssetIndex fromJson(String json) {
      AssetIndex index = MojangJson.parse(json, AssetIndex.class, "Asset index");
      if (index != null && index.objects != null) {
         for (Entry<String, AssetIndex.AssetObject> e : index.objects.entrySet()) {
            if (e.getValue() == null || e.getValue().hash() == null || e.getValue().hash().length() != 40) {
               throw new IllegalArgumentException("Asset '" + e.getKey() + "' has no valid hash");
            }
         }

         return new AssetIndex(Map.copyOf(index.objects), index.virtual, index.mapToResources);
      } else {
         throw new IllegalArgumentException("Asset index is missing 'objects'");
      }
   }

   public Map<String, AssetIndex.AssetObject> uniqueObjects() {
      Map<String, AssetIndex.AssetObject> unique = new LinkedHashMap<>();
      this.objects.values().forEach(o -> unique.putIfAbsent(o.hash(), o));
      return unique;
   }

   public long totalSize() {
      return this.uniqueObjects().values().stream().mapToLong(AssetIndex.AssetObject::size).sum();
   }

   public boolean isLegacyLayout() {
      return Boolean.TRUE.equals(this.virtual) || Boolean.TRUE.equals(this.mapToResources);
   }

   public record AssetObject(String hash, long size) {
      public String url() {
         return "https://resources.download.minecraft.net/" + this.hash.substring(0, 2) + "/" + this.hash;
      }
   }
}

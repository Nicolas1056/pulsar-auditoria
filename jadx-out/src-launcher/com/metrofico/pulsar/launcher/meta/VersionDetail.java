package com.metrofico.pulsar.launcher.meta;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record VersionDetail(
   String id,
   String type,
   String mainClass,
   VersionDetail.JavaVersion javaVersion,
   String assets,
   VersionDetail.AssetIndex assetIndex,
   VersionDetail.Downloads downloads,
   List<VersionDetail.Library> libraries,
   VersionDetail.Arguments arguments,
   String minecraftArguments
) {
   public static VersionDetail fromJson(String json) {
      VersionDetail detail = MojangJson.parse(json, VersionDetail.class, "Version detail");
      if (detail == null
         || detail.id == null
         || detail.mainClass == null
         || detail.downloads == null
         || detail.downloads.client == null
         || detail.libraries == null
         || detail.assetIndex == null) {
         throw new IllegalArgumentException("Version detail is missing required fields (id/mainClass/downloads.client/libraries/assetIndex)");
      } else if (detail.arguments == null && detail.minecraftArguments == null) {
         throw new IllegalArgumentException("Version detail " + detail.id + " has neither arguments nor minecraftArguments");
      } else {
         return new VersionDetail(
            detail.id,
            detail.type,
            detail.mainClass,
            detail.javaVersion,
            detail.assets,
            detail.assetIndex,
            detail.downloads,
            List.copyOf(detail.libraries),
            detail.arguments,
            detail.minecraftArguments
         );
      }
   }

   public boolean usesLegacyArguments() {
      return this.arguments == null;
   }

   public record Argument(List<String> values, List<VersionDetail.Rule> rules) {
      public boolean isConditional() {
         return !this.rules.isEmpty();
      }
   }

   static final class ArgumentAdapter implements JsonDeserializer<VersionDetail.Argument> {
      public VersionDetail.Argument deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
         if (element.isJsonPrimitive()) {
            return new VersionDetail.Argument(List.of(element.getAsString()), List.of());
         } else if (!element.isJsonObject()) {
            throw new JsonParseException("Argument must be a string or an object, got: " + element);
         } else {
            JsonObject object = element.getAsJsonObject();
            List<VersionDetail.Rule> rules = new ArrayList<>();
            if (object.has("rules")) {
               for (JsonElement rule : object.getAsJsonArray("rules")) {
                  rules.add((VersionDetail.Rule)context.deserialize(rule, VersionDetail.Rule.class));
               }
            }

            JsonElement value = object.get("value");
            if (value == null) {
               throw new JsonParseException("Conditional argument without value: " + element);
            } else {
               List<String> values = new ArrayList<>();
               if (value.isJsonArray()) {
                  for (JsonElement v : (JsonArray)value) {
                     values.add(v.getAsString());
                  }
               } else {
                  values.add(value.getAsString());
               }

               return new VersionDetail.Argument(List.copyOf(values), List.copyOf(rules));
            }
         }
      }
   }

   public record Arguments(List<VersionDetail.Argument> game, List<VersionDetail.Argument> jvm) {
   }

   public record Artifact(String url, String sha1, long size, String path) {
   }

   public record AssetIndex(String id, String sha1, long size, long totalSize, String url) {
   }

   public record Downloads(VersionDetail.Artifact client, VersionDetail.Artifact server) {
   }

   public record Extract(List<String> exclude) {
      public List<String> exclude() {
         return this.exclude == null ? List.of() : this.exclude;
      }
   }

   public record JavaVersion(String component, int majorVersion) {
   }

   public record Library(
      String name, VersionDetail.LibraryDownloads downloads, List<VersionDetail.Rule> rules, Map<String, String> natives, VersionDetail.Extract extract
   ) {
      public List<VersionDetail.Rule> rules() {
         return this.rules == null ? List.of() : this.rules;
      }

      public boolean hasLegacyNatives() {
         return this.natives != null && !this.natives.isEmpty();
      }
   }

   public record LibraryDownloads(VersionDetail.Artifact artifact, Map<String, VersionDetail.Artifact> classifiers) {
   }

   public record Os(String name, String version, String arch) {
   }

   public record Rule(String action, VersionDetail.Os os, Map<String, Boolean> features) {
      public boolean allows() {
         return "allow".equals(this.action);
      }
   }
}

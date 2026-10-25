package com.metrofico.pulsar.launcher.meta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

final class MojangJson {
   static final Gson GSON = new GsonBuilder().registerTypeAdapter(VersionDetail.Argument.class, new VersionDetail.ArgumentAdapter()).create();

   static <T> T parse(String json, Class<T> type, String what) {
      try {
         return (T)GSON.fromJson(json, type);
      } catch (JsonParseException var4) {
         throw new IllegalArgumentException(what + " is not valid JSON", var4);
      }
   }

   private MojangJson() {
   }
}

package com.metrofico.pulsar.bootstrap.mixin;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public record MixinConfigFile(String name, String pkg, List<String> mixinClasses) {
   public static MixinConfigFile read(String name, InputStream in) throws IOException {
      String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      String patched = json.replaceFirst("\"package\"\\s*:", "\"pkg\":");

      MixinConfigFile.Raw raw;
      try {
         raw = (MixinConfigFile.Raw)new Gson().fromJson(patched, MixinConfigFile.Raw.class);
      } catch (JsonParseException var10) {
         throw new IOException("Mixin config " + name + " is not valid JSON", var10);
      }

      if (raw != null && raw.pkg != null && !raw.pkg.isBlank()) {
         List<String> classes = new ArrayList<>();

         for (List<String> group : new List[]{raw.mixins, raw.client, raw.server}) {
            if (group != null) {
               group.forEach(m -> classes.add(raw.pkg + "." + m));
            }
         }

         return new MixinConfigFile(name, raw.pkg, List.copyOf(classes));
      } else {
         throw new IOException("Mixin config " + name + " has no \"package\"");
      }
   }

   private record Raw(String pkg, List<String> mixins, List<String> client, List<String> server) {
   }
}

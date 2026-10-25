package com.metrofico.pulsar.api.gfx;

import com.metrofico.pulsar.api.pack.Json;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class IconAtlas {
   private final int cell;
   private final Map<String, Integer> index = new HashMap<>();

   public IconAtlas(byte[] json) {
      Map<String, Object> root = Json.object(Json.parse(new String(json, StandardCharsets.UTF_8)), "icons");
      this.cell = (int)Json.integer(root, "cell", "icons");
      Json.object(root.get("icons"), "icons").forEach((k, v) -> this.index.put(k, ((Number)v).intValue()));
   }

   public int cell() {
      return this.cell;
   }

   public int column(String name) {
      Integer c = this.index.get(name);
      return c == null ? -1 : c;
   }
}

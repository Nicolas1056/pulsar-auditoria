package com.metrofico.pulsar.api.pack;

import java.util.LinkedHashMap;
import java.util.Map;

public record Envelope(String type, Map<String, Object> fields) {
   public static final int MAX_MESSAGE_BYTES = 1048576;

   public static Envelope parse(String json) {
      Map<String, Object> o = Json.object(Json.parse(json), "envelope");
      String t = Json.string(o, "t", "envelope");
      Map<String, Object> fields = new LinkedHashMap<>(o);
      fields.remove("t");
      return new Envelope(t, fields);
   }

   public String toJson() {
      Map<String, Object> o = new LinkedHashMap<>();
      o.put("t", this.type);
      o.putAll(this.fields);
      return Json.write(o);
   }
}

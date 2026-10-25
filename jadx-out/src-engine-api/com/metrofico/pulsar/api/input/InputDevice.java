package com.metrofico.pulsar.api.input;

import java.util.LinkedHashMap;
import java.util.Map;

public record InputDevice(String id, String kind, String vid, String pid, String serialSha256, String manufacturer, String model) {
   static final String NO_SERIAL = "-";

   public String physicalKey() {
      return this.vid + ":" + this.pid + ":" + ("-".equals(this.serialSha256) ? "name:" + this.model : this.serialSha256);
   }

   public String displayName() {
      String name = (!this.manufacturer.isBlank() && !this.model.startsWith(this.manufacturer) ? this.manufacturer + " " + this.model : this.model).trim();
      return name.isEmpty() ? this.vid + ":" + this.pid : name;
   }

   Map<String, Object> toJson() {
      Map<String, Object> o = new LinkedHashMap<>();
      o.put("kind", this.kind);
      o.put("vid", this.vid);
      o.put("pid", this.pid);
      o.put("serial", this.serialSha256);
      o.put("manufacturer", this.manufacturer);
      o.put("model", this.model);
      return o;
   }
}

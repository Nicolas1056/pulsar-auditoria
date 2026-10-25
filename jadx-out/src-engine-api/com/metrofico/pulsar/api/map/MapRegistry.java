package com.metrofico.pulsar.api.map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MapRegistry {
   public static final int MAX_AREAS = 128;
   private MapDefinition definition = MapDefinition.empty();
   private final Map<String, MapArea> areas = new LinkedHashMap<>();

   public boolean apply(Map<String, Object> fields) {
      boolean ok = true;
      if (fields.containsKey("area")) {
         ok &= this.applyArea(fields);
      }

      Object var4 = fields.get("areas");
      if (var4 instanceof List) {
         for (Object o : (List)var4) {
            ok &= o instanceof Map<?, ?> m && this.applyArea((Map<String, Object>)m);
         }
      }

      this.definition = MapDefinition.merge(this.definition, fields);
      return ok;
   }

   private boolean applyArea(Map<String, Object> f) {
      if (!(f.get("area") instanceof String id && !id.isEmpty())) {
         return false;
      } else if (Boolean.TRUE.equals(f.get("remove"))) {
         return this.areas.remove(id) != null;
      } else {
         MapArea previous = this.areas.get(id);
         if (previous == null && this.areas.size() >= 128) {
            return false;
         } else {
            MapArea a = MapArea.from(f, previous);
            if (!a.valid()) {
               return false;
            } else {
               this.areas.put(id, a);
               return true;
            }
         }
      }
   }

   public MapDefinition definition() {
      return this.definition;
   }

   public boolean enabled() {
      return this.definition.defined();
   }

   public List<MapArea> areas() {
      return new ArrayList<>(this.areas.values());
   }

   public void clear() {
      this.definition = MapDefinition.empty();
      this.areas.clear();
   }
}

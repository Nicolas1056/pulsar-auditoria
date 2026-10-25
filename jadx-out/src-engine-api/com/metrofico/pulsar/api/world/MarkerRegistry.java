package com.metrofico.pulsar.api.world;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MarkerRegistry {
   public static final int MAX_MARKERS = 64;
   private final Map<String, Marker> markers = new LinkedHashMap<>();

   public boolean apply(Map<String, Object> fields) {
      if (!(fields.get("markers") instanceof List<?> list)) {
         return this.applyOne(fields);
      } else {
         boolean ok = !list.isEmpty();

         for (Object o : list) {
            ok &= o instanceof Map<?, ?> m && this.applyOne((Map<String, Object>)m);
         }

         return ok;
      }
   }

   private boolean applyOne(Map<String, Object> fields) {
      if (!(fields.get("marker") instanceof String id && !id.isEmpty())) {
         return false;
      } else if (Boolean.TRUE.equals(fields.get("remove"))) {
         return this.markers.remove(id) != null;
      } else {
         Marker previous = this.markers.get(id);
         if (previous == null && this.markers.size() >= 64) {
            return false;
         } else {
            this.markers.put(id, Marker.from(fields, previous));
            return true;
         }
      }
   }

   public List<Marker> all() {
      return new ArrayList<>(this.markers.values());
   }

   public int size() {
      return this.markers.size();
   }

   public void clear() {
      this.markers.clear();
   }
}

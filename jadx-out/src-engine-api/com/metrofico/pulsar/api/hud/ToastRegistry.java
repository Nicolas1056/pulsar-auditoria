package com.metrofico.pulsar.api.hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongSupplier;

public final class ToastRegistry {
   public static final int MAX_VISIBLE_PER_ZONE = 5;
   public static final int MAX_QUEUED = 64;
   private final LongSupplier clock;
   private final Map<String, Toast> visible = new LinkedHashMap<>();
   private final List<Map<String, Object>> queued = new ArrayList<>();
   private int auto;

   public ToastRegistry(LongSupplier clock) {
      this.clock = clock;
   }

   public boolean apply(Map<String, Object> fields) {
      String id = fields.get("toast") instanceof String s && !s.isEmpty() ? s : null;
      if (!Boolean.TRUE.equals(fields.get("remove"))) {
         if (!(fields.get("title") instanceof String) && !(fields.get("text") instanceof String)) {
            return false;
         } else {
            if (id == null) {
               id = "toast-" + ++this.auto;
            }

            if (this.visible.containsKey(id)) {
               this.visible.put(id, Toast.from(fields, id, this.visible.get(id).createdAt()));
               return true;
            } else {
               Zone zone = fields.get("zone") instanceof String sx ? Zone.byId(sx).orElse(Zone.TOP_RIGHT) : Zone.TOP_RIGHT;
               if (this.countIn(zone) >= 5) {
                  if (this.queued.size() >= 64) {
                     return false;
                  } else {
                     Map<String, Object> copy = new LinkedHashMap<>(fields);
                     copy.put("toast", id);
                     this.queued.add(copy);
                     return true;
                  }
               } else {
                  this.visible.put(id, Toast.from(fields, id, this.clock.getAsLong()));
                  return true;
               }
            }
         }
      } else {
         return id != null && this.visible.remove(id) != null;
      }
   }

   private int countIn(Zone zone) {
      int n = 0;

      for (Toast t : this.visible.values()) {
         if (t.zone() == zone) {
            n++;
         }
      }

      return n;
   }

   public List<Toast> tick() {
      long now = this.clock.getAsLong();
      this.visible.values().removeIf(t -> now >= t.expiresAt());
      Iterator<Map<String, Object>> it = this.queued.iterator();

      while (it.hasNext()) {
         Map<String, Object> f = it.next();
         Zone zone = f.get("zone") instanceof String s ? Zone.byId(s).orElse(Zone.TOP_RIGHT) : Zone.TOP_RIGHT;
         if (this.countIn(zone) < 5) {
            it.remove();
            String id = (String)f.get("toast");
            this.visible.put(id, Toast.from(f, id, now));
         }
      }

      return new ArrayList<>(this.visible.values());
   }

   public int queued() {
      return this.queued.size();
   }

   public void clear() {
      this.visible.clear();
      this.queued.clear();
   }
}

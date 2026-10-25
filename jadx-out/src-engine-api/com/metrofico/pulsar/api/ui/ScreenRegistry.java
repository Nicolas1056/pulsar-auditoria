package com.metrofico.pulsar.api.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.LongSupplier;

public final class ScreenRegistry {
   public static final int MAX_SCREENS = 32;
   private final Map<String, ScreenDefinition> screens = new LinkedHashMap<>();
   private String openRequest;
   private boolean closeRequest;
   private final LongSupplier clock;
   private Map<String, String> chosenKeys = Map.of();

   public ScreenRegistry() {
      this(System::currentTimeMillis);
   }

   public ScreenRegistry(LongSupplier clock) {
      this.clock = clock;
   }

   public boolean apply(Map<String, Object> fields) {
      if (fields.get("screen") instanceof String id && !id.isEmpty()) {
         if (Boolean.TRUE.equals(fields.get("remove"))) {
            return this.screens.remove(id) != null;
         } else {
            ScreenDefinition previous = this.screens.get(id);
            Node root = previous == null ? null : previous.root();
            if (fields.get("root") instanceof Map<?, ?> m) {
               try {
                  root = Node.parse((Map<String, Object>)m);
               } catch (IllegalArgumentException var13) {
                  return false;
               }
            }

            if (root == null) {
               return false;
            } else if (previous == null && this.screens.size() >= 32) {
               return false;
            } else {
               boolean ok = true;
               Object var7 = fields.get("patch");
               if (var7 instanceof List) {
                  for (Object p : (List)var7) {
                     if (p instanceof Map<?, ?> pm && pm.get("node") instanceof String nodeId && pm.get("props") instanceof Map<?, ?> props) {
                        Node target = root.find(nodeId);
                        if (target != null) {
                           target.patch((Map<String, Object>)props);
                        } else {
                           ok = false;
                        }
                     } else {
                        ok = false;
                     }
                  }
               }

               this.screens.put(id, ScreenDefinition.from(id, fields, previous, root, this.clock.getAsLong()));
               if (Boolean.TRUE.equals(fields.get("open"))) {
                  this.openRequest = id;
               }

               if (Boolean.TRUE.equals(fields.get("close"))) {
                  this.closeRequest = true;
               }

               return ok;
            }
         }
      } else if (Boolean.TRUE.equals(fields.get("close"))) {
         this.closeRequest = true;
         return true;
      } else {
         return false;
      }
   }

   public Optional<ScreenDefinition> get(String id) {
      return Optional.ofNullable(this.screens.get(id));
   }

   public void chosenKeys(Map<String, String> keys) {
      this.chosenKeys = Map.copyOf(keys);
   }

   public Map<String, String> chosenKeys() {
      return this.chosenKeys;
   }

   public String keyOf(ScreenDefinition s) {
      return KeyBindings.effective(s, this.chosenKeys);
   }

   public Optional<ScreenDefinition> byKey(String key) {
      return this.screens.values().stream().filter(s -> !s.overlay() && key.equals(this.keyOf(s))).findFirst();
   }

   public List<ScreenDefinition> all() {
      return new ArrayList<>(this.screens.values());
   }

   public List<ScreenDefinition> overlays() {
      long now = this.clock.getAsLong();
      this.screens.values().removeIf(sx -> sx.expired(now));
      List<ScreenDefinition> out = new ArrayList<>();

      for (ScreenDefinition s : this.screens.values()) {
         if (s.overlay()) {
            out.add(s);
         }
      }

      return out;
   }

   public Optional<String> takeOpenRequest() {
      String r = this.openRequest;
      this.openRequest = null;
      return Optional.ofNullable(r);
   }

   public boolean takeCloseRequest() {
      boolean r = this.closeRequest;
      this.closeRequest = false;
      return r;
   }

   public void clear() {
      this.screens.clear();
      this.openRequest = null;
      this.closeRequest = false;
   }

   public static Map<String, Object> event(String screen, String node, String action, Map<String, Object> data) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("t", "event");
      m.put("cap", "ui.menu");
      m.put("screen", screen);
      m.put("node", node);
      m.put("action", action);
      if (data != null && !data.isEmpty()) {
         m.put("data", data);
      }

      return m;
   }
}

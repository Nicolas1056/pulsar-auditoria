package com.metrofico.pulsar.api.ui;

import java.util.List;
import java.util.Map;

public final class TabListState {
   private boolean nativeVisible = true;
   private Node root;
   private int version;

   public boolean apply(Map<String, Object> fields) {
      if (fields.get("native") instanceof Boolean b) {
         this.nativeVisible = b;
      }

      boolean ok = true;
      if (fields.get("root") instanceof Map<?, ?> m) {
         try {
            this.root = Node.parse((Map<String, Object>)m);
         } catch (IllegalArgumentException var10) {
            return false;
         }
      }

      if (Boolean.TRUE.equals(fields.get("remove"))) {
         this.root = null;
      }

      this.version++;
      if (fields.get("patch") instanceof List<?> patches && this.root != null) {
         for (Object p : patches) {
            if (p instanceof Map<?, ?> pm && pm.get("node") instanceof String nodeId && pm.get("props") instanceof Map<?, ?> props) {
               Node target = this.root.find(nodeId);
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

      return ok;
   }

   public boolean nativeVisible() {
      return this.nativeVisible;
   }

   public Node root() {
      return this.root;
   }

   public int version() {
      return this.version;
   }

   public void clear() {
      this.nativeVisible = true;
      this.root = null;
      this.version++;
   }
}

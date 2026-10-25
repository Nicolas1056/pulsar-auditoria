package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.hud.PanelDefinition;
import com.metrofico.pulsar.api.map.FogOfWar;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class Node {
   public static final int MAX_NODES = 1024;
   public static final int MAX_DEPTH = 12;
   public static final int MAX_TEXT = 256;
   private final Node.Type type;
   private final String id;
   private final Map<String, Object> props;
   private final List<Node> children;

   public Node(Node.Type type, String id, Map<String, Object> props, List<Node> children) {
      this.type = type;
      this.id = id;
      this.props = new LinkedHashMap<>(props);
      this.children = new ArrayList<>(children);
   }

   public static Node parse(Map<String, Object> o) {
      int[] counter = new int[]{0};
      return parse(o, "n", 0, counter);
   }

   private static Node parse(Map<String, Object> o, String path, int depth, int[] counter) {
      if (depth > 12) {
         throw new IllegalArgumentException("ui: árbol demasiado profundo");
      } else if (++counter[0] > 1024) {
         throw new IllegalArgumentException("ui: demasiados componentes (máx. 1024)");
      } else {
         Node.Type type;
         try {
            type = Node.Type.valueOf(String.valueOf(o.getOrDefault("type", "box")).toUpperCase(Locale.ROOT));
         } catch (IllegalArgumentException var13) {
            throw new IllegalArgumentException("ui: tipo desconocido " + o.get("type"));
         }

         String id = o.get("id") instanceof String s && !s.isEmpty() ? s : path;
         Map<String, Object> props = new LinkedHashMap<>();

         for (Entry<String, Object> e : o.entrySet()) {
            if (!e.getKey().equals("type") && !e.getKey().equals("id") && !e.getKey().equals("children")) {
               props.put(e.getKey(), e.getValue());
            }
         }

         if (props.get("text") instanceof String t && t.length() > 256) {
            props.put("text", t.substring(0, 256));
         }

         List<Node> children = new ArrayList<>();
         if (o.get("children") instanceof List<?> list) {
            int i = 0;

            for (Object c : list) {
               if (c instanceof Map<?, ?> m) {
                  children.add(parse((Map<String, Object>)m, path + "." + i, depth + 1, counter));
               }

               i++;
            }
         }

         return new Node(type, id, props, children);
      }
   }

   public Node.Type type() {
      return this.type;
   }

   public String id() {
      return this.id;
   }

   public Map<String, Object> props() {
      return this.props;
   }

   public List<Node> children() {
      return this.children;
   }

   public void patch(Map<String, Object> changes) {
      for (Entry<String, Object> e : changes.entrySet()) {
         if (e.getValue() == null) {
            this.props.remove(e.getKey());
         } else if (e.getKey().equals("tiles") && this.props.get("tiles") instanceof List<?> prev && e.getValue() instanceof List<?> add) {
            Map<String, Object> byImage = new LinkedHashMap<>();

            for (Object o : prev) {
               if (o instanceof Map<?, ?> t && t.get("image") instanceof String img) {
                  byImage.put(img, o);
               }
            }

            for (Object ox : add) {
               if (ox instanceof Map<?, ?> t && t.get("image") instanceof String img) {
                  byImage.put(img, ox);
               }
            }

            this.props.put("tiles", new ArrayList<>(byImage.values()));
         } else if (e.getKey().equals("revealed")) {
            this.props.put("revealed", FogOfWar.union(this.props.get("revealed"), e.getValue()));
         } else {
            this.props.put(e.getKey(), e.getValue());
         }
      }
   }

   public Node find(String nodeId) {
      if (this.id.equals(nodeId)) {
         return this;
      } else {
         for (Node c : this.children) {
            Node f = c.find(nodeId);
            if (f != null) {
               return f;
            }
         }

         return null;
      }
   }

   public int count() {
      int n = 1;

      for (Node c : this.children) {
         n += c.count();
      }

      return n;
   }

   public String text() {
      return this.props.get("text") instanceof String s ? s : "";
   }

   public int color(String key, int fallback) {
      return PanelDefinition.color(this.props.get(key), fallback);
   }

   public int integer(String key, int fallback) {
      return this.props.get(key) instanceof Number n ? n.intValue() : fallback;
   }

   public double number(String key, double fallback) {
      return this.props.get(key) instanceof Number n ? n.doubleValue() : fallback;
   }

   public boolean bool(String key, boolean fallback) {
      return this.props.get(key) instanceof Boolean b ? b : fallback;
   }

   public String string(String key, String fallback) {
      return this.props.get(key) instanceof String s ? s : fallback;
   }

   public boolean visible() {
      return this.bool("visible", true);
   }

   public boolean enabled() {
      return this.bool("enabled", true);
   }

   public Node.Size size(String key) {
      Object v = this.props.get(key);
      if (v instanceof Number n) {
         return new Node.Size(Node.Size.Kind.PX, Math.max(0.0, n.doubleValue()));
      } else {
         if (v instanceof String s) {
            if (s.equals("fill")) {
               return new Node.Size(Node.Size.Kind.FILL, 1.0);
            }

            if (s.endsWith("%")) {
               try {
                  return new Node.Size(Node.Size.Kind.PERCENT, Math.max(0.0, Math.min(100.0, Double.parseDouble(s.substring(0, s.length() - 1)))));
               } catch (NumberFormatException var5) {
                  return null;
               }
            }
         }

         return null;
      }
   }

   public record Size(Node.Size.Kind kind, double value) {
      public static enum Kind {
         PX,
         PERCENT,
         FILL;
      }
   }

   public static enum Type {
      BOX,
      ROW,
      COLUMN,
      GRID,
      TEXT,
      IMAGE,
      BUTTON,
      SPACER,
      PROGRESS,
      MAP;
   }
}

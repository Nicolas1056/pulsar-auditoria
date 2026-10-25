package com.metrofico.pulsar.api.hud;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Panel {
   private PanelDefinition definition;
   private List<String> lines;
   private final Map<String, String> values = new LinkedHashMap<>();
   private boolean visible = true;
   private Zone placement;

   Panel(PanelDefinition definition) {
      this.definition = definition;
      this.lines = definition.lines();
   }

   public String id() {
      return this.definition.id();
   }

   public PanelDefinition definition() {
      return this.definition;
   }

   public boolean visible() {
      return this.visible;
   }

   void definition(PanelDefinition d) {
      this.definition = d;
   }

   void lines(List<String> lines) {
      this.lines = List.copyOf(lines);
   }

   void values(Map<String, String> v) {
      this.values.putAll(v);
   }

   void visible(boolean v) {
      this.visible = v;
   }

   public Zone placement() {
      return this.placement;
   }

   void placement(Zone z) {
      this.placement = z;
   }

   public Zone zone() {
      return this.placement != null ? this.placement : this.definition.zone();
   }

   public List<String> resolvedLines() {
      List<String> out = new ArrayList<>(this.lines.size());

      for (String line : this.lines) {
         out.add(this.resolve(line));
      }

      return out;
   }

   public String resolvedTitle() {
      return this.resolve(this.definition.title());
   }

   private String resolve(String text) {
      if (!this.values.isEmpty() && text.indexOf(123) >= 0) {
         StringBuilder sb = new StringBuilder(text.length());
         int i = 0;

         while (i < text.length()) {
            int open = text.indexOf(123, i);
            int close = open < 0 ? -1 : text.indexOf(125, open);
            if (open < 0 || close < 0) {
               sb.append(text, i, text.length());
               break;
            }

            sb.append(text, i, open);
            String key = text.substring(open + 1, close);
            sb.append(this.values.getOrDefault(key, "{" + key + "}"));
            i = close + 1;
         }

         return sb.toString();
      } else {
         return text;
      }
   }
}

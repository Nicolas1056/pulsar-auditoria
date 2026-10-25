package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.pack.Json;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

public final class PanelRegistry {
   public static final int MAX_PANELS = 16;
   private final Map<String, Panel> panels = new LinkedHashMap<>();
   private Map<String, Zone> placements = Map.of();

   public void define(PanelDefinition d) {
      Panel existing = this.panels.get(d.id());
      if (existing != null) {
         existing.definition(d);
      } else if (this.panels.size() < 16) {
         this.panels.put(d.id(), this.create(d));
      }
   }

   public void placements(Map<String, Zone> byPanel) {
      this.placements = Map.copyOf(byPanel);

      for (Panel p : this.panels.values()) {
         p.placement(this.placements.get(p.id()));
      }
   }

   public void place(String panelId, Zone zone) {
      Map<String, Zone> next = new LinkedHashMap<>(this.placements);
      if (zone == null) {
         next.remove(panelId);
      } else {
         next.put(panelId, zone);
      }

      this.placements(next);
   }

   private Panel create(PanelDefinition d) {
      Panel p = new Panel(d);
      p.placement(this.placements.get(d.id()));
      return p;
   }

   public boolean apply(Map<String, Object> fields) {
      if (!(fields.get("panels") instanceof List<?> list)) {
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
      if (!(fields.get("panel") instanceof String id && !id.isEmpty())) {
         return false;
      } else if (Boolean.TRUE.equals(fields.get("remove"))) {
         return this.panels.remove(id) != null;
      } else {
         Panel panel = this.panels.get(id);
         if (panel == null) {
            if (this.panels.size() >= 16) {
               return false;
            }

            Map<String, Object> seed = new LinkedHashMap<>(fields);
            seed.putIfAbsent("lines", List.of());
            panel = this.create(PanelDefinition.parse(seed));
            this.panels.put(id, panel);
         } else if (fields.containsKey("zone") || fields.containsKey("title") || fields.containsKey("style")) {
            PanelDefinition d = panel.definition();
            Map<String, Object> merged = new LinkedHashMap<>();
            merged.put("panel", id);
            merged.put("zone", fields.getOrDefault("zone", d.zone().id()));
            merged.put("title", fields.getOrDefault("title", d.title()));
            merged.put("lines", d.lines());
            if (fields.get("style") instanceof Map<?, ?> style) {
               merged.put("style", style);
               PanelDefinition nd = PanelDefinition.parse(merged);
               panel.definition(nd);
            } else {
               PanelDefinition nd = PanelDefinition.parse(merged);
               panel.definition(new PanelDefinition(nd.id(), nd.zone(), nd.title(), nd.lines(), d.align(), d.color(), d.background()));
            }
         }

         if (fields.containsKey("lines")) {
            panel.lines(PanelDefinition.lines(fields.get("lines")));
         }

         if (fields.get("values") instanceof Map<?, ?> values) {
            Map<String, String> v = new LinkedHashMap<>();

            for (Entry<String, Object> e : values.entrySet()) {
               v.put(e.getKey(), PanelDefinition.clip(Json.write(e.getValue()).replaceAll("^\"|\"$", "")));
            }

            panel.values(v);
         }

         if (fields.get("visible") instanceof Boolean visible) {
            panel.visible(visible);
         }

         return true;
      }
   }

   public Optional<Panel> get(String id) {
      return Optional.ofNullable(this.panels.get(id));
   }

   public List<Panel> visible() {
      List<Panel> out = new ArrayList<>();

      for (Panel p : this.panels.values()) {
         if (p.visible()) {
            out.add(p);
         }
      }

      return out;
   }

   public int size() {
      return this.panels.size();
   }

   public void clear() {
      this.panels.clear();
   }
}

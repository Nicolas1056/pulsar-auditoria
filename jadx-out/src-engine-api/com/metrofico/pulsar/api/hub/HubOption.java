package com.metrofico.pulsar.api.hub;

import java.util.LinkedHashSet;
import java.util.List;

public record HubOption(
   String id, String category, String title, String desc, String icon, HubOption.Kind kind, String[] segments, String[] segmentIds, List<Param> params
) {
   public static HubOption segmented(String id, String category, String title, String desc, String icon, String[] segments, String[] segmentIds) {
      return new HubOption(id, category, title, desc, icon, HubOption.Kind.SEGMENTED, segments, segmentIds, List.of());
   }

   public static HubOption module(String id, String category, String title, String desc, String icon, Param... params) {
      return new HubOption(id, category, title, desc, icon, HubOption.Kind.TOGGLE, null, null, List.of(params));
   }

   public HubOption withParams(List<Param> filtered) {
      return new HubOption(this.id, this.category, this.title, this.desc, this.icon, this.kind, this.segments, this.segmentIds, filtered);
   }

   public String enabledKey() {
      return this.id + ".enabled";
   }

   public List<String> resetIds() {
      LinkedHashSet<String> ids = new LinkedHashSet<>();
      ids.add(this.id);

      for (Param p : this.params) {
         int dot = p.id().indexOf(46);
         ids.add(dot < 0 ? p.id() : p.id().substring(0, dot));
      }

      return List.copyOf(ids);
   }

   public static enum Kind {
      TOGGLE,
      SEGMENTED;
   }
}

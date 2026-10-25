package com.metrofico.pulsar.api.guard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record GuardReport(boolean debugger, List<GuardReport.Module> modules, int foreignThreads, int remoteOpens) {
   public static final GuardReport EMPTY = new GuardReport(false, List.of(), 0, 0);

   public Map<String, Object> toJson() {
      List<Map<String, Object>> ms = new ArrayList<>(this.modules.size());

      for (GuardReport.Module m : this.modules) {
         Map<String, Object> o = new LinkedHashMap<>();
         o.put("name", m.name());
         o.put("sha256", m.sha256());
         o.put("signed", m.signed());
         ms.add(o);
      }

      Map<String, Object> out = new LinkedHashMap<>();
      out.put("debugger", this.debugger);
      out.put("modules", ms);
      out.put("foreignThreads", this.foreignThreads);
      out.put("remoteOpens", this.remoteOpens);
      return out;
   }

   public record Module(String name, String sha256, boolean signed) {
   }
}

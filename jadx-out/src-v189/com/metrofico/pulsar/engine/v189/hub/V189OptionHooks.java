package com.metrofico.pulsar.engine.v189.hub;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hub.ClientOptions.Hooks;
import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.PerfBooster189;

public final class V189OptionHooks implements Hooks {
   private static String lastKey;

   public void apply(String id) {
      if (id == null || id.startsWith("controls.")) {
         ToggleSneakSprint.refresh();
      }

      if (id == null || id.startsWith("perf.") && !id.equals("perf.backup")) {
         PerfBooster189.apply();
         ClientOptions o = ClientOptions.get();
         boolean regions = o.enabled("perf") && o.bool("perf.terrainRegions", true);
         boolean smartLeaves = o.enabled("perf") && o.bool("perf.smartLeaves", true);
         String key = regions
            + "/"
            + (regions && o.bool("perf.compactVertices", true))
            + "/"
            + (regions && o.bool("perf.terrainMultidraw", true))
            + "/"
            + smartLeaves;
         if (lastKey != null && !lastKey.equals(key)) {
            ave mc = ave.A();
            if (mc.g != null && mc.f != null) {
               FrameFlags.refresh();
               mc.g.a();
            }
         }

         lastKey = key;
      }
   }
}

package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.Zone;
import java.util.List;

public final class DiagModule implements HudModule {
   @Override
   public String id() {
      return "diag";
   }

   @Override
   public boolean enabled() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.diagnostics", false);
   }

   @Override
   public Zone zone() {
      return ClientOptions.get().zone("diag.zone", Zone.BOTTOM_RIGHT);
   }

   @Override
   public boolean present() {
      return !HudModules.data().diagLines().isEmpty();
   }

   @Override
   public int width(Canvas g) {
      int w = 0;

      for (String l : HudModules.data().diagLines()) {
         w = Math.max(w, g.textWidth(Font.REGULAR, l, 7.0F));
      }

      return w + 2;
   }

   @Override
   public int height(Canvas g) {
      return g.lineHeight(Font.REGULAR, 7.0F) * Math.max(1, HudModules.data().diagLines().size());
   }

   @Override
   public void draw(Canvas g, int x, int y) {
      List<String> lines = HudModules.data().diagLines();
      int lh = g.lineHeight(Font.REGULAR, 7.0F);

      for (int i = 0; i < lines.size(); i++) {
         g.text(Font.REGULAR, lines.get(i), x + 1, y + i * lh, 7.0F, -862280785);
      }
   }
}

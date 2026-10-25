package com.metrofico.pulsar.engine.v189.hud.scoreboard;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarText;

record LegacySidebarText(String text, int width) implements SidebarText {
   public void draw(Canvas canvas, int x, int y, int argb, boolean shadow) {
      canvas.vtext(this.text, x, y, argb, shadow);
   }

   public boolean animated() {
      return this.text.indexOf("§k") >= 0 || this.text.indexOf("§K") >= 0;
   }
}

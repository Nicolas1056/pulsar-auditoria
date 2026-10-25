package com.metrofico.pulsar.api.hud.scoreboard;

import com.metrofico.pulsar.api.gfx.Canvas;

public final class PlainSidebarText implements SidebarText {
   private final String text;
   private final int color;
   private int width = -1;

   public PlainSidebarText(String text, int color) {
      this.text = text;
      this.color = color;
   }

   public PlainSidebarText measure(Canvas canvas) {
      if (this.width < 0) {
         this.width = canvas.vwidth(this.text);
      }

      return this;
   }

   @Override
   public int width() {
      return Math.max(this.width, 0);
   }

   @Override
   public void draw(Canvas canvas, int x, int y, int argb, boolean shadow) {
      canvas.vtext(this.text, x, y, this.color != 0 ? this.color : argb, shadow);
   }
}

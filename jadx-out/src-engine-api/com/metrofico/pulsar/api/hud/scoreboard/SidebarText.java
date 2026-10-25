package com.metrofico.pulsar.api.hud.scoreboard;

import com.metrofico.pulsar.api.gfx.Canvas;

public interface SidebarText {
   int width();

   void draw(Canvas var1, int var2, int var3, int var4, boolean var5);

   default boolean animated() {
      return false;
   }
}

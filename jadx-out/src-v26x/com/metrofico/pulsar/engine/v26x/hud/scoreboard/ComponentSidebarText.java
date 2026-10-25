package com.metrofico.pulsar.engine.v26x.hud.scoreboard;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.scoreboard.SidebarText;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

record ComponentSidebarText(Component text, int width) implements SidebarText {
   static ComponentSidebarText of(Component text) {
      return new ComponentSidebarText(text, Minecraft.getInstance().font.width(text));
   }

   public void draw(Canvas canvas, int x, int y, int argb, boolean shadow) {
      ((Canvas26)canvas).gfx().text(Minecraft.getInstance().font, this.text, x, y, argb, shadow);
   }
}

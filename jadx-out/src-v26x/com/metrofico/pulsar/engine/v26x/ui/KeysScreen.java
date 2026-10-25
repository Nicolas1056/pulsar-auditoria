package com.metrofico.pulsar.engine.v26x.ui;

import com.metrofico.pulsar.api.ui.KeysView;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class KeysScreen extends Screen {
   private final KeysView view = new KeysView(PackRuntime.get(), () -> Minecraft.getInstance().setScreenAndShow(null));

   public KeysScreen() {
      super(Component.literal("Teclas"));
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean keyPressed(KeyEvent e) {
      return this.view.keyPressed(e.key() == 256, MenuScreen.keyName(e.key())) || super.keyPressed(e);
   }

   public boolean isPauseScreen() {
      return false;
   }
}

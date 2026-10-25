package com.metrofico.pulsar.engine.v26x.ui;

import com.metrofico.pulsar.api.ui.MenuView;
import com.metrofico.pulsar.api.ui.MenuView.Key;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class MenuScreen extends Screen {
   private final MenuView view;

   public MenuScreen(String screenId) {
      super(Component.literal(screenId));
      this.view = new MenuView(PackRuntime.get(), screenId, () -> Minecraft.getInstance().setScreenAndShow(null));
   }

   public String screenId() {
      return this.view.screenId();
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return super.mouseClicked(e, doubleClick) || this.view.mouseClicked(e.x(), e.y(), e.button());
   }

   public boolean mouseDragged(MouseButtonEvent e, double dx, double dy) {
      return this.view.mouseDragged(e.x(), e.y()) || super.mouseDragged(e, dx, dy);
   }

   public boolean mouseReleased(MouseButtonEvent e) {
      this.view.mouseReleased();
      return super.mouseReleased(e);
   }

   public boolean mouseScrolled(double mx, double my, double dx, double dy) {
      return this.view.mouseScrolled(mx, my, dy) || super.mouseScrolled(mx, my, dx, dy);
   }

   public boolean keyPressed(KeyEvent e) {
      Key key = switch (e.key()) {
         case 32 -> Key.SPACE;
         case 256 -> Key.ESC;
         case 257 -> Key.ENTER;
         case 258 -> Key.TAB;
         default -> Key.OTHER;
      };
      return this.view.keyPressed(key, keyName(e.key())) || super.keyPressed(e);
   }

   public static String keyName(int key) {
      return key >= 65 && key <= 90 ? String.valueOf((char)key) : (key >= 290 && key <= 314 ? "F" + (key - 289) : null);
   }

   public boolean isPauseScreen() {
      return false;
   }
}

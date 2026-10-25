package com.metrofico.pulsar.engine.v26x.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudEditView;
import com.metrofico.pulsar.api.hud.HudEditView.Host;
import com.metrofico.pulsar.api.hud.HudEditView.Key;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.metrofico.pulsar.engine.v26x.ui.KeysScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class HudEditScreen extends Screen implements Host {
   private final Screen parent;
   private final HudEditView view;

   public HudEditScreen() {
      this(null);
   }

   public HudEditScreen(Screen parent) {
      super(Component.literal("Editar HUD"));
      this.parent = parent;
      this.view = new HudEditView(this, Hud26.RENDERER, PackRuntime.get());
   }

   public Canvas measure() {
      return Canvas26.measure();
   }

   public boolean shiftDown() {
      long w = Minecraft.getInstance().getWindow().handle();
      return GLFW.glfwGetKey(w, 340) == 1 || GLFW.glfwGetKey(w, 344) == 1;
   }

   public Runnable keysScreen() {
      return () -> Minecraft.getInstance().setScreenAndShow(new KeysScreen());
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseScrolled(double x, double y, double dx, double dy) {
      return this.view.mouseScrolled(x, y, dy) || super.mouseScrolled(x, y, dx, dy);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean mouseDragged(MouseButtonEvent e, double dx, double dy) {
      return this.view.mouseDragged(e.x(), e.y()) || super.mouseDragged(e, dx, dy);
   }

   public boolean mouseReleased(MouseButtonEvent e) {
      return this.view.mouseReleased() || super.mouseReleased(e);
   }

   public boolean keyPressed(KeyEvent e) {
      Key key = switch (e.key()) {
         case 45, 333 -> Key.MINUS;
         case 61, 334 -> Key.PLUS;
         case 256 -> Key.ESC;
         case 258 -> Key.TAB;
         case 262 -> Key.RIGHT;
         case 263 -> Key.LEFT;
         case 264 -> Key.DOWN;
         case 265 -> Key.UP;
         default -> Key.OTHER;
      };
      return this.view.keyPressed(key) || super.keyPressed(e);
   }

   public void onClose() {
      this.view.close();
   }

   public void close() {
      if (this.parent != null) {
         Minecraft.getInstance().setScreenAndShow(this.parent);
      } else {
         Minecraft.getInstance().setScreenAndShow(null);
      }
   }

   public boolean isPauseScreen() {
      return false;
   }
}

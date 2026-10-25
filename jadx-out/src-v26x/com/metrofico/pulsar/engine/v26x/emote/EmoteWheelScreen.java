package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.EmoteWheel;
import com.metrofico.pulsar.api.emote.EmoteWheelView;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.hub.PulsarCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class EmoteWheelScreen extends Screen implements EmoteScreen {
   private final EmoteWheelView view;

   public EmoteWheelScreen(boolean hold) {
      super(Component.literal("Emotes"));
      this.view = new EmoteWheelView(hold, () -> Minecraft.getInstance().setScreenAndShow(null));
   }

   static char keyChar(int glfwKey) {
      if (EmoteWheel.isKey(KeyCode.fromGlfw(glfwKey))) {
         return '\u0001';
      } else if (glfwKey == 256) {
         return '\u001b';
      } else if (glfwKey == 259) {
         return '\b';
      } else {
         return glfwKey >= 48 && glfwKey <= 90 ? (char)glfwKey : '\u0000';
      }
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return EmoteWheel.isKey(KeyCode.fromMouse(e.button()))
         ? this.view.keyPressed('\u0001')
         : this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean mouseReleased(MouseButtonEvent e) {
      return EmoteWheel.isKey(KeyCode.fromMouse(e.button())) ? this.view.keyReleased('\u0001') : super.mouseReleased(e);
   }

   public boolean keyPressed(KeyEvent e) {
      return this.view.keyPressed(keyChar(e.key())) || super.keyPressed(e);
   }

   public boolean keyReleased(KeyEvent e) {
      return this.view.keyReleased(keyChar(e.key())) || super.keyReleased(e);
   }

   public void onClose() {
      PulsarCursor.reset();
      super.onClose();
   }

   public boolean isPauseScreen() {
      return false;
   }
}

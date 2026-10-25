package com.metrofico.pulsar.engine.v26x.emote;

import com.metrofico.pulsar.api.emote.EmoteLibraryView;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.hub.PulsarCursor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class EmoteLibraryScreen extends Screen implements EmoteScreen {
   private final EmoteLibraryView view;

   public EmoteLibraryScreen(int slot) {
      super(Component.literal("Biblioteca de emotes"));
      this.view = new EmoteLibraryView(slot, () -> Minecraft.getInstance().setScreenAndShow(null));
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean mouseScrolled(double x, double y, double dx, double dy) {
      return this.view.mouseScrolled(dy);
   }

   public boolean keyPressed(KeyEvent e) {
      return this.view.keyPressed(EmoteWheelScreen.keyChar(e.key())) || super.keyPressed(e);
   }

   public boolean keyReleased(KeyEvent e) {
      return this.view.keyReleased(EmoteWheelScreen.keyChar(e.key())) || super.keyReleased(e);
   }

   public boolean charTyped(CharacterEvent e) {
      return this.view.charTyped(e.codepoint()) || super.charTyped(e);
   }

   public void onClose() {
      PulsarCursor.reset();
      this.view.close();
   }

   public boolean isPauseScreen() {
      return false;
   }
}

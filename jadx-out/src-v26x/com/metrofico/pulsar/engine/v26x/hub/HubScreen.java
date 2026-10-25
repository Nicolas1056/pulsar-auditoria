package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.hub.HubHost;
import com.metrofico.pulsar.api.hub.HubView;
import com.metrofico.pulsar.api.hub.HubView.Key;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.engine.v26x.hud.HudEditScreen;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class HubScreen extends Screen implements HubHost {
   static final int BG = -233959146;
   static final int ROW_HOVER = -15328478;
   static final int HOVER_STRONG = -14472394;
   static final int LINE = -14341066;
   static final int INK = -1512206;
   static final int SECONDARY = -6642769;
   static final int MUTED = -10853262;
   static final int SCRIM = -1509949440;
   static final int INK_ON_INK = -15855338;
   private final Screen parent;
   private final HubView view;

   public HubScreen(Screen parent) {
      super(Component.literal("Pulsar"));
      this.parent = parent;
      this.view = new HubView(this);
   }

   public HubScreen(Screen parent, String optionId) {
      super(Component.literal("Pulsar"));
      this.parent = parent;
      this.view = new HubView(this, optionId);
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean mouseDragged(MouseButtonEvent e, double dx, double dy) {
      return this.view.mouseDragged(e.x()) || super.mouseDragged(e, dx, dy);
   }

   public boolean mouseReleased(MouseButtonEvent e) {
      return this.view.mouseReleased(e.x()) || super.mouseReleased(e);
   }

   public boolean mouseScrolled(double x, double y, double dx, double dy) {
      return this.view.mouseScrolled(dy);
   }

   public boolean keyPressed(KeyEvent e) {
      Key key = switch (e.key()) {
         case 256 -> Key.ESC;
         case 257 -> Key.ENTER;
         case 258 -> Key.TAB;
         case 259 -> Key.BACKSPACE;
         default -> Key.OTHER;
      };
      return this.view.keyPressed(key, KeyCode.fromGlfw(e.key())) || super.keyPressed(e);
   }

   public boolean charTyped(CharacterEvent e) {
      return this.view.charTyped(e.codepoint()) || super.charTyped(e);
   }

   public void onClose() {
      this.close();
   }

   public void close() {
      this.view.closed();
      PulsarCursor.reset();
      Minecraft.getInstance().setScreenAndShow(this.parent);
   }

   public void openHudEditor() {
      Minecraft.getInstance().setScreenAndShow(new HudEditScreen(this));
   }

   public String perfLabel() {
      return PerfStats.backend();
   }

   public boolean isPauseScreen() {
      return true;
   }
}

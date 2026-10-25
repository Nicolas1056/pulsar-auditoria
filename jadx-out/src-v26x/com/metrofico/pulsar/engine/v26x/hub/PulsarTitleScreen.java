package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.ui.TitleHost;
import com.metrofico.pulsar.api.ui.TitleView;
import com.metrofico.pulsar.api.ui.TitleView.Key;
import com.metrofico.pulsar.engine.v26x.perf.BenchShot26;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public final class PulsarTitleScreen extends Screen implements TitleHost {
   private final TitleView view = new TitleView(this);

   public PulsarTitleScreen() {
      super(Component.literal("Pulsar Client"));
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.extractPanorama(g, partialTick);
      this.view.render(new Canvas26(g), mouseX, mouseY);
      BenchShot26.frameEnd();
   }

   public void extractBackground(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean keyPressed(KeyEvent e) {
      Key key = switch (e.key()) {
         case 257, 335 -> Key.ENTER;
         case 258 -> Key.TAB;
         case 264 -> Key.DOWN;
         case 265 -> Key.UP;
         default -> Key.OTHER;
      };
      return this.view.keyPressed(key);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void removed() {
      this.view.closed();
      PulsarCursor.reset();
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void singleplayer() {
      Minecraft.getInstance().setScreenAndShow(new SelectWorldScreen(this));
   }

   public void multiplayer() {
      Minecraft.getInstance().setScreenAndShow(new JoinMultiplayerScreen(this));
   }

   public void options() {
      Minecraft mc = Minecraft.getInstance();
      mc.setScreenAndShow(new OptionsScreen(this, mc.options, false));
   }

   public void hub() {
      Minecraft.getInstance().setScreenAndShow(new HubScreen(this));
   }

   public void quit() {
      Minecraft.getInstance().stop();
   }

   public String translate(String key) {
      return I18n.get(key, new Object[0]);
   }

   public String gameVersion() {
      return SharedConstants.getCurrentVersion().name();
   }

   public String playerName() {
      Minecraft mc = Minecraft.getInstance();
      return mc.getUser() == null ? null : mc.getUser().getName();
   }
}

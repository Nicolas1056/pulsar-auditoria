package com.metrofico.pulsar.engine.v26x.ui;

import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.api.input.MouseLockView;
import com.metrofico.pulsar.api.input.MouseLock.Block;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class MouseLockScreen extends Screen {
   private final MouseLockView view;

   public MouseLockScreen(Block block) {
      super(Minecraft.getInstance(), Minecraft.getInstance().font, Component.literal("Pulsar"));
      this.view = new MouseLockView(block, () -> Minecraft.getInstance().stop());
   }

   public static void install() {
      MouseLock lock = MouseLock.current();
      if (lock != null) {
         lock.onBlock(b -> Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreenAndShow(new MouseLockScreen(b))));
      }
   }

   public static Screen enforce(Screen screen) {
      MouseLock lock = MouseLock.current();
      return (Screen)(lock != null && lock.blocked() && !(screen instanceof MouseLockScreen) ? new MouseLockScreen(lock.block()) : screen);
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button());
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public boolean isPauseScreen() {
      return false;
   }
}

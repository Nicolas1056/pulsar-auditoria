package com.metrofico.pulsar.engine.v26x.pack;

import com.metrofico.pulsar.api.pack.ConsentView;
import com.metrofico.pulsar.api.pack.PackManifest;
import com.metrofico.pulsar.api.pack.ServerRecord;
import com.metrofico.pulsar.api.pack.PackSession.Consent;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public final class ConsentScreen extends Screen {
   private final ConsentView view;

   public ConsentScreen(ServerRecord server, PackManifest manifest, Consumer<Consent> onDecision) {
      super(Minecraft.getInstance(), Minecraft.getInstance().font, Component.literal("Este servidor usa Pulsar"));
      this.view = new ConsentView(server, manifest, onDecision, () -> Minecraft.getInstance().setScreenAndShow(null));
   }

   public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float partialTick) {
      this.view.render(new Canvas26(g), mouseX, mouseY);
      super.extractRenderState(g, mouseX, mouseY, partialTick);
   }

   public boolean mouseClicked(MouseButtonEvent e, boolean doubleClick) {
      return this.view.mouseClicked(e.x(), e.y(), e.button()) || super.mouseClicked(e, doubleClick);
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void onClose() {
      this.view.choose(Consent.NONE);
   }
}

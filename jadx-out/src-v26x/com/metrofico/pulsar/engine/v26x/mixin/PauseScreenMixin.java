package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.hub.HubScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PauseScreen.class})
abstract class PauseScreenMixin extends Screen {
   private PauseScreenMixin() {
      super(null);
   }

   @Inject(
      method = {"init"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$hubButton(CallbackInfo ci) {
      this.addRenderableWidget(Button.builder(Component.literal("Pulsar"), b -> {
         BootstrapLog.info("pulsar hub: button pressed");
         Minecraft.getInstance().setScreenAndShow(new HubScreen((PauseScreen)this));
      }).bounds(this.width - 64, 8, 56, 20).build());
      BootstrapLog.info("pulsar hub: button added at " + (this.width - 64) + ",8 (screen " + this.width + "x" + this.height + ")");
   }
}

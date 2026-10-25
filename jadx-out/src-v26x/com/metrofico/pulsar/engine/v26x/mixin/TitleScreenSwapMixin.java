package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v26x.hub.PulsarTitleScreen;
import com.metrofico.pulsar.engine.v26x.ui.MouseLockScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({Gui.class})
abstract class TitleScreenSwapMixin {
   @ModifyVariable(
      method = {"setScreen"},
      at = @At("HEAD"),
      argsOnly = true,
      require = 1
   )
   private Screen pulsar$titleScreen(Screen screen) {
      screen = MouseLockScreen.enforce(screen);
      return (Screen)(screen instanceof TitleScreen && ClientOptions.get().pulsarTitleScreen() ? new PulsarTitleScreen() : screen);
   }
}

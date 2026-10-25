package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Hud.class})
abstract class HudEffectsMixin {
   @Inject(
      method = {"extractEffects"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$effects(GuiGraphicsExtractor g, DeltaTracker delta, CallbackInfo ci) {
      if (ClientOptions.get().enabled("effects")) {
         ci.cancel();
      }
   }
}

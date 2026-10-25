package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hud.modules.CrosshairRenderer;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Hud.class})
abstract class HudCrosshairMixin {
   @Inject(
      method = {"extractCrosshair"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$crosshair(GuiGraphicsExtractor g, DeltaTracker delta, CallbackInfo ci) {
      if (CrosshairRenderer.enabled()) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.gameMode == null || mc.gameMode.getPlayerMode() != GameType.SPECTATOR) {
            if (!mc.debugEntries.isCurrentlyEnabled(DebugScreenEntries.THREE_DIMENSIONAL_CROSSHAIR)) {
               ci.cancel();
               if (mc.options.getCameraType().isFirstPerson()) {
                  g.nextStratum();
                  CrosshairRenderer.draw(new Canvas26(g), g.guiWidth() / 2, g.guiHeight() / 2);
               }
            }
         }
      }
   }
}

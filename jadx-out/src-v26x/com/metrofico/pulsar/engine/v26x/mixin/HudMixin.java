package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import com.metrofico.pulsar.engine.v26x.hud.Hud26;
import com.metrofico.pulsar.engine.v26x.perf.BenchShot26;
import com.metrofico.pulsar.engine.v26x.perf.ParticleCount;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Hud.class})
abstract class HudMixin {
   @Inject(
      method = {"extractRenderState"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cinematicHud(GuiGraphicsExtractor g, DeltaTracker delta, CallbackInfo ci) {
      if (CameraDirector.get().hideHud()) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"extractRenderState"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$panels(GuiGraphicsExtractor g, DeltaTracker delta, CallbackInfo ci) {
      Hud hud = (Hud)this;
      if (!hud.isHidden()) {
         Hud26.RENDERER.render(new Canvas26(g));
         if (Hud26.TABLIST != null) {
            Hud26.TABLIST.render(new Canvas26(g));
         }

         Minecraft mc = Minecraft.getInstance();
         BenchShot26.frameEnd();
         PerfStats.frame(mc.particleEngine != null ? ParticleCount.of(mc.particleEngine) : 0, mc.getFrameTimeNs() / 1000000.0);
      }
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hud.modules.CrosshairRenderer;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.hud.Hud189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({avo.class})
public abstract class InGameHudMixin {
   @Inject(
      method = {"a(F)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cinematicHud(float tickDelta, CallbackInfo ci) {
      Hud189.tickDelta = tickDelta;
      PerfStats189.begin(5);
      if (CameraDirector.get().hideHud()) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"a(F)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$hud(float tickDelta, CallbackInfo ci) {
      long t0 = System.nanoTime();
      Canvas189 g = new Canvas189();
      Hud189.RENDERER.render(g);
      Hud189.TABLIST.render(g);
      g.flush();
      PerfStats189.hudPulsar(System.nanoTime() - t0);
      PerfStats189.end(5);
      PerfStats189.frame(pulsar$particles(), Hud189.frameMs);
   }

   private static int pulsar$particles() {
      bec pm = ave.A().j;
      if (pm == null) {
         return 0;
      } else {
         int n = 0;

         for (List<beb>[] layer : ((ParticleManagerAccessor)pm).pulsar$particles()) {
            for (List<beb> list : layer) {
               n += list.size();
            }
         }

         return n;
      }
   }

   @Redirect(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lavo;b(IIIIII)V",
         ordinal = 0
      ),
      require = 1
   )
   private void pulsar$crosshair(avo hud, int x, int y, int u, int v, int w, int h) {
      if (!CrosshairRenderer.enabled()) {
         hud.b(x, y, u, v, w, h);
      } else {
         Canvas189 g = new Canvas189();
         CrosshairRenderer.draw(g, g.width() / 2, g.height() / 2);
         g.flush();
      }
   }
}

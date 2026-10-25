package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.gui.render.GuiRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiRenderer.class})
abstract class GuiRendererMixin {
   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$begin(CallbackInfo ci) {
      PerfStats.begin(2);
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$end(CallbackInfo ci) {
      PerfStats.end(2);
   }
}

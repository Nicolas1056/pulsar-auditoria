package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SectionRenderDispatcher.class})
public abstract class SectionRenderDispatcherMixin {
   @Redirect(
      method = {"lambda$new$0"},
      at = @At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/vertex/VertexFormat;getVertexSize()I"
      )
   )
   private static int pulsar$alignment(VertexFormat format) {
      int a = format.getVertexSize();
      int b = 16;
      int x = a;
      int y = b;

      while (y != 0) {
         int t = x % y;
         x = y;
         y = t;
      }

      return a / x * b;
   }

   @Inject(
      method = {"uploadTerrainBuffersToGpu"},
      at = {@At("HEAD")}
   )
   private void pulsar$uploadBegin(CallbackInfo ci) {
      PerfStats.begin(4);
   }

   @Inject(
      method = {"uploadTerrainBuffersToGpu"},
      at = {@At("TAIL")}
   )
   private void pulsar$uploadEnd(CallbackInfo ci) {
      PerfStats.end(4);
   }
}

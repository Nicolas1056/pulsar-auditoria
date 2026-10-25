package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.TerrainRegions189;
import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfy.class})
public abstract class VboChunkRenderManagerMixin {
   @Shadow
   protected abstract void a();

   @Inject(
      method = {"a(Ladf;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$regions(adf layer, CallbackInfo ci) {
      if (TerrainRegions189.enabled()) {
         AbstractChunkRenderManagerAccessor self = (AbstractChunkRenderManagerAccessor)this;
         if (!self.pulsar$ready()) {
            ci.cancel();
         } else {
            bfh mgr = (bfh)this;
            int ordinal = layer.ordinal();
            List<bht> fallback;
            if (layer == adf.d) {
               TerrainRegions189.Pass pass = new TerrainRegions189.Pass(self.pulsar$viewX(), self.pulsar$viewY(), self.pulsar$viewZ());
               fallback = new ArrayList<>();

               for (bht chunk : self.pulsar$chunks()) {
                  if (!pass.draw(chunk, ordinal)) {
                     fallback.add(chunk);
                  }
               }

               pass.end();
            } else {
               fallback = TerrainRegions189.drawGrouped(self.pulsar$chunks(), ordinal, self.pulsar$viewX(), self.pulsar$viewY(), self.pulsar$viewZ());
            }

            for (bht chunkx : fallback) {
               bmt vb = chunkx.b(ordinal);
               bfl.E();
               mgr.a(chunkx);
               chunkx.f();
               vb.a();
               this.a();
               vb.a(7);
               bfl.F();
            }

            bqs.g(bqs.R, 0);
            bfl.G();
            self.pulsar$chunks().clear();
            ci.cancel();
         }
      }
   }
}

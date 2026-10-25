package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SectionBuffer;
import com.metrofico.pulsar.engine.v189.perf.TerrainRegions189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bho.class})
public abstract class ChunkBuilderMixin {
   @Inject(
      method = {"a(Lbfd;Lbmt;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$regionUpload(bfd buffer, bmt target, CallbackInfo ci) {
      if (TerrainRegions189.enabled()) {
         SectionBuffer owner = (SectionBuffer)target;
         if (owner.pulsar$chunk() != null && owner.pulsar$layer() >= 0) {
            buffer.b();
            TerrainRegions189.upload(owner.pulsar$chunk(), owner.pulsar$layer(), buffer.f());
            ci.cancel();
         }
      }
   }
}

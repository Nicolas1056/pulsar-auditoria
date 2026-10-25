package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.TerrainMultidraw;
import com.mojang.blaze3d.textures.GpuSampler;
import net.minecraft.client.renderer.chunk.ChunkSectionLayerGroup;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChunkSectionsToRender.class})
abstract class ChunkSectionsToRenderMixin {
   @Inject(
      method = {"renderGroup"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$render(ChunkSectionLayerGroup group, GpuSampler sampler, CallbackInfo ci) {
      if (TerrainMultidraw.active() && TerrainMultidraw.renderGroup(group, sampler)) {
         ci.cancel();
      }
   }
}

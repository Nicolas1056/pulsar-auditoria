package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.CompactVertices;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import com.metrofico.pulsar.engine.v26x.perf.TerrainMultidraw;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LevelRenderer.class})
abstract class LevelRendererMixin {
   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$renderBegin(CallbackInfo ci) {
      PerfStats.begin(1);
      SpriteCulling.nextFrame();
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$renderEnd(CallbackInfo ci) {
      PerfStats.end(1);
   }

   @Inject(
      method = {"submitEntities"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$entitiesBegin(CallbackInfo ci) {
      PerfStats.begin(5);
   }

   @Inject(
      method = {"submitEntities"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$entitiesEnd(CallbackInfo ci) {
      PerfStats.end(5);
   }

   @Inject(
      method = {"prepareChunkRenders"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$prepare(Matrix4fc modelView, CallbackInfoReturnable<ChunkSectionsToRender> cir) {
      SpriteCulling.markVisible(((LevelRenderer)this).visibleSections());
      if (TerrainMultidraw.active()) {
         cir.setReturnValue(TerrainMultidraw.prepare((LevelRenderer)this, modelView));
      } else if (CompactVertices.abortIfNeeded("multidraw inactive")) {
         cir.setReturnValue(TerrainMultidraw.empty());
      }
   }
}

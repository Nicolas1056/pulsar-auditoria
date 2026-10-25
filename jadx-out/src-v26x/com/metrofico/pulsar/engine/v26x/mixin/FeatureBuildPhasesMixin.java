package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

abstract class FeatureBuildPhasesMixin {
   @Mixin({ModelFeatureRenderer.class})
   abstract static class Models {
      @Inject(
         method = {"buildGroup"},
         at = {@At("HEAD")},
         require = 1
      )
      private void pulsar$begin(CallbackInfo ci) {
         PerfStats.begin(9);
      }

      @Inject(
         method = {"buildGroup"},
         at = {@At("TAIL")},
         require = 1
      )
      private void pulsar$end(CallbackInfo ci) {
         PerfStats.end(9);
      }
   }

   @Mixin({NameTagFeatureRenderer.class})
   abstract static class NameTags {
      @Inject(
         method = {"buildGroup"},
         at = {@At("HEAD")},
         require = 1
      )
      private void pulsar$begin(CallbackInfo ci) {
         PerfStats.begin(8);
      }

      @Inject(
         method = {"buildGroup"},
         at = {@At("TAIL")},
         require = 1
      )
      private void pulsar$end(CallbackInfo ci) {
         PerfStats.end(8);
      }
   }
}

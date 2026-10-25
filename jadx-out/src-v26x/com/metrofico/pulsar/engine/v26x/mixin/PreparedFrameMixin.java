package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   targets = {"net.minecraft.client.renderer.feature.FeatureRenderDispatcher$PreparedFrame"}
)
abstract class PreparedFrameMixin {
   @Shadow
   @Final
   FeatureRenderDispatcher this$0;

   @Inject(
      method = {"close"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$endDraw(CallbackInfo ci) {
      ParallelFeatures.endDraw(((FeatureRenderDispatcherAccessor)this.this$0).pulsar$featureRenderers().values());
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.EntityBake;
import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import com.metrofico.pulsar.engine.v26x.perf.PoseCache26;
import com.metrofico.pulsar.engine.v26x.perf.TextBake;
import java.util.List;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.FeatureRendererMap;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({FeatureRenderDispatcher.class})
abstract class FeatureRenderDispatcherMixin {
   @Inject(
      method = {"prepareFrame"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$prepareBegin(CallbackInfoReturnable<?> cir) {
      PerfStats.begin(6);
      TextBake.tick();
      EntityBake.beginFrame();
      PoseCache26.beginFrame();
   }

   @Inject(
      method = {"prepareFrame"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$prepareEnd(CallbackInfoReturnable<?> cir) {
      PerfStats.end(6);
   }

   @Inject(
      method = {"prepareFrameWithContext"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$beginFrame(CallbackInfoReturnable<?> cir) {
      ParallelFeatures.beginFrame();
   }

   @Redirect(
      method = {"prepareFrameWithContext"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedGroup;prepare(Lnet/minecraft/client/renderer/feature/FeatureFrameContext;Lnet/minecraft/client/renderer/feature/FeatureRendererMap;Ljava/util/List;)V"
      ),
      require = 1
   )
   private void pulsar$prepareGroup(@Coerce Object group, FeatureFrameContext context, FeatureRendererMap renderers, List<SubmitNode> all) {
      ParallelFeatures.prepare(group, context, renderers, all);
   }

   @Inject(
      method = {"prepareFrameWithContext"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/renderer/feature/FeatureRendererMap;values()Ljava/lang/Iterable;",
         ordinal = 1
      )},
      require = 1
   )
   private void pulsar$join(CallbackInfoReturnable<?> cir) {
      ParallelFeatures.join();
   }

   @Inject(
      method = {"renderAllFeatures"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$drawBegin(CallbackInfo ci) {
      PerfStats.begin(7);
   }

   @Inject(
      method = {"renderAllFeatures"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$drawEnd(CallbackInfo ci) {
      PerfStats.end(7);
   }
}

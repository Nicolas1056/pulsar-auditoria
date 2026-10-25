package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LightmapRenderStateExtractor.class})
abstract class LightmapExtractorMixin {
   @Inject(
      method = {"extract(Lnet/minecraft/client/renderer/state/LightmapRenderState;F)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$fullbright(LightmapRenderState state, float partialTick, CallbackInfo ci) {
      ClientOptions o = ClientOptions.get();
      if (o.enabled("fullbright")) {
         float intensity = (float)(o.dbl("fullbright.intensity", 100.0) / 100.0);
         if (intensity > state.nightVisionEffectIntensity) {
            state.nightVisionEffectIntensity = intensity;
            state.nightVisionColor = LightmapRenderStateExtractor.WHITE;
         }
      }
   }
}

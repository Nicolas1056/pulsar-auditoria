package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.NoErrorContext;
import com.mojang.blaze3d.opengl.GlBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GlBackend.class})
public abstract class GlBackendMixin {
   @Inject(
      method = {"setWindowHints"},
      at = {@At("TAIL")}
   )
   private void pulsar$noError(CallbackInfo ci) {
      NoErrorContext.applyHint();
   }
}

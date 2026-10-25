package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.BufferDeletes189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bga.class})
public abstract class BuiltChunkStorageMixin {
   @Inject(
      method = {"a()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$clearBegin(CallbackInfo ci) {
      BufferDeletes189.begin();
   }

   @Inject(
      method = {"a()V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$clearEnd(CallbackInfo ci) {
      BufferDeletes189.end();
   }
}

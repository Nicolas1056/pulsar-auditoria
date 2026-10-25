package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bpx.class})
abstract class SoundReloadMixin {
   @Unique
   private Thread pulsar$loader;

   @Redirect(
      method = {"i"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/lang/Thread;start()V"
      ),
      require = 1
   )
   private void pulsar$trackLoader(Thread loader) {
      this.pulsar$loader = loader;
      loader.start();
   }

   @Inject(
      method = {"a()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$awaitLoader(CallbackInfo ci) {
      Thread loader = this.pulsar$loader;
      if (loader != null && loader.isAlive()) {
         try {
            loader.join(10000L);
         } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
         }
      }
   }
}

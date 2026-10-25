package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({avo.class})
public abstract class ScoreboardSidebarMixin {
   @Inject(
      method = {"a(Lauk;Lavr;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$replaceSidebar(auk objective, avr window, CallbackInfo ci) {
      ci.cancel();
   }
}

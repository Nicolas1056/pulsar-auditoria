package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.PerfBooster189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({avh.class})
public abstract class GameOptionsMixin {
   @Inject(
      method = {"b()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$writeVanillaValues(CallbackInfo ci) {
      PerfBooster189.beforeSave((avh)this);
   }

   @Inject(
      method = {"b()V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$restoreBoostedValues(CallbackInfo ci) {
      PerfBooster189.afterSave((avh)this);
   }
}

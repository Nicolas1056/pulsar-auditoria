package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bet.class})
abstract class PlayerEntryRefreshMixin {
   @Shadow
   private bdc a;

   @Inject(
      method = {"b"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$refreshEntry(CallbackInfoReturnable<bdc> cir) {
      bcy network = ave.A().u();
      if (network != null && this.a != null) {
         bdc current = network.a(((bet)this).aK());
         if (current != null && current != this.a) {
            this.a = current;
         }
      }
   }
}

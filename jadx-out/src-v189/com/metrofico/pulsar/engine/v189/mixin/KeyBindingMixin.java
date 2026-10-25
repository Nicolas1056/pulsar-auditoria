package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.controls.ToggleKeys189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({avb.class})
public abstract class KeyBindingMixin {
   @Inject(
      method = {"d"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$toggle(CallbackInfoReturnable<Boolean> cir) {
      int state = ToggleKeys189.state((avb)this);
      if (state >= 0) {
         cir.setReturnValue(state == 1);
      }
   }
}

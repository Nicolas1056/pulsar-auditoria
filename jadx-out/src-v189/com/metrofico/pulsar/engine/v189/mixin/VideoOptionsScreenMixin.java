package com.metrofico.pulsar.engine.v189.mixin;

import awf.a;
import com.metrofico.pulsar.engine.v189.ui.DynamicFovButton189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ayb.class})
public abstract class VideoOptionsScreenMixin extends axu {
   @Shadow
   private awd h;

   @Inject(
      method = {"b()V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$dynamicFovRow(CallbackInfo ci) {
      if (this.h instanceof awf) {
         ((OptionPairWidgetAccessor)this.h).pulsar$entries().add(new a(new DynamicFovButton189(this.l / 2 - 155, 0), null));
      }
   }
}

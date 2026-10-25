package com.metrofico.pulsar.engine.v189.mixin;

import java.util.Map;
import java.util.Map.Entry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({pz.class})
public abstract class DataTrackerMixin {
   @Shadow
   @Final
   private Map<Integer, ?> d;
   @Unique
   private final Object[] pulsar$fast = new Object[32];

   @Inject(
      method = {"a(ILjava/lang/Object;)V", "a(II)V"},
      at = {@At("RETURN")},
      require = 2
   )
   private void pulsar$index(CallbackInfo ci) {
      for (Entry<Integer, ?> e : this.d.entrySet()) {
         int id = e.getKey();
         if (id >= 0 && id < 32) {
            this.pulsar$fast[id] = e.getValue();
         }
      }
   }

   @Inject(
      method = {"j"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$fastGet(int id, CallbackInfoReturnable cir) {
      if (id >= 0 && id < 32) {
         Object e = this.pulsar$fast[id];
         if (e != null) {
            cir.setReturnValue(e);
         }
      }
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.ArrayBufferGuard189;
import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.FireBatch189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfx.class})
public abstract class TessellatorMixin {
   @Shadow
   private bfd a;
   @Shadow
   private bfe b;

   @Inject(
      method = {"b"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$capture(CallbackInfo ci) {
      ArrayBufferGuard189.beforeClientArrays();
      if (FireBatch189.collecting() && EntityBatch189.onRenderThread()) {
         this.a.e();
         if (FireBatch189.collect(this.a)) {
            this.a.b();
         } else {
            this.b.a(this.a);
         }

         ci.cancel();
      } else if (EntityCache189.capturing() && EntityBatch189.onRenderThread()) {
         if (this.a.g() != bms.c) {
            this.a.e();
            if (EntityCache189.captureBuffer(this.a)) {
               EntityBatch189.markPending();
               this.a.b();
            } else {
               this.b.a(this.a);
            }

            ci.cancel();
         }
      }
   }
}

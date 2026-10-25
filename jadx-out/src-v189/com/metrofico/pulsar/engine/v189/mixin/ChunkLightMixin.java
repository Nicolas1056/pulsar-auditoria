package com.metrofico.pulsar.engine.v189.mixin;

import amy.a;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.SectionDirt189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({amy.class})
public abstract class ChunkLightMixin {
   @Shadow
   private boolean o;
   @Unique
   private long pulsar$retryAt;
   @Unique
   private int pulsar$failures;

   @Redirect(
      method = {"b(Z)V"},
      at = @At(
         value = "INVOKE",
         target = "Lamy;n()V"
      ),
      require = 1
   )
   private void pulsar$populateWithBackoff(amy chunk) {
      long now = System.nanoTime();
      if (!FrameFlags.perf || now >= this.pulsar$retryAt) {
         long t0 = System.nanoTime();
         chunk.n();
         SectionDirt189.populated(System.nanoTime() - t0);
         if (this.o) {
            this.pulsar$failures = 0;
            this.pulsar$retryAt = 0L;
         } else {
            this.pulsar$retryAt = now + (2000000000L << Math.min(4, this.pulsar$failures++));
         }
      }
   }

   @Redirect(
      method = {"b(Z)V"},
      at = @At(
         value = "INVOKE",
         target = "Lamy;h(Z)V"
      ),
      require = 1
   )
   private void pulsar$gapsTimed(amy chunk, boolean onlyOne) {
      long t0 = System.nanoTime();
      this.pulsar$recheckSkyLightGaps(onlyOne);
      SectionDirt189.gapsChecked(System.nanoTime() - t0);
   }

   @Redirect(
      method = {"b(Z)V"},
      at = @At(
         value = "INVOKE",
         target = "Ladm;b(Lcj;Lcj;)V"
      ),
      require = 1
   )
   private void pulsar$blockEntityQueued(adm world, cj from, cj to) {
      amy self = (amy)this;
      boolean stored = self.a(from, a.c) != null;
      SectionDirt189.blockEntityCreated(from, self.a(from), stored);
      if (stored || !FrameFlags.blockEntityLoop) {
         world.b(from, to);
      }
   }

   @Invoker("h")
   protected abstract void pulsar$recheckSkyLightGaps(boolean var1);
}

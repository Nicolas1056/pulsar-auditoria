package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.CachedChunk189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bcz.class})
public abstract class ClientChunkProviderMixin {
   @Unique
   private final CachedChunk189[] pulsar$cache = new CachedChunk189[1024];
   @Shadow
   private nq<amy> c;
   @Shadow
   private amy b;
   @Unique
   private Thread pulsar$owner;

   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$captureOwner(CallbackInfo ci) {
      this.pulsar$owner = Thread.currentThread();
   }

   @Overwrite
   public amy d(int x, int z) {
      if (!FrameFlags.perf) {
         amy chunk = (amy)this.c.a(adg.a(x, z));
         return chunk == null ? this.b : chunk;
      } else {
         int slot = CachedChunk189.slotOf(x, z);
         long key = CachedChunk189.keyOf(x, z);
         CachedChunk189 cached = this.pulsar$cache[slot];
         if (cached != null && cached.key() == key) {
            return cached.chunk();
         } else {
            amy chunk = (amy)this.c.a(adg.a(x, z));
            if (chunk == null) {
               chunk = this.b;
            }

            if (Thread.currentThread() == this.pulsar$owner) {
               this.pulsar$cache[slot] = new CachedChunk189(key, chunk);
            }

            return chunk;
         }
      }
   }

   @Inject(
      method = {"b"},
      at = {@At("HEAD"), @At("RETURN")},
      require = 2
   )
   private void pulsar$forgetUnloaded(int x, int z, CallbackInfo ci) {
      this.pulsar$cache[CachedChunk189.slotOf(x, z)] = null;
   }

   @Inject(
      method = {"c(II)Lamy;"},
      at = {@At("HEAD"), @At("RETURN")},
      require = 2
   )
   private void pulsar$forgetLoaded(int x, int z, CallbackInfoReturnable<amy> cir) {
      this.pulsar$cache[CachedChunk189.slotOf(x, z)] = null;
   }
}

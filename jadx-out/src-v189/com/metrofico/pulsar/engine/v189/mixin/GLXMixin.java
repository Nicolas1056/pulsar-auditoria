package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bqs.class})
public abstract class GLXMixin {
   @Shadow
   public static boolean P;

   @Inject(
      method = {"f()Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$forceVbo(CallbackInfoReturnable<Boolean> cir) {
      if (P) {
         if (!FrameFlags.refreshed) {
            try {
               FrameFlags.refresh();
            } catch (RuntimeException var2) {
               return;
            }
         }

         if (FrameFlags.terrainRegions) {
            cir.setReturnValue(true);
         }
      }
   }
}

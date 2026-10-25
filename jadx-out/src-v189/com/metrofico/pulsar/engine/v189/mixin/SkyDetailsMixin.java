package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfr.class})
public abstract class SkyDetailsMixin {
   @Shadow
   private int p;

   @Inject(
      method = {"a(FI)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noSky(float tickDelta, int pass, CallbackInfo ci) {
      if (!FrameFlags.sky) {
         ci.cancel();
      }
   }

   @Redirect(
      method = {"a(FI)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;o(I)V"
      ),
      require = 3
   )
   private void pulsar$stars(int list) {
      if (FrameFlags.stars || list != this.p) {
         bfl.o(list);
      }
   }

   @Redirect(
      method = {"a(FI)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfx;b()V",
         ordinal = 1
      ),
      require = 1
   )
   private void pulsar$sun(bfx tess) {
      pulsar$drawOrDiscard(tess, FrameFlags.sunMoon);
   }

   @Redirect(
      method = {"a(FI)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfx;b()V",
         ordinal = 2
      ),
      require = 1
   )
   private void pulsar$moon(bfx tess) {
      pulsar$drawOrDiscard(tess, FrameFlags.sunMoon);
   }

   private static void pulsar$drawOrDiscard(bfx tess, boolean draw) {
      if (draw) {
         tess.b();
      } else {
         tess.c().e();
         tess.c().b();
      }
   }
}

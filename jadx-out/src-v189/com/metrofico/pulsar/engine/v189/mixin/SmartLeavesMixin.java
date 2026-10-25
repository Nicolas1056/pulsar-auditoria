package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({akg.class})
public abstract class SmartLeavesMixin {
   @Shadow
   protected boolean R;

   @Redirect(
      method = {"a"},
      at = @At(
         value = "FIELD",
         target = "Lakg;R:Z"
      ),
      require = 1
   )
   private boolean pulsar$smartLeaves(akg self, adq view, cj neighborPos, cq side) {
      return !this.R ? false : !FrameFlags.smartLeaves || !pulsar$interiorFace(view, neighborPos, side);
   }

   private static boolean pulsar$interiorFace(adq view, cj neighborPos, cq side) {
      if (!(view.p(neighborPos).c() instanceof akg)) {
         return false;
      } else {
         afh beyond = view.p(neighborPos.a(side)).c();
         return beyond instanceof akg || beyond.c();
      }
   }
}

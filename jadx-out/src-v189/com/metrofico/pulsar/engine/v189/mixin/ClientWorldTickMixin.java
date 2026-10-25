package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({bdb.class})
public abstract class ClientWorldTickMixin {
   @Redirect(
      method = {"c()V"},
      at = @At(
         value = "INVOKE",
         target = "Lbcz;d()Z"
      ),
      require = 1
   )
   private boolean pulsar$subLight(bcz p) {
      PerfStats189.subEnd(2);
      PerfStats189.subBegin(3);

      boolean var2;
      try {
         var2 = p.d();
      } finally {
         PerfStats189.subEnd(3);
         PerfStats189.subBegin(2);
      }

      return var2;
   }
}

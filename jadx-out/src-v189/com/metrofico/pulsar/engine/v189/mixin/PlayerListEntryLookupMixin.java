package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.gfx.PlayerSkins189;
import java.util.UUID;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bcy.class})
public abstract class PlayerListEntryLookupMixin {
   @Inject(
      method = {"a(Ljava/util/UUID;)Lbdc;"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void pulsar$retainedEntry(UUID uuid, CallbackInfoReturnable<bdc> cir) {
      if (cir.getReturnValue() == null) {
         bdc retained = PlayerSkins189.retainedEntry(uuid);
         if (retained != null) {
            cir.setReturnValue(retained);
         }
      }
   }
}

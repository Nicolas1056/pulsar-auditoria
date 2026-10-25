package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.legit.LegitWatch;
import com.metrofico.pulsar.api.legit.ReachSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bda.class})
public abstract class AttackReachMixin {
   @Inject(
      method = {"a(Lwn;Lpk;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$reach(wn player, pk target, CallbackInfo ci) {
      if (LegitWatch.host() != null) {
         aug b = target.aR();
         ReachSensor.hit(player.s, player.t + player.aS(), player.u, b.a, b.b, b.c, b.d, b.e, b.f);
      }
   }
}

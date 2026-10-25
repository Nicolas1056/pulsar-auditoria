package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.ServerMoveStamp189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bex.class})
public abstract class OtherClientPlayerMoveMixin implements ServerMoveStamp189 {
   @Unique
   private long pulsar$lastServerMove = System.nanoTime();

   @Inject(
      method = {"a(DDDFFIZ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$serverMove(double x, double y, double z, float yaw, float pitch, int steps, boolean teleport, CallbackInfo ci) {
      this.pulsar$lastServerMove = System.nanoTime();
   }

   @Override
   public long pulsar$lastServerMove() {
      return this.pulsar$lastServerMove;
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import io.netty.channel.epoll.Epoll;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {Epoll.class},
   remap = false
)
public abstract class EpollMixin {
   @Inject(
      method = {"isAvailable"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$noEpoll(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(false);
   }
}

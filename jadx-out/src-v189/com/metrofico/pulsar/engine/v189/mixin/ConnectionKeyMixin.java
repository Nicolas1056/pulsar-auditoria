package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.legit.ConnectionIdentity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bcx.class})
public abstract class ConnectionKeyMixin {
   @Inject(
      method = {"a(Ljh;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$encryptionKey(jh packet, CallbackInfo ci) {
      ConnectionIdentity.encryptingWith(packet.b());
   }
}

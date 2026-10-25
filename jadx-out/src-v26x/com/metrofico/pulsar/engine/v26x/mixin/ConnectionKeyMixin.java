package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.legit.ConnectionIdentity;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientHandshakePacketListenerImpl.class})
abstract class ConnectionKeyMixin {
   @Inject(
      method = {"handleHello"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$encryptionKey(ClientboundHelloPacket packet, CallbackInfo ci) {
      try {
         ConnectionIdentity.encryptingWith(packet.getPublicKey());
      } catch (Exception var4) {
         ConnectionIdentity.encryptingWith(null);
      }
   }
}

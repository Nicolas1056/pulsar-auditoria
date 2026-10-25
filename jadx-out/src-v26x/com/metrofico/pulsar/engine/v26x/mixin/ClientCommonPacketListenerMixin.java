package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.metrofico.pulsar.engine.v26x.pack.PulsarChannel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientCommonPacketListenerImpl.class})
abstract class ClientCommonPacketListenerMixin {
   @Inject(
      method = {"handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$payload(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
      if (packet.payload() instanceof DiscardedPayload d && PulsarChannel.isPulsar(d.id())) {
         String json = PulsarChannel.takeInbound();
         if (json != null) {
            Minecraft.getInstance().execute(() -> PackRuntime.get().onMessage(json));
         }

         ci.cancel();
      }
   }
}

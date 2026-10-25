package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.NetStats;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Connection.class})
abstract class ConnectionMixin {
   @Inject(
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$arrived(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
      NetStats.packetArrived();
   }

   @Inject(
      method = {"handleDisconnection"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$disconnected(CallbackInfo ci) {
      NetStats.disconnected();
   }
}

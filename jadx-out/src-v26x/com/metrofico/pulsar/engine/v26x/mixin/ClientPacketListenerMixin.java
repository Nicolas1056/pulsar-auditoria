package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.metrofico.pulsar.engine.v26x.perf.NetStats;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.ping.ClientboundPongResponsePacket;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPacketListener.class})
abstract class ClientPacketListenerMixin {
   @Inject(
      method = {"handleLogin"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$joined(ClientboundLoginPacket packet, CallbackInfo ci) {
      PackRuntime.get().onJoined();
   }

   @Inject(
      method = {"close"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$left(CallbackInfo ci) {
      PackRuntime.get().onLeft();
   }

   @Inject(
      method = {"handlePongResponse"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$pong(ClientboundPongResponsePacket packet, CallbackInfo ci) {
      NetStats.pong(Util.getMillis() - packet.time());
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.legit.ConnectionIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ConnectScreen.class})
abstract class ConnectionIdentityMixin {
   @Inject(
      method = {"startConnecting"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$typedHost(
      Screen parent, Minecraft minecraft, ServerAddress address, ServerData data, boolean quickPlay, TransferState transfer, CallbackInfo ci
   ) {
      ConnectionIdentity.connecting(address.getHost());
   }
}

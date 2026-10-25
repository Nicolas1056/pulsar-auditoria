package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
abstract class PlayerInfoRefreshMixin {
   @Shadow
   private PlayerInfo playerInfo;

   @Inject(
      method = {"getPlayerInfo"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$refreshInfo(CallbackInfoReturnable<PlayerInfo> cir) {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null && this.playerInfo != null) {
         PlayerInfo current = connection.getPlayerInfo(((AbstractClientPlayer)this).getUUID());
         if (current != null && current != this.playerInfo) {
            this.playerInfo = current;
         }
      }
   }
}

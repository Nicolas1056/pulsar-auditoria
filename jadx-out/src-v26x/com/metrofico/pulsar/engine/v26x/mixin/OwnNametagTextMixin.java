package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityRenderer.class})
abstract class OwnNametagTextMixin {
   private static Component pulsar$lastPrefix;
   private static Component pulsar$lastSuffix;
   private static boolean pulsar$lastDecorates;

   @Inject(
      method = {"getNameTag"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$ownName(Entity entity, CallbackInfoReturnable<Component> cir) {
      Minecraft mc = Minecraft.getInstance();
      if (entity == mc.player && FrameFlags.ownNametag) {
         PlayerTeam team = entity.getTeam();
         if (team == null || !pulsar$decorates(team)) {
            ClientPacketListener connection = mc.getConnection();
            PlayerInfo info = connection == null ? null : connection.getPlayerInfo(entity.getUUID());
            Component tab = info == null ? null : info.getTabListDisplayName();
            if (tab != null) {
               cir.setReturnValue(tab);
            }
         }
      }
   }

   private static boolean pulsar$decorates(PlayerTeam team) {
      Component prefix = team.getPlayerPrefix();
      Component suffix = team.getPlayerSuffix();
      if (prefix != pulsar$lastPrefix || suffix != pulsar$lastSuffix) {
         pulsar$lastPrefix = prefix;
         pulsar$lastSuffix = suffix;
         pulsar$lastDecorates = !prefix.getString().isEmpty() || !suffix.getString().isEmpty();
      }

      return pulsar$lastDecorates;
   }
}

package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.Team.Visibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntityRenderer.class})
abstract class OwnNametagMixin {
   @Inject(
      method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$ownNametag(LivingEntity entity, double distanceSq, CallbackInfoReturnable<Boolean> cir) {
      Minecraft mc = Minecraft.getInstance();
      if (entity == mc.player && FrameFlags.ownNametag) {
         Screen screen = mc.gui.screen();
         boolean place = !mc.options.getCameraType().isFirstPerson() || screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen;
         cir.setReturnValue(place && !mc.gui.hud.isHidden() && !entity.isInvisibleTo(mc.player) && !entity.isVehicle() && pulsar$ownTeamShows(entity.getTeam()));
      }
   }

   private static boolean pulsar$ownTeamShows(Team team) {
      if (team == null) {
         return true;
      } else {
         Visibility rule = team.getNameTagVisibility();
         return rule != Visibility.NEVER && rule != Visibility.HIDE_FOR_OWN_TEAM;
      }
   }
}

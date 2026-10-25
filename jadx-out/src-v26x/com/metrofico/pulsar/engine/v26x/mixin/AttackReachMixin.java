package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.legit.LegitWatch;
import com.metrofico.pulsar.api.legit.ReachSensor;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MultiPlayerGameMode.class})
abstract class AttackReachMixin {
   @Inject(
      method = {"attack"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$reach(Player player, Entity target, CallbackInfo ci) {
      if (LegitWatch.host() != null) {
         AABB b = target.getBoundingBox();
         ReachSensor.hit(player.getX(), player.getEyeY(), player.getZ(), b.minX, b.minY, b.minZ, b.maxX, b.maxY, b.maxZ);
      }
   }
}

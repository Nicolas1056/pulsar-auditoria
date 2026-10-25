package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v26x.perf.ItemLod;
import com.metrofico.pulsar.engine.v26x.perf.NameOnly26;
import com.metrofico.pulsar.engine.v26x.perf.OcclusionCuller;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LevelExtractor.class})
abstract class LevelExtractorMixin {
   @Inject(
      method = {"extract"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$frameEnd(DeltaTracker delta, Camera camera, float partialTick, CallbackInfo ci) {
      PerfStats.end(0);
   }

   @Inject(
      method = {"extract"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frame(DeltaTracker delta, Camera camera, float partialTick, CallbackInfo ci) {
      PerfStats.begin(0);
      FrameFlags.refresh();
      NameOnly26.beginFrame();
      Minecraft mc = Minecraft.getInstance();
      if (FrameFlags.perf && mc.level != null && camera.isInitialized()) {
         OcclusionCuller.frame(camera.position(), mc.level);
         ItemLod.beginFrame(mc.level, camera.position());
      } else {
         ItemLod.beginFrame(null, camera.position());
      }
   }

   @Inject(
      method = {"isEntityVisible"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$occlusion(Entity entity, Frustum frustum, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
      if (FrameFlags.perf) {
         if (entity instanceof ItemEntity item) {
            ItemLod.Verdict v = ItemLod.check(item, OcclusionCuller.eye());
            if (v == ItemLod.Verdict.HIDE) {
               cir.setReturnValue(false);
               return;
            }

            if (v == ItemLod.Verdict.SHOW) {
               cir.setReturnValue(frustum.isVisible(item.getBoundingBox().inflate(0.5)));
               return;
            }
         }

         if (FrameFlags.cullEntities) {
            PerfStats.entitySeen();
            Minecraft mc = Minecraft.getInstance();
            Entity cam = mc.getCameraEntity();
            if (entity != cam && !entity.isCurrentlyGlowing()) {
               if (entity instanceof Avatar avatar && (!(entity instanceof Player) || !FrameFlags.cullPlayers)) {
                  if (FrameFlags.occludePlayers) {
                     if (cam == null || !cam.hasIndirectPassenger(entity) && !entity.hasIndirectPassenger(cam)) {
                        NameOnly26.test(avatar);
                     }

                     return;
                  }

                  if (entity instanceof Player) {
                     return;
                  }
               }

               if (cam == null || !cam.hasIndirectPassenger(entity) && !entity.hasIndirectPassenger(cam)) {
                  AABB box = entity.getBoundingBox();
                  boolean named = entity.isCustomNameVisible() || entity instanceof ArmorStand;
                  if (named) {
                     if (entity.isInvisible() || box.getSize() < 0.3) {
                        return;
                     }

                     box = box.minmax(new AABB(entity.getX() - 0.5, box.maxY, entity.getZ() - 0.5, entity.getX() + 0.5, box.maxY + 1.0, entity.getZ() + 0.5));
                  }

                  if (OcclusionCuller.candidate(box, OcclusionCuller.eye())) {
                     if (OcclusionCuller.hidden(OcclusionCuller.entityKey(entity.getId()), box)) {
                        PerfStats.entityCulled();
                        cir.setReturnValue(false);
                     }
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = {"setSectionDirty(IIIZ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$dirty(int sx, int sy, int sz, boolean now, CallbackInfo ci) {
      OcclusionCuller.invalidateSection(sx, sy, sz);
   }

   @Inject(
      method = {"setLevel"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$level(ClientLevel level, CallbackInfo ci) {
      OcclusionCuller.levelChanged(level);
   }
}

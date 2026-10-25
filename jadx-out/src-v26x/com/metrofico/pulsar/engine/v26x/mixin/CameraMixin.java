package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Camera.class})
abstract class CameraMixin {
   @Shadow
   private boolean detached;
   @Unique
   private static final String PULSAR_DETACHED = System.getenv("PULSAR_CINE_DETACHED");
   @Unique
   private static final boolean PULSAR_EXTRACT = !"0".equals(System.getenv("PULSAR_CINE_EXTRACT"));

   @Shadow
   protected abstract void setPosition(double var1, double var3, double var5);

   @Shadow
   protected abstract void setRotation(float var1, float var2);

   @Unique
   private void pulsar$apply() {
      double[] f = CameraDirector.get().frame();
      if (f != null) {
         this.detached = PULSAR_DETACHED != null ? "1".equals(PULSAR_DETACHED) : !CameraDirector.get().hideSelf();
         this.setRotation((float)f[3], (float)f[4]);
         this.setPosition(f[0], f[1], f[2]);
      }
   }

   @Redirect(
      method = {"alignWithEntity"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"
      ),
      require = 2
   )
   private float pulsar$freecamYaw(Entity entity, float partialTick) {
      return Freecam.active() && entity == Minecraft.getInstance().player ? Freecam.yaw() : entity.getViewYRot(partialTick);
   }

   @Redirect(
      method = {"alignWithEntity"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"
      ),
      require = 2
   )
   private float pulsar$freecamPitch(Entity entity, float partialTick) {
      return Freecam.active() && entity == Minecraft.getInstance().player ? Freecam.pitch() : entity.getViewXRot(partialTick);
   }

   @Inject(
      method = {"update"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$cinematic(DeltaTracker delta, CallbackInfo ci) {
      this.pulsar$apply();
   }

   @Inject(
      method = {"extractRenderState"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$extract(CameraRenderState state, float partialTick, CallbackInfo ci) {
      if (PULSAR_EXTRACT) {
         this.pulsar$apply();
      }
   }
}

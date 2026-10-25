package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.emote.EmoteClock;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.modules.DynamicFov;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Visuals17;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.api.perf.FrameStats;
import com.metrofico.pulsar.engine.v189.emote.EmotePose189;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.hud.Hud189;
import com.metrofico.pulsar.engine.v189.perf.BenchShot189;
import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.EntityLod189;
import com.metrofico.pulsar.engine.v189.perf.FovWatch189;
import com.metrofico.pulsar.engine.v189.perf.MatrixMirror189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bfk.class})
public abstract class GameRendererMixin {
   @Shadow
   @Final
   private int[] H;
   @Shadow
   private float x;
   @Shadow
   private float y;
   private static boolean pulsar$terrainWas;

   private static float pulsar$intensity() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("fullbright") ? (float)(o.dbl("fullbright.intensity", 100.0) / 100.0) : 0.0F;
   }

   @Inject(
      method = {"g"},
      at = {@At(
         value = "INVOKE",
         target = "Lblz;d()V"
      )},
      require = 1
   )
   private void pulsar$fullBright(float tickDelta, CallbackInfo ci) {
      float k = Math.min(1.0F, pulsar$intensity());
      if (!(k <= 0.0F)) {
         int[] px = this.H;

         for (int i = 0; i < px.length; i++) {
            int c = px[i];
            int r = c >> 16 & 0xFF;
            int g = c >> 8 & 0xFF;
            int b = c & 0xFF;
            r += (int)((255 - r) * k);
            g += (int)((255 - g) * k);
            b += (int)((255 - b) * k);
            px[i] = c & 0xFF000000 | r << 16 | g << 8 | b;
         }
      }
   }

   @Inject(
      method = {"a(FZ)F"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void pulsar$zoomFov(float tickDelta, boolean useFovSetting, CallbackInfoReturnable<Float> cir) {
      if (ave.A().m != null) {
         Zoom.release();
         Freecam.release();
      }

      if (useFovSetting && Zoom.enabled() && Zoom.active()) {
         cir.setReturnValue((float)(((Float)cir.getReturnValue()).floatValue() / Zoom.factor()));
      }

      if (useFovSetting) {
         FovWatch189.observe((Float)cir.getReturnValue());
      }
   }

   @Redirect(
      method = {"a(FZ)F"},
      at = @At(
         value = "FIELD",
         target = "Lbfk;x:F",
         opcode = 180
      ),
      require = 1
   )
   private float pulsar$dynamicFov(bfk self) {
      return DynamicFov.multiplier(this.x);
   }

   @Redirect(
      method = {"a(FZ)F"},
      at = @At(
         value = "FIELD",
         target = "Lbfk;y:F",
         opcode = 180
      ),
      require = 2
   )
   private float pulsar$dynamicFovLast(bfk self) {
      return DynamicFov.multiplier(this.y);
   }

   @Redirect(
      method = {"f(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lpk;aS()F"
      ),
      require = 1
   )
   private float pulsar$sneakCamera(pk entity, float tickDelta) {
      float vanilla = entity.aS();
      return entity != ave.A().h ? vanilla : Visuals17.cameraEyeHeight(vanilla, entity.av(), tickDelta);
   }

   @Redirect(
      method = {"e()V"},
      at = @At(
         value = "FIELD",
         target = "Lavh;a:F",
         opcode = 180
      ),
      require = 1
   )
   private float pulsar$zoomSensitivityTick(avh options) {
      return (float)Zoom.sensitivity(options.a);
   }

   @Redirect(
      method = {"a(FJ)V"},
      at = @At(
         value = "FIELD",
         target = "Lavh;a:F",
         opcode = 180
      ),
      require = 1
   )
   private float pulsar$zoomSensitivityRender(avh options) {
      return (float)Zoom.sensitivity(options.a);
   }

   @Inject(
      method = {"a(FJ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frameClock(float tickDelta, long nanos, CallbackInfo ci) {
      EmoteClock.frame();
      Hud189.frameMs = FrameStats.frameStart();
      FramePacer.frame(Hud189.frameMs, PerfStats189.lastSwapMs);
      FrameFlags.refresh();
      boolean terrain = FrameFlags.terrainRegions;
      if (terrain != pulsar$terrainWas) {
         pulsar$terrainWas = terrain;
         ave mc = ave.A();
         if (mc.g != null && mc.f != null) {
            mc.g.a();
         }
      }

      SpriteCulling189.nextFrame();
      EntityBatch189.frame();
      MatrixMirror189.frame();
      EntityLod189.frame();
      EntityCache189.frame();
      Canvas189.frame();
   }

   @Inject(
      method = {"a(FJ)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$benchShot(float tickDelta, long nanos, CallbackInfo ci) {
      BenchShot189.frameEnd();
   }

   @Inject(
      method = {"f(F)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cinematicCamera(float tickDelta, CallbackInfo ci) {
      double[] f = CameraDirector.get().frame();
      if (f != null) {
         pk e = ave.A().ac();
         if (e != null) {
            double ex = e.p + (e.s - e.p) * tickDelta;
            double ey = e.q + (e.t - e.q) * tickDelta;
            double ez = e.r + (e.u - e.r) * tickDelta;
            bfl.b((float)f[4], 1.0F, 0.0F, 0.0F);
            bfl.b((float)f[3] + 180.0F, 0.0F, 1.0F, 0.0F);
            bfl.b((float)(-(f[0] - ex)), (float)(-(f[1] - ey)), (float)(-(f[2] - ez)));
            ci.cancel();
         }
      }
   }

   @Inject(
      method = {"b(FI)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noHand(float tickDelta, int pass, CallbackInfo ci) {
      PerfStats189.begin(10);
      if (CameraDirector.get().active()) {
         ci.cancel();
      } else {
         EmotePose189.firstPersonHand = true;
      }
   }

   @Inject(
      method = {"b(FI)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$handEnd(float tickDelta, int pass, CallbackInfo ci) {
      EmotePose189.firstPersonHand = false;
      PerfStats189.end(10);
   }

   @Redirect(
      method = {"a(FJ)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbew;c(FF)V"
      ),
      require = 2
   )
   private void pulsar$noTurn(bew player, float dx, float dy) {
      if (!CameraDirector.get().active()) {
         if (Freecam.active()) {
            Freecam.turn(dx * 0.15F, -dy * 0.15F);
         } else {
            player.c(dx, dy);
         }
      }
   }

   private static boolean pulsar$freecam(pk e) {
      return Freecam.active() && e == ave.A().ac();
   }

   @Redirect(
      method = {"f(F)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;y:F",
         opcode = 180
      ),
      require = 5
   )
   private float pulsar$freecamYaw(pk e) {
      return pulsar$freecam(e) ? Freecam.yaw() : e.y;
   }

   @Redirect(
      method = {"f(F)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;A:F",
         opcode = 180
      ),
      require = 4
   )
   private float pulsar$freecamPrevYaw(pk e) {
      return pulsar$freecam(e) ? Freecam.yaw() : e.A;
   }

   @Redirect(
      method = {"f(F)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;z:F",
         opcode = 180
      ),
      require = 5
   )
   private float pulsar$freecamPitch(pk e) {
      return pulsar$freecam(e) ? Freecam.pitch() : e.z;
   }

   @Redirect(
      method = {"f(F)V"},
      at = @At(
         value = "FIELD",
         target = "Lpk;B:F",
         opcode = 180
      ),
      require = 4
   )
   private float pulsar$freecamPrevPitch(pk e) {
      return pulsar$freecam(e) ? Freecam.pitch() : e.B;
   }
}

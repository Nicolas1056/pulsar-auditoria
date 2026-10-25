package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.EntityLod189;
import com.metrofico.pulsar.engine.v189.perf.FireBatch189;
import com.metrofico.pulsar.engine.v189.perf.ItemLod189;
import com.metrofico.pulsar.engine.v189.perf.LabelBatch189;
import com.metrofico.pulsar.engine.v189.perf.NameOnly189;
import com.metrofico.pulsar.engine.v189.perf.OcclusionCuller189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({biu.class})
public abstract class EntityRenderDispatcherMixin {
   @Shadow
   private boolean r;

   @Inject(
      method = {"a(Lpk;Lbia;DDD)Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cull(pk entity, bia camera, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
      if (FrameFlags.perf) {
         double ex = OcclusionCuller189.eyeX();
         double ey = OcclusionCuller189.eyeY();
         double ez = OcclusionCuller189.eyeZ();
         if (entity instanceof uz item) {
            ItemLod189.Verdict v = ItemLod189.check(item, ex, ey, ez);
            if (v == ItemLod189.Verdict.HIDE) {
               cir.setReturnValue(false);
               return;
            }

            if (v == ItemLod189.Verdict.SHOW) {
               cir.setReturnValue(camera.a(item.aR().b(0.5, 0.5, 0.5)));
               return;
            }
         }

         if (FrameFlags.cullEntities) {
            PerfStats189.ENTITIES_SEEN.incrementAndGet();
            pk cam = ave.A().ac();
            if (entity != cam) {
               if (entity instanceof wn player && !FrameFlags.cullPlayers) {
                  if (cam == null || cam.m != entity && entity.m != cam && cam.l != entity && entity.l != cam) {
                     NameOnly189.test(player);
                  }
               } else if (cam == null || cam.m != entity && entity.m != cam && cam.l != entity && entity.l != cam) {
                  aug box = entity.aR();
                  boolean named = entity.aN() || entity instanceof um;
                  if (named) {
                     if (entity.ax() || (box.d - box.a) * (box.e - box.b) < 0.09) {
                        return;
                     }

                     box = box.a(new aug(entity.s - 0.5, box.e, entity.u - 0.5, entity.s + 0.5, box.e + 1.0, entity.u + 0.5));
                  }

                  if (OcclusionCuller189.candidate(box)) {
                     if (OcclusionCuller189.hidden(OcclusionCuller189.entityKey(entity.F()), box)) {
                        PerfStats189.ENTITIES_CULLED.incrementAndGet();
                        cir.setReturnValue(false);
                     }
                  }
               }
            }
         }
      }
   }

   @Redirect(
      method = {"a(Lpk;FZ)Z", "b(Lpk;F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbqs;a(IFF)V"
      ),
      require = 2
   )
   private void pulsar$entityLight(int unit, float s, float t) {
      LabelBatch189.light(s, t);
      FireBatch189.light(unit, s, t);
      bqs.a(unit, s, t);
   }

   @Inject(
      method = {"a(Lpk;DDDFFZ)Z"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$lodBegin(pk entity, double x, double y, double z, float yaw, float tickDelta, boolean hitbox, CallbackInfoReturnable<Boolean> cir) {
      EntityCache189.entityStart();
      EntityLod189.begin(entity, x, y, z);
   }

   @Inject(
      method = {"a(Lpk;DDDFFZ)Z"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$lodEnd(pk entity, double x, double y, double z, float yaw, float tickDelta, boolean hitbox, CallbackInfoReturnable<Boolean> cir) {
      EntityLod189.end();
      FireBatch189.entityDone();
      EntityCache189.entityEnd();
   }

   @Redirect(
      method = {"a(Lpk;DDDFFZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Lbiv;a(Lpk;DDDFF)V"
      ),
      require = 1
   )
   private void pulsar$cachedRender(biv<pk> renderer, pk entity, double x, double y, double z, float yaw, float tickDelta) {
      this.pulsar$renderBody(renderer, entity, x, y, z, yaw, tickDelta);
      FireBatch189.entityBodyDone();
      EntityCache189.bodyDone();
   }

   private void pulsar$renderBody(biv<pk> renderer, pk entity, double x, double y, double z, float yaw, float tickDelta) {
      if (NameOnly189.active(entity)) {
         if (!this.pulsar$outlines()) {
            ((EntityRendererAccessor)renderer).pulsar$renderName(entity, x, y, z);
         }
      } else if (this.pulsar$outlines() || !EntityCache189.enabled()) {
         renderer.a(entity, x, y, z, yaw, tickDelta);
      } else if (!EntityCache189.replayOrBegin(entity, renderer, tickDelta, x, y, z)) {
         try {
            renderer.a(entity, x, y, z, yaw, tickDelta);
         } finally {
            EntityCache189.end(entity, renderer, tickDelta, x, y, z);
         }
      }
   }

   @Inject(
      method = {"a(Ladm;Lavn;Lpk;Lpk;Lavh;F)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$freecamLabels(adm world, avn font, pk camera, pk target, avh options, float tickDelta, CallbackInfo ci) {
      if (Freecam.active()) {
         biu self = (biu)this;
         self.e = Freecam.yaw();
         self.f = Freecam.pitch();
      }
   }

   @Redirect(
      method = {"a(Lpk;DDDFFZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Lbiv;b(Lpk;DDDFF)V"
      ),
      require = 1
   )
   private void pulsar$postRender(biv<pk> renderer, pk entity, double x, double y, double z, float yaw, float tickDelta) {
      if (!NameOnly189.active(entity)) {
         renderer.b(entity, x, y, z, yaw, tickDelta);
      }
   }

   private boolean pulsar$outlines() {
      return this.r;
   }
}

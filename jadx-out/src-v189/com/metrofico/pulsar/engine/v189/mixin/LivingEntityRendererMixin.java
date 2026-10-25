package com.metrofico.pulsar.engine.v189.mixin;

import auq.a;
import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmotePlayer.Active;
import com.metrofico.pulsar.api.hud.modules.Visuals17;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.NameCache189;
import com.metrofico.pulsar.engine.v189.visuals.LayerTint189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bjl.class})
public abstract class LivingEntityRendererMixin {
   private static pk pulsar$naming;

   @Redirect(
      method = {"b(Lpr;DDD)V"},
      at = @At(
         value = "INVOKE",
         target = "Lpr;f_()Leu;"
      ),
      require = 1
   )
   private eu pulsar$name(pr entity) {
      pulsar$naming = entity;
      if (entity instanceof wn player) {
         return FrameFlags.ownNametag && entity == ave.A().h ? pulsar$ownName(player) : entity.f_();
      } else {
         return ((NameCache189)entity).pulsar$cachedName();
      }
   }

   private static eu pulsar$ownName(wn player) {
      if (!(player.bO() instanceof aul team && (!team.e().isEmpty() || !team.f().isEmpty()))) {
         bcy network = ave.A().u();
         bdc entry = network == null ? null : network.a(player.aK());
         return entry != null && entry.k() != null ? entry.k() : player.f_();
      } else {
         return player.f_();
      }
   }

   @Redirect(
      method = {"b(Lpr;DDD)V"},
      at = @At(
         value = "INVOKE",
         target = "Leu;d()Ljava/lang/String;"
      ),
      require = 1
   )
   private String pulsar$formatted(eu text) {
      pk e = pulsar$naming;
      return e != null && !(e instanceof wn) && ((NameCache189)e).pulsar$cachedName() == text ? ((NameCache189)e).pulsar$cachedFormatted() : text.d();
   }

   @Inject(
      method = {"a(Lpr;)Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$ownNametag(pr entity, CallbackInfoReturnable<Boolean> cir) {
      ave mc = ave.A();
      if (entity != mc.h) {
         if (entity instanceof wn && mc.h != null && FrameFlags.invisibleNametags && entity.f(mc.h)) {
            cir.setReturnValue(false);
         }
      } else if (FrameFlags.ownNametag) {
         axu screen = mc.m;
         boolean place = EntityCache189.inWorldPass() || screen instanceof azc || screen instanceof ayu;
         cir.setReturnValue(place && ave.v() && !entity.f(mc.h) && entity.l == null && pulsar$ownTeamShows(entity.bO()));
      }
   }

   private static boolean pulsar$ownTeamShows(auq team) {
      if (team == null) {
         return true;
      } else {
         a rule = team.i();
         return rule != a.b && rule != a.d;
      }
   }

   @Inject(
      method = {"f", "e", "a(Lpr;DDDFF)V"},
      at = {@At("HEAD")},
      require = 3
   )
   private void pulsar$flushBatchHead(CallbackInfo ci) {
      if (EntityBatch189.pending()) {
         EntityBatch189.flush();
      }
   }

   @Inject(
      method = {"a(Lpr;DDDFF)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$flushBatchTail(CallbackInfo ci) {
      if (EntityBatch189.pending()) {
         EntityBatch189.flush();
      }
   }

   @Inject(
      method = {"a(Lpr;FZ)Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$noOverlayWhileCapturing(pr entity, float tickDelta, boolean combine, CallbackInfoReturnable<Boolean> cir) {
      if (EntityCache189.capturing()) {
         cir.setReturnValue(false);
      }
   }

   @Redirect(
      method = {"a(Lpr;FFFFFFF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbjl;a(Lpr;FZ)Z"
      ),
      require = 1
   )
   private boolean pulsar$layerTint(bjl<pr> self, pr entity, float tickDelta, boolean combine) {
      LayerTint189.active = true;

      boolean var5;
      try {
         var5 = ((LivingEntityRendererAccessor)self).pulsar$overlayBegin(entity, tickDelta, combine);
      } finally {
         LayerTint189.active = false;
      }

      return var5;
   }

   @ModifyArg(
      method = {"a(Lpr;FZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;",
         ordinal = 0,
         remap = false
      ),
      require = 1
   )
   private float pulsar$tintR(float vanilla) {
      return pulsar$tint(vanilla, 0);
   }

   @ModifyArg(
      method = {"a(Lpr;FZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;",
         ordinal = 1,
         remap = false
      ),
      require = 1
   )
   private float pulsar$tintG(float vanilla) {
      return pulsar$tint(vanilla, 1);
   }

   @ModifyArg(
      method = {"a(Lpr;FZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;",
         ordinal = 2,
         remap = false
      ),
      require = 1
   )
   private float pulsar$tintB(float vanilla) {
      return pulsar$tint(vanilla, 2);
   }

   @ModifyArg(
      method = {"a(Lpr;FZ)Z"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;",
         ordinal = 3,
         remap = false
      ),
      require = 1
   )
   private float pulsar$tintA(float vanilla) {
      return pulsar$tint(vanilla, 3);
   }

   private static float pulsar$tint(float vanilla, int component) {
      return LayerTint189.active && Visuals17.armorHit() ? Visuals17.component(Visuals17.armorColor(), component) : vanilla;
   }

   @Inject(
      method = {"a(Lpr;FFFFFF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$modelBegin(CallbackInfo ci) {
      EntityCache189.segment(1);
   }

   @Inject(
      method = {"a(Lpr;FFFFFF)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$modelEnd(CallbackInfo ci) {
      EntityCache189.segment(0);
   }

   @Redirect(
      method = {"a(Lpr;FFFFFFF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lblb;a(Lpr;FFFFFFF)V"
      ),
      require = 1
   )
   private void pulsar$feature(blb<pr> feature, pr entity, float a, float b, float c, float d, float e, float f, float g) {
      EntityCache189.segment(feature.b() ? 2 : 3);
      feature.a(entity, a, b, c, d, e, f, g);
      EntityCache189.segment(0);
   }

   private static Active pulsar$running(pr e) {
      if (!EmotePlayer.anyActive()) {
         return null;
      } else {
         Active a = EmotePlayer.active(e.F());
         return a != null && a.running() ? a : null;
      }
   }

   private static Active pulsar$yawLock(pr e) {
      return EntityCache189.inWorldPass() ? pulsar$running(e) : null;
   }

   private static float pulsar$head(pr e, float headYaw) {
      Active a = pulsar$yawLock(e);
      if (a == null) {
         return headYaw;
      } else {
         return a.animatesHead() ? a.lockYaw : a.lockYaw + ns.a(ns.g(headYaw - a.lockYaw), -75.0F, 75.0F);
      }
   }

   @Redirect(
      method = {"a(Lpr;DDDFF)V"},
      at = @At(
         value = "FIELD",
         target = "Lpr;aI:F",
         opcode = 180
      ),
      require = 2
   )
   private float pulsar$bodyYaw(pr e) {
      Active a = pulsar$yawLock(e);
      return a == null ? e.aI : a.lockYaw;
   }

   @Redirect(
      method = {"a(Lpr;DDDFF)V"},
      at = @At(
         value = "FIELD",
         target = "Lpr;aJ:F",
         opcode = 180
      ),
      require = 2
   )
   private float pulsar$prevBodyYaw(pr e) {
      Active a = pulsar$yawLock(e);
      return a == null ? e.aJ : a.lockYaw;
   }

   @Redirect(
      method = {"a(Lpr;DDDFF)V"},
      at = @At(
         value = "FIELD",
         target = "Lpr;aK:F",
         opcode = 180
      ),
      require = 1
   )
   private float pulsar$headYaw(pr e) {
      return pulsar$head(e, e.aK);
   }

   @Redirect(
      method = {"a(Lpr;DDDFF)V"},
      at = @At(
         value = "FIELD",
         target = "Lpr;aL:F",
         opcode = 180
      ),
      require = 1
   )
   private float pulsar$prevHeadYaw(pr e) {
      return pulsar$head(e, e.aL);
   }
}

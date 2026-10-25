package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.social.PulsarPlayers;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.LabelBatch189;
import com.metrofico.pulsar.engine.v189.perf.NameCache189;
import java.util.UUID;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({biv.class})
public abstract class EntityRendererMixin {
   @Shadow
   @Final
   protected biu b;
   private static pk pulsar$naming;
   private static float pulsar$worldPitch = Float.NaN;

   @Shadow
   public abstract avn c();

   @Redirect(
      method = {"a(Lpk;DDD)V"},
      at = @At(
         value = "INVOKE",
         target = "Lpk;f_()Leu;"
      ),
      require = 1
   )
   private eu pulsar$name(pk entity) {
      pulsar$naming = entity;
      return entity instanceof wn ? entity.f_() : ((NameCache189)entity).pulsar$cachedName();
   }

   @Redirect(
      method = {"a(Lpk;DDD)V"},
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
      method = {"a(Lpk;Ljava/lang/String;DDDI)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$captureLabel(pk entity, String text, double d, double e, double f, int maxDistance, CallbackInfo ci) {
      if (!EntityCache189.inWorldPass()) {
         pulsar$worldPitch = this.b.f;
         this.b.f = 0.0F;
      } else if (LabelBatch189.enabled() && !LabelBatch189.capturing() && (text.indexOf(167) < 0 || !text.contains("§n") && !text.contains("§m"))) {
         double g = entity.h(this.b.c);
         if (g > maxDistance * maxDistance) {
            ci.cancel();
         } else {
            UUID markPlayer = pulsar$markPlayer(entity, text);
            if (EntityCache189.label(text, d, e, f, entity.K, text.equals("deadmau5") ? -10 : 0, maxDistance, markPlayer)) {
               ci.cancel();
            } else {
               LabelBatch189.capture(
                  this.c(), text, d, e, f, entity.K, this.b.e, this.b.f, text.equals("deadmau5") ? -10 : 0, PulsarPlayers.nametag(markPlayer)
               );
               ci.cancel();
            }
         }
      }
   }

   private static UUID pulsar$markPlayer(pk entity, String text) {
      if (entity instanceof wn player && entity != ave.A().h) {
         String name = player.e_();
         return name != null && text.contains(name) ? player.aK() : null;
      } else {
         return null;
      }
   }

   @Inject(
      method = {"a(Lpk;Ljava/lang/String;DDDI)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$restorePitch(pk entity, String text, double d, double e, double f, int maxDistance, CallbackInfo ci) {
      if (!Float.isNaN(pulsar$worldPitch)) {
         this.b.f = pulsar$worldPitch;
         pulsar$worldPitch = Float.NaN;
      }
   }
}

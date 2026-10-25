package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bjh.class})
public abstract class Glint17Mixin {
   private static float pulsar$glintScale = 8.0F;

   @Inject(
      method = {"a(Lboq;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$glintBegin(boq model, CallbackInfo ci) {
      bmi sprite = Visuals17.glint() ? model.e() : null;
      pulsar$glintScale = sprite == null ? 8.0F : Visuals17.glintScale(sprite.f() - sprite.e());
   }

   @Redirect(
      method = {"a(Lboq;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;a(FFF)V"
      ),
      require = 2
   )
   private void pulsar$glintScale(float x, float y, float z) {
      if (Visuals17.glint()) {
         bfl.a(pulsar$glintScale, pulsar$glintScale, pulsar$glintScale);
      } else {
         bfl.a(x, y, z);
      }
   }

   @Redirect(
      method = {"a(Lboq;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;b(FFF)V"
      ),
      require = 2
   )
   private void pulsar$glintShift(float x, float y, float z) {
      bfl.b(Visuals17.glint() ? Visuals17.glintShift(x, pulsar$glintScale) : x, y, z);
   }

   @ModifyArg(
      method = {"a(Lboq;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbjh;a(Lboq;I)V"
      ),
      index = 1,
      require = 2
   )
   private int pulsar$glintColor(int vanilla) {
      return Visuals17.glint() ? -10407781 : vanilla;
   }
}

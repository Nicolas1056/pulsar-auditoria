package com.metrofico.pulsar.engine.v189.mixin;

import bgr.b;
import com.metrofico.pulsar.api.hud.modules.Visuals17;
import com.metrofico.pulsar.engine.v189.visuals.FirstPerson17;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfn.class})
public abstract class FirstPersonItemMixin {
   @Shadow
   private zx d;
   private static float pulsar$tickDelta;

   @Inject(
      method = {"a(F)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frame(float tickDelta, CallbackInfo ci) {
      pulsar$tickDelta = tickDelta;
   }

   private static float pulsar$swing(float vanilla) {
      ave mc = ave.A();
      return mc.h == null ? vanilla : mc.h.l(pulsar$tickDelta);
   }

   @ModifyArg(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfn;b(FF)V",
         ordinal = 1
      ),
      index = 1,
      require = 1
   )
   private float pulsar$drinkSwing(float vanilla) {
      return Visuals17.potionDrinking() && this.d != null && this.d.m() == aba.c ? pulsar$swing(vanilla) : vanilla;
   }

   @ModifyArg(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfn;b(FF)V",
         ordinal = 2
      ),
      index = 1,
      require = 1
   )
   private float pulsar$blockHitSwing(float vanilla) {
      return Visuals17.blockHit() ? pulsar$swing(vanilla) : vanilla;
   }

   @ModifyArg(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfn;b(FF)V",
         ordinal = 3
      ),
      index = 1,
      require = 1
   )
   private float pulsar$bowSwing(float vanilla) {
      return Visuals17.bow() ? pulsar$swing(vanilla) : vanilla;
   }

   @ModifyArg(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfn;a(Lpr;Lzx;Lbgr$b;)V"
      ),
      index = 2,
      require = 1
   )
   private b pulsar$position17(b vanilla) {
      if (!FirstPerson17.applies(this.d)) {
         return vanilla;
      } else {
         FirstPerson17.transform(this.d);
         return b.a;
      }
   }

   @Inject(
      method = {"a(FLbet;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lbfl;a(FFF)V"
      )},
      require = 1
   )
   private void pulsar$bowBefore(CallbackInfo ci) {
      if (Visuals17.bow()) {
         bfl.b(-335.0F, 0.0F, 0.0F, 1.0F);
         bfl.b(-50.0F, 0.0F, 1.0F, 0.0F);
         bfl.b(0.0F, 0.5F, 0.0F);
      }
   }

   @Inject(
      method = {"a(FLbet;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lbfl;a(FFF)V",
         shift = Shift.AFTER
      )},
      require = 1
   )
   private void pulsar$bowAfter(CallbackInfo ci) {
      if (Visuals17.bow()) {
         bfl.b(0.0F, -0.5F, 0.0F);
         bfl.b(50.0F, 0.0F, 1.0F, 0.0F);
         bfl.b(335.0F, 0.0F, 0.0F, 1.0F);
      }
   }
}

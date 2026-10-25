package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bmi.class})
public abstract class SpriteMixin implements SpriteCulling189.SpriteExt {
   @Unique
   private int pulsar$lastActive = -1000;
   @Unique
   private boolean pulsar$animated;

   @Override
   public int pulsar$lastActive() {
      return this.pulsar$lastActive;
   }

   @Override
   public void pulsar$lastActive(int frame) {
      this.pulsar$lastActive = frame;
   }

   @Inject(
      method = {"e()F", "f", "g", "h"},
      at = {@At("HEAD")},
      require = 4
   )
   private void pulsar$uv(CallbackInfoReturnable<Float> cir) {
      this.pulsar$touch();
   }

   @Inject(
      method = {"a(D)F", "b(D)F"},
      at = {@At("HEAD")},
      require = 2
   )
   private void pulsar$frameUv(double d, CallbackInfoReturnable<Float> cir) {
      this.pulsar$touch();
   }

   @Override
   public boolean pulsar$animated() {
      return this.pulsar$animated;
   }

   @Override
   public void pulsar$animated(boolean animated) {
      this.pulsar$animated = animated;
   }

   @Unique
   private void pulsar$touch() {
      if (SpriteCulling189.enabled()) {
         if (SpriteCulling189.onRenderThread()) {
            this.pulsar$lastActive = SpriteCulling189.frame();
         } else if (this.pulsar$animated) {
            SpriteCulling189.usedByWorker((bmi)this);
         }
      }
   }
}

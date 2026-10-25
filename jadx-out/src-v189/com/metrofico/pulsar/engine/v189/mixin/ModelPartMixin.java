package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.emote.JointLink189;
import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.MatrixMirror189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bct.class})
public abstract class ModelPartMixin implements JointLink189.Host {
   @Unique
   private JointLink189 pulsar$joint;
   @Shadow
   public boolean k;
   @Shadow
   public boolean j;
   @Shadow
   public float o;
   @Shadow
   public float p;
   @Shadow
   public float q;
   @Shadow
   public float c;
   @Shadow
   public float d;
   @Shadow
   public float e;
   @Shadow
   public float f;
   @Shadow
   public float g;
   @Shadow
   public float h;
   @Shadow
   public List<bct> m;

   @Inject(
      method = {"a(F)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$cpuRender(float scale, CallbackInfo ci) {
      if (EntityBatch189.cpuParts()) {
         ci.cancel();
         if (!this.k && this.j) {
            bct self = (bct)this;
            MatrixMirror189.matrix();
            MatrixMirror189.translate(this.o, this.p, this.q);
            if (this.f != 0.0F || this.g != 0.0F || this.h != 0.0F) {
               MatrixMirror189.push();
               MatrixMirror189.translate(this.c * scale, this.d * scale, this.e * scale);
               if (this.h != 0.0F) {
                  MatrixMirror189.rotate(this.h * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
               }

               if (this.g != 0.0F) {
                  MatrixMirror189.rotate(this.g * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
               }

               if (this.f != 0.0F) {
                  MatrixMirror189.rotate(this.f * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
               }

               EntityBatch189.part(self, 0, scale);
               this.pulsar$children(scale);
               MatrixMirror189.pop();
            } else if (this.c == 0.0F && this.d == 0.0F && this.e == 0.0F) {
               EntityBatch189.part(self, 0, scale);
               this.pulsar$children(scale);
            } else {
               MatrixMirror189.translate(this.c * scale, this.d * scale, this.e * scale);
               EntityBatch189.part(self, 0, scale);
               this.pulsar$children(scale);
               MatrixMirror189.translate(-this.c * scale, -this.d * scale, -this.e * scale);
            }

            MatrixMirror189.translate(-this.o, -this.p, -this.q);
         }
      }
   }

   private void pulsar$children(float scale) {
      if (this.m != null) {
         for (int i = 0; i < this.m.size(); i++) {
            this.m.get(i).a(scale);
         }
      }
   }

   @Override
   public JointLink189 pulsar$joint() {
      return this.pulsar$joint;
   }

   @Override
   public void pulsar$joint(JointLink189 link) {
      this.pulsar$joint = link;
   }

   @Redirect(
      method = {"a(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;o(I)V"
      ),
      require = 3
   )
   private void pulsar$render(int list, float scale) {
      EntityBatch189.part((bct)this, list, scale);
   }

   @Redirect(
      method = {"b(F)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;o(I)V"
      ),
      require = 1
   )
   private void pulsar$rotateAndRender(int list, float scale) {
      EntityBatch189.part((bct)this, list, scale);
   }
}

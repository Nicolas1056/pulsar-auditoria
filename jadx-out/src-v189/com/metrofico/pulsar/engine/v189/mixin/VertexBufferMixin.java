package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.BufferDeletes189;
import com.metrofico.pulsar.engine.v189.perf.LazyBuffers189;
import com.metrofico.pulsar.engine.v189.perf.SectionBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({bmt.class})
public abstract class VertexBufferMixin implements SectionBuffer {
   @Unique
   private bht pulsar$chunk;
   @Unique
   private int pulsar$layer = -1;
   @Shadow
   private int a;

   @Override
   public bht pulsar$chunk() {
      return this.pulsar$chunk;
   }

   @Override
   public int pulsar$layer() {
      return this.pulsar$layer;
   }

   @Redirect(
      method = {"<init>"},
      at = @At(
         value = "INVOKE",
         target = "Lbqs;e()I"
      ),
      require = 1
   )
   private int pulsar$genLater() {
      return LazyBuffers189.gen();
   }

   @Redirect(
      method = {"a()V"},
      at = @At(
         value = "INVOKE",
         target = "Lbqs;g(II)V"
      ),
      require = 1
   )
   private void pulsar$bind(int target, int buffer) {
      if (buffer == 0) {
         buffer = this.a = bqs.e();
      }

      bqs.g(target, buffer);
   }

   @Redirect(
      method = {"c"},
      at = @At(
         value = "INVOKE",
         target = "Lbqs;g(I)V"
      ),
      require = 1
   )
   private void pulsar$delete(int id) {
      if (id != 0 && !BufferDeletes189.defer(id)) {
         bqs.g(id);
      }
   }

   @Override
   public void pulsar$owner(bht chunk, int layer) {
      this.pulsar$chunk = chunk;
      this.pulsar$layer = layer;
   }
}

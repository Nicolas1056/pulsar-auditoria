package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.CompactVertices;
import com.mojang.blaze3d.vertex.MeshData;
import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MeshData.class})
public abstract class MeshDataMixin implements CompactVertices.MeshExt {
   @Unique
   private int pulsar$compactBytes = -1;

   @Inject(
      method = {"vertexBuffer"},
      at = {@At("RETURN")}
   )
   private void pulsar$limit(CallbackInfoReturnable<ByteBuffer> cir) {
      if (this.pulsar$compactBytes >= 0 && cir.getReturnValue() != null) {
         ((ByteBuffer)cir.getReturnValue()).limit(this.pulsar$compactBytes);
      }
   }

   @Override
   public int pulsar$compactBytes() {
      return this.pulsar$compactBytes;
   }

   @Override
   public void pulsar$setCompactBytes(int bytes) {
      this.pulsar$compactBytes = bytes;
   }
}

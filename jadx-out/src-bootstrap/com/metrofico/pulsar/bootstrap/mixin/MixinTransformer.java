package com.metrofico.pulsar.bootstrap.mixin;

import com.metrofico.pulsar.bootstrap.transform.ClassTransformer;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;

final class MixinTransformer implements ClassTransformer {
   private final IMixinTransformer mixin;

   MixinTransformer(IMixinTransformer mixin) {
      this.mixin = mixin;
   }

   @Override
   public String name() {
      return "mixin";
   }

   @Override
   public byte[] transform(String className, byte[] classBytes) {
      return this.mixin.transformClassBytes(className, className, classBytes);
   }
}

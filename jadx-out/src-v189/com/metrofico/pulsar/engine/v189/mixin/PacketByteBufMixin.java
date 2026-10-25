package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({em.class})
public abstract class PacketByteBufMixin {
   @ModifyVariable(
      method = {"c(I)Ljava/lang/String;"},
      at = @At("HEAD"),
      argsOnly = true,
      require = 1
   )
   private int pulsar$maxLength(int vanilla) {
      return Math.max(vanilla, 32767);
   }
}

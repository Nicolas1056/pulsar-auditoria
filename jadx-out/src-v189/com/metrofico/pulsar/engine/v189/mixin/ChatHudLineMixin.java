package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.ObfuscatedLine189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ava.class})
public abstract class ChatHudLineMixin implements ObfuscatedLine189 {
   @Unique
   private boolean pulsar$obfuscated;

   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$scan(int creationTick, eu text, int id, CallbackInfo ci) {
      this.pulsar$obfuscated = text != null && text.d().indexOf("§k") >= 0;
   }

   @Override
   public boolean pulsar$obfuscated() {
      return this.pulsar$obfuscated;
   }
}

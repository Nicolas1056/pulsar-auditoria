package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin({auo.class})
public abstract class ScoreboardMixin {
   @ModifyConstant(
      method = {"a(Ljava/lang/String;Lauu;)Lauk;", "e(Ljava/lang/String;)Laul;"},
      constant = {@Constant(
         intValue = 16
      )},
      require = 2
   )
   private int pulsar$nameLimit(int vanilla) {
      return 32767;
   }

   @ModifyConstant(
      method = {"c(Ljava/lang/String;Lauk;)Laum;", "a(Ljava/lang/String;Ljava/lang/String;)Z"},
      constant = {@Constant(
         intValue = 40
      )},
      require = 2
   )
   private int pulsar$playerLimit(int vanilla) {
      return 32767;
   }
}

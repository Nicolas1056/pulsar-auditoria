package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.TextBake;
import net.minecraft.client.gui.font.FontManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FontManager.class})
abstract class FontManagerMixin {
   @Inject(
      method = {"apply"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$flushTextBake(CallbackInfo ci) {
      TextBake.clear();
   }
}

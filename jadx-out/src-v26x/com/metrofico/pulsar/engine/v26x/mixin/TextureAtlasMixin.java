package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TextureAtlas.class})
public abstract class TextureAtlasMixin {
   @Inject(
      method = {"cycleAnimationFrames"},
      at = {@At("TAIL")}
   )
   private void pulsar$endTick(CallbackInfo ci) {
      SpriteCulling.endTick();
   }
}

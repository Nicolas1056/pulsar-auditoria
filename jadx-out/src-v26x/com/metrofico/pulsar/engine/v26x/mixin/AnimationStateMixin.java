package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AnimationState.class})
public abstract class AnimationStateMixin implements SpriteCulling.StateExt {
   @Unique
   private TextureAtlasSprite pulsar$sprite;

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void pulsar$skipUnseen(CallbackInfo ci) {
      if (!SpriteCulling.shouldTick(this.pulsar$sprite)) {
         ci.cancel();
      }
   }

   @Override
   public TextureAtlasSprite pulsar$sprite() {
      return this.pulsar$sprite;
   }

   @Override
   public void pulsar$setSprite(TextureAtlasSprite sprite) {
      this.pulsar$sprite = sprite;
   }
}

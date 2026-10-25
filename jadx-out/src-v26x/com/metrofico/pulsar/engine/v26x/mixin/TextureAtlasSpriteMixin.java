package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({TextureAtlasSprite.class})
public abstract class TextureAtlasSpriteMixin implements SpriteCulling.SpriteExt {
   @Unique
   private int pulsar$lastActive;

   @Inject(
      method = {"getU(F)F", "getV(F)F", "getU0()F", "getU1()F", "getV0()F", "getV1()F", "wrap(Lcom/mojang/blaze3d/vertex/VertexConsumer;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"},
      at = {@At("HEAD")}
   )
   private void pulsar$touch(CallbackInfoReturnable<?> cir) {
      SpriteCulling.touch((TextureAtlasSprite)this);
   }

   @Inject(
      method = {"createAnimationState"},
      at = {@At("RETURN")}
   )
   private void pulsar$link(CallbackInfoReturnable<AnimationState> cir) {
      if (cir.getReturnValue() instanceof SpriteCulling.StateExt e) {
         e.pulsar$setSprite((TextureAtlasSprite)this);
      }
   }

   @Override
   public int pulsar$lastActive() {
      return this.pulsar$lastActive;
   }

   @Override
   public void pulsar$setLastActive(int frame) {
      this.pulsar$lastActive = frame;
   }
}

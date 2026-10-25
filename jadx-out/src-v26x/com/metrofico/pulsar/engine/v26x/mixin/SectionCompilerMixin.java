package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.CompactVertices;
import com.metrofico.pulsar.engine.v26x.perf.FaceGroups;
import com.metrofico.pulsar.engine.v26x.perf.SpriteCulling;
import java.util.List;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.client.renderer.chunk.SectionCompiler.Results;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SectionCompiler.class})
public abstract class SectionCompilerMixin {
   @Inject(
      method = {"compile"},
      at = {@At("HEAD")}
   )
   private void pulsar$beginSprites(CallbackInfoReturnable<Results> cir) {
      SpriteCulling.beginRecording();
   }

   @Inject(
      method = {"compile"},
      at = {@At("RETURN")}
   )
   private void pulsar$sortFaces(CallbackInfoReturnable<Results> cir) {
      List<TextureAtlasSprite> sprites = SpriteCulling.endRecording();
      Results results = (Results)cir.getReturnValue();
      if (results != null) {
         try {
            if (results instanceof FaceGroups.Holder h) {
               h.pulsar$setSprites(sprites);
            }

            FaceGroups.sort(results);
            CompactVertices.compact(results);
         } catch (Throwable var6) {
         }
      }
   }
}

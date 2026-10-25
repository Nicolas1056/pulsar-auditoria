package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.FaceGroups;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.client.renderer.chunk.TranslucencyPointOfView;
import net.minecraft.client.renderer.chunk.SectionCompiler.Results;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CompiledSectionMesh.class})
public abstract class CompiledSectionMeshMixin implements FaceGroups.Holder {
   @Unique
   private Map<ChunkSectionLayer, int[]> pulsar$faces;
   @Unique
   private boolean pulsar$compact;
   @Unique
   private List<TextureAtlasSprite> pulsar$sprites;

   @Inject(
      method = {"<init>(Lnet/minecraft/client/renderer/chunk/TranslucencyPointOfView;Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;)V"},
      at = {@At("TAIL")}
   )
   private void pulsar$copyFaces(TranslucencyPointOfView pov, Results results, CallbackInfo ci) {
      if (results instanceof FaceGroups.Holder h) {
         this.pulsar$faces = h.pulsar$faces();
         this.pulsar$compact = h.pulsar$compact();
         this.pulsar$sprites = h.pulsar$sprites();
      }
   }

   @Override
   public Map<ChunkSectionLayer, int[]> pulsar$faces() {
      return this.pulsar$faces;
   }

   @Override
   public void pulsar$setFaces(Map<ChunkSectionLayer, int[]> faces) {
      this.pulsar$faces = faces;
   }

   @Override
   public boolean pulsar$compact() {
      return this.pulsar$compact;
   }

   @Override
   public void pulsar$setCompact(boolean compact) {
      this.pulsar$compact = compact;
   }

   @Override
   public List<TextureAtlasSprite> pulsar$sprites() {
      return this.pulsar$sprites;
   }

   @Override
   public void pulsar$setSprites(List<TextureAtlasSprite> sprites) {
      this.pulsar$sprites = sprites;
   }
}

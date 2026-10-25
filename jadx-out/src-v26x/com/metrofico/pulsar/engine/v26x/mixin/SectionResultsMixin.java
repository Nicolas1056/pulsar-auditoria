package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.FaceGroups;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.chunk.SectionCompiler.Results;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({Results.class})
public abstract class SectionResultsMixin implements FaceGroups.Holder {
   @Unique
   private Map<ChunkSectionLayer, int[]> pulsar$faces;
   @Unique
   private boolean pulsar$compact;
   @Unique
   private List<TextureAtlasSprite> pulsar$sprites;

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

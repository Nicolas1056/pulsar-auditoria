package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderPipelines.class})
public interface RenderPipelinesAccessor {
   @Accessor("TERRAIN_SNIPPET")
   static Snippet pulsar$terrainSnippet() {
      throw new AssertionError();
   }
}

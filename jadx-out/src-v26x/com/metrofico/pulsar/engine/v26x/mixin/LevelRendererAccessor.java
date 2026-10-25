package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LevelRenderer.class})
public interface LevelRendererAccessor {
   @Accessor("levelRenderState")
   LevelRenderState pulsar$levelRenderState();
}

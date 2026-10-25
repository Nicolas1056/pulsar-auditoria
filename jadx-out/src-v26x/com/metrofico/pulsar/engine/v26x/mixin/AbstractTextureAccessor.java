package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.textures.GpuSampler;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({AbstractTexture.class})
public interface AbstractTextureAccessor {
   @Accessor("sampler")
   void pulsar$setSampler(GpuSampler var1);
}

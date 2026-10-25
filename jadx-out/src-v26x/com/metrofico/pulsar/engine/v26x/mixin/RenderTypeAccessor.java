package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderType.class})
public interface RenderTypeAccessor {
   @Accessor("name")
   String pulsar$name();
}

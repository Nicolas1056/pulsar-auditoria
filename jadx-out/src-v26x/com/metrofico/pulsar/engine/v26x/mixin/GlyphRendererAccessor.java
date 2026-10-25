package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
   targets = {"net.minecraft.client.renderer.feature.NameTagFeatureRenderer$GlyphRenderer"}
)
public interface GlyphRendererAccessor {
   @Accessor("pose")
   Matrix4f pulsar$pose();

   @Accessor("lightCoords")
   int pulsar$lightCoords();

   @Accessor("displayMode")
   DisplayMode pulsar$displayMode();

   @Accessor("this$0")
   NameTagFeatureRenderer pulsar$outer();
}

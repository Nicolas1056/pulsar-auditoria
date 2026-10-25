package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Submit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({NameTagFeatureRenderer.class})
public interface NameTagFeatureRendererAccessor {
   @Invoker("prepareText")
   static PreparedText pulsar$prepareText(Font font, Submit submit) {
      throw new AssertionError();
   }
}

package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bkn.class})
public interface ArmorFeatureRendererAccessor {
   @Accessor("d")
   bbo pulsar$firstLayer();

   @Accessor("c")
   bbo pulsar$secondLayer();
}

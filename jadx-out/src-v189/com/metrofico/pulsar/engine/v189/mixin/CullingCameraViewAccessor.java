package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bic.class})
public interface CullingCameraViewAccessor {
   @Accessor("a")
   bid pulsar$clipper();

   @Accessor("b")
   double pulsar$x();

   @Accessor("c")
   double pulsar$y();

   @Accessor("d")
   double pulsar$z();
}

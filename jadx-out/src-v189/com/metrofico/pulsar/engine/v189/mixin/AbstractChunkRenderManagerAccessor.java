package com.metrofico.pulsar.engine.v189.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bfh.class})
public interface AbstractChunkRenderManagerAccessor {
   @Accessor("c")
   double pulsar$viewX();

   @Accessor("d")
   double pulsar$viewY();

   @Accessor("e")
   double pulsar$viewZ();

   @Accessor("a")
   List<bht> pulsar$chunks();

   @Accessor("b")
   boolean pulsar$ready();
}

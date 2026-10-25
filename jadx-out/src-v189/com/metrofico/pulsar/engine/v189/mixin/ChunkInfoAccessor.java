package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
   targets = {"bfr$a"}
)
public interface ChunkInfoAccessor {
   @Accessor("a")
   bht pulsar$chunk();

   @Mutable
   @Accessor("a")
   void pulsar$chunk(bht var1);

   @Mutable
   @Accessor("b")
   void pulsar$direction(cq var1);

   @Mutable
   @Accessor("d")
   void pulsar$level(int var1);
}

package com.metrofico.pulsar.engine.v189.mixin;

import java.util.List;
import java.util.Set;
import org.lwjgl.util.vector.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({bfr.class})
public interface WorldRendererAccessor {
   @Accessor("o")
   bga pulsar$chunks();

   @Accessor("k")
   bdb pulsar$world();

   @Accessor("m")
   List<Object> pulsar$visibleChunks();

   @Accessor("m")
   void pulsar$visibleChunks(List<Object> var1);

   @Accessor("ac")
   void pulsar$needsTerrainUpdate(boolean var1);

   @Invoker("c")
   Set<cq> pulsar$openFaces(cj var1);

   @Invoker("a")
   Vector3f pulsar$facing(pk var1, double var2);
}

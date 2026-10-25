package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({BufferBuilder.class})
public interface BufferBuilderAccessor {
   @Invoker("beginVertex")
   long pulsar$beginVertex();

   @Accessor("elements")
   VertexFormatElement[] pulsar$elements();

   @Accessor("initialElementsToFill")
   int pulsar$initialElementsToFill();

   @Accessor("elementsToFill")
   void pulsar$setElementsToFill(int var1);

   @Accessor("entityFormat")
   boolean pulsar$entityFormat();

   @Accessor("vertexPointer")
   long pulsar$vertexPointer();

   @Accessor("vertices")
   int pulsar$vertices();
}

package com.metrofico.pulsar.engine.v26x.mixin;

import java.util.Set;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CubeDefinition.class})
public interface CubeDefinitionAccessor {
   @Accessor("comment")
   String pulsar$comment();

   @Accessor("origin")
   Vector3fc pulsar$origin();

   @Accessor("dimensions")
   Vector3fc pulsar$dimensions();

   @Accessor("grow")
   CubeDeformation pulsar$grow();

   @Accessor("mirror")
   boolean pulsar$mirror();

   @Accessor("texCoord")
   UVPair pulsar$texCoord();

   @Accessor("texScale")
   UVPair pulsar$texScale();

   @Accessor("visibleFaces")
   Set<Direction> pulsar$visibleFaces();
}

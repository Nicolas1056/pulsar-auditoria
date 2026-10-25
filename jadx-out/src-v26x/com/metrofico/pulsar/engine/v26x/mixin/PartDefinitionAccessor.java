package com.metrofico.pulsar.engine.v26x.mixin;

import java.util.List;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PartDefinition.class})
public interface PartDefinitionAccessor {
   @Accessor("cubes")
   List<CubeDefinition> pulsar$cubes();

   @Accessor("partPose")
   PartPose pulsar$pose();
}

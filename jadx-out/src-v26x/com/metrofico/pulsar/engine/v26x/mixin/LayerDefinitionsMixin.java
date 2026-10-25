package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.emote.MeshSubdivider;
import java.util.Map;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LayerDefinitions.class})
abstract class LayerDefinitionsMixin {
   @Inject(
      method = {"createRoots"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$subdivide(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> cir) {
      cir.setReturnValue(MeshSubdivider.apply((Map<ModelLayerLocation, LayerDefinition>)cir.getReturnValue()));
   }
}

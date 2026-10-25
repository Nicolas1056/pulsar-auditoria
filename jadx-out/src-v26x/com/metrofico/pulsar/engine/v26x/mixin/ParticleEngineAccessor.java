package com.metrofico.pulsar.engine.v26x.mixin;

import java.util.Map;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ParticleEngine.class})
public interface ParticleEngineAccessor {
   @Accessor("particles")
   Map<ParticleRenderType, ParticleGroup<?>> pulsar$particles();
}

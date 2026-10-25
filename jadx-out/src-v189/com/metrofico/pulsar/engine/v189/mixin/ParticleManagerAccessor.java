package com.metrofico.pulsar.engine.v189.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({bec.class})
public interface ParticleManagerAccessor {
   @Accessor("c")
   List<beb>[][] pulsar$particles();
}

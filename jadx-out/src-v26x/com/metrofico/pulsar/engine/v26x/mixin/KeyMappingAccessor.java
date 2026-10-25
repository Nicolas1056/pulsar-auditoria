package com.metrofico.pulsar.engine.v26x.mixin;

import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({KeyMapping.class})
public interface KeyMappingAccessor {
   @Accessor("key")
   Key pulsar$key();
}

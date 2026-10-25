package com.metrofico.pulsar.engine.v26x.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({LocalPlayer.class})
public interface LocalPlayerAccessor {
   @Accessor("sprintTriggerTime")
   int pulsar$sprintTriggerTime();
}

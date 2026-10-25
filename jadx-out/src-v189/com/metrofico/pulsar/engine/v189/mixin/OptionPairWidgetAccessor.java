package com.metrofico.pulsar.engine.v189.mixin;

import awf.a;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({awf.class})
public interface OptionPairWidgetAccessor {
   @Accessor("u")
   List<a> pulsar$entries();
}

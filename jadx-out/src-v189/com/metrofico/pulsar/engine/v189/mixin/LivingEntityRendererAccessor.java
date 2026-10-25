package com.metrofico.pulsar.engine.v189.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({bjl.class})
public interface LivingEntityRendererAccessor {
   @Invoker("a")
   boolean pulsar$addFeature(blb<?> var1);

   @Accessor("h")
   List<blb<?>> pulsar$features();

   @Invoker("a")
   boolean pulsar$overlayBegin(pr var1, float var2, boolean var3);

   @Invoker("f")
   void pulsar$overlayEnd();
}

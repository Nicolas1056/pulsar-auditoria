package com.metrofico.pulsar.engine.v189.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({avn.class})
public interface TextRendererAccessor {
   @Accessor("i")
   float pulsar$x();

   @Accessor("j")
   float pulsar$y();

   @Accessor("m")
   float pulsar$red();

   @Accessor("n")
   float pulsar$green();

   @Accessor("o")
   float pulsar$blue();

   @Accessor("p")
   float pulsar$alpha();

   @Accessor("d")
   int[] pulsar$characterWidths();

   @Accessor("e")
   byte[] pulsar$glyphWidths();

   @Accessor("f")
   int[] pulsar$colorCodes();

   @Accessor("g")
   jy pulsar$fontTexture();

   @Invoker("a")
   jy pulsar$fontPage(int var1);
}

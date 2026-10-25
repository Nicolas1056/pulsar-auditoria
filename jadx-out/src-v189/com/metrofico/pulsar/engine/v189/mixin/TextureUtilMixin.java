package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.TextureUpload189;
import java.awt.image.BufferedImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bml.class})
public abstract class TextureUtilMixin {
   @Unique
   private static int pulsar$height;

   @Inject(
      method = {"a(Ljava/awt/image/BufferedImage;IIZZ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$uploadBegin(BufferedImage image, int x, int y, boolean blur, boolean clamp, CallbackInfo ci) {
      pulsar$height = image.getHeight();
   }

   @ModifyVariable(
      method = {"a(Ljava/awt/image/BufferedImage;IIZZ)V"},
      at = @At("STORE"),
      index = 7,
      require = 1
   )
   private static int pulsar$rowsPerChunk(int rows) {
      return TextureUpload189.rows(rows, pulsar$height);
   }
}

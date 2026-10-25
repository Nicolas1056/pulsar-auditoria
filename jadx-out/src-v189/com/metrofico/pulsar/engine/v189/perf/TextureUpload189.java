package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;

public final class TextureUpload189 {
   private TextureUpload189() {
   }

   public static int rows(int vanillaRows, int imageHeight) {
      return FrameFlags.textureUpload && vanillaRows > 0 && imageHeight > 0 ? Math.min(vanillaRows, imageHeight) : vanillaRows;
   }
}

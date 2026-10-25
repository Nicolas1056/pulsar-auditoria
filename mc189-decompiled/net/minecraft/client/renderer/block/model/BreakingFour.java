package net.minecraft.client.renderer.block.model;

import java.util.Arrays;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BreakingFour extends BakedQuad {
   private final TextureAtlasSprite d;

   public BreakingFour(BakedQuad var1, TextureAtlasSprite var2) {
      super(Arrays.copyOf(var1.a(), var1.a().length), var1.b, FaceBakery.a(var1.a()));
      this.d = var2;
      this.e();
   }

   private void e() {
      for (int var1 = 0; var1 < 4; var1++) {
         this.a(var1);
      }
   }

   private void a(int var1) {
      int var2 = 7 * var1;
      float var3 = Float.intBitsToFloat(this.a[var2]);
      float var4 = Float.intBitsToFloat(this.a[var2 + 1]);
      float var5 = Float.intBitsToFloat(this.a[var2 + 2]);
      float var6 = 0.0F;
      float var7 = 0.0F;
      switch (this.c) {
         case a:
            var6 = var3 * 16.0F;
            var7 = (1.0F - var5) * 16.0F;
            break;
         case b:
            var6 = var3 * 16.0F;
            var7 = var5 * 16.0F;
            break;
         case c:
            var6 = (1.0F - var3) * 16.0F;
            var7 = (1.0F - var4) * 16.0F;
            break;
         case d:
            var6 = var3 * 16.0F;
            var7 = (1.0F - var4) * 16.0F;
            break;
         case e:
            var6 = var5 * 16.0F;
            var7 = (1.0F - var4) * 16.0F;
            break;
         case f:
            var6 = (1.0F - var5) * 16.0F;
            var7 = (1.0F - var4) * 16.0F;
      }

      this.a[var2 + 4] = Float.floatToRawIntBits(this.d.a((double)var6));
      this.a[var2 + 4 + 1] = Float.floatToRawIntBits(this.d.b((double)var7));
   }
}

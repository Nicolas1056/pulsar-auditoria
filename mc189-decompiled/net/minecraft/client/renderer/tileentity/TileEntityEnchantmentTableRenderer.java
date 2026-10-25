package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
   private static final ResourceLocation c = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private ModelBook d = new ModelBook();

   public void a(TileEntityEnchantmentTable var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2 + 0.5F, (float)var4 + 0.75F, (float)var6 + 0.5F);
      float var10 = var1.a + var8;
      GlStateManager.b(0.0F, 0.1F + MathHelper.a(var10 * 0.1F) * 0.01F, 0.0F);
      float var11 = var1.l - var1.m;

      while (var11 >= (float) Math.PI) {
         var11 -= (float) (Math.PI * 2);
      }

      while (var11 < (float) -Math.PI) {
         var11 += (float) (Math.PI * 2);
      }

      float var12 = var1.m + var11 * var8;
      GlStateManager.b(-var12 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(80.0F, 0.0F, 0.0F, 1.0F);
      this.a(c);
      float var13 = var1.g + (var1.f - var1.g) * var8 + 0.25F;
      float var14 = var1.g + (var1.f - var1.g) * var8 + 0.75F;
      var13 = (var13 - MathHelper.b((double)var13)) * 1.6F - 0.3F;
      var14 = (var14 - MathHelper.b((double)var14)) * 1.6F - 0.3F;
      if (var13 < 0.0F) {
         var13 = 0.0F;
      }

      if (var14 < 0.0F) {
         var14 = 0.0F;
      }

      if (var13 > 1.0F) {
         var13 = 1.0F;
      }

      if (var14 > 1.0F) {
         var14 = 1.0F;
      }

      float var15 = var1.k + (var1.j - var1.k) * var8;
      GlStateManager.o();
      this.d.a(null, var10, var13, var14, var15, 0.0F, 0.0625F);
      GlStateManager.F();
   }
}

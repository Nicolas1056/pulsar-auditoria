package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest> {
   private static final ResourceLocation c = new ResourceLocation("textures/entity/chest/ender.png");
   private ModelChest d = new ModelChest();

   public void a(TileEntityEnderChest var1, double var2, double var4, double var6, float var8, int var9) {
      int var10 = 0;
      if (var1.t()) {
         var10 = var1.u();
      }

      if (var9 >= 0) {
         this.a(a[var9]);
         GlStateManager.n(5890);
         GlStateManager.E();
         GlStateManager.a(4.0F, 4.0F, 1.0F);
         GlStateManager.b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.n(5888);
      } else {
         this.a(c);
      }

      GlStateManager.E();
      GlStateManager.B();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.b((float)var2, (float)var4 + 1.0F, (float)var6 + 1.0F);
      GlStateManager.a(1.0F, -1.0F, -1.0F);
      GlStateManager.b(0.5F, 0.5F, 0.5F);
      short var11 = 0;
      if (var10 == 2) {
         var11 = 180;
      }

      if (var10 == 3) {
         var11 = 0;
      }

      if (var10 == 4) {
         var11 = 90;
      }

      if (var10 == 5) {
         var11 = -90;
      }

      GlStateManager.b((float)var11, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-0.5F, -0.5F, -0.5F);
      float var12 = var1.f + (var1.a - var1.f) * var8;
      var12 = 1.0F - var12;
      var12 = 1.0F - var12 * var12 * var12;
      this.d.a.f = -(var12 * (float) Math.PI / 2.0F);
      this.d.a();
      GlStateManager.C();
      GlStateManager.F();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      if (var9 >= 0) {
         GlStateManager.n(5890);
         GlStateManager.F();
         GlStateManager.n(5888);
      }
   }
}

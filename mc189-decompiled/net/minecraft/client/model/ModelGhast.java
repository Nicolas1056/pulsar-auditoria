package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGhast extends ModelBase {
   ModelRenderer a;
   ModelRenderer[] b = new ModelRenderer[9];

   public ModelGhast() {
      byte var1 = -16;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
      this.a.d += 24 + var1;
      Random var2 = new Random(1660L);

      for (int var3 = 0; var3 < this.b.length; var3++) {
         this.b[var3] = new ModelRenderer(this, 0, 0);
         float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float var5 = (var3 / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
         int var6 = var2.nextInt(7) + 8;
         this.b[var3].a(-1.0F, 0.0F, -1.0F, 2, var6, 2);
         this.b[var3].c = var4;
         this.b[var3].e = var5;
         this.b[var3].d = 31 + var1;
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for (int var8 = 0; var8 < this.b.length; var8++) {
         this.b[var8].f = 0.2F * MathHelper.a(var3 * 0.3F + var8) + 0.4F;
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      GlStateManager.E();
      GlStateManager.b(0.0F, 0.6F, 0.0F);
      this.a.a(var7);

      for (ModelRenderer var11 : this.b) {
         var11.a(var7);
      }

      GlStateManager.F();
   }
}

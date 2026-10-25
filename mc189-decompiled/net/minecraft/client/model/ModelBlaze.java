package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze extends ModelBase {
   private ModelRenderer[] a = new ModelRenderer[12];
   private ModelRenderer b;

   public ModelBlaze() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         this.a[var1] = new ModelRenderer(this, 0, 16);
         this.a[var1].a(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.b = new ModelRenderer(this, 0, 0);
      this.b.a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.b.a(var7);

      for (int var8 = 0; var8 < this.a.length; var8++) {
         this.a[var8].a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = var3 * (float) Math.PI * -0.1F;

      for (int var9 = 0; var9 < 4; var9++) {
         this.a[var9].d = -2.0F + MathHelper.b((var9 * 2 + var3) * 0.25F);
         this.a[var9].c = MathHelper.b(var8) * 9.0F;
         this.a[var9].e = MathHelper.a(var8) * 9.0F;
         var8++;
      }

      var8 = (float) (Math.PI / 4) + var3 * (float) Math.PI * 0.03F;

      for (int var12 = 4; var12 < 8; var12++) {
         this.a[var12].d = 2.0F + MathHelper.b((var12 * 2 + var3) * 0.25F);
         this.a[var12].c = MathHelper.b(var8) * 7.0F;
         this.a[var12].e = MathHelper.a(var8) * 7.0F;
         var8++;
      }

      var8 = 0.47123894F + var3 * (float) Math.PI * -0.05F;

      for (int var13 = 8; var13 < 12; var13++) {
         this.a[var13].d = 11.0F + MathHelper.b((var13 * 1.5F + var3) * 0.5F);
         this.a[var13].c = MathHelper.b(var8) * 5.0F;
         this.a[var13].e = MathHelper.a(var8) * 5.0F;
         var8++;
      }

      this.b.g = var4 / (180.0F / (float)Math.PI);
      this.b.f = var5 / (180.0F / (float)Math.PI);
   }
}

package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
   ModelRenderer a;
   ModelRenderer[] b = new ModelRenderer[8];

   public ModelSquid() {
      byte var1 = -16;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.a.d += 24 + var1;

      for (int var2 = 0; var2 < this.b.length; var2++) {
         this.b[var2] = new ModelRenderer(this, 48, 0);
         double var3 = var2 * Math.PI * 2.0 / this.b.length;
         float var5 = (float)Math.cos(var3) * 5.0F;
         float var6 = (float)Math.sin(var3) * 5.0F;
         this.b[var2].a(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.b[var2].c = var5;
         this.b[var2].e = var6;
         this.b[var2].d = 31 + var1;
         var3 = var2 * Math.PI * -2.0 / this.b.length + (Math.PI / 2);
         this.b[var2].g = (float)var3;
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for (ModelRenderer var11 : this.b) {
         var11.f = var3;
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);

      for (int var8 = 0; var8 < this.b.length; var8++) {
         this.b[var8].a(var7);
      }
   }
}

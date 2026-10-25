package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {
   ModelRenderer[] a = new ModelRenderer[8];
   ModelRenderer b;

   public ModelMagmaCube() {
      for (int var1 = 0; var1 < this.a.length; var1++) {
         byte var2 = 0;
         int var3 = var1;
         if (var1 == 2) {
            var2 = 24;
            var3 = 10;
         } else if (var1 == 3) {
            var2 = 24;
            var3 = 19;
         }

         this.a[var1] = new ModelRenderer(this, var2, var3);
         this.a[var1].a(-4.0F, 16 + var1, -4.0F, 8, 1, 8);
      }

      this.b = new ModelRenderer(this, 0, 16);
      this.b.a(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityMagmaCube var5 = (EntityMagmaCube)var1;
      float var6 = var5.c + (var5.b - var5.c) * var4;
      if (var6 < 0.0F) {
         var6 = 0.0F;
      }

      for (int var7 = 0; var7 < this.a.length; var7++) {
         this.a[var7].d = -(4 - var7) * var6 * 1.7F;
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.b.a(var7);

      for (int var8 = 0; var8 < this.a.length; var8++) {
         this.a[var8].a(var7);
      }
   }
}

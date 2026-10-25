package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;

public class ModelWither extends ModelBase {
   private ModelRenderer[] a;
   private ModelRenderer[] b;

   public ModelWither(float var1) {
      this.t = 64;
      this.u = 64;
      this.a = new ModelRenderer[3];
      this.a[0] = new ModelRenderer(this, 0, 16);
      this.a[0].a(-10.0F, 3.9F, -0.5F, 20, 3, 3, var1);
      this.a[1] = new ModelRenderer(this).b(this.t, this.u);
      this.a[1].a(-2.0F, 6.9F, -0.5F);
      this.a[1].a(0, 22).a(0.0F, 0.0F, 0.0F, 3, 10, 3, var1);
      this.a[1].a(24, 22).a(-4.0F, 1.5F, 0.5F, 11, 2, 2, var1);
      this.a[1].a(24, 22).a(-4.0F, 4.0F, 0.5F, 11, 2, 2, var1);
      this.a[1].a(24, 22).a(-4.0F, 6.5F, 0.5F, 11, 2, 2, var1);
      this.a[2] = new ModelRenderer(this, 12, 22);
      this.a[2].a(0.0F, 0.0F, 0.0F, 3, 6, 3, var1);
      this.b = new ModelRenderer[3];
      this.b[0] = new ModelRenderer(this, 0, 0);
      this.b[0].a(-4.0F, -4.0F, -4.0F, 8, 8, 8, var1);
      this.b[1] = new ModelRenderer(this, 32, 0);
      this.b[1].a(-4.0F, -4.0F, -4.0F, 6, 6, 6, var1);
      this.b[1].c = -8.0F;
      this.b[1].d = 4.0F;
      this.b[2] = new ModelRenderer(this, 32, 0);
      this.b[2].a(-4.0F, -4.0F, -4.0F, 6, 6, 6, var1);
      this.b[2].c = 10.0F;
      this.b[2].d = 4.0F;
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);

      for (ModelRenderer var11 : this.b) {
         var11.a(var7);
      }

      for (ModelRenderer var15 : this.a) {
         var15.a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = MathHelper.b(var3 * 0.1F);
      this.a[1].f = (0.065F + 0.05F * var8) * (float) Math.PI;
      this.a[2].a(-2.0F, 6.9F + MathHelper.b(this.a[1].f) * 10.0F, -0.5F + MathHelper.a(this.a[1].f) * 10.0F);
      this.a[2].f = (0.265F + 0.1F * var8) * (float) Math.PI;
      this.b[0].g = var4 / (180.0F / (float)Math.PI);
      this.b[0].f = var5 / (180.0F / (float)Math.PI);
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityWither var5 = (EntityWither)var1;

      for (int var6 = 1; var6 < 3; var6++) {
         this.b[var6].g = (var5.a(var6 - 1) - var1.aI) / (180.0F / (float)Math.PI);
         this.b[var6].f = var5.b(var6 - 1) / (180.0F / (float)Math.PI);
      }
   }
}

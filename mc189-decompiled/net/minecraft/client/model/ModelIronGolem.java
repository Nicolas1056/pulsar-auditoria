package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelIronGolem extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;

   public ModelIronGolem() {
      this(0.0F);
   }

   public ModelIronGolem(float var1) {
      this(var1, -7.0F);
   }

   public ModelIronGolem(float var1, float var2) {
      short var3 = 128;
      short var4 = 128;
      this.a = new ModelRenderer(this).b(var3, var4);
      this.a.a(0.0F, 0.0F + var2, -2.0F);
      this.a.a(0, 0).a(-4.0F, -12.0F, -5.5F, 8, 10, 8, var1);
      this.a.a(24, 0).a(-1.0F, -5.0F, -7.5F, 2, 4, 2, var1);
      this.b = new ModelRenderer(this).b(var3, var4);
      this.b.a(0.0F, 0.0F + var2, 0.0F);
      this.b.a(0, 40).a(-9.0F, -2.0F, -6.0F, 18, 12, 11, var1);
      this.b.a(0, 70).a(-4.5F, 10.0F, -3.0F, 9, 5, 6, var1 + 0.5F);
      this.c = new ModelRenderer(this).b(var3, var4);
      this.c.a(0.0F, -7.0F, 0.0F);
      this.c.a(60, 21).a(-13.0F, -2.5F, -3.0F, 4, 30, 6, var1);
      this.d = new ModelRenderer(this).b(var3, var4);
      this.d.a(0.0F, -7.0F, 0.0F);
      this.d.a(60, 58).a(9.0F, -2.5F, -3.0F, 4, 30, 6, var1);
      this.e = new ModelRenderer(this, 0, 22).b(var3, var4);
      this.e.a(-4.0F, 18.0F + var2, 0.0F);
      this.e.a(37, 0).a(-3.5F, -3.0F, -3.0F, 6, 16, 5, var1);
      this.f = new ModelRenderer(this, 0, 22).b(var3, var4);
      this.f.i = true;
      this.f.a(60, 0).a(5.0F, 18.0F + var2, 0.0F);
      this.f.a(-3.5F, -3.0F, -3.0F, 6, 16, 5, var1);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
      this.b.a(var7);
      this.e.a(var7);
      this.f.a(var7);
      this.c.a(var7);
      this.d.a(var7);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.a.f = var5 / (180.0F / (float)Math.PI);
      this.e.f = -1.5F * this.a(var1, 13.0F) * var2;
      this.f.f = 1.5F * this.a(var1, 13.0F) * var2;
      this.e.g = 0.0F;
      this.f.g = 0.0F;
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityIronGolem var5 = (EntityIronGolem)var1;
      int var6 = var5.cl();
      if (var6 > 0) {
         this.c.f = -2.0F + 1.5F * this.a(var6 - var4, 10.0F);
         this.d.f = -2.0F + 1.5F * this.a(var6 - var4, 10.0F);
      } else {
         int var7 = var5.cm();
         if (var7 > 0) {
            this.c.f = -0.8F + 0.025F * this.a(var7, 70.0F);
            this.d.f = 0.0F;
         } else {
            this.c.f = (-0.2F + 1.5F * this.a(var2, 13.0F)) * var3;
            this.d.f = (-0.2F - 1.5F * this.a(var2, 13.0F)) * var3;
         }
      }
   }

   private float a(float var1, float var2) {
      return (Math.abs(var1 % var2 - var2 * 0.5F) - var2 * 0.25F) / (var2 * 0.25F);
   }
}

package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {
   public boolean a;
   public boolean b;

   public ModelEnderman(float var1) {
      super(0.0F, -14.0F, 64, 32);
      float var2 = -14.0F;
      this.f = new ModelRenderer(this, 0, 16);
      this.f.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1 - 0.5F);
      this.f.a(0.0F, 0.0F + var2, 0.0F);
      this.g = new ModelRenderer(this, 32, 16);
      this.g.a(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1);
      this.g.a(0.0F, 0.0F + var2, 0.0F);
      this.h = new ModelRenderer(this, 56, 0);
      this.h.a(-1.0F, -2.0F, -1.0F, 2, 30, 2, var1);
      this.h.a(-3.0F, 2.0F + var2, 0.0F);
      this.i = new ModelRenderer(this, 56, 0);
      this.i.i = true;
      this.i.a(-1.0F, -2.0F, -1.0F, 2, 30, 2, var1);
      this.i.a(5.0F, 2.0F + var2, 0.0F);
      this.j = new ModelRenderer(this, 56, 0);
      this.j.a(-1.0F, 0.0F, -1.0F, 2, 30, 2, var1);
      this.j.a(-2.0F, 12.0F + var2, 0.0F);
      this.k = new ModelRenderer(this, 56, 0);
      this.k.i = true;
      this.k.a(-1.0F, 0.0F, -1.0F, 2, 30, 2, var1);
      this.k.a(2.0F, 12.0F + var2, 0.0F);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.e.j = true;
      float var8 = -14.0F;
      this.g.f = 0.0F;
      this.g.d = var8;
      this.g.e = -0.0F;
      this.j.f -= 0.0F;
      this.k.f -= 0.0F;
      this.h.f = (float)(this.h.f * 0.5);
      this.i.f = (float)(this.i.f * 0.5);
      this.j.f = (float)(this.j.f * 0.5);
      this.k.f = (float)(this.k.f * 0.5);
      float var9 = 0.4F;
      if (this.h.f > var9) {
         this.h.f = var9;
      }

      if (this.i.f > var9) {
         this.i.f = var9;
      }

      if (this.h.f < -var9) {
         this.h.f = -var9;
      }

      if (this.i.f < -var9) {
         this.i.f = -var9;
      }

      if (this.j.f > var9) {
         this.j.f = var9;
      }

      if (this.k.f > var9) {
         this.k.f = var9;
      }

      if (this.j.f < -var9) {
         this.j.f = -var9;
      }

      if (this.k.f < -var9) {
         this.k.f = -var9;
      }

      if (this.a) {
         this.h.f = -0.5F;
         this.i.f = -0.5F;
         this.h.h = 0.05F;
         this.i.h = -0.05F;
      }

      this.h.e = 0.0F;
      this.i.e = 0.0F;
      this.j.e = 0.0F;
      this.k.e = 0.0F;
      this.j.d = 9.0F + var8;
      this.k.d = 9.0F + var8;
      this.e.e = -0.0F;
      this.e.d = var8 + 1.0F;
      this.f.c = this.e.c;
      this.f.d = this.e.d;
      this.f.e = this.e.e;
      this.f.f = this.e.f;
      this.f.g = this.e.g;
      this.f.h = this.e.h;
      if (this.b) {
         float var10 = 1.0F;
         this.e.d -= var10 * 5.0F;
      }
   }
}

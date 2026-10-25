package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBiped extends ModelBase {
   public ModelRenderer e;
   public ModelRenderer f;
   public ModelRenderer g;
   public ModelRenderer h;
   public ModelRenderer i;
   public ModelRenderer j;
   public ModelRenderer k;
   public int l;
   public int m;
   public boolean n;
   public boolean o;

   public ModelBiped() {
      this(0.0F);
   }

   public ModelBiped(float var1) {
      this(var1, 0.0F, 64, 32);
   }

   public ModelBiped(float var1, float var2, int var3, int var4) {
      this.t = var3;
      this.u = var4;
      this.e = new ModelRenderer(this, 0, 0);
      this.e.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1);
      this.e.a(0.0F, 0.0F + var2, 0.0F);
      this.f = new ModelRenderer(this, 32, 0);
      this.f.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1 + 0.5F);
      this.f.a(0.0F, 0.0F + var2, 0.0F);
      this.g = new ModelRenderer(this, 16, 16);
      this.g.a(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1);
      this.g.a(0.0F, 0.0F + var2, 0.0F);
      this.h = new ModelRenderer(this, 40, 16);
      this.h.a(-3.0F, -2.0F, -2.0F, 4, 12, 4, var1);
      this.h.a(-5.0F, 2.0F + var2, 0.0F);
      this.i = new ModelRenderer(this, 40, 16);
      this.i.i = true;
      this.i.a(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1);
      this.i.a(5.0F, 2.0F + var2, 0.0F);
      this.j = new ModelRenderer(this, 0, 16);
      this.j.a(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.j.a(-1.9F, 12.0F + var2, 0.0F);
      this.k = new ModelRenderer(this, 0, 16);
      this.k.i = true;
      this.k.a(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.k.a(1.9F, 12.0F + var2, 0.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      GlStateManager.E();
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.a(1.5F / var8, 1.5F / var8, 1.5F / var8);
         GlStateManager.b(0.0F, 16.0F * var7, 0.0F);
         this.e.a(var7);
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.g.a(var7);
         this.h.a(var7);
         this.i.a(var7);
         this.j.a(var7);
         this.k.a(var7);
         this.f.a(var7);
      } else {
         if (var1.av()) {
            GlStateManager.b(0.0F, 0.2F, 0.0F);
         }

         this.e.a(var7);
         this.g.a(var7);
         this.h.a(var7);
         this.i.a(var7);
         this.j.a(var7);
         this.k.a(var7);
         this.f.a(var7);
      }

      GlStateManager.F();
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.e.g = var4 / (180.0F / (float)Math.PI);
      this.e.f = var5 / (180.0F / (float)Math.PI);
      this.h.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 2.0F * var2 * 0.5F;
      this.i.f = MathHelper.b(var1 * 0.6662F) * 2.0F * var2 * 0.5F;
      this.h.h = 0.0F;
      this.i.h = 0.0F;
      this.j.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
      this.k.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.j.g = 0.0F;
      this.k.g = 0.0F;
      if (this.q) {
         this.h.f += (float) (-Math.PI / 5);
         this.i.f += (float) (-Math.PI / 5);
         this.j.f = (float) (-Math.PI * 2.0 / 5.0);
         this.k.f = (float) (-Math.PI * 2.0 / 5.0);
         this.j.g = (float) (Math.PI / 10);
         this.k.g = (float) (-Math.PI / 10);
      }

      if (this.l != 0) {
         this.i.f = this.i.f * 0.5F - (float) (Math.PI / 10) * this.l;
      }

      this.h.g = 0.0F;
      this.h.h = 0.0F;
      switch (this.m) {
         case 0:
         case 2:
         default:
            break;
         case 1:
            this.h.f = this.h.f * 0.5F - (float) (Math.PI / 10) * this.m;
            break;
         case 3:
            this.h.f = this.h.f * 0.5F - (float) (Math.PI / 10) * this.m;
            this.h.g = (float) (-Math.PI / 6);
      }

      this.i.g = 0.0F;
      if (this.p > -9990.0F) {
         float var8 = this.p;
         this.g.g = MathHelper.a(MathHelper.c(var8) * (float) Math.PI * 2.0F) * 0.2F;
         this.h.e = MathHelper.a(this.g.g) * 5.0F;
         this.h.c = -MathHelper.b(this.g.g) * 5.0F;
         this.i.e = -MathHelper.a(this.g.g) * 5.0F;
         this.i.c = MathHelper.b(this.g.g) * 5.0F;
         this.h.g = this.h.g + this.g.g;
         this.i.g = this.i.g + this.g.g;
         this.i.f = this.i.f + this.g.g;
         var8 = 1.0F - this.p;
         var8 *= var8;
         var8 *= var8;
         var8 = 1.0F - var8;
         float var9 = MathHelper.a(var8 * (float) Math.PI);
         float var10 = MathHelper.a(this.p * (float) Math.PI) * -(this.e.f - 0.7F) * 0.75F;
         this.h.f = (float)(this.h.f - (var9 * 1.2 + var10));
         this.h.g = this.h.g + this.g.g * 2.0F;
         this.h.h = this.h.h + MathHelper.a(this.p * (float) Math.PI) * -0.4F;
      }

      if (this.n) {
         this.g.f = 0.5F;
         this.h.f += 0.4F;
         this.i.f += 0.4F;
         this.j.e = 4.0F;
         this.k.e = 4.0F;
         this.j.d = 9.0F;
         this.k.d = 9.0F;
         this.e.d = 1.0F;
      } else {
         this.g.f = 0.0F;
         this.j.e = 0.1F;
         this.k.e = 0.1F;
         this.j.d = 12.0F;
         this.k.d = 12.0F;
         this.e.d = 0.0F;
      }

      this.h.h = this.h.h + (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
      this.i.h = this.i.h - (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
      this.h.f = this.h.f + MathHelper.a(var3 * 0.067F) * 0.05F;
      this.i.f = this.i.f - MathHelper.a(var3 * 0.067F) * 0.05F;
      if (this.o) {
         float var15 = 0.0F;
         float var16 = 0.0F;
         this.h.h = 0.0F;
         this.i.h = 0.0F;
         this.h.g = -(0.1F - var15 * 0.6F) + this.e.g;
         this.i.g = 0.1F - var15 * 0.6F + this.e.g + 0.4F;
         this.h.f = (float) (-Math.PI / 2) + this.e.f;
         this.i.f = (float) (-Math.PI / 2) + this.e.f;
         this.h.f -= var15 * 1.2F - var16 * 0.4F;
         this.i.f -= var15 * 1.2F - var16 * 0.4F;
         this.h.h = this.h.h + (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
         this.i.h = this.i.h - (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
         this.h.f = this.h.f + MathHelper.a(var3 * 0.067F) * 0.05F;
         this.i.f = this.i.f - MathHelper.a(var3 * 0.067F) * 0.05F;
      }

      a(this.e, this.f);
   }

   @Override
   public void a(ModelBase var1) {
      super.a(var1);
      if (var1 instanceof ModelBiped) {
         ModelBiped var2 = (ModelBiped)var1;
         this.l = var2.l;
         this.m = var2.m;
         this.n = var2.n;
         this.o = var2.o;
      }
   }

   public void a(boolean var1) {
      this.e.j = var1;
      this.f.j = var1;
      this.g.j = var1;
      this.h.j = var1;
      this.i.j = var1;
      this.j.j = var1;
      this.k.j = var1;
   }

   public void a(float var1) {
      this.h.c(var1);
   }
}

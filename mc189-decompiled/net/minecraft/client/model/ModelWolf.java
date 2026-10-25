package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.MathHelper;

public class ModelWolf extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   ModelRenderer g;
   ModelRenderer h;

   public ModelWolf() {
      float var1 = 0.0F;
      float var2 = 13.5F;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-3.0F, -3.0F, -2.0F, 6, 6, 4, var1);
      this.a.a(-1.0F, var2, -7.0F);
      this.b = new ModelRenderer(this, 18, 14);
      this.b.a(-4.0F, -2.0F, -3.0F, 6, 9, 6, var1);
      this.b.a(0.0F, 14.0F, 2.0F);
      this.h = new ModelRenderer(this, 21, 0);
      this.h.a(-4.0F, -3.0F, -3.0F, 8, 6, 7, var1);
      this.h.a(-1.0F, 14.0F, 2.0F);
      this.c = new ModelRenderer(this, 0, 18);
      this.c.a(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
      this.c.a(-2.5F, 16.0F, 7.0F);
      this.d = new ModelRenderer(this, 0, 18);
      this.d.a(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
      this.d.a(0.5F, 16.0F, 7.0F);
      this.e = new ModelRenderer(this, 0, 18);
      this.e.a(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
      this.e.a(-2.5F, 16.0F, -4.0F);
      this.f = new ModelRenderer(this, 0, 18);
      this.f.a(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
      this.f.a(0.5F, 16.0F, -4.0F);
      this.g = new ModelRenderer(this, 9, 18);
      this.g.a(-1.0F, 0.0F, -1.0F, 2, 8, 2, var1);
      this.g.a(-1.0F, 12.0F, 8.0F);
      this.a.a(16, 14).a(-3.0F, -5.0F, 0.0F, 2, 2, 1, var1);
      this.a.a(16, 14).a(1.0F, -5.0F, 0.0F, 2, 2, 1, var1);
      this.a.a(0, 10).a(-1.5F, 0.0F, -5.0F, 3, 3, 4, var1);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.a(var2, var3, var4, var5, var6, var7, var1);
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.E();
         GlStateManager.b(0.0F, 5.0F * var7, 2.0F * var7);
         this.a.b(var7);
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
         this.g.b(var7);
         this.h.a(var7);
         GlStateManager.F();
      } else {
         this.a.b(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
         this.g.b(var7);
         this.h.a(var7);
      }
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      EntityWolf var5 = (EntityWolf)var1;
      if (var5.cv()) {
         this.g.g = 0.0F;
      } else {
         this.g.g = MathHelper.b(var2 * 0.6662F) * 1.4F * var3;
      }

      if (var5.cn()) {
         this.h.a(-1.0F, 16.0F, -3.0F);
         this.h.f = (float) (Math.PI * 2.0 / 5.0);
         this.h.g = 0.0F;
         this.b.a(0.0F, 18.0F, 0.0F);
         this.b.f = (float) (Math.PI / 4);
         this.g.a(-1.0F, 21.0F, 6.0F);
         this.c.a(-2.5F, 22.0F, 2.0F);
         this.c.f = (float) (Math.PI * 3.0 / 2.0);
         this.d.a(0.5F, 22.0F, 2.0F);
         this.d.f = (float) (Math.PI * 3.0 / 2.0);
         this.e.f = 5.811947F;
         this.e.a(-2.49F, 17.0F, -4.0F);
         this.f.f = 5.811947F;
         this.f.a(0.51F, 17.0F, -4.0F);
      } else {
         this.b.a(0.0F, 14.0F, 2.0F);
         this.b.f = (float) (Math.PI / 2);
         this.h.a(-1.0F, 14.0F, -3.0F);
         this.h.f = this.b.f;
         this.g.a(-1.0F, 12.0F, 8.0F);
         this.c.a(-2.5F, 16.0F, 7.0F);
         this.d.a(0.5F, 16.0F, 7.0F);
         this.e.a(-2.5F, 16.0F, -4.0F);
         this.f.a(0.5F, 16.0F, -4.0F);
         this.c.f = MathHelper.b(var2 * 0.6662F) * 1.4F * var3;
         this.d.f = MathHelper.b(var2 * 0.6662F + (float) Math.PI) * 1.4F * var3;
         this.e.f = MathHelper.b(var2 * 0.6662F + (float) Math.PI) * 1.4F * var3;
         this.f.f = MathHelper.b(var2 * 0.6662F) * 1.4F * var3;
      }

      this.a.h = var5.q(var4) + var5.i(var4, 0.0F);
      this.h.h = var5.i(var4, -0.08F);
      this.b.h = var5.i(var4, -0.16F);
      this.g.h = var5.i(var4, -0.2F);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.a.f = var5 / (180.0F / (float)Math.PI);
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.g.f = var3;
   }
}

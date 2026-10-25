package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelChicken extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   public ModelRenderer g;
   public ModelRenderer h;

   public ModelChicken() {
      byte var1 = 16;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.a.a(0.0F, -1 + var1, -4.0F);
      this.g = new ModelRenderer(this, 14, 0);
      this.g.a(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.g.a(0.0F, -1 + var1, -4.0F);
      this.h = new ModelRenderer(this, 14, 4);
      this.h.a(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.h.a(0.0F, -1 + var1, -4.0F);
      this.b = new ModelRenderer(this, 0, 9);
      this.b.a(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.b.a(0.0F, var1, 0.0F);
      this.c = new ModelRenderer(this, 26, 0);
      this.c.a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.c.a(-2.0F, 3 + var1, 1.0F);
      this.d = new ModelRenderer(this, 26, 0);
      this.d.a(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.d.a(1.0F, 3 + var1, 1.0F);
      this.e = new ModelRenderer(this, 24, 13);
      this.e.a(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.e.a(-4.0F, -3 + var1, 0.0F);
      this.f = new ModelRenderer(this, 24, 13);
      this.f.a(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.f.a(4.0F, -3 + var1, 0.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.E();
         GlStateManager.b(0.0F, 5.0F * var7, 2.0F * var7);
         this.a.a(var7);
         this.g.a(var7);
         this.h.a(var7);
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
         GlStateManager.F();
      } else {
         this.a.a(var7);
         this.g.a(var7);
         this.h.a(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.a.f = var5 / (180.0F / (float)Math.PI);
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.g.f = this.a.f;
      this.g.g = this.a.g;
      this.h.f = this.a.f;
      this.h.g = this.a.g;
      this.b.f = (float) (Math.PI / 2);
      this.c.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
      this.d.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.e.h = var3;
      this.f.h = -var3;
   }
}

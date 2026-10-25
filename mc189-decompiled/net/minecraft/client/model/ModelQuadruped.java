package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelQuadruped extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   protected float g = 8.0F;
   protected float h = 4.0F;

   public ModelQuadruped(int var1, float var2) {
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-4.0F, -4.0F, -8.0F, 8, 8, 8, var2);
      this.a.a(0.0F, 18 - var1, -6.0F);
      this.b = new ModelRenderer(this, 28, 8);
      this.b.a(-5.0F, -10.0F, -7.0F, 10, 16, 8, var2);
      this.b.a(0.0F, 17 - var1, 2.0F);
      this.c = new ModelRenderer(this, 0, 16);
      this.c.a(-2.0F, 0.0F, -2.0F, 4, var1, 4, var2);
      this.c.a(-3.0F, 24 - var1, 7.0F);
      this.d = new ModelRenderer(this, 0, 16);
      this.d.a(-2.0F, 0.0F, -2.0F, 4, var1, 4, var2);
      this.d.a(3.0F, 24 - var1, 7.0F);
      this.e = new ModelRenderer(this, 0, 16);
      this.e.a(-2.0F, 0.0F, -2.0F, 4, var1, 4, var2);
      this.e.a(-3.0F, 24 - var1, -5.0F);
      this.f = new ModelRenderer(this, 0, 16);
      this.f.a(-2.0F, 0.0F, -2.0F, 4, var1, 4, var2);
      this.f.a(3.0F, 24 - var1, -5.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.E();
         GlStateManager.b(0.0F, this.g * var7, this.h * var7);
         this.a.a(var7);
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
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = 180.0F / (float)Math.PI;
      this.a.f = var5 / (180.0F / (float)Math.PI);
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.b.f = (float) (Math.PI / 2);
      this.c.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
      this.d.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.e.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.f.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
   }
}

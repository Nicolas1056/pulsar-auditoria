package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelPlayer extends ModelBiped {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer v;
   private ModelRenderer w;
   private ModelRenderer x;
   private boolean y;

   public ModelPlayer(float var1, boolean var2) {
      super(var1, 0.0F, 64, 64);
      this.y = var2;
      this.x = new ModelRenderer(this, 24, 0);
      this.x.a(-3.0F, -6.0F, -1.0F, 6, 6, 1, var1);
      this.w = new ModelRenderer(this, 0, 0);
      this.w.b(64, 32);
      this.w.a(-5.0F, 0.0F, -1.0F, 10, 16, 1, var1);
      if (var2) {
         this.i = new ModelRenderer(this, 32, 48);
         this.i.a(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1);
         this.i.a(5.0F, 2.5F, 0.0F);
         this.h = new ModelRenderer(this, 40, 16);
         this.h.a(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1);
         this.h.a(-5.0F, 2.5F, 0.0F);
         this.a = new ModelRenderer(this, 48, 48);
         this.a.a(-1.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
         this.a.a(5.0F, 2.5F, 0.0F);
         this.b = new ModelRenderer(this, 40, 32);
         this.b.a(-2.0F, -2.0F, -2.0F, 3, 12, 4, var1 + 0.25F);
         this.b.a(-5.0F, 2.5F, 10.0F);
      } else {
         this.i = new ModelRenderer(this, 32, 48);
         this.i.a(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1);
         this.i.a(5.0F, 2.0F, 0.0F);
         this.a = new ModelRenderer(this, 48, 48);
         this.a.a(-1.0F, -2.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
         this.a.a(5.0F, 2.0F, 0.0F);
         this.b = new ModelRenderer(this, 40, 32);
         this.b.a(-3.0F, -2.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
         this.b.a(-5.0F, 2.0F, 10.0F);
      }

      this.k = new ModelRenderer(this, 16, 48);
      this.k.a(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1);
      this.k.a(1.9F, 12.0F, 0.0F);
      this.c = new ModelRenderer(this, 0, 48);
      this.c.a(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.c.a(1.9F, 12.0F, 0.0F);
      this.d = new ModelRenderer(this, 0, 32);
      this.d.a(-2.0F, 0.0F, -2.0F, 4, 12, 4, var1 + 0.25F);
      this.d.a(-1.9F, 12.0F, 0.0F);
      this.v = new ModelRenderer(this, 16, 32);
      this.v.a(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1 + 0.25F);
      this.v.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      GlStateManager.E();
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.c.a(var7);
         this.d.a(var7);
         this.a.a(var7);
         this.b.a(var7);
         this.v.a(var7);
      } else {
         if (var1.av()) {
            GlStateManager.b(0.0F, 0.2F, 0.0F);
         }

         this.c.a(var7);
         this.d.a(var7);
         this.a.a(var7);
         this.b.a(var7);
         this.v.a(var7);
      }

      GlStateManager.F();
   }

   public void b(float var1) {
      a(this.e, this.x);
      this.x.c = 0.0F;
      this.x.d = 0.0F;
      this.x.a(var1);
   }

   public void c(float var1) {
      this.w.a(var1);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      a(this.k, this.c);
      a(this.j, this.d);
      a(this.i, this.a);
      a(this.h, this.b);
      a(this.g, this.v);
      if (var7.av()) {
         this.w.d = 2.0F;
      } else {
         this.w.d = 0.0F;
      }
   }

   public void a() {
      this.h.a(0.0625F);
      this.b.a(0.0625F);
   }

   public void b() {
      this.i.a(0.0625F);
      this.a.a(0.0625F);
   }

   @Override
   public void a(boolean var1) {
      super.a(var1);
      this.a.j = var1;
      this.b.j = var1;
      this.c.j = var1;
      this.d.j = var1;
      this.v.j = var1;
      this.w.j = var1;
      this.x.j = var1;
   }

   @Override
   public void a(float var1) {
      if (this.y) {
         this.h.c++;
         this.h.c(var1);
         this.h.c--;
      } else {
         this.h.c(var1);
      }
   }
}

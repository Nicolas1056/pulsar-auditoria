package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStand extends ModelArmorStandArmor {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;

   public ModelArmorStand() {
      this(0.0F);
   }

   public ModelArmorStand(float var1) {
      super(var1, 64, 64);
      this.e = new ModelRenderer(this, 0, 0);
      this.e.a(-1.0F, -7.0F, -1.0F, 2, 7, 2, var1);
      this.e.a(0.0F, 0.0F, 0.0F);
      this.g = new ModelRenderer(this, 0, 26);
      this.g.a(-6.0F, 0.0F, -1.5F, 12, 3, 3, var1);
      this.g.a(0.0F, 0.0F, 0.0F);
      this.h = new ModelRenderer(this, 24, 0);
      this.h.a(-2.0F, -2.0F, -1.0F, 2, 12, 2, var1);
      this.h.a(-5.0F, 2.0F, 0.0F);
      this.i = new ModelRenderer(this, 32, 16);
      this.i.i = true;
      this.i.a(0.0F, -2.0F, -1.0F, 2, 12, 2, var1);
      this.i.a(5.0F, 2.0F, 0.0F);
      this.j = new ModelRenderer(this, 8, 0);
      this.j.a(-1.0F, 0.0F, -1.0F, 2, 11, 2, var1);
      this.j.a(-1.9F, 12.0F, 0.0F);
      this.k = new ModelRenderer(this, 40, 16);
      this.k.i = true;
      this.k.a(-1.0F, 0.0F, -1.0F, 2, 11, 2, var1);
      this.k.a(1.9F, 12.0F, 0.0F);
      this.a = new ModelRenderer(this, 16, 0);
      this.a.a(-3.0F, 3.0F, -1.0F, 2, 7, 2, var1);
      this.a.a(0.0F, 0.0F, 0.0F);
      this.a.j = true;
      this.b = new ModelRenderer(this, 48, 16);
      this.b.a(1.0F, 3.0F, -1.0F, 2, 7, 2, var1);
      this.b.a(0.0F, 0.0F, 0.0F);
      this.c = new ModelRenderer(this, 0, 48);
      this.c.a(-4.0F, 10.0F, -1.0F, 8, 2, 2, var1);
      this.c.a(0.0F, 0.0F, 0.0F);
      this.d = new ModelRenderer(this, 0, 32);
      this.d.a(-6.0F, 11.0F, -6.0F, 12, 1, 12, var1);
      this.d.a(0.0F, 12.0F, 0.0F);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      if (var7 instanceof EntityArmorStand) {
         EntityArmorStand var8 = (EntityArmorStand)var7;
         this.i.j = var8.q();
         this.h.j = var8.q();
         this.d.j = !var8.r();
         this.k.a(1.9F, 12.0F, 0.0F);
         this.j.a(-1.9F, 12.0F, 0.0F);
         this.a.f = (float) (Math.PI / 180.0) * var8.u().b();
         this.a.g = (float) (Math.PI / 180.0) * var8.u().c();
         this.a.h = (float) (Math.PI / 180.0) * var8.u().d();
         this.b.f = (float) (Math.PI / 180.0) * var8.u().b();
         this.b.g = (float) (Math.PI / 180.0) * var8.u().c();
         this.b.h = (float) (Math.PI / 180.0) * var8.u().d();
         this.c.f = (float) (Math.PI / 180.0) * var8.u().b();
         this.c.g = (float) (Math.PI / 180.0) * var8.u().c();
         this.c.h = (float) (Math.PI / 180.0) * var8.u().d();
         float var9 = (var8.x().b() + var8.y().b()) / 2.0F;
         float var10 = (var8.x().c() + var8.y().c()) / 2.0F;
         float var11 = (var8.x().d() + var8.y().d()) / 2.0F;
         this.d.f = 0.0F;
         this.d.g = (float) (Math.PI / 180.0) * -var7.y;
         this.d.h = 0.0F;
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      GlStateManager.E();
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.a.a(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
      } else {
         if (var1.av()) {
            GlStateManager.b(0.0F, 0.2F, 0.0F);
         }

         this.a.a(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
      }

      GlStateManager.F();
   }

   @Override
   public void a(float var1) {
      boolean var2 = this.h.j;
      this.h.j = true;
      super.a(var1);
      this.h.j = var2;
   }
}

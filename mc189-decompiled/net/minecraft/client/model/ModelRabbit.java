package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.MathHelper;

public class ModelRabbit extends ModelBase {
   ModelRenderer a;
   ModelRenderer b;
   ModelRenderer c;
   ModelRenderer d;
   ModelRenderer e;
   ModelRenderer f;
   ModelRenderer g;
   ModelRenderer h;
   ModelRenderer i;
   ModelRenderer j;
   ModelRenderer k;
   ModelRenderer l;
   private float m = 0.0F;
   private float n = 0.0F;

   public ModelRabbit() {
      this.a("head.main", 0, 0);
      this.a("head.nose", 0, 24);
      this.a("head.ear1", 0, 10);
      this.a("head.ear2", 6, 10);
      this.a = new ModelRenderer(this, 26, 24);
      this.a.a(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.a.a(3.0F, 17.5F, 3.7F);
      this.a.i = true;
      this.a(this.a, 0.0F, 0.0F, 0.0F);
      this.b = new ModelRenderer(this, 8, 24);
      this.b.a(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.b.a(-3.0F, 17.5F, 3.7F);
      this.b.i = true;
      this.a(this.b, 0.0F, 0.0F, 0.0F);
      this.c = new ModelRenderer(this, 30, 15);
      this.c.a(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.c.a(3.0F, 17.5F, 3.7F);
      this.c.i = true;
      this.a(this.c, (float) (-Math.PI / 9), 0.0F, 0.0F);
      this.d = new ModelRenderer(this, 16, 15);
      this.d.a(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.d.a(-3.0F, 17.5F, 3.7F);
      this.d.i = true;
      this.a(this.d, (float) (-Math.PI / 9), 0.0F, 0.0F);
      this.e = new ModelRenderer(this, 0, 0);
      this.e.a(-3.0F, -2.0F, -10.0F, 6, 5, 10);
      this.e.a(0.0F, 19.0F, 8.0F);
      this.e.i = true;
      this.a(this.e, (float) (-Math.PI / 9), 0.0F, 0.0F);
      this.f = new ModelRenderer(this, 8, 15);
      this.f.a(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.f.a(3.0F, 17.0F, -1.0F);
      this.f.i = true;
      this.a(this.f, (float) (-Math.PI / 18), 0.0F, 0.0F);
      this.g = new ModelRenderer(this, 0, 15);
      this.g.a(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.g.a(-3.0F, 17.0F, -1.0F);
      this.g.i = true;
      this.a(this.g, (float) (-Math.PI / 18), 0.0F, 0.0F);
      this.h = new ModelRenderer(this, 32, 0);
      this.h.a(-2.5F, -4.0F, -5.0F, 5, 4, 5);
      this.h.a(0.0F, 16.0F, -1.0F);
      this.h.i = true;
      this.a(this.h, 0.0F, 0.0F, 0.0F);
      this.i = new ModelRenderer(this, 52, 0);
      this.i.a(-2.5F, -9.0F, -1.0F, 2, 5, 1);
      this.i.a(0.0F, 16.0F, -1.0F);
      this.i.i = true;
      this.a(this.i, 0.0F, (float) (-Math.PI / 12), 0.0F);
      this.j = new ModelRenderer(this, 58, 0);
      this.j.a(0.5F, -9.0F, -1.0F, 2, 5, 1);
      this.j.a(0.0F, 16.0F, -1.0F);
      this.j.i = true;
      this.a(this.j, 0.0F, (float) (Math.PI / 12), 0.0F);
      this.k = new ModelRenderer(this, 52, 6);
      this.k.a(-1.5F, -1.5F, 0.0F, 3, 3, 2);
      this.k.a(0.0F, 20.0F, 7.0F);
      this.k.i = true;
      this.a(this.k, -0.3490659F, 0.0F, 0.0F);
      this.l = new ModelRenderer(this, 32, 9);
      this.l.a(-0.5F, -2.5F, -5.5F, 1, 1, 1);
      this.l.a(0.0F, 16.0F, -1.0F);
      this.l.i = true;
      this.a(this.l, 0.0F, 0.0F, 0.0F);
   }

   private void a(ModelRenderer var1, float var2, float var3, float var4) {
      var1.f = var2;
      var1.g = var3;
      var1.h = var4;
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      if (this.r) {
         float var8 = 2.0F;
         GlStateManager.E();
         GlStateManager.b(0.0F, 5.0F * var7, 2.0F * var7);
         this.h.a(var7);
         this.j.a(var7);
         this.i.a(var7);
         this.l.a(var7);
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.a(1.0F / var8, 1.0F / var8, 1.0F / var8);
         GlStateManager.b(0.0F, 24.0F * var7, 0.0F);
         this.a.a(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
         this.g.a(var7);
         this.k.a(var7);
         GlStateManager.F();
      } else {
         this.a.a(var7);
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
         this.e.a(var7);
         this.f.a(var7);
         this.g.a(var7);
         this.h.a(var7);
         this.i.a(var7);
         this.j.a(var7);
         this.k.a(var7);
         this.l.a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = var3 - var7.W;
      EntityRabbit var9 = (EntityRabbit)var7;
      this.l.f = this.h.f = this.i.f = this.j.f = var5 * (float) (Math.PI / 180.0);
      this.l.g = this.h.g = var4 * (float) (Math.PI / 180.0);
      this.i.g = this.l.g - (float) (Math.PI / 12);
      this.j.g = this.l.g + (float) (Math.PI / 12);
      this.m = MathHelper.a(var9.p(var8) * (float) Math.PI);
      this.c.f = this.d.f = (this.m * 50.0F - 21.0F) * (float) (Math.PI / 180.0);
      this.a.f = this.b.f = this.m * 50.0F * (float) (Math.PI / 180.0);
      this.f.f = this.g.f = (this.m * -40.0F - 11.0F) * (float) (Math.PI / 180.0);
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
   }
}

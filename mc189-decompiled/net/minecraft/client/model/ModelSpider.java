package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSpider extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   public ModelRenderer g;
   public ModelRenderer h;
   public ModelRenderer i;
   public ModelRenderer j;
   public ModelRenderer k;

   public ModelSpider() {
      float var1 = 0.0F;
      byte var2 = 15;
      this.a = new ModelRenderer(this, 32, 4);
      this.a.a(-4.0F, -4.0F, -8.0F, 8, 8, 8, var1);
      this.a.a(0.0F, var2, -3.0F);
      this.b = new ModelRenderer(this, 0, 0);
      this.b.a(-3.0F, -3.0F, -3.0F, 6, 6, 6, var1);
      this.b.a(0.0F, var2, 0.0F);
      this.c = new ModelRenderer(this, 0, 12);
      this.c.a(-5.0F, -4.0F, -6.0F, 10, 8, 12, var1);
      this.c.a(0.0F, var2, 9.0F);
      this.d = new ModelRenderer(this, 18, 0);
      this.d.a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.d.a(-4.0F, var2, 2.0F);
      this.e = new ModelRenderer(this, 18, 0);
      this.e.a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.e.a(4.0F, var2, 2.0F);
      this.f = new ModelRenderer(this, 18, 0);
      this.f.a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.f.a(-4.0F, var2, 1.0F);
      this.g = new ModelRenderer(this, 18, 0);
      this.g.a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.g.a(4.0F, var2, 1.0F);
      this.h = new ModelRenderer(this, 18, 0);
      this.h.a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.h.a(-4.0F, var2, 0.0F);
      this.i = new ModelRenderer(this, 18, 0);
      this.i.a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.i.a(4.0F, var2, 0.0F);
      this.j = new ModelRenderer(this, 18, 0);
      this.j.a(-15.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.j.a(-4.0F, var2, -1.0F);
      this.k = new ModelRenderer(this, 18, 0);
      this.k.a(-1.0F, -1.0F, -1.0F, 16, 2, 2, var1);
      this.k.a(4.0F, var2, -1.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
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
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.a.f = var5 / (180.0F / (float)Math.PI);
      float var8 = (float) (Math.PI / 4);
      this.d.h = -var8;
      this.e.h = var8;
      this.f.h = -var8 * 0.74F;
      this.g.h = var8 * 0.74F;
      this.h.h = -var8 * 0.74F;
      this.i.h = var8 * 0.74F;
      this.j.h = -var8;
      this.k.h = var8;
      float var9 = -0.0F;
      float var10 = (float) (Math.PI / 8);
      this.d.g = var10 * 2.0F + var9;
      this.e.g = -var10 * 2.0F - var9;
      this.f.g = var10 * 1.0F + var9;
      this.g.g = -var10 * 1.0F - var9;
      this.h.g = -var10 * 1.0F + var9;
      this.i.g = var10 * 1.0F - var9;
      this.j.g = -var10 * 2.0F + var9;
      this.k.g = var10 * 2.0F - var9;
      float var11 = -(MathHelper.b(var1 * 0.6662F * 2.0F + 0.0F) * 0.4F) * var2;
      float var12 = -(MathHelper.b(var1 * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * var2;
      float var13 = -(MathHelper.b(var1 * 0.6662F * 2.0F + (float) (Math.PI / 2)) * 0.4F) * var2;
      float var14 = -(MathHelper.b(var1 * 0.6662F * 2.0F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * var2;
      float var15 = Math.abs(MathHelper.a(var1 * 0.6662F + 0.0F) * 0.4F) * var2;
      float var16 = Math.abs(MathHelper.a(var1 * 0.6662F + (float) Math.PI) * 0.4F) * var2;
      float var17 = Math.abs(MathHelper.a(var1 * 0.6662F + (float) (Math.PI / 2)) * 0.4F) * var2;
      float var18 = Math.abs(MathHelper.a(var1 * 0.6662F + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * var2;
      this.d.g += var11;
      this.e.g += -var11;
      this.f.g += var12;
      this.g.g += -var12;
      this.h.g += var13;
      this.i.g += -var13;
      this.j.g += var14;
      this.k.g += -var14;
      this.d.h += var15;
      this.e.h += -var15;
      this.f.h += var16;
      this.g.h += -var16;
      this.h.h += var17;
      this.i.h += -var17;
      this.j.h += var18;
      this.k.h += -var18;
   }
}

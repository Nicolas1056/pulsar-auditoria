package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;

public class ModelBat extends ModelBase {
   private ModelRenderer a;
   private ModelRenderer b;
   private ModelRenderer c;
   private ModelRenderer d;
   private ModelRenderer e;
   private ModelRenderer f;

   public ModelBat() {
      this.t = 64;
      this.u = 64;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      ModelRenderer var1 = new ModelRenderer(this, 24, 0);
      var1.a(-4.0F, -6.0F, -2.0F, 3, 4, 1);
      this.a.a(var1);
      ModelRenderer var2 = new ModelRenderer(this, 24, 0);
      var2.i = true;
      var2.a(1.0F, -6.0F, -2.0F, 3, 4, 1);
      this.a.a(var2);
      this.b = new ModelRenderer(this, 0, 16);
      this.b.a(-3.0F, 4.0F, -3.0F, 6, 12, 6);
      this.b.a(0, 34).a(-5.0F, 16.0F, 0.0F, 10, 6, 1);
      this.c = new ModelRenderer(this, 42, 0);
      this.c.a(-12.0F, 1.0F, 1.5F, 10, 16, 1);
      this.e = new ModelRenderer(this, 24, 16);
      this.e.a(-12.0F, 1.0F, 1.5F);
      this.e.a(-8.0F, 1.0F, 0.0F, 8, 12, 1);
      this.d = new ModelRenderer(this, 42, 0);
      this.d.i = true;
      this.d.a(2.0F, 1.0F, 1.5F, 10, 16, 1);
      this.f = new ModelRenderer(this, 24, 16);
      this.f.i = true;
      this.f.a(12.0F, 1.0F, 1.5F);
      this.f.a(0.0F, 1.0F, 0.0F, 8, 12, 1);
      this.b.a(this.c);
      this.b.a(this.d);
      this.c.a(this.e);
      this.d.a(this.f);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
      this.b.a(var7);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      if (((EntityBat)var7).n()) {
         float var8 = 180.0F / (float)Math.PI;
         this.a.f = var5 / (180.0F / (float)Math.PI);
         this.a.g = (float) Math.PI - var4 / (180.0F / (float)Math.PI);
         this.a.h = (float) Math.PI;
         this.a.a(0.0F, -2.0F, 0.0F);
         this.c.a(-3.0F, 0.0F, 3.0F);
         this.d.a(3.0F, 0.0F, 3.0F);
         this.b.f = (float) Math.PI;
         this.c.f = (float) (-Math.PI / 20);
         this.c.g = (float) (-Math.PI * 2.0 / 5.0);
         this.e.g = -1.7278761F;
         this.d.f = this.c.f;
         this.d.g = -this.c.g;
         this.f.g = -this.e.g;
      } else {
         float var9 = 180.0F / (float)Math.PI;
         this.a.f = var5 / (180.0F / (float)Math.PI);
         this.a.g = var4 / (180.0F / (float)Math.PI);
         this.a.h = 0.0F;
         this.a.a(0.0F, 0.0F, 0.0F);
         this.c.a(0.0F, 0.0F, 0.0F);
         this.d.a(0.0F, 0.0F, 0.0F);
         this.b.f = (float) (Math.PI / 4) + MathHelper.b(var3 * 0.1F) * 0.15F;
         this.b.g = 0.0F;
         this.c.g = MathHelper.b(var3 * 1.3F) * (float) Math.PI * 0.25F;
         this.d.g = -this.c.g;
         this.e.g = this.c.g * 0.5F;
         this.f.g = -this.c.g * 0.5F;
      }
   }
}

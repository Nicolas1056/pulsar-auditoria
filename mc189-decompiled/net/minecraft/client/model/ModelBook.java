package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBook extends ModelBase {
   public ModelRenderer a = new ModelRenderer(this).a(0, 0).a(-6.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer b = new ModelRenderer(this).a(16, 0).a(0.0F, -5.0F, 0.0F, 6, 10, 0);
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   public ModelRenderer g = new ModelRenderer(this).a(12, 0).a(-1.0F, -5.0F, 0.0F, 2, 10, 0);

   public ModelBook() {
      this.c = new ModelRenderer(this).a(0, 10).a(0.0F, -4.0F, -0.99F, 5, 8, 1);
      this.d = new ModelRenderer(this).a(12, 10).a(0.0F, -4.0F, -0.01F, 5, 8, 1);
      this.e = new ModelRenderer(this).a(24, 10).a(0.0F, -4.0F, 0.0F, 5, 8, 0);
      this.f = new ModelRenderer(this).a(24, 10).a(0.0F, -4.0F, 0.0F, 5, 8, 0);
      this.a.a(0.0F, 0.0F, -1.0F);
      this.b.a(0.0F, 0.0F, 1.0F);
      this.g.g = (float) (Math.PI / 2);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
      this.b.a(var7);
      this.g.a(var7);
      this.c.a(var7);
      this.d.a(var7);
      this.e.a(var7);
      this.f.a(var7);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = (MathHelper.a(var1 * 0.02F) * 0.1F + 1.25F) * var4;
      this.a.g = (float) Math.PI + var8;
      this.b.g = -var8;
      this.c.g = var8;
      this.d.g = -var8;
      this.e.g = var8 - var8 * 2.0F * var2;
      this.f.g = var8 - var8 * 2.0F * var3;
      this.c.c = MathHelper.a(var8);
      this.d.c = MathHelper.a(var8);
      this.e.c = MathHelper.a(var8);
      this.f.c = MathHelper.a(var8);
   }
}

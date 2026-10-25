package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCreeper extends ModelBase {
   public ModelRenderer a;
   public ModelRenderer b;
   public ModelRenderer c;
   public ModelRenderer d;
   public ModelRenderer e;
   public ModelRenderer f;
   public ModelRenderer g;

   public ModelCreeper() {
      this(0.0F);
   }

   public ModelCreeper(float var1) {
      byte var2 = 6;
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1);
      this.a.a(0.0F, var2, 0.0F);
      this.b = new ModelRenderer(this, 32, 0);
      this.b.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, var1 + 0.5F);
      this.b.a(0.0F, var2, 0.0F);
      this.c = new ModelRenderer(this, 16, 16);
      this.c.a(-4.0F, 0.0F, -2.0F, 8, 12, 4, var1);
      this.c.a(0.0F, var2, 0.0F);
      this.d = new ModelRenderer(this, 0, 16);
      this.d.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.d.a(-2.0F, 12 + var2, 4.0F);
      this.e = new ModelRenderer(this, 0, 16);
      this.e.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.e.a(2.0F, 12 + var2, 4.0F);
      this.f = new ModelRenderer(this, 0, 16);
      this.f.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.f.a(-2.0F, 12 + var2, -4.0F);
      this.g = new ModelRenderer(this, 0, 16);
      this.g.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.g.a(2.0F, 12 + var2, -4.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
      this.c.a(var7);
      this.d.a(var7);
      this.e.a(var7);
      this.f.a(var7);
      this.g.a(var7);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.a.f = var5 / (180.0F / (float)Math.PI);
      this.d.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
      this.e.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.f.f = MathHelper.b(var1 * 0.6662F + (float) Math.PI) * 1.4F * var2;
      this.g.f = MathHelper.b(var1 * 0.6662F) * 1.4F * var2;
   }
}

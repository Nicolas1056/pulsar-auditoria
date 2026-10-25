package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWitch extends ModelVillager {
   public boolean g;
   private ModelRenderer h = new ModelRenderer(this).b(64, 128);
   private ModelRenderer i;

   public ModelWitch(float var1) {
      super(var1, 0.0F, 64, 128);
      this.h.a(0.0F, -2.0F, 0.0F);
      this.h.a(0, 0).a(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
      this.f.a(this.h);
      this.i = new ModelRenderer(this).b(64, 128);
      this.i.a(-5.0F, -10.03125F, -5.0F);
      this.i.a(0, 64).a(0.0F, 0.0F, 0.0F, 10, 2, 10);
      this.a.a(this.i);
      ModelRenderer var2 = new ModelRenderer(this).b(64, 128);
      var2.a(1.75F, -4.0F, 2.0F);
      var2.a(0, 76).a(0.0F, 0.0F, 0.0F, 7, 4, 7);
      var2.f = -0.05235988F;
      var2.h = 0.02617994F;
      this.i.a(var2);
      ModelRenderer var3 = new ModelRenderer(this).b(64, 128);
      var3.a(1.75F, -4.0F, 2.0F);
      var3.a(0, 87).a(0.0F, 0.0F, 0.0F, 4, 4, 4);
      var3.f = -0.10471976F;
      var3.h = 0.05235988F;
      var2.a(var3);
      ModelRenderer var4 = new ModelRenderer(this).b(64, 128);
      var4.a(1.75F, -2.0F, 2.0F);
      var4.a(0, 95).a(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
      var4.f = (float) (-Math.PI / 15);
      var4.h = 0.10471976F;
      var3.a(var4);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.f.o = this.f.p = this.f.q = 0.0F;
      float var8 = 0.01F * (var7.F() % 10);
      this.f.f = MathHelper.a(var7.W * var8) * 4.5F * (float) Math.PI / 180.0F;
      this.f.g = 0.0F;
      this.f.h = MathHelper.b(var7.W * var8) * 2.5F * (float) Math.PI / 180.0F;
      if (this.g) {
         this.f.f = -0.9F;
         this.f.q = -0.09375F;
         this.f.p = 0.1875F;
      }
   }
}

package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep1 extends ModelQuadruped {
   private float i;

   public ModelSheep1() {
      super(12, 0.0F);
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
      this.a.a(0.0F, 6.0F, -8.0F);
      this.b = new ModelRenderer(this, 28, 8);
      this.b.a(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
      this.b.a(0.0F, 5.0F, 2.0F);
      float var1 = 0.5F;
      this.c = new ModelRenderer(this, 0, 16);
      this.c.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.c.a(-3.0F, 12.0F, 7.0F);
      this.d = new ModelRenderer(this, 0, 16);
      this.d.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.d.a(3.0F, 12.0F, 7.0F);
      this.e = new ModelRenderer(this, 0, 16);
      this.e.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.e.a(-3.0F, 12.0F, -5.0F);
      this.f = new ModelRenderer(this, 0, 16);
      this.f.a(-2.0F, 0.0F, -2.0F, 4, 6, 4, var1);
      this.f.a(3.0F, 12.0F, -5.0F);
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      super.a(var1, var2, var3, var4);
      this.a.d = 6.0F + ((EntitySheep)var1).p(var4) * 9.0F;
      this.i = ((EntitySheep)var1).q(var4);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.a.f = this.i;
   }
}

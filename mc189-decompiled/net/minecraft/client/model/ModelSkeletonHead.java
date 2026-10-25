package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSkeletonHead extends ModelBase {
   public ModelRenderer a;

   public ModelSkeletonHead() {
      this(0, 35, 64, 64);
   }

   public ModelSkeletonHead(int var1, int var2, int var3, int var4) {
      this.t = var3;
      this.u = var4;
      this.a = new ModelRenderer(this, var1, var2);
      this.a.a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
      this.a.a(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      this.a.g = var4 / (180.0F / (float)Math.PI);
      this.a.f = var5 / (180.0F / (float)Math.PI);
   }
}

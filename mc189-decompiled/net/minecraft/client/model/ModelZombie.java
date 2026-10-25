package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombie extends ModelBiped {
   public ModelZombie() {
      this(0.0F, false);
   }

   protected ModelZombie(float var1, float var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public ModelZombie(float var1, boolean var2) {
      super(var1, 0.0F, 64, var2 ? 32 : 64);
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      super.a(var1, var2, var3, var4, var5, var6, var7);
      float var8 = MathHelper.a(this.p * (float) Math.PI);
      float var9 = MathHelper.a((1.0F - (1.0F - this.p) * (1.0F - this.p)) * (float) Math.PI);
      this.h.h = 0.0F;
      this.i.h = 0.0F;
      this.h.g = -(0.1F - var8 * 0.6F);
      this.i.g = 0.1F - var8 * 0.6F;
      this.h.f = (float) (-Math.PI / 2);
      this.i.f = (float) (-Math.PI / 2);
      this.h.f -= var8 * 1.2F - var9 * 0.4F;
      this.i.f -= var8 * 1.2F - var9 * 0.4F;
      this.h.h = this.h.h + (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
      this.i.h = this.i.h - (MathHelper.b(var3 * 0.09F) * 0.05F + 0.05F);
      this.h.f = this.h.f + MathHelper.a(var3 * 0.067F) * 0.05F;
      this.i.f = this.i.f - MathHelper.a(var3 * 0.067F) * 0.05F;
   }
}

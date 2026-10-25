package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombieVillager extends ModelBiped {
   public ModelZombieVillager() {
      this(0.0F, 0.0F, false);
   }

   public ModelZombieVillager(float var1, float var2, boolean var3) {
      super(var1, 0.0F, 64, var3 ? 32 : 64);
      if (var3) {
         this.e = new ModelRenderer(this, 0, 0);
         this.e.a(-4.0F, -10.0F, -4.0F, 8, 8, 8, var1);
         this.e.a(0.0F, 0.0F + var2, 0.0F);
      } else {
         this.e = new ModelRenderer(this);
         this.e.a(0.0F, 0.0F + var2, 0.0F);
         this.e.a(0, 32).a(-4.0F, -10.0F, -4.0F, 8, 10, 8, var1);
         this.e.a(24, 32).a(-1.0F, -3.0F, -6.0F, 2, 4, 2, var1);
      }
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

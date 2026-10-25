package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX {
   private float a;

   protected EntityLavaFX(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.v *= 0.8F;
      this.w *= 0.8F;
      this.x *= 0.8F;
      this.w = this.V.nextFloat() * 0.4F + 0.05F;
      this.ar = this.as = this.at = 1.0F;
      this.h = this.h * (this.V.nextFloat() * 2.0F + 0.2F);
      this.a = this.h;
      this.g = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.T = false;
      this.k(49);
   }

   @Override
   public int b(float var1) {
      float var2 = (this.f + var1) / this.g;
      var2 = MathHelper.a(var2, 0.0F, 1.0F);
      int var3 = super.b(var1);
      short var4 = 240;
      int var5 = var3 >> 16 & 0xFF;
      return var4 | var5 << 16;
   }

   @Override
   public float c(float var1) {
      return 1.0F;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g;
      this.h = this.a * (1.0F - var9 * var9);
      super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.f++ >= this.g) {
         this.J();
      }

      float var1 = (float)this.f / this.g;
      if (this.V.nextFloat() > var1) {
         this.o.a(EnumParticleTypes.l, this.s, this.t, this.u, this.v, this.w, this.x);
      }

      this.w -= 0.03;
      this.d(this.v, this.w, this.x);
      this.v *= 0.999F;
      this.w *= 0.999F;
      this.x *= 0.999F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityLavaFX(var2, var3, var5, var7);
      }
   }
}

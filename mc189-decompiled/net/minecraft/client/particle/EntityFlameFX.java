package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {
   private float a;

   protected EntityFlameFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.v = this.v * 0.01F + var8;
      this.w = this.w * 0.01F + var10;
      this.x = this.x * 0.01F + var12;
      this.s = this.s + (this.V.nextFloat() - this.V.nextFloat()) * 0.05F;
      this.t = this.t + (this.V.nextFloat() - this.V.nextFloat()) * 0.05F;
      this.u = this.u + (this.V.nextFloat() - this.V.nextFloat()) * 0.05F;
      this.a = this.h;
      this.ar = this.as = this.at = 1.0F;
      this.g = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
      this.T = true;
      this.k(48);
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g;
      this.h = this.a * (1.0F - var9 * var9 * 0.5F);
      super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public int b(float var1) {
      float var2 = (this.f + var1) / this.g;
      var2 = MathHelper.a(var2, 0.0F, 1.0F);
      int var3 = super.b(var1);
      int var4 = var3 & 0xFF;
      int var5 = var3 >> 16 & 0xFF;
      var4 += (int)(var2 * 15.0F * 16.0F);
      if (var4 > 240) {
         var4 = 240;
      }

      return var4 | var5 << 16;
   }

   @Override
   public float c(float var1) {
      float var2 = (this.f + var1) / this.g;
      var2 = MathHelper.a(var2, 0.0F, 1.0F);
      float var3 = super.c(var1);
      return var3 * var2 + (1.0F - var2);
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.f++ >= this.g) {
         this.J();
      }

      this.d(this.v, this.w, this.x);
      this.v *= 0.96F;
      this.w *= 0.96F;
      this.x *= 0.96F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityFlameFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}

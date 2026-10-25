package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX {
   private float a;
   private double az;
   private double aA;
   private double aB;

   protected EntityPortalFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.v = var8;
      this.w = var10;
      this.x = var12;
      this.az = this.s = var2;
      this.aA = this.t = var4;
      this.aB = this.u = var6;
      float var14 = this.V.nextFloat() * 0.6F + 0.4F;
      this.a = this.h = this.V.nextFloat() * 0.2F + 0.5F;
      this.ar = this.as = this.at = 1.0F * var14;
      this.as *= 0.3F;
      this.ar *= 0.9F;
      this.g = (int)(Math.random() * 10.0) + 40;
      this.T = true;
      this.k((int)(Math.random() * 8.0));
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g;
      var9 = 1.0F - var9;
      var9 *= var9;
      var9 = 1.0F - var9;
      this.h = this.a * var9;
      super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public int b(float var1) {
      int var2 = super.b(var1);
      float var3 = (float)this.f / this.g;
      var3 *= var3;
      var3 *= var3;
      int var4 = var2 & 0xFF;
      int var5 = var2 >> 16 & 0xFF;
      var5 += (int)(var3 * 15.0F * 16.0F);
      if (var5 > 240) {
         var5 = 240;
      }

      return var4 | var5 << 16;
   }

   @Override
   public float c(float var1) {
      float var2 = super.c(var1);
      float var3 = (float)this.f / this.g;
      var3 = var3 * var3 * var3 * var3;
      return var2 * (1.0F - var3) + var3;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      float var1 = (float)this.f / this.g;
      float var3 = -var1 + var1 * var1 * 2.0F;
      float var4 = 1.0F - var3;
      this.s = this.az + this.v * var4;
      this.t = this.aA + this.w * var4 + (1.0F - var1);
      this.u = this.aB + this.x * var4;
      if (this.f++ >= this.g) {
         this.J();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityPortalFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}

package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrit2FX extends EntityFX {
   float a;

   protected EntityCrit2FX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(var1, var2, var4, var6, var8, var10, var12, 1.0F);
   }

   protected EntityCrit2FX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.v *= 0.1F;
      this.w *= 0.1F;
      this.x *= 0.1F;
      this.v += var8 * 0.4;
      this.w += var10 * 0.4;
      this.x += var12 * 0.4;
      this.ar = this.as = this.at = (float)(Math.random() * 0.3F + 0.6F);
      this.h *= 0.75F;
      this.h *= var14;
      this.a = this.h;
      this.g = (int)(6.0 / (Math.random() * 0.8 + 0.6));
      this.g = (int)(this.g * var14);
      this.T = false;
      this.k(65);
      this.t_();
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g * 32.0F;
      var9 = MathHelper.a(var9, 0.0F, 1.0F);
      this.h = this.a * var9;
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

      this.d(this.v, this.w, this.x);
      this.as = (float)(this.as * 0.96);
      this.at = (float)(this.at * 0.9);
      this.v *= 0.7F;
      this.w *= 0.7F;
      this.x *= 0.7F;
      this.w -= 0.02F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityCrit2FX(var2, var3, var5, var7, var9, var11, var13);
      }
   }

   public static class MagicFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntityCrit2FX var16 = new EntityCrit2FX(var2, var3, var5, var7, var9, var11, var13);
         var16.b(var16.b() * 0.3F, var16.g() * 0.8F, var16.i());
         var16.k();
         return var16;
      }
   }
}

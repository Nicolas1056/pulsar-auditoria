package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityAuraFX extends EntityFX {
   protected EntityAuraFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      float var14 = this.V.nextFloat() * 0.1F + 0.2F;
      this.ar = var14;
      this.as = var14;
      this.at = var14;
      this.k(0);
      this.a(0.02F, 0.02F);
      this.h = this.h * (this.V.nextFloat() * 0.6F + 0.5F);
      this.v *= 0.02F;
      this.w *= 0.02F;
      this.x *= 0.02F;
      this.g = (int)(20.0 / (Math.random() * 0.8 + 0.2));
      this.T = true;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.d(this.v, this.w, this.x);
      this.v *= 0.99;
      this.w *= 0.99;
      this.x *= 0.99;
      if (this.g-- <= 0) {
         this.J();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityAuraFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }

   public static class HappyVillagerFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntityAuraFX var16 = new EntityAuraFX(var2, var3, var5, var7, var9, var11, var13);
         var16.k(82);
         var16.b(1.0F, 1.0F, 1.0F);
         return var16;
      }
   }
}

package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {
   protected EntityExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
      this.v = var8 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.w = var10 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.x = var12 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.ar = this.as = this.at = this.V.nextFloat() * 0.3F + 0.7F;
      this.h = this.V.nextFloat() * this.V.nextFloat() * 6.0F + 1.0F;
      this.g = (int)(16.0 / (this.V.nextFloat() * 0.8 + 0.2)) + 2;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.f++ >= this.g) {
         this.J();
      }

      this.k(7 - this.f * 8 / this.g);
      this.w += 0.004;
      this.d(this.v, this.w, this.x);
      this.v *= 0.9F;
      this.w *= 0.9F;
      this.x *= 0.9F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityExplodeFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}

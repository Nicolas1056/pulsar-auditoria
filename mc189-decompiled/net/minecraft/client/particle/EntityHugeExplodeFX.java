package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX extends EntityFX {
   private int a;
   private int az = 8;

   protected EntityHugeExplodeFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   @Override
   public void t_() {
      for (int var1 = 0; var1 < 6; var1++) {
         double var2 = this.s + (this.V.nextDouble() - this.V.nextDouble()) * 4.0;
         double var4 = this.t + (this.V.nextDouble() - this.V.nextDouble()) * 4.0;
         double var6 = this.u + (this.V.nextDouble() - this.V.nextDouble()) * 4.0;
         this.o.a(EnumParticleTypes.b, var2, var4, var6, (float)this.a / this.az, 0.0, 0.0);
      }

      this.a++;
      if (this.a == this.az) {
         this.J();
      }
   }

   @Override
   public int a() {
      return 1;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityHugeExplodeFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}

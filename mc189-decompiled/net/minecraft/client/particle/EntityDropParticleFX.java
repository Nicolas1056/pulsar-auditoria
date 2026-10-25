package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticleFX extends EntityFX {
   private Material a;
   private int az;

   protected EntityDropParticleFX(World var1, double var2, double var4, double var6, Material var8) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.v = this.w = this.x = 0.0;
      if (var8 == Material.h) {
         this.ar = 0.0F;
         this.as = 0.0F;
         this.at = 1.0F;
      } else {
         this.ar = 1.0F;
         this.as = 0.0F;
         this.at = 0.0F;
      }

      this.k(113);
      this.a(0.01F, 0.01F);
      this.i = 0.06F;
      this.a = var8;
      this.az = 40;
      this.g = (int)(64.0 / (Math.random() * 0.8 + 0.2));
      this.v = this.w = this.x = 0.0;
   }

   @Override
   public int b(float var1) {
      return this.a == Material.h ? super.b(var1) : 257;
   }

   @Override
   public float c(float var1) {
      return this.a == Material.h ? super.c(var1) : 1.0F;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.a == Material.h) {
         this.ar = 0.2F;
         this.as = 0.3F;
         this.at = 1.0F;
      } else {
         this.ar = 1.0F;
         this.as = 16.0F / (40 - this.az + 16);
         this.at = 4.0F / (40 - this.az + 8);
      }

      this.w = this.w - this.i;
      if (this.az-- > 0) {
         this.v *= 0.02;
         this.w *= 0.02;
         this.x *= 0.02;
         this.k(113);
      } else {
         this.k(112);
      }

      this.d(this.v, this.w, this.x);
      this.v *= 0.98F;
      this.w *= 0.98F;
      this.x *= 0.98F;
      if (this.g-- <= 0) {
         this.J();
      }

      if (this.C) {
         if (this.a == Material.h) {
            this.J();
            this.o.a(EnumParticleTypes.f, this.s, this.t, this.u, 0.0, 0.0, 0.0);
         } else {
            this.k(114);
         }

         this.v *= 0.7F;
         this.x *= 0.7F;
      }

      BlockPos var1 = new BlockPos(this);
      IBlockState var2 = this.o.p(var1);
      Material var3 = var2.c().t();
      if (var3.d() || var3.a()) {
         double var4 = 0.0;
         if (var2.c() instanceof BlockLiquid) {
            var4 = BlockLiquid.b(var2.b(BlockLiquid.b));
         }

         double var6 = MathHelper.c(this.t) + 1 - var4;
         if (this.t < var6) {
            this.J();
         }
      }
   }

   public static class LavaFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityDropParticleFX(var2, var3, var5, var7, Material.i);
      }
   }

   public static class WaterFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityDropParticleFX(var2, var3, var5, var7, Material.h);
      }
   }
}

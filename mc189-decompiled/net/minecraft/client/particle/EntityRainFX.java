package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityRainFX extends EntityFX {
   protected EntityRainFX(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.v *= 0.3F;
      this.w = Math.random() * 0.2F + 0.1F;
      this.x *= 0.3F;
      this.ar = 1.0F;
      this.as = 1.0F;
      this.at = 1.0F;
      this.k(19 + this.V.nextInt(4));
      this.a(0.01F, 0.01F);
      this.i = 0.06F;
      this.g = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.w = this.w - this.i;
      this.d(this.v, this.w, this.x);
      this.v *= 0.98F;
      this.w *= 0.98F;
      this.x *= 0.98F;
      if (this.g-- <= 0) {
         this.J();
      }

      if (this.C) {
         if (Math.random() < 0.5) {
            this.J();
         }

         this.v *= 0.7F;
         this.x *= 0.7F;
      }

      BlockPos var1 = new BlockPos(this);
      IBlockState var2 = this.o.p(var1);
      Block var3 = var2.c();
      var3.a((IBlockAccess)this.o, var1);
      Material var4 = var2.c().t();
      if (var4.d() || var4.a()) {
         double var5 = 0.0;
         if (var2.c() instanceof BlockLiquid) {
            var5 = 1.0F - BlockLiquid.b(var2.b(BlockLiquid.b));
         } else {
            var5 = var3.E();
         }

         double var7 = MathHelper.c(this.t) + var5;
         if (this.t < var7) {
            this.J();
         }
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityRainFX(var2, var3, var5, var7);
      }
   }
}

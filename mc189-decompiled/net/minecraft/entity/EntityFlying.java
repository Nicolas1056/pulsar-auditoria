package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityLiving {
   public EntityFlying(World var1) {
      super(var1);
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
   }

   @Override
   public void g(float var1, float var2) {
      if (this.V()) {
         this.a(var1, var2, 0.02F);
         this.d(this.v, this.w, this.x);
         this.v *= 0.8F;
         this.w *= 0.8F;
         this.x *= 0.8F;
      } else if (this.ab()) {
         this.a(var1, var2, 0.02F);
         this.d(this.v, this.w, this.x);
         this.v *= 0.5;
         this.w *= 0.5;
         this.x *= 0.5;
      } else {
         float var3 = 0.91F;
         if (this.C) {
            var3 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.91F;
         }

         float var4 = 0.16277136F / (var3 * var3 * var3);
         this.a(var1, var2, this.C ? 0.1F * var4 : 0.02F);
         var3 = 0.91F;
         if (this.C) {
            var3 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.91F;
         }

         this.d(this.v, this.w, this.x);
         this.v *= var3;
         this.w *= var3;
         this.x *= var3;
      }

      this.aA = this.aB;
      double var9 = this.s - this.p;
      double var5 = this.u - this.r;
      float var7 = MathHelper.a(var9 * var9 + var5 * var5) * 4.0F;
      if (var7 > 1.0F) {
         var7 = 1.0F;
      }

      this.aB = this.aB + (var7 - this.aB) * 0.4F;
      this.aC = this.aC + this.aB;
   }

   @Override
   public boolean k_() {
      return false;
   }
}

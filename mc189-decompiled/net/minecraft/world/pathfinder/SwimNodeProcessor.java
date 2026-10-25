package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SwimNodeProcessor extends NodeProcessor {
   @Override
   public void a(IBlockAccess var1, Entity var2) {
      super.a(var1, var2);
   }

   @Override
   public void a() {
      super.a();
   }

   @Override
   public PathPoint a(Entity var1) {
      return this.a(MathHelper.c(var1.aR().a), MathHelper.c(var1.aR().b + 0.5), MathHelper.c(var1.aR().c));
   }

   @Override
   public PathPoint a(Entity var1, double var2, double var4, double var6) {
      return this.a(MathHelper.c(var2 - var1.J / 2.0F), MathHelper.c(var4 + 0.5), MathHelper.c(var6 - var1.J / 2.0F));
   }

   @Override
   public int a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5) {
      int var6 = 0;

      for (EnumFacing var10 : EnumFacing.values()) {
         PathPoint var11 = this.a(var2, var3.a + var10.g(), var3.b + var10.h(), var3.c + var10.i());
         if (var11 != null && !var11.i && var11.a(var4) < var5) {
            var1[var6++] = var11;
         }
      }

      return var6;
   }

   private PathPoint a(Entity var1, int var2, int var3, int var4) {
      int var5 = this.b(var1, var2, var3, var4);
      return var5 == -1 ? this.a(var2, var3, var4) : null;
   }

   private int b(Entity var1, int var2, int var3, int var4) {
      BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();

      for (int var6 = var2; var6 < var2 + this.c; var6++) {
         for (int var7 = var3; var7 < var3 + this.d; var7++) {
            for (int var8 = var4; var8 < var4 + this.e; var8++) {
               Block var9 = this.a.p(var5.c(var6, var7, var8)).c();
               if (var9.t() != Material.h) {
                  return 0;
               }
            }
         }
      }

      return -1;
   }
}

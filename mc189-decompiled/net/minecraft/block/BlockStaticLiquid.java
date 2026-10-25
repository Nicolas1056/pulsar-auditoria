package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockStaticLiquid extends BlockLiquid {
   protected BlockStaticLiquid(Material var1) {
      super(var1);
      this.a(false);
      if (var1 == Material.i) {
         this.a(true);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.e(var1, var2, var3)) {
         this.f(var1, var2, var3);
      }
   }

   private void f(World var1, BlockPos var2, IBlockState var3) {
      BlockDynamicLiquid var4 = a(this.J);
      var1.a(var2, var4.Q().a(b, var3.b(b)), 2);
      var1.a(var2, var4, this.a(var1));
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.J == Material.i) {
         if (var1.Q().b("doFireTick")) {
            int var5 = var4.nextInt(3);
            if (var5 > 0) {
               BlockPos var6 = var2;

               for (int var7 = 0; var7 < var5; var7++) {
                  var6 = var6.a(var4.nextInt(3) - 1, 1, var4.nextInt(3) - 1);
                  Block var8 = var1.p(var6).c();
                  if (var8.J == Material.a) {
                     if (this.f(var1, var6)) {
                        var1.a(var6, Blocks.ab.Q());
                        return;
                     }
                  } else if (var8.J.c()) {
                     return;
                  }
               }
            } else {
               for (int var9 = 0; var9 < 3; var9++) {
                  BlockPos var10 = var2.a(var4.nextInt(3) - 1, 0, var4.nextInt(3) - 1);
                  if (var1.d(var10.a()) && this.m(var1, var10)) {
                     var1.a(var10.a(), Blocks.ab.Q());
                  }
               }
            }
         }
      }
   }

   protected boolean f(World var1, BlockPos var2) {
      for (EnumFacing var6 : EnumFacing.values()) {
         if (this.m(var1, var2.a(var6))) {
            return true;
         }
      }

      return false;
   }

   private boolean m(World var1, BlockPos var2) {
      return var1.p(var2).c().t().h();
   }
}

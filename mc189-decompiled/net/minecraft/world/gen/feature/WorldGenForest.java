package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenForest extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.c);
   private static final IBlockState b = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.c).a(BlockOldLeaf.b, false);
   private boolean c;

   public WorldGenForest(boolean var1, boolean var2) {
      super(var1);
      this.c = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + 5;
      if (this.c) {
         var4 += var2.nextInt(7);
      }

      boolean var5 = true;
      if (var3.o() >= 1 && var3.o() + var4 + 1 <= 256) {
         for (int var6 = var3.o(); var6 <= var3.o() + 1 + var4; var6++) {
            byte var7 = 1;
            if (var6 == var3.o()) {
               var7 = 0;
            }

            if (var6 >= var3.o() + 1 + var4 - 2) {
               var7 = 2;
            }

            BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

            for (int var9 = var3.n() - var7; var9 <= var3.n() + var7 && var5; var9++) {
               for (int var10 = var3.p() - var7; var10 <= var3.p() + var7 && var5; var10++) {
                  if (var6 < 0 || var6 >= 256) {
                     var5 = false;
                  } else if (!this.a(var1.p(var8.c(var9, var6, var10)).c())) {
                     var5 = false;
                  }
               }
            }
         }

         if (!var5) {
            return false;
         } else {
            Block var16 = var1.p(var3.b()).c();
            if ((var16 == Blocks.c || var16 == Blocks.d || var16 == Blocks.ak) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());

               for (int var17 = var3.o() - 3 + var4; var17 <= var3.o() + var4; var17++) {
                  int var19 = var17 - (var3.o() + var4);
                  int var21 = 1 - var19 / 2;

                  for (int var22 = var3.n() - var21; var22 <= var3.n() + var21; var22++) {
                     int var11 = var22 - var3.n();

                     for (int var12 = var3.p() - var21; var12 <= var3.p() + var21; var12++) {
                        int var13 = var12 - var3.p();
                        if (Math.abs(var11) != var21 || Math.abs(var13) != var21 || var2.nextInt(2) != 0 && var19 != 0) {
                           BlockPos var14 = new BlockPos(var22, var17, var12);
                           Block var15 = var1.p(var14).c();
                           if (var15.t() == Material.a || var15.t() == Material.j) {
                              this.a(var1, var14, b);
                           }
                        }
                     }
                  }
               }

               for (int var18 = 0; var18 < var4; var18++) {
                  Block var20 = var1.p(var3.b(var18)).c();
                  if (var20.t() == Material.a || var20.t() == Material.j) {
                     this.a(var1, var3.b(var18), a);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}

package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenTaiga1 extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.b);
   private static final IBlockState b = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.b).a(BlockLeaves.b, false);

   public WorldGenTaiga1() {
      super(false);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(5) + 7;
      int var5 = var4 - var2.nextInt(2) - 3;
      int var6 = var4 - var5;
      int var7 = 1 + var2.nextInt(var6 + 1);
      boolean var8 = true;
      if (var3.o() >= 1 && var3.o() + var4 + 1 <= 256) {
         for (int var9 = var3.o(); var9 <= var3.o() + 1 + var4 && var8; var9++) {
            int var10 = 1;
            if (var9 - var3.o() < var5) {
               var10 = 0;
            } else {
               var10 = var7;
            }

            BlockPos.MutableBlockPos var11 = new BlockPos.MutableBlockPos();

            for (int var12 = var3.n() - var10; var12 <= var3.n() + var10 && var8; var12++) {
               for (int var13 = var3.p() - var10; var13 <= var3.p() + var10 && var8; var13++) {
                  if (var9 < 0 || var9 >= 256) {
                     var8 = false;
                  } else if (!this.a(var1.p(var11.c(var12, var9, var13)).c())) {
                     var8 = false;
                  }
               }
            }
         }

         if (!var8) {
            return false;
         } else {
            Block var17 = var1.p(var3.b()).c();
            if ((var17 == Blocks.c || var17 == Blocks.d) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());
               int var19 = 0;

               for (int var20 = var3.o() + var4; var20 >= var3.o() + var5; var20--) {
                  for (int var22 = var3.n() - var19; var22 <= var3.n() + var19; var22++) {
                     int var24 = var22 - var3.n();

                     for (int var14 = var3.p() - var19; var14 <= var3.p() + var19; var14++) {
                        int var15 = var14 - var3.p();
                        if (Math.abs(var24) != var19 || Math.abs(var15) != var19 || var19 <= 0) {
                           BlockPos var16 = new BlockPos(var22, var20, var14);
                           if (!var1.p(var16).c().o()) {
                              this.a(var1, var16, b);
                           }
                        }
                     }
                  }

                  if (var19 >= 1 && var20 == var3.o() + var5 + 1) {
                     var19--;
                  } else if (var19 < var7) {
                     var19++;
                  }
               }

               for (int var21 = 0; var21 < var4 - 1; var21++) {
                  Block var23 = var1.p(var3.b(var21)).c();
                  if (var23.t() == Material.a || var23.t() == Material.j) {
                     this.a(var1, var3.b(var21), a);
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

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

public class WorldGenTaiga2 extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.b);
   private static final IBlockState b = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.b).a(BlockLeaves.b, false);

   public WorldGenTaiga2(boolean var1) {
      super(var1);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(4) + 6;
      int var5 = 1 + var2.nextInt(2);
      int var6 = var4 - var5;
      int var7 = 2 + var2.nextInt(2);
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
                  if (var9 >= 0 && var9 < 256) {
                     Block var14 = var1.p(var11.c(var12, var9, var13)).c();
                     if (var14.t() != Material.a && var14.t() != Material.j) {
                        var8 = false;
                     }
                  } else {
                     var8 = false;
                  }
               }
            }
         }

         if (!var8) {
            return false;
         } else {
            Block var20 = var1.p(var3.b()).c();
            if ((var20 == Blocks.c || var20 == Blocks.d || var20 == Blocks.ak) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());
               int var22 = var2.nextInt(2);
               int var23 = 1;
               byte var24 = 0;

               for (int var25 = 0; var25 <= var6; var25++) {
                  int var27 = var3.o() + var4 - var25;

                  for (int var15 = var3.n() - var22; var15 <= var3.n() + var22; var15++) {
                     int var16 = var15 - var3.n();

                     for (int var17 = var3.p() - var22; var17 <= var3.p() + var22; var17++) {
                        int var18 = var17 - var3.p();
                        if (Math.abs(var16) != var22 || Math.abs(var18) != var22 || var22 <= 0) {
                           BlockPos var19 = new BlockPos(var15, var27, var17);
                           if (!var1.p(var19).c().o()) {
                              this.a(var1, var19, b);
                           }
                        }
                     }
                  }

                  if (var22 >= var23) {
                     var22 = var24;
                     var24 = 1;
                     if (++var23 > var7) {
                        var23 = var7;
                     }
                  } else {
                     var22++;
                  }
               }

               int var26 = var2.nextInt(3);

               for (int var28 = 0; var28 < var4 - var26; var28++) {
                  Block var29 = var1.p(var3.b(var28)).c();
                  if (var29.t() == Material.a || var29.t() == Material.j) {
                     this.a(var1, var3.b(var28), a);
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

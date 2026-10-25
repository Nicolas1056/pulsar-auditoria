package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenCanopyTree extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.s.Q().a(BlockNewLog.b, BlockPlanks.EnumType.f);
   private static final IBlockState b = Blocks.u.Q().a(BlockNewLeaf.Q, BlockPlanks.EnumType.f).a(BlockLeaves.b, false);

   public WorldGenCanopyTree(boolean var1) {
      super(var1);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(2) + 6;
      int var5 = var3.n();
      int var6 = var3.o();
      int var7 = var3.p();
      if (var6 >= 1 && var6 + var4 + 1 < 256) {
         BlockPos var8 = var3.b();
         Block var9 = var1.p(var8).c();
         if (var9 != Blocks.c && var9 != Blocks.d) {
            return false;
         } else if (!this.a(var1, var3, var4)) {
            return false;
         } else {
            this.a(var1, var8);
            this.a(var1, var8.f());
            this.a(var1, var8.d());
            this.a(var1, var8.d().f());
            EnumFacing var10 = EnumFacing.Plane.a.a(var2);
            int var11 = var4 - var2.nextInt(4);
            int var12 = 2 - var2.nextInt(3);
            int var13 = var5;
            int var14 = var7;
            int var15 = var6 + var4 - 1;

            for (int var16 = 0; var16 < var4; var16++) {
               if (var16 >= var11 && var12 > 0) {
                  var13 += var10.g();
                  var14 += var10.i();
                  var12--;
               }

               int var17 = var6 + var16;
               BlockPos var18 = new BlockPos(var13, var17, var14);
               Material var19 = var1.p(var18).c().t();
               if (var19 == Material.a || var19 == Material.j) {
                  this.b(var1, var18);
                  this.b(var1, var18.f());
                  this.b(var1, var18.d());
                  this.b(var1, var18.f().d());
               }
            }

            for (int var21 = -2; var21 <= 0; var21++) {
               for (int var24 = -2; var24 <= 0; var24++) {
                  byte var27 = -1;
                  this.a(var1, var13 + var21, var15 + var27, var14 + var24);
                  this.a(var1, 1 + var13 - var21, var15 + var27, var14 + var24);
                  this.a(var1, var13 + var21, var15 + var27, 1 + var14 - var24);
                  this.a(var1, 1 + var13 - var21, var15 + var27, 1 + var14 - var24);
                  if ((var21 > -2 || var24 > -1) && (var21 != -1 || var24 != -2)) {
                     var27 = 1;
                     this.a(var1, var13 + var21, var15 + var27, var14 + var24);
                     this.a(var1, 1 + var13 - var21, var15 + var27, var14 + var24);
                     this.a(var1, var13 + var21, var15 + var27, 1 + var14 - var24);
                     this.a(var1, 1 + var13 - var21, var15 + var27, 1 + var14 - var24);
                  }
               }
            }

            if (var2.nextBoolean()) {
               this.a(var1, var13, var15 + 2, var14);
               this.a(var1, var13 + 1, var15 + 2, var14);
               this.a(var1, var13 + 1, var15 + 2, var14 + 1);
               this.a(var1, var13, var15 + 2, var14 + 1);
            }

            for (int var22 = -3; var22 <= 4; var22++) {
               for (int var25 = -3; var25 <= 4; var25++) {
                  if ((var22 != -3 || var25 != -3)
                     && (var22 != -3 || var25 != 4)
                     && (var22 != 4 || var25 != -3)
                     && (var22 != 4 || var25 != 4)
                     && (Math.abs(var22) < 3 || Math.abs(var25) < 3)) {
                     this.a(var1, var13 + var22, var15, var14 + var25);
                  }
               }
            }

            for (int var23 = -1; var23 <= 2; var23++) {
               for (int var26 = -1; var26 <= 2; var26++) {
                  if ((var23 < 0 || var23 > 1 || var26 < 0 || var26 > 1) && var2.nextInt(3) <= 0) {
                     int var29 = var2.nextInt(3) + 2;

                     for (int var30 = 0; var30 < var29; var30++) {
                        this.b(var1, new BlockPos(var5 + var23, var15 - var30 - 1, var7 + var26));
                     }

                     for (int var31 = -1; var31 <= 1; var31++) {
                        for (int var20 = -1; var20 <= 1; var20++) {
                           this.a(var1, var13 + var23 + var31, var15, var14 + var26 + var20);
                        }
                     }

                     for (int var32 = -2; var32 <= 2; var32++) {
                        for (int var33 = -2; var33 <= 2; var33++) {
                           if (Math.abs(var32) != 2 || Math.abs(var33) != 2) {
                              this.a(var1, var13 + var23 + var32, var15 - 1, var14 + var26 + var33);
                           }
                        }
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean a(World var1, BlockPos var2, int var3) {
      int var4 = var2.n();
      int var5 = var2.o();
      int var6 = var2.p();
      BlockPos.MutableBlockPos var7 = new BlockPos.MutableBlockPos();

      for (int var8 = 0; var8 <= var3 + 1; var8++) {
         byte var9 = 1;
         if (var8 == 0) {
            var9 = 0;
         }

         if (var8 >= var3 - 1) {
            var9 = 2;
         }

         for (int var10 = -var9; var10 <= var9; var10++) {
            for (int var11 = -var9; var11 <= var9; var11++) {
               if (!this.a(var1.p(var7.c(var4 + var10, var5 + var8, var6 + var11)).c())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void b(World var1, BlockPos var2) {
      if (this.a(var1.p(var2).c())) {
         this.a(var1, var2, a);
      }
   }

   private void a(World var1, int var2, int var3, int var4) {
      BlockPos var5 = new BlockPos(var2, var3, var4);
      Block var6 = var1.p(var5).c();
      if (var6.t() == Material.a) {
         this.a(var1, var5, b);
      }
   }
}

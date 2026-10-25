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

public class WorldGenSavannaTree extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.s.Q().a(BlockNewLog.b, BlockPlanks.EnumType.e);
   private static final IBlockState b = Blocks.u.Q().a(BlockNewLeaf.Q, BlockPlanks.EnumType.e).a(BlockLeaves.b, false);

   public WorldGenSavannaTree(boolean var1) {
      super(var1);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + var2.nextInt(3) + 5;
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
            Block var20 = var1.p(var3.b()).c();
            if ((var20 == Blocks.c || var20 == Blocks.d) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());
               EnumFacing var21 = EnumFacing.Plane.a.a(var2);
               int var22 = var4 - var2.nextInt(4) - 1;
               int var23 = 3 - var2.nextInt(3);
               int var24 = var3.n();
               int var11 = var3.p();
               int var12 = 0;

               for (int var13 = 0; var13 < var4; var13++) {
                  int var14 = var3.o() + var13;
                  if (var13 >= var22 && var23 > 0) {
                     var24 += var21.g();
                     var11 += var21.i();
                     var23--;
                  }

                  BlockPos var15 = new BlockPos(var24, var14, var11);
                  Material var16 = var1.p(var15).c().t();
                  if (var16 == Material.a || var16 == Material.j) {
                     this.b(var1, var15);
                     var12 = var14;
                  }
               }

               BlockPos var28 = new BlockPos(var24, var12, var11);

               for (int var31 = -3; var31 <= 3; var31++) {
                  for (int var34 = -3; var34 <= 3; var34++) {
                     if (Math.abs(var31) != 3 || Math.abs(var34) != 3) {
                        this.c(var1, var28.a(var31, 0, var34));
                     }
                  }
               }

               var28 = var28.a();

               for (int var32 = -1; var32 <= 1; var32++) {
                  for (int var35 = -1; var35 <= 1; var35++) {
                     this.c(var1, var28.a(var32, 0, var35));
                  }
               }

               this.c(var1, var28.g(2));
               this.c(var1, var28.f(2));
               this.c(var1, var28.e(2));
               this.c(var1, var28.d(2));
               var24 = var3.n();
               var11 = var3.p();
               EnumFacing var30 = EnumFacing.Plane.a.a(var2);
               if (var30 != var21) {
                  int var33 = var22 - var2.nextInt(2) - 1;
                  int var36 = 1 + var2.nextInt(3);
                  var12 = 0;

                  for (int var37 = var33; var37 < var4 && var36 > 0; var36--) {
                     if (var37 >= 1) {
                        int var17 = var3.o() + var37;
                        var24 += var30.g();
                        var11 += var30.i();
                        BlockPos var18 = new BlockPos(var24, var17, var11);
                        Material var19 = var1.p(var18).c().t();
                        if (var19 == Material.a || var19 == Material.j) {
                           this.b(var1, var18);
                           var12 = var17;
                        }
                     }

                     var37++;
                  }

                  if (var12 > 0) {
                     BlockPos var38 = new BlockPos(var24, var12, var11);

                     for (int var40 = -2; var40 <= 2; var40++) {
                        for (int var42 = -2; var42 <= 2; var42++) {
                           if (Math.abs(var40) != 2 || Math.abs(var42) != 2) {
                              this.c(var1, var38.a(var40, 0, var42));
                           }
                        }
                     }

                     var38 = var38.a();

                     for (int var41 = -1; var41 <= 1; var41++) {
                        for (int var43 = -1; var43 <= 1; var43++) {
                           this.c(var1, var38.a(var41, 0, var43));
                        }
                     }
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

   private void b(World var1, BlockPos var2) {
      this.a(var1, var2, a);
   }

   private void c(World var1, BlockPos var2) {
      Material var3 = var1.p(var2).c().t();
      if (var3 == Material.a || var3 == Material.j) {
         this.a(var1, var2, b);
      }
   }
}

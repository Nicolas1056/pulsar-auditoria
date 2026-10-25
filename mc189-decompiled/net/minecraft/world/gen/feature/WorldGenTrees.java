package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenTrees extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.a);
   private static final IBlockState b = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.a).a(BlockLeaves.b, false);
   private final int c;
   private final boolean d;
   private final IBlockState e;
   private final IBlockState f;

   public WorldGenTrees(boolean var1) {
      this(var1, 4, a, b, false);
   }

   public WorldGenTrees(boolean var1, int var2, IBlockState var3, IBlockState var4, boolean var5) {
      super(var1);
      this.c = var2;
      this.e = var3;
      this.f = var4;
      this.d = var5;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(3) + this.c;
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
            Block var19 = var1.p(var3.b()).c();
            if ((var19 == Blocks.c || var19 == Blocks.d || var19 == Blocks.ak) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());
               byte var20 = 3;
               byte var21 = 0;

               for (int var22 = var3.o() - var20 + var4; var22 <= var3.o() + var4; var22++) {
                  int var26 = var22 - (var3.o() + var4);
                  int var11 = var21 + 1 - var26 / 2;

                  for (int var12 = var3.n() - var11; var12 <= var3.n() + var11; var12++) {
                     int var13 = var12 - var3.n();

                     for (int var14 = var3.p() - var11; var14 <= var3.p() + var11; var14++) {
                        int var15 = var14 - var3.p();
                        if (Math.abs(var13) != var11 || Math.abs(var15) != var11 || var2.nextInt(2) != 0 && var26 != 0) {
                           BlockPos var16 = new BlockPos(var12, var22, var14);
                           Block var17 = var1.p(var16).c();
                           if (var17.t() == Material.a || var17.t() == Material.j || var17.t() == Material.l) {
                              this.a(var1, var16, this.f);
                           }
                        }
                     }
                  }
               }

               for (int var23 = 0; var23 < var4; var23++) {
                  Block var27 = var1.p(var3.b(var23)).c();
                  if (var27.t() == Material.a || var27.t() == Material.j || var27.t() == Material.l) {
                     this.a(var1, var3.b(var23), this.e);
                     if (this.d && var23 > 0) {
                        if (var2.nextInt(3) > 0 && var1.d(var3.a(-1, var23, 0))) {
                           this.a(var1, var3.a(-1, var23, 0), BlockVine.N);
                        }

                        if (var2.nextInt(3) > 0 && var1.d(var3.a(1, var23, 0))) {
                           this.a(var1, var3.a(1, var23, 0), BlockVine.P);
                        }

                        if (var2.nextInt(3) > 0 && var1.d(var3.a(0, var23, -1))) {
                           this.a(var1, var3.a(0, var23, -1), BlockVine.O);
                        }

                        if (var2.nextInt(3) > 0 && var1.d(var3.a(0, var23, 1))) {
                           this.a(var1, var3.a(0, var23, 1), BlockVine.b);
                        }
                     }
                  }
               }

               if (this.d) {
                  for (int var24 = var3.o() - 3 + var4; var24 <= var3.o() + var4; var24++) {
                     int var28 = var24 - (var3.o() + var4);
                     int var30 = 2 - var28 / 2;
                     BlockPos.MutableBlockPos var32 = new BlockPos.MutableBlockPos();

                     for (int var34 = var3.n() - var30; var34 <= var3.n() + var30; var34++) {
                        for (int var35 = var3.p() - var30; var35 <= var3.p() + var30; var35++) {
                           var32.c(var34, var24, var35);
                           if (var1.p(var32).c().t() == Material.j) {
                              BlockPos var36 = var32.e();
                              BlockPos var37 = var32.f();
                              BlockPos var38 = var32.c();
                              BlockPos var18 = var32.d();
                              if (var2.nextInt(4) == 0 && var1.p(var36).c().t() == Material.a) {
                                 this.b(var1, var36, BlockVine.N);
                              }

                              if (var2.nextInt(4) == 0 && var1.p(var37).c().t() == Material.a) {
                                 this.b(var1, var37, BlockVine.P);
                              }

                              if (var2.nextInt(4) == 0 && var1.p(var38).c().t() == Material.a) {
                                 this.b(var1, var38, BlockVine.O);
                              }

                              if (var2.nextInt(4) == 0 && var1.p(var18).c().t() == Material.a) {
                                 this.b(var1, var18, BlockVine.b);
                              }
                           }
                        }
                     }
                  }

                  if (var2.nextInt(5) == 0 && var4 > 5) {
                     for (int var25 = 0; var25 < 2; var25++) {
                        for (EnumFacing var31 : EnumFacing.Plane.a) {
                           if (var2.nextInt(4 - var25) == 0) {
                              EnumFacing var33 = var31.d();
                              this.a(var1, var2.nextInt(3), var3.a(var33.g(), var4 - 5 + var25, var33.i()), var31);
                           }
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

   private void a(World var1, int var2, BlockPos var3, EnumFacing var4) {
      this.a(var1, var3, Blocks.bN.Q().a(BlockCocoa.a, var2).a(BlockCocoa.O, var4));
   }

   private void a(World var1, BlockPos var2, PropertyBool var3) {
      this.a(var1, var2, Blocks.bn.Q().a(var3, true));
   }

   private void b(World var1, BlockPos var2, PropertyBool var3) {
      this.a(var1, var2, var3);
      int var4 = 4;

      for (BlockPos var5 = var2.b(); var1.p(var5).c().t() == Material.a && var4 > 0; var4--) {
         this.a(var1, var5, var3);
         var5 = var5.b();
      }
   }
}

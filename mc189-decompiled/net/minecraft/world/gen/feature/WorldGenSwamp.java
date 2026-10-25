package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSwamp extends WorldGenAbstractTree {
   private static final IBlockState a = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.a);
   private static final IBlockState b = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.a).a(BlockOldLeaf.b, false);

   public WorldGenSwamp() {
      super(false);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = var2.nextInt(4) + 5;

      while (var1.p(var3.b()).c().t() == Material.h) {
         var3 = var3.b();
      }

      boolean var5 = true;
      if (var3.o() >= 1 && var3.o() + var4 + 1 <= 256) {
         for (int var6 = var3.o(); var6 <= var3.o() + 1 + var4; var6++) {
            byte var7 = 1;
            if (var6 == var3.o()) {
               var7 = 0;
            }

            if (var6 >= var3.o() + 1 + var4 - 2) {
               var7 = 3;
            }

            BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

            for (int var9 = var3.n() - var7; var9 <= var3.n() + var7 && var5; var9++) {
               for (int var10 = var3.p() - var7; var10 <= var3.p() + var7 && var5; var10++) {
                  if (var6 >= 0 && var6 < 256) {
                     Block var11 = var1.p(var8.c(var9, var6, var10)).c();
                     if (var11.t() != Material.a && var11.t() != Material.j) {
                        if (var11 != Blocks.j && var11 != Blocks.i) {
                           var5 = false;
                        } else if (var6 > var3.o()) {
                           var5 = false;
                        }
                     }
                  } else {
                     var5 = false;
                  }
               }
            }
         }

         if (!var5) {
            return false;
         } else {
            Block var17 = var1.p(var3.b()).c();
            if ((var17 == Blocks.c || var17 == Blocks.d) && var3.o() < 256 - var4 - 1) {
               this.a(var1, var3.b());

               for (int var18 = var3.o() - 3 + var4; var18 <= var3.o() + var4; var18++) {
                  int var21 = var18 - (var3.o() + var4);
                  int var24 = 2 - var21 / 2;

                  for (int var26 = var3.n() - var24; var26 <= var3.n() + var24; var26++) {
                     int var28 = var26 - var3.n();

                     for (int var12 = var3.p() - var24; var12 <= var3.p() + var24; var12++) {
                        int var13 = var12 - var3.p();
                        if (Math.abs(var28) != var24 || Math.abs(var13) != var24 || var2.nextInt(2) != 0 && var21 != 0) {
                           BlockPos var14 = new BlockPos(var26, var18, var12);
                           if (!var1.p(var14).c().o()) {
                              this.a(var1, var14, b);
                           }
                        }
                     }
                  }
               }

               for (int var19 = 0; var19 < var4; var19++) {
                  Block var22 = var1.p(var3.b(var19)).c();
                  if (var22.t() == Material.a || var22.t() == Material.j || var22 == Blocks.i || var22 == Blocks.j) {
                     this.a(var1, var3.b(var19), a);
                  }
               }

               for (int var20 = var3.o() - 3 + var4; var20 <= var3.o() + var4; var20++) {
                  int var23 = var20 - (var3.o() + var4);
                  int var25 = 2 - var23 / 2;
                  BlockPos.MutableBlockPos var27 = new BlockPos.MutableBlockPos();

                  for (int var29 = var3.n() - var25; var29 <= var3.n() + var25; var29++) {
                     for (int var30 = var3.p() - var25; var30 <= var3.p() + var25; var30++) {
                        var27.c(var29, var20, var30);
                        if (var1.p(var27).c().t() == Material.j) {
                           BlockPos var31 = var27.e();
                           BlockPos var32 = var27.f();
                           BlockPos var15 = var27.c();
                           BlockPos var16 = var27.d();
                           if (var2.nextInt(4) == 0 && var1.p(var31).c().t() == Material.a) {
                              this.a(var1, var31, BlockVine.N);
                           }

                           if (var2.nextInt(4) == 0 && var1.p(var32).c().t() == Material.a) {
                              this.a(var1, var32, BlockVine.P);
                           }

                           if (var2.nextInt(4) == 0 && var1.p(var15).c().t() == Material.a) {
                              this.a(var1, var15, BlockVine.O);
                           }

                           if (var2.nextInt(4) == 0 && var1.p(var16).c().t() == Material.a) {
                              this.a(var1, var16, BlockVine.b);
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

   private void a(World var1, BlockPos var2, PropertyBool var3) {
      IBlockState var4 = Blocks.bn.Q().a(var3, true);
      this.a(var1, var2, var4);
      int var5 = 4;

      for (BlockPos var6 = var2.b(); var1.p(var6).c().t() == Material.a && var5 > 0; var5--) {
         this.a(var1, var6, var4);
         var6 = var6.b();
      }
   }
}

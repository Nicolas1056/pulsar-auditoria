package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLeaves extends BlockLeavesBase {
   public static final PropertyBool a = PropertyBool.a("decayable");
   public static final PropertyBool b = PropertyBool.a("check_decay");
   int[] N;
   protected int O;
   protected boolean P;

   public BlockLeaves() {
      super(Material.j, false);
      this.a(true);
      this.a(CreativeTabs.c);
      this.c(0.2F);
      this.e(1);
      this.a(h);
   }

   @Override
   public int H() {
      return ColorizerFoliage.a(0.5, 1.0);
   }

   @Override
   public int h(IBlockState var1) {
      return ColorizerFoliage.c();
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.b(var1, var2);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      byte var4 = 1;
      int var5 = var4 + 1;
      int var6 = var2.n();
      int var7 = var2.o();
      int var8 = var2.p();
      if (var1.a(new BlockPos(var6 - var5, var7 - var5, var8 - var5), new BlockPos(var6 + var5, var7 + var5, var8 + var5))) {
         for (int var9 = -var4; var9 <= var4; var9++) {
            for (int var10 = -var4; var10 <= var4; var10++) {
               for (int var11 = -var4; var11 <= var4; var11++) {
                  BlockPos var12 = var2.a(var9, var10, var11);
                  IBlockState var13 = var1.p(var12);
                  if (var13.c().t() == Material.j && !var13.b(b)) {
                     var1.a(var12, var13.a(b, true), 4);
                  }
               }
            }
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var3.b(b) && var3.b(a)) {
            byte var5 = 4;
            int var6 = var5 + 1;
            int var7 = var2.n();
            int var8 = var2.o();
            int var9 = var2.p();
            byte var10 = 32;
            int var11 = var10 * var10;
            int var12 = var10 / 2;
            if (this.N == null) {
               this.N = new int[var10 * var10 * var10];
            }

            if (var1.a(new BlockPos(var7 - var6, var8 - var6, var9 - var6), new BlockPos(var7 + var6, var8 + var6, var9 + var6))) {
               BlockPos.MutableBlockPos var13 = new BlockPos.MutableBlockPos();

               for (int var14 = -var5; var14 <= var5; var14++) {
                  for (int var15 = -var5; var15 <= var5; var15++) {
                     for (int var16 = -var5; var16 <= var5; var16++) {
                        Block var17 = var1.p(var13.c(var7 + var14, var8 + var15, var9 + var16)).c();
                        if (var17 != Blocks.r && var17 != Blocks.s) {
                           if (var17.t() == Material.j) {
                              this.N[(var14 + var12) * var11 + (var15 + var12) * var10 + var16 + var12] = -2;
                           } else {
                              this.N[(var14 + var12) * var11 + (var15 + var12) * var10 + var16 + var12] = -1;
                           }
                        } else {
                           this.N[(var14 + var12) * var11 + (var15 + var12) * var10 + var16 + var12] = 0;
                        }
                     }
                  }
               }

               for (int var19 = 1; var19 <= 4; var19++) {
                  for (int var20 = -var5; var20 <= var5; var20++) {
                     for (int var21 = -var5; var21 <= var5; var21++) {
                        for (int var22 = -var5; var22 <= var5; var22++) {
                           if (this.N[(var20 + var12) * var11 + (var21 + var12) * var10 + var22 + var12] == var19 - 1) {
                              if (this.N[(var20 + var12 - 1) * var11 + (var21 + var12) * var10 + var22 + var12] == -2) {
                                 this.N[(var20 + var12 - 1) * var11 + (var21 + var12) * var10 + var22 + var12] = var19;
                              }

                              if (this.N[(var20 + var12 + 1) * var11 + (var21 + var12) * var10 + var22 + var12] == -2) {
                                 this.N[(var20 + var12 + 1) * var11 + (var21 + var12) * var10 + var22 + var12] = var19;
                              }

                              if (this.N[(var20 + var12) * var11 + (var21 + var12 - 1) * var10 + var22 + var12] == -2) {
                                 this.N[(var20 + var12) * var11 + (var21 + var12 - 1) * var10 + var22 + var12] = var19;
                              }

                              if (this.N[(var20 + var12) * var11 + (var21 + var12 + 1) * var10 + var22 + var12] == -2) {
                                 this.N[(var20 + var12) * var11 + (var21 + var12 + 1) * var10 + var22 + var12] = var19;
                              }

                              if (this.N[(var20 + var12) * var11 + (var21 + var12) * var10 + (var22 + var12 - 1)] == -2) {
                                 this.N[(var20 + var12) * var11 + (var21 + var12) * var10 + (var22 + var12 - 1)] = var19;
                              }

                              if (this.N[(var20 + var12) * var11 + (var21 + var12) * var10 + var22 + var12 + 1] == -2) {
                                 this.N[(var20 + var12) * var11 + (var21 + var12) * var10 + var22 + var12 + 1] = var19;
                              }
                           }
                        }
                     }
                  }
               }
            }

            int var18 = this.N[var12 * var11 + var12 * var10 + var12];
            if (var18 >= 0) {
               var1.a(var2, var3.a(b, false), 4);
            } else {
               this.e(var1, var2);
            }
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.C(var2.a()) && !World.a(var1, var2.b()) && var4.nextInt(15) == 1) {
         double var5 = var2.n() + var4.nextFloat();
         double var7 = var2.o() - 0.05;
         double var9 = var2.p() + var4.nextFloat();
         var1.a(EnumParticleTypes.s, var5, var7, var9, 0.0, 0.0, 0.0);
      }
   }

   private void e(World var1, BlockPos var2) {
      this.b(var1, var2, var1.p(var2), 0);
      var1.g(var2);
   }

   @Override
   public int a(Random var1) {
      return var1.nextInt(20) == 0 ? 1 : 0;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.g);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D) {
         int var6 = this.d(var3);
         if (var5 > 0) {
            var6 -= 2 << var5;
            if (var6 < 10) {
               var6 = 10;
            }
         }

         if (var1.s.nextInt(var6) == 0) {
            Item var7 = this.a(var3, var1.s, var5);
            a(var1, var2, new ItemStack(var7, 1, this.a(var3)));
         }

         var6 = 200;
         if (var5 > 0) {
            var6 -= 10 << var5;
            if (var6 < 40) {
               var6 = 40;
            }
         }

         this.a(var1, var2, var3, var6);
      }
   }

   protected void a(World var1, BlockPos var2, IBlockState var3, int var4) {
   }

   protected int d(IBlockState var1) {
      return 20;
   }

   @Override
   public boolean c() {
      return !this.R;
   }

   public void b(boolean var1) {
      this.P = var1;
      this.R = var1;
      this.O = var1 ? 0 : 1;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return this.P ? EnumWorldBlockLayer.b : EnumWorldBlockLayer.a;
   }

   @Override
   public boolean w() {
      return false;
   }

   public abstract BlockPlanks.EnumType b(int var1);
}

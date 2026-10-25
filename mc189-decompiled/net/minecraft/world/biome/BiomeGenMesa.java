package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMesa extends BiomeGenBase {
   private IBlockState[] aD;
   private long aE;
   private NoiseGeneratorPerlin aF;
   private NoiseGeneratorPerlin aG;
   private NoiseGeneratorPerlin aH;
   private boolean aI;
   private boolean aJ;

   public BiomeGenMesa(int var1, boolean var2, boolean var3) {
      super(var1);
      this.aI = var2;
      this.aJ = var3;
      this.b();
      this.a(2.0F, 0.0F);
      this.au.clear();
      this.ak = Blocks.m.Q().a(BlockSand.a, BlockSand.EnumType.b);
      this.al = Blocks.cu.Q();
      this.as.A = -999;
      this.as.D = 20;
      this.as.F = 3;
      this.as.G = 5;
      this.as.B = 0;
      this.au.clear();
      if (var3) {
         this.as.A = 5;
      }
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      return this.aA;
   }

   @Override
   public int c(BlockPos var1) {
      return 10387789;
   }

   @Override
   public int b(BlockPos var1) {
      return 9470285;
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      super.a(var1, var2, var3);
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      if (this.aD == null || this.aE != var1.J()) {
         this.a(var1.J());
      }

      if (this.aF == null || this.aG == null || this.aE != var1.J()) {
         Random var8 = new Random(this.aE);
         this.aF = new NoiseGeneratorPerlin(var8, 4);
         this.aG = new NoiseGeneratorPerlin(var8, 1);
      }

      this.aE = var1.J();
      double var22 = 0.0;
      if (this.aI) {
         int var10 = (var4 & -16) + (var5 & 15);
         int var11 = (var5 & -16) + (var4 & 15);
         double var12 = Math.min(Math.abs(var6), this.aF.a(var10 * 0.25, var11 * 0.25));
         if (var12 > 0.0) {
            double var14 = 0.001953125;
            double var16 = Math.abs(this.aG.a(var10 * var14, var11 * var14));
            var22 = var12 * var12 * 2.5;
            double var18 = Math.ceil(var16 * 50.0) + 14.0;
            if (var22 > var18) {
               var22 = var18;
            }

            var22 += 64.0;
         }
      }

      int var24 = var4 & 15;
      int var25 = var5 & 15;
      int var26 = var1.F();
      IBlockState var13 = Blocks.cu.Q();
      IBlockState var27 = this.al;
      int var15 = (int)(var6 / 3.0 + 3.0 + var2.nextDouble() * 0.25);
      boolean var28 = Math.cos(var6 / 3.0 * Math.PI) > 0.0;
      int var17 = -1;
      boolean var29 = false;

      for (int var19 = 255; var19 >= 0; var19--) {
         if (var3.a(var25, var19, var24).c().t() == Material.a && var19 < (int)var22) {
            var3.a(var25, var19, var24, Blocks.b.Q());
         }

         if (var19 <= var2.nextInt(5)) {
            var3.a(var25, var19, var24, Blocks.h.Q());
         } else {
            IBlockState var20 = var3.a(var25, var19, var24);
            if (var20.c().t() == Material.a) {
               var17 = -1;
            } else if (var20.c() == Blocks.b) {
               if (var17 == -1) {
                  var29 = false;
                  if (var15 <= 0) {
                     var13 = null;
                     var27 = Blocks.b.Q();
                  } else if (var19 >= var26 - 4 && var19 <= var26 + 1) {
                     var13 = Blocks.cu.Q();
                     var27 = this.al;
                  }

                  if (var19 < var26 && (var13 == null || var13.c().t() == Material.a)) {
                     var13 = Blocks.j.Q();
                  }

                  var17 = var15 + Math.max(0, var19 - var26);
                  if (var19 >= var26 - 1) {
                     if (!this.aJ || var19 <= 86 + var15 * 2) {
                        if (var19 > var26 + 3 + var15) {
                           IBlockState var21;
                           if (var19 < 64 || var19 > 127) {
                              var21 = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.b);
                           } else if (var28) {
                              var21 = Blocks.cz.Q();
                           } else {
                              var21 = this.a(var4, var19, var5);
                           }

                           var3.a(var25, var19, var24, var21);
                        } else {
                           var3.a(var25, var19, var24, this.ak);
                           var29 = true;
                        }
                     } else if (var28) {
                        var3.a(var25, var19, var24, Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.b));
                     } else {
                        var3.a(var25, var19, var24, Blocks.c.Q());
                     }
                  } else {
                     var3.a(var25, var19, var24, var27);
                     if (var27.c() == Blocks.cu) {
                        var3.a(var25, var19, var24, var27.c().Q().a(BlockColored.a, EnumDyeColor.b));
                     }
                  }
               } else if (var17 > 0) {
                  var17--;
                  if (var29) {
                     var3.a(var25, var19, var24, Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.b));
                  } else {
                     IBlockState var30 = this.a(var4, var19, var5);
                     var3.a(var25, var19, var24, var30);
                  }
               }
            }
         }
      }
   }

   private void a(long var1) {
      this.aD = new IBlockState[64];
      Arrays.fill(this.aD, Blocks.cz.Q());
      Random var3 = new Random(var1);
      this.aH = new NoiseGeneratorPerlin(var3, 1);

      for (int var12 = 0; var12 < 64; var12++) {
         var12 += var3.nextInt(5) + 1;
         if (var12 < 64) {
            this.aD[var12] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.b);
         }
      }

      int var13 = var3.nextInt(4) + 2;

      for (int var5 = 0; var5 < var13; var5++) {
         int var6 = var3.nextInt(3) + 1;
         int var7 = var3.nextInt(64);

         for (int var8 = 0; var7 + var8 < 64 && var8 < var6; var8++) {
            this.aD[var7 + var8] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.e);
         }
      }

      int var14 = var3.nextInt(4) + 2;

      for (int var15 = 0; var15 < var14; var15++) {
         int var17 = var3.nextInt(3) + 2;
         int var20 = var3.nextInt(64);

         for (int var9 = 0; var20 + var9 < 64 && var9 < var17; var9++) {
            this.aD[var20 + var9] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.m);
         }
      }

      int var16 = var3.nextInt(4) + 2;

      for (int var18 = 0; var18 < var16; var18++) {
         int var21 = var3.nextInt(3) + 1;
         int var23 = var3.nextInt(64);

         for (int var10 = 0; var23 + var10 < 64 && var10 < var21; var10++) {
            this.aD[var23 + var10] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.o);
         }
      }

      int var19 = var3.nextInt(3) + 3;
      int var22 = 0;

      for (int var24 = 0; var24 < var19; var24++) {
         byte var25 = 1;
         var22 += var3.nextInt(16) + 4;

         for (int var11 = 0; var22 + var11 < 64 && var11 < var25; var11++) {
            this.aD[var22 + var11] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.a);
            if (var22 + var11 > 1 && var3.nextBoolean()) {
               this.aD[var22 + var11 - 1] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.i);
            }

            if (var22 + var11 < 63 && var3.nextBoolean()) {
               this.aD[var22 + var11 + 1] = Blocks.cu.Q().a(BlockColored.a, EnumDyeColor.i);
            }
         }
      }
   }

   private IBlockState a(int var1, int var2, int var3) {
      int var4 = (int)Math.round(this.aH.a(var1 * 1.0 / 512.0, var1 * 1.0 / 512.0) * 2.0);
      return this.aD[(var2 + var4 + 64) % 64];
   }

   @Override
   protected BiomeGenBase d(int var1) {
      boolean var2 = this.az == BiomeGenBase.aa.az;
      BiomeGenMesa var3 = new BiomeGenMesa(var1, var2, this.aJ);
      if (!var2) {
         var3.a(g);
         var3.a(this.ah + " M");
      } else {
         var3.a(this.ah + " (Bryce)");
      }

      var3.a(this.ai, true);
      return var3;
   }
}

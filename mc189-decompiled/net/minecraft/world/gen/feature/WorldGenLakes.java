package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class WorldGenLakes extends WorldGenerator {
   private Block a;

   public WorldGenLakes(Block var1) {
      this.a = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      var3 = var3.a(-8, 0, -8);

      while (var3.o() > 5 && var1.d(var3)) {
         var3 = var3.b();
      }

      if (var3.o() <= 4) {
         return false;
      } else {
         var3 = var3.c(4);
         boolean[] var4 = new boolean[2048];
         int var5 = var2.nextInt(4) + 4;

         for (int var6 = 0; var6 < var5; var6++) {
            double var7 = var2.nextDouble() * 6.0 + 3.0;
            double var9 = var2.nextDouble() * 4.0 + 2.0;
            double var11 = var2.nextDouble() * 6.0 + 3.0;
            double var13 = var2.nextDouble() * (16.0 - var7 - 2.0) + 1.0 + var7 / 2.0;
            double var15 = var2.nextDouble() * (8.0 - var9 - 4.0) + 2.0 + var9 / 2.0;
            double var17 = var2.nextDouble() * (16.0 - var11 - 2.0) + 1.0 + var11 / 2.0;

            for (int var19 = 1; var19 < 15; var19++) {
               for (int var20 = 1; var20 < 15; var20++) {
                  for (int var21 = 1; var21 < 7; var21++) {
                     double var22 = (var19 - var13) / (var7 / 2.0);
                     double var24 = (var21 - var15) / (var9 / 2.0);
                     double var26 = (var20 - var17) / (var11 / 2.0);
                     double var28 = var22 * var22 + var24 * var24 + var26 * var26;
                     if (var28 < 1.0) {
                        var4[(var19 * 16 + var20) * 8 + var21] = true;
                     }
                  }
               }
            }
         }

         for (int var32 = 0; var32 < 16; var32++) {
            for (int var37 = 0; var37 < 16; var37++) {
               for (int var8 = 0; var8 < 8; var8++) {
                  boolean var46 = !var4[(var32 * 16 + var37) * 8 + var8]
                     && (
                        var32 < 15 && var4[((var32 + 1) * 16 + var37) * 8 + var8]
                           || var32 > 0 && var4[((var32 - 1) * 16 + var37) * 8 + var8]
                           || var37 < 15 && var4[(var32 * 16 + var37 + 1) * 8 + var8]
                           || var37 > 0 && var4[(var32 * 16 + (var37 - 1)) * 8 + var8]
                           || var8 < 7 && var4[(var32 * 16 + var37) * 8 + var8 + 1]
                           || var8 > 0 && var4[(var32 * 16 + var37) * 8 + (var8 - 1)]
                     );
                  if (var46) {
                     Material var10 = var1.p(var3.a(var32, var8, var37)).c().t();
                     if (var8 >= 4 && var10.d()) {
                        return false;
                     }

                     if (var8 < 4 && !var10.a() && var1.p(var3.a(var32, var8, var37)).c() != this.a) {
                        return false;
                     }
                  }
               }
            }
         }

         for (int var33 = 0; var33 < 16; var33++) {
            for (int var38 = 0; var38 < 16; var38++) {
               for (int var42 = 0; var42 < 8; var42++) {
                  if (var4[(var33 * 16 + var38) * 8 + var42]) {
                     var1.a(var3.a(var33, var42, var38), var42 >= 4 ? Blocks.a.Q() : this.a.Q(), 2);
                  }
               }
            }
         }

         for (int var34 = 0; var34 < 16; var34++) {
            for (int var39 = 0; var39 < 16; var39++) {
               for (int var43 = 4; var43 < 8; var43++) {
                  if (var4[(var34 * 16 + var39) * 8 + var43]) {
                     BlockPos var47 = var3.a(var34, var43 - 1, var39);
                     if (var1.p(var47).c() == Blocks.d && var1.b(EnumSkyBlock.a, var3.a(var34, var43, var39)) > 0) {
                        BiomeGenBase var49 = var1.b(var47);
                        if (var49.ak.c() == Blocks.bw) {
                           var1.a(var47, Blocks.bw.Q(), 2);
                        } else {
                           var1.a(var47, Blocks.c.Q(), 2);
                        }
                     }
                  }
               }
            }
         }

         if (this.a.t() == Material.i) {
            for (int var35 = 0; var35 < 16; var35++) {
               for (int var40 = 0; var40 < 16; var40++) {
                  for (int var44 = 0; var44 < 8; var44++) {
                     boolean var48 = !var4[(var35 * 16 + var40) * 8 + var44]
                        && (
                           var35 < 15 && var4[((var35 + 1) * 16 + var40) * 8 + var44]
                              || var35 > 0 && var4[((var35 - 1) * 16 + var40) * 8 + var44]
                              || var40 < 15 && var4[(var35 * 16 + var40 + 1) * 8 + var44]
                              || var40 > 0 && var4[(var35 * 16 + (var40 - 1)) * 8 + var44]
                              || var44 < 7 && var4[(var35 * 16 + var40) * 8 + var44 + 1]
                              || var44 > 0 && var4[(var35 * 16 + var40) * 8 + (var44 - 1)]
                        );
                     if (var48 && (var44 < 4 || var2.nextInt(2) != 0) && var1.p(var3.a(var35, var44, var40)).c().t().a()) {
                        var1.a(var3.a(var35, var44, var40), Blocks.b.Q(), 2);
                     }
                  }
               }
            }
         }

         if (this.a.t() == Material.h) {
            for (int var36 = 0; var36 < 16; var36++) {
               for (int var41 = 0; var41 < 16; var41++) {
                  byte var45 = 4;
                  if (var1.v(var3.a(var36, var45, var41))) {
                     var1.a(var3.a(var36, var45, var41), Blocks.aI.Q(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}

package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenIceSpike extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      while (var1.d(var3) && var3.o() > 2) {
         var3 = var3.b();
      }

      if (var1.p(var3).c() != Blocks.aJ) {
         return false;
      } else {
         var3 = var3.b(var2.nextInt(4));
         int var4 = var2.nextInt(4) + 7;
         int var5 = var4 / 4 + var2.nextInt(2);
         if (var5 > 1 && var2.nextInt(60) == 0) {
            var3 = var3.b(10 + var2.nextInt(30));
         }

         for (int var6 = 0; var6 < var4; var6++) {
            float var7 = (1.0F - (float)var6 / var4) * var5;
            int var8 = MathHelper.f(var7);

            for (int var9 = -var8; var9 <= var8; var9++) {
               float var10 = MathHelper.a(var9) - 0.25F;

               for (int var11 = -var8; var11 <= var8; var11++) {
                  float var12 = MathHelper.a(var11) - 0.25F;
                  if ((var9 == 0 && var11 == 0 || !(var10 * var10 + var12 * var12 > var7 * var7))
                     && (var9 != -var8 && var9 != var8 && var11 != -var8 && var11 != var8 || !(var2.nextFloat() > 0.75F))) {
                     Block var13 = var1.p(var3.a(var9, var6, var11)).c();
                     if (var13.t() == Material.a || var13 == Blocks.d || var13 == Blocks.aJ || var13 == Blocks.aI) {
                        this.a(var1, var3.a(var9, var6, var11), Blocks.cB.Q());
                     }

                     if (var6 != 0 && var8 > 1) {
                        var13 = var1.p(var3.a(var9, -var6, var11)).c();
                        if (var13.t() == Material.a || var13 == Blocks.d || var13 == Blocks.aJ || var13 == Blocks.aI) {
                           this.a(var1, var3.a(var9, -var6, var11), Blocks.cB.Q());
                        }
                     }
                  }
               }
            }
         }

         int var15 = var5 - 1;
         if (var15 < 0) {
            var15 = 0;
         } else if (var15 > 1) {
            var15 = 1;
         }

         for (int var16 = -var15; var16 <= var15; var16++) {
            for (int var17 = -var15; var17 <= var15; var17++) {
               BlockPos var18 = var3.a(var16, -1, var17);
               int var19 = 50;
               if (Math.abs(var16) == 1 && Math.abs(var17) == 1) {
                  var19 = var2.nextInt(5);
               }

               while (var18.o() > 50) {
                  Block var20 = var1.p(var18).c();
                  if (var20.t() != Material.a && var20 != Blocks.d && var20 != Blocks.aJ && var20 != Blocks.aI && var20 != Blocks.cB) {
                     break;
                  }

                  this.a(var1, var18, Blocks.cB.Q());
                  var18 = var18.b();
                  if (--var19 <= 0) {
                     var18 = var18.c(var2.nextInt(5) + 1);
                     var19 = var2.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}

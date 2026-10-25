package net.minecraft.world.gen.feature;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMinable extends WorldGenerator {
   private final IBlockState a;
   private final int b;
   private final Predicate<IBlockState> c;

   public WorldGenMinable(IBlockState var1, int var2) {
      this(var1, var2, BlockHelper.a(Blocks.b));
   }

   public WorldGenMinable(IBlockState var1, int var2, Predicate<IBlockState> var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      float var4 = var2.nextFloat() * (float) Math.PI;
      double var5 = var3.n() + 8 + MathHelper.a(var4) * this.b / 8.0F;
      double var7 = var3.n() + 8 - MathHelper.a(var4) * this.b / 8.0F;
      double var9 = var3.p() + 8 + MathHelper.b(var4) * this.b / 8.0F;
      double var11 = var3.p() + 8 - MathHelper.b(var4) * this.b / 8.0F;
      double var13 = var3.o() + var2.nextInt(3) - 2;
      double var15 = var3.o() + var2.nextInt(3) - 2;

      for (int var17 = 0; var17 < this.b; var17++) {
         float var18 = (float)var17 / this.b;
         double var19 = var5 + (var7 - var5) * var18;
         double var21 = var13 + (var15 - var13) * var18;
         double var23 = var9 + (var11 - var9) * var18;
         double var25 = var2.nextDouble() * this.b / 16.0;
         double var27 = (MathHelper.a((float) Math.PI * var18) + 1.0F) * var25 + 1.0;
         double var29 = (MathHelper.a((float) Math.PI * var18) + 1.0F) * var25 + 1.0;
         int var31 = MathHelper.c(var19 - var27 / 2.0);
         int var32 = MathHelper.c(var21 - var29 / 2.0);
         int var33 = MathHelper.c(var23 - var27 / 2.0);
         int var34 = MathHelper.c(var19 + var27 / 2.0);
         int var35 = MathHelper.c(var21 + var29 / 2.0);
         int var36 = MathHelper.c(var23 + var27 / 2.0);

         for (int var37 = var31; var37 <= var34; var37++) {
            double var38 = (var37 + 0.5 - var19) / (var27 / 2.0);
            if (var38 * var38 < 1.0) {
               for (int var40 = var32; var40 <= var35; var40++) {
                  double var41 = (var40 + 0.5 - var21) / (var29 / 2.0);
                  if (var38 * var38 + var41 * var41 < 1.0) {
                     for (int var43 = var33; var43 <= var36; var43++) {
                        double var44 = (var43 + 0.5 - var23) / (var27 / 2.0);
                        if (var38 * var38 + var41 * var41 + var44 * var44 < 1.0) {
                           BlockPos var46 = new BlockPos(var37, var40, var43);
                           if (this.c.apply(var1.p(var46))) {
                              var1.a(var46, this.a, 2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}

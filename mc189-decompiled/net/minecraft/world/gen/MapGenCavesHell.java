package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MapGenCavesHell extends MapGenBase {
   protected void a(long var1, int var3, int var4, ChunkPrimer var5, double var6, double var8, double var10) {
      this.a(var1, var3, var4, var5, var6, var8, var10, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5);
   }

   protected void a(
      long var1,
      int var3,
      int var4,
      ChunkPrimer var5,
      double var6,
      double var8,
      double var10,
      float var12,
      float var13,
      float var14,
      int var15,
      int var16,
      double var17
   ) {
      double var19 = var3 * 16 + 8;
      double var21 = var4 * 16 + 8;
      float var23 = 0.0F;
      float var24 = 0.0F;
      Random var25 = new Random(var1);
      if (var16 <= 0) {
         int var26 = this.a * 16 - 16;
         var16 = var26 - var25.nextInt(var26 / 4);
      }

      boolean var55 = false;
      if (var15 == -1) {
         var15 = var16 / 2;
         var55 = true;
      }

      int var27 = var25.nextInt(var16 / 2) + var16 / 4;

      for (boolean var28 = var25.nextInt(6) == 0; var15 < var16; var15++) {
         double var29 = 1.5 + MathHelper.a(var15 * (float) Math.PI / var16) * var12 * 1.0F;
         double var31 = var29 * var17;
         float var33 = MathHelper.b(var14);
         float var34 = MathHelper.a(var14);
         var6 += MathHelper.b(var13) * var33;
         var8 += var34;
         var10 += MathHelper.a(var13) * var33;
         if (var28) {
            var14 *= 0.92F;
         } else {
            var14 *= 0.7F;
         }

         var14 += var24 * 0.1F;
         var13 += var23 * 0.1F;
         var24 *= 0.9F;
         var23 *= 0.75F;
         var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
         var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;
         if (!var55 && var15 == var27 && var12 > 1.0F) {
            this.a(
               var25.nextLong(),
               var3,
               var4,
               var5,
               var6,
               var8,
               var10,
               var25.nextFloat() * 0.5F + 0.5F,
               var13 - (float) (Math.PI / 2),
               var14 / 3.0F,
               var15,
               var16,
               1.0
            );
            this.a(
               var25.nextLong(),
               var3,
               var4,
               var5,
               var6,
               var8,
               var10,
               var25.nextFloat() * 0.5F + 0.5F,
               var13 + (float) (Math.PI / 2),
               var14 / 3.0F,
               var15,
               var16,
               1.0
            );
            return;
         }

         if (var55 || var25.nextInt(4) != 0) {
            double var35 = var6 - var19;
            double var37 = var10 - var21;
            double var39 = var16 - var15;
            double var41 = var12 + 2.0F + 16.0F;
            if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
               return;
            }

            if (!(var6 < var19 - 16.0 - var29 * 2.0)
               && !(var10 < var21 - 16.0 - var29 * 2.0)
               && !(var6 > var19 + 16.0 + var29 * 2.0)
               && !(var10 > var21 + 16.0 + var29 * 2.0)) {
               int var56 = MathHelper.c(var6 - var29) - var3 * 16 - 1;
               int var36 = MathHelper.c(var6 + var29) - var3 * 16 + 1;
               int var57 = MathHelper.c(var8 - var31) - 1;
               int var38 = MathHelper.c(var8 + var31) + 1;
               int var58 = MathHelper.c(var10 - var29) - var4 * 16 - 1;
               int var40 = MathHelper.c(var10 + var29) - var4 * 16 + 1;
               if (var56 < 0) {
                  var56 = 0;
               }

               if (var36 > 16) {
                  var36 = 16;
               }

               if (var57 < 1) {
                  var57 = 1;
               }

               if (var38 > 120) {
                  var38 = 120;
               }

               if (var58 < 0) {
                  var58 = 0;
               }

               if (var40 > 16) {
                  var40 = 16;
               }

               boolean var59 = false;

               for (int var42 = var56; !var59 && var42 < var36; var42++) {
                  for (int var43 = var58; !var59 && var43 < var40; var43++) {
                     for (int var44 = var38 + 1; !var59 && var44 >= var57 - 1; var44--) {
                        if (var44 >= 0 && var44 < 128) {
                           IBlockState var45 = var5.a(var42, var44, var43);
                           if (var45.c() == Blocks.k || var45.c() == Blocks.l) {
                              var59 = true;
                           }

                           if (var44 != var57 - 1 && var42 != var56 && var42 != var36 - 1 && var43 != var58 && var43 != var40 - 1) {
                              var44 = var57;
                           }
                        }
                     }
                  }
               }

               if (!var59) {
                  for (int var60 = var56; var60 < var36; var60++) {
                     double var61 = (var60 + var3 * 16 + 0.5 - var6) / var29;

                     for (int var62 = var58; var62 < var40; var62++) {
                        double var46 = (var62 + var4 * 16 + 0.5 - var10) / var29;

                        for (int var48 = var38; var48 > var57; var48--) {
                           double var49 = (var48 - 1 + 0.5 - var8) / var31;
                           if (var49 > -0.7 && var61 * var61 + var49 * var49 + var46 * var46 < 1.0) {
                              IBlockState var51 = var5.a(var60, var48, var62);
                              if (var51.c() == Blocks.aV || var51.c() == Blocks.d || var51.c() == Blocks.c) {
                                 var5.a(var60, var48, var62, Blocks.a.Q());
                              }
                           }
                        }
                     }
                  }

                  if (var55) {
                     break;
                  }
               }
            }
         }
      }
   }

   @Override
   protected void a(World var1, int var2, int var3, int var4, int var5, ChunkPrimer var6) {
      int var7 = this.b.nextInt(this.b.nextInt(this.b.nextInt(10) + 1) + 1);
      if (this.b.nextInt(5) != 0) {
         var7 = 0;
      }

      for (int var8 = 0; var8 < var7; var8++) {
         double var9 = var2 * 16 + this.b.nextInt(16);
         double var11 = this.b.nextInt(128);
         double var13 = var3 * 16 + this.b.nextInt(16);
         int var15 = 1;
         if (this.b.nextInt(4) == 0) {
            this.a(this.b.nextLong(), var4, var5, var6, var9, var11, var13);
            var15 += this.b.nextInt(4);
         }

         for (int var16 = 0; var16 < var15; var16++) {
            float var17 = this.b.nextFloat() * (float) Math.PI * 2.0F;
            float var18 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float var19 = this.b.nextFloat() * 2.0F + this.b.nextFloat();
            this.a(this.b.nextLong(), var4, var5, var6, var9, var11, var13, var19 * 2.0F, var17, var18, 0, 0, 0.5);
         }
      }
   }
}

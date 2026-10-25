package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenMegaJungle extends WorldGenHugeTrees {
   public WorldGenMegaJungle(boolean var1, int var2, int var3, IBlockState var4, IBlockState var5) {
      super(var1, var2, var3, var4, var5);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      int var4 = this.a(var2);
      if (!this.a(var1, var2, var3, var4)) {
         return false;
      } else {
         this.c(var1, var3.b(var4), 2);

         for (int var5 = var3.o() + var4 - 2 - var2.nextInt(4); var5 > var3.o() + var4 / 2; var5 -= 2 + var2.nextInt(4)) {
            float var6 = var2.nextFloat() * (float) Math.PI * 2.0F;
            int var7 = var3.n() + (int)(0.5F + MathHelper.b(var6) * 4.0F);
            int var8 = var3.p() + (int)(0.5F + MathHelper.a(var6) * 4.0F);

            for (int var9 = 0; var9 < 5; var9++) {
               var7 = var3.n() + (int)(1.5F + MathHelper.b(var6) * var9);
               var8 = var3.p() + (int)(1.5F + MathHelper.a(var6) * var9);
               this.a(var1, new BlockPos(var7, var5 - 3 + var9 / 2, var8), this.b);
            }

            int var16 = 1 + var2.nextInt(2);
            int var10 = var5;

            for (int var11 = var5 - var16; var11 <= var10; var11++) {
               int var12 = var11 - var10;
               this.b(var1, new BlockPos(var7, var11, var8), 1 - var12);
            }
         }

         for (int var13 = 0; var13 < var4; var13++) {
            BlockPos var14 = var3.b(var13);
            if (this.a(var1.p(var14).c())) {
               this.a(var1, var14, this.b);
               if (var13 > 0) {
                  this.a(var1, var2, var14.e(), BlockVine.N);
                  this.a(var1, var2, var14.c(), BlockVine.O);
               }
            }

            if (var13 < var4 - 1) {
               BlockPos var15 = var14.f();
               if (this.a(var1.p(var15).c())) {
                  this.a(var1, var15, this.b);
                  if (var13 > 0) {
                     this.a(var1, var2, var15.f(), BlockVine.P);
                     this.a(var1, var2, var15.c(), BlockVine.O);
                  }
               }

               BlockPos var17 = var14.d().f();
               if (this.a(var1.p(var17).c())) {
                  this.a(var1, var17, this.b);
                  if (var13 > 0) {
                     this.a(var1, var2, var17.f(), BlockVine.P);
                     this.a(var1, var2, var17.d(), BlockVine.b);
                  }
               }

               BlockPos var18 = var14.d();
               if (this.a(var1.p(var18).c())) {
                  this.a(var1, var18, this.b);
                  if (var13 > 0) {
                     this.a(var1, var2, var18.e(), BlockVine.N);
                     this.a(var1, var2, var18.d(), BlockVine.b);
                  }
               }
            }
         }

         return true;
      }
   }

   private void a(World var1, Random var2, BlockPos var3, PropertyBool var4) {
      if (var2.nextInt(3) > 0 && var1.d(var3)) {
         this.a(var1, var3, Blocks.bn.Q().a(var4, true));
      }
   }

   private void c(World var1, BlockPos var2, int var3) {
      byte var4 = 2;

      for (int var5 = -var4; var5 <= 0; var5++) {
         this.a(var1, var2.b(var5), var3 + 1 - var5);
      }
   }
}

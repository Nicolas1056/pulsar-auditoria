package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenShrub extends WorldGenTrees {
   private final IBlockState a;
   private final IBlockState b;

   public WorldGenShrub(IBlockState var1, IBlockState var2) {
      super(false);
      this.b = var1;
      this.a = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      Block var4;
      while (((var4 = var1.p(var3).c()).t() == Material.a || var4.t() == Material.j) && var3.o() > 0) {
         var3 = var3.b();
      }

      Block var5 = var1.p(var3).c();
      if (var5 == Blocks.d || var5 == Blocks.c) {
         var3 = var3.a();
         this.a(var1, var3, this.b);

         for (int var6 = var3.o(); var6 <= var3.o() + 2; var6++) {
            int var7 = var6 - var3.o();
            int var8 = 2 - var7;

            for (int var9 = var3.n() - var8; var9 <= var3.n() + var8; var9++) {
               int var10 = var9 - var3.n();

               for (int var11 = var3.p() - var8; var11 <= var3.p() + var8; var11++) {
                  int var12 = var11 - var3.p();
                  if (Math.abs(var10) != var8 || Math.abs(var12) != var8 || var2.nextInt(2) != 0) {
                     BlockPos var13 = new BlockPos(var9, var6, var11);
                     if (!var1.p(var13).c().o()) {
                        this.a(var1, var13, this.a);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}

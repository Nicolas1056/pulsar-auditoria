package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSand extends WorldGenerator {
   private Block a;
   private int b;

   public WorldGenSand(Block var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (var1.p(var3).c().t() != Material.h) {
         return false;
      } else {
         int var4 = var2.nextInt(this.b - 2) + 2;
         byte var5 = 2;

         for (int var6 = var3.n() - var4; var6 <= var3.n() + var4; var6++) {
            for (int var7 = var3.p() - var4; var7 <= var3.p() + var4; var7++) {
               int var8 = var6 - var3.n();
               int var9 = var7 - var3.p();
               if (var8 * var8 + var9 * var9 <= var4 * var4) {
                  for (int var10 = var3.o() - var5; var10 <= var3.o() + var5; var10++) {
                     BlockPos var11 = new BlockPos(var6, var10, var7);
                     Block var12 = var1.p(var11).c();
                     if (var12 == Blocks.d || var12 == Blocks.c) {
                        var1.a(var11, this.a.Q(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}

package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenClay extends WorldGenerator {
   private Block a = Blocks.aL;
   private int b;

   public WorldGenClay(int var1) {
      this.b = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (var1.p(var3).c().t() != Material.h) {
         return false;
      } else {
         int var4 = var2.nextInt(this.b - 2) + 2;
         byte var5 = 1;

         for (int var6 = var3.n() - var4; var6 <= var3.n() + var4; var6++) {
            for (int var7 = var3.p() - var4; var7 <= var3.p() + var4; var7++) {
               int var8 = var6 - var3.n();
               int var9 = var7 - var3.p();
               if (var8 * var8 + var9 * var9 <= var4 * var4) {
                  for (int var10 = var3.o() - var5; var10 <= var3.o() + var5; var10++) {
                     BlockPos var11 = new BlockPos(var6, var10, var7);
                     Block var12 = var1.p(var11).c();
                     if (var12 == Blocks.d || var12 == Blocks.aL) {
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

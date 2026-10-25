package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSpikes extends WorldGenerator {
   private Block a;

   public WorldGenSpikes(Block var1) {
      this.a = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (var1.d(var3) && var1.p(var3.b()).c() == this.a) {
         int var4 = var2.nextInt(32) + 6;
         int var5 = var2.nextInt(4) + 1;
         BlockPos.MutableBlockPos var6 = new BlockPos.MutableBlockPos();

         for (int var7 = var3.n() - var5; var7 <= var3.n() + var5; var7++) {
            for (int var8 = var3.p() - var5; var8 <= var3.p() + var5; var8++) {
               int var9 = var7 - var3.n();
               int var10 = var8 - var3.p();
               if (var9 * var9 + var10 * var10 <= var5 * var5 + 1 && var1.p(var6.c(var7, var3.o() - 1, var8)).c() != this.a) {
                  return false;
               }
            }
         }

         for (int var12 = var3.o(); var12 < var3.o() + var4 && var12 < 256; var12++) {
            for (int var14 = var3.n() - var5; var14 <= var3.n() + var5; var14++) {
               for (int var15 = var3.p() - var5; var15 <= var3.p() + var5; var15++) {
                  int var16 = var14 - var3.n();
                  int var11 = var15 - var3.p();
                  if (var16 * var16 + var11 * var11 <= var5 * var5 + 1) {
                     var1.a(new BlockPos(var14, var12, var15), Blocks.Z.Q(), 2);
                  }
               }
            }
         }

         EntityEnderCrystal var13 = new EntityEnderCrystal(var1);
         var13.b(var3.n() + 0.5F, var3.o() + var4, var3.p() + 0.5F, var2.nextFloat() * 360.0F, 0.0F);
         var1.d(var13);
         var1.a(var3.b(var4), Blocks.h.Q(), 2);
         return true;
      } else {
         return false;
      }
   }
}

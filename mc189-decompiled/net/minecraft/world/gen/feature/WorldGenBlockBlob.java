package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator {
   private final Block a;
   private final int b;

   public WorldGenBlockBlob(Block var1, int var2) {
      super(false);
      this.a = var1;
      this.b = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      while (var3.o() > 3) {
         if (!var1.d(var3.b())) {
            Block var4 = var1.p(var3.b()).c();
            if (var4 == Blocks.c || var4 == Blocks.d || var4 == Blocks.b) {
               break;
            }
         }

         var3 = var3.b();
      }

      if (var3.o() <= 3) {
         return false;
      } else {
         int var12 = this.b;

         for (int var5 = 0; var12 >= 0 && var5 < 3; var5++) {
            int var6 = var12 + var2.nextInt(2);
            int var7 = var12 + var2.nextInt(2);
            int var8 = var12 + var2.nextInt(2);
            float var9 = (var6 + var7 + var8) * 0.333F + 0.5F;

            for (BlockPos var11 : BlockPos.a(var3.a(-var6, -var7, -var8), var3.a(var6, var7, var8))) {
               if (var11.i(var3) <= var9 * var9) {
                  var1.a(var11, this.a.Q(), 4);
               }
            }

            var3 = var3.a(-(var12 + 1) + var2.nextInt(2 + var12 * 2), 0 - var2.nextInt(2), -(var12 + 1) + var2.nextInt(2 + var12 * 2));
         }

         return true;
      }
   }
}

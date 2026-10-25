package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenGlowStone2 extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (!var1.d(var3)) {
         return false;
      } else if (var1.p(var3.a()).c() != Blocks.aV) {
         return false;
      } else {
         var1.a(var3, Blocks.aX.Q(), 2);

         for (int var4 = 0; var4 < 1500; var4++) {
            BlockPos var5 = var3.a(var2.nextInt(8) - var2.nextInt(8), -var2.nextInt(12), var2.nextInt(8) - var2.nextInt(8));
            if (var1.p(var5).c().t() == Material.a) {
               int var6 = 0;

               for (EnumFacing var10 : EnumFacing.values()) {
                  if (var1.p(var5.a(var10)).c() == Blocks.aX) {
                     var6++;
                  }

                  if (var6 > 1) {
                     break;
                  }
               }

               if (var6 == 1) {
                  var1.a(var5, Blocks.aX.Q(), 2);
               }
            }
         }

         return true;
      }
   }
}

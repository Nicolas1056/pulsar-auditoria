package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenDeadBush extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      Block var4;
      while (((var4 = var1.p(var3).c()).t() == Material.a || var4.t() == Material.j) && var3.o() > 0) {
         var3 = var3.b();
      }

      for (int var5 = 0; var5 < 4; var5++) {
         BlockPos var6 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var6) && Blocks.I.f(var1, var6, Blocks.I.Q())) {
            var1.a(var6, Blocks.I.Q(), 2);
         }
      }

      return true;
   }
}

package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenFire extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      for (int var4 = 0; var4 < 64; var4++) {
         BlockPos var5 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var5) && var1.p(var5.b()).c() == Blocks.aV) {
            var1.a(var5, Blocks.ab.Q(), 2);
         }
      }

      return true;
   }
}

package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenPumpkin extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      for (int var4 = 0; var4 < 64; var4++) {
         BlockPos var5 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var5) && var1.p(var5.b()).c() == Blocks.c && Blocks.aU.d(var1, var5)) {
            var1.a(var5, Blocks.aU.Q().a(BlockPumpkin.O, EnumFacing.Plane.a.a(var2)), 2);
         }
      }

      return true;
   }
}

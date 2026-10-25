package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenDoublePlant extends WorldGenerator {
   private BlockDoublePlant.EnumPlantType a;

   public void a(BlockDoublePlant.EnumPlantType var1) {
      this.a = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      boolean var4 = false;

      for (int var5 = 0; var5 < 64; var5++) {
         BlockPos var6 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var6) && (!var1.t.o() || var6.o() < 254) && Blocks.cF.d(var1, var6)) {
            Blocks.cF.a(var1, var6, this.a, 2);
            var4 = true;
         }
      }

      return var4;
   }
}

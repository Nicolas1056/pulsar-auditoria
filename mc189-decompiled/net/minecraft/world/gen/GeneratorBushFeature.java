package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockBush;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GeneratorBushFeature extends WorldGenerator {
   private BlockBush a;

   public GeneratorBushFeature(BlockBush var1) {
      this.a = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      for (int var4 = 0; var4 < 64; var4++) {
         BlockPos var5 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var5) && (!var1.t.o() || var5.o() < 255) && this.a.f(var1, var5, this.a.Q())) {
            var1.a(var5, this.a.Q(), 2);
         }
      }

      return true;
   }
}

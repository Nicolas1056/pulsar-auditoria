package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenFlowers extends WorldGenerator {
   private BlockFlower a;
   private IBlockState b;

   public WorldGenFlowers(BlockFlower var1, BlockFlower.EnumFlowerType var2) {
      this.a(var1, var2);
   }

   public void a(BlockFlower var1, BlockFlower.EnumFlowerType var2) {
      this.a = var1;
      this.b = var1.Q().a(var1.n(), var2);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      for (int var4 = 0; var4 < 64; var4++) {
         BlockPos var5 = var3.a(var2.nextInt(8) - var2.nextInt(8), var2.nextInt(4) - var2.nextInt(4), var2.nextInt(8) - var2.nextInt(8));
         if (var1.d(var5) && (!var1.t.o() || var5.o() < 255) && this.a.f(var1, var5, this.b)) {
            var1.a(var5, this.b, 2);
         }
      }

      return true;
   }
}

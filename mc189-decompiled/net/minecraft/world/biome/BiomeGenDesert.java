package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenDesert extends BiomeGenBase {
   public BiomeGenDesert(int var1) {
      super(var1);
      this.au.clear();
      this.ak = Blocks.m.Q();
      this.al = Blocks.m.Q();
      this.as.A = -999;
      this.as.D = 2;
      this.as.F = 50;
      this.as.G = 10;
      this.au.clear();
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      super.a(var1, var2, var3);
      if (var2.nextInt(1000) == 0) {
         int var4 = var2.nextInt(16) + 8;
         int var5 = var2.nextInt(16) + 8;
         BlockPos var6 = var1.m(var3.a(var4, 0, var5)).a();
         new WorldGenDesertWells().b(var1, var2, var6);
      }
   }
}

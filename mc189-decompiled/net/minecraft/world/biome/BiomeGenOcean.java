package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class BiomeGenOcean extends BiomeGenBase {
   public BiomeGenOcean(int var1) {
      super(var1);
      this.au.clear();
   }

   @Override
   public BiomeGenBase.TempCategory m() {
      return BiomeGenBase.TempCategory.a;
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      super.a(var1, var2, var3, var4, var5, var6);
   }
}

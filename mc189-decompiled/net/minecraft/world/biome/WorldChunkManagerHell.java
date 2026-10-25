package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.util.BlockPos;

public class WorldChunkManagerHell extends WorldChunkManager {
   private BiomeGenBase b;
   private float c;

   public WorldChunkManagerHell(BiomeGenBase var1, float var2) {
      this.b = var1;
      this.c = var2;
   }

   @Override
   public BiomeGenBase a(BlockPos var1) {
      return this.b;
   }

   @Override
   public BiomeGenBase[] a(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.b);
      return var1;
   }

   @Override
   public float[] a(float[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new float[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.c);
      return var1;
   }

   @Override
   public BiomeGenBase[] b(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.b);
      return var1;
   }

   @Override
   public BiomeGenBase[] a(BiomeGenBase[] var1, int var2, int var3, int var4, int var5, boolean var6) {
      return this.b(var1, var2, var3, var4, var5);
   }

   @Override
   public BlockPos a(int var1, int var2, int var3, List<BiomeGenBase> var4, Random var5) {
      return var4.contains(this.b) ? new BlockPos(var1 - var3 + var5.nextInt(var3 * 2 + 1), 0, var2 - var3 + var5.nextInt(var3 * 2 + 1)) : null;
   }

   @Override
   public boolean a(int var1, int var2, int var3, List<BiomeGenBase> var4) {
      return var4.contains(this.b);
   }
}

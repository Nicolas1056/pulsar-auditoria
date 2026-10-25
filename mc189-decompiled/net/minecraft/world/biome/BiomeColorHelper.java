package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BiomeColorHelper {
   private static final BiomeColorHelper.ColorResolver a = new BiomeColorHelper.ColorResolver() {
      @Override
      public int a(BiomeGenBase var1, BlockPos var2) {
         return var1.b(var2);
      }
   };
   private static final BiomeColorHelper.ColorResolver b = new BiomeColorHelper.ColorResolver() {
      @Override
      public int a(BiomeGenBase var1, BlockPos var2) {
         return var1.c(var2);
      }
   };
   private static final BiomeColorHelper.ColorResolver c = new BiomeColorHelper.ColorResolver() {
      @Override
      public int a(BiomeGenBase var1, BlockPos var2) {
         return var1.ar;
      }
   };

   private static int a(IBlockAccess var0, BlockPos var1, BiomeColorHelper.ColorResolver var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      for (BlockPos.MutableBlockPos var7 : BlockPos.b(var1.a(-1, 0, -1), var1.a(1, 0, 1))) {
         int var8 = var2.a(var0.b(var7), var7);
         var3 += (var8 & 0xFF0000) >> 16;
         var4 += (var8 & 0xFF00) >> 8;
         var5 += var8 & 0xFF;
      }

      return (var3 / 9 & 0xFF) << 16 | (var4 / 9 & 0xFF) << 8 | var5 / 9 & 0xFF;
   }

   public static int a(IBlockAccess var0, BlockPos var1) {
      return a(var0, var1, a);
   }

   public static int b(IBlockAccess var0, BlockPos var1) {
      return a(var0, var1, b);
   }

   public static int c(IBlockAccess var0, BlockPos var1) {
      return a(var0, var1, c);
   }

   interface ColorResolver {
      int a(BiomeGenBase var1, BlockPos var2);
   }
}

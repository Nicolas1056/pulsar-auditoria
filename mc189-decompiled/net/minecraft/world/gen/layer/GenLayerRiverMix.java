package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiverMix extends GenLayer {
   private GenLayer c;
   private GenLayer d;

   public GenLayerRiverMix(long var1, GenLayer var3, GenLayer var4) {
      super(var1);
      this.c = var3;
      this.d = var4;
   }

   @Override
   public void a(long var1) {
      this.c.a(var1);
      this.d.a(var1);
      super.a(var1);
   }

   @Override
   public int[] a(int var1, int var2, int var3, int var4) {
      int[] var5 = this.c.a(var1, var2, var3, var4);
      int[] var6 = this.d.a(var1, var2, var3, var4);
      int[] var7 = IntCache.a(var3 * var4);

      for (int var8 = 0; var8 < var3 * var4; var8++) {
         if (var5[var8] == BiomeGenBase.p.az || var5[var8] == BiomeGenBase.N.az) {
            var7[var8] = var5[var8];
         } else if (var6[var8] == BiomeGenBase.w.az) {
            if (var5[var8] == BiomeGenBase.B.az) {
               var7[var8] = BiomeGenBase.A.az;
            } else if (var5[var8] != BiomeGenBase.D.az && var5[var8] != BiomeGenBase.E.az) {
               var7[var8] = var6[var8] & 0xFF;
            } else {
               var7[var8] = BiomeGenBase.E.az;
            }
         } else {
            var7[var8] = var5[var8];
         }
      }

      return var7;
   }
}

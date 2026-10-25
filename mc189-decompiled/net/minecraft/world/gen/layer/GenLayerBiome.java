package net.minecraft.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public class GenLayerBiome extends GenLayer {
   private BiomeGenBase[] c = new BiomeGenBase[]{BiomeGenBase.r, BiomeGenBase.r, BiomeGenBase.r, BiomeGenBase.Y, BiomeGenBase.Y, BiomeGenBase.q};
   private BiomeGenBase[] d = new BiomeGenBase[]{BiomeGenBase.t, BiomeGenBase.S, BiomeGenBase.s, BiomeGenBase.q, BiomeGenBase.Q, BiomeGenBase.v};
   private BiomeGenBase[] e = new BiomeGenBase[]{BiomeGenBase.t, BiomeGenBase.s, BiomeGenBase.u, BiomeGenBase.q};
   private BiomeGenBase[] f = new BiomeGenBase[]{BiomeGenBase.B, BiomeGenBase.B, BiomeGenBase.B, BiomeGenBase.T};
   private final ChunkProviderSettings g;

   public GenLayerBiome(long var1, GenLayer var3, WorldType var4, String var5) {
      super(var1);
      this.a = var3;
      if (var4 == WorldType.h) {
         this.c = new BiomeGenBase[]{BiomeGenBase.r, BiomeGenBase.t, BiomeGenBase.s, BiomeGenBase.v, BiomeGenBase.q, BiomeGenBase.u};
         this.g = null;
      } else if (var4 == WorldType.f) {
         this.g = ChunkProviderSettings.Factory.a(var5).b();
      } else {
         this.g = null;
      }
   }

   @Override
   public int[] a(int var1, int var2, int var3, int var4) {
      int[] var5 = this.a.a(var1, var2, var3, var4);
      int[] var6 = IntCache.a(var3 * var4);

      for (int var7 = 0; var7 < var4; var7++) {
         for (int var8 = 0; var8 < var3; var8++) {
            this.a(var8 + var1, var7 + var2);
            int var9 = var5[var8 + var7 * var3];
            int var10 = (var9 & 3840) >> 8;
            var9 &= -3841;
            if (this.g != null && this.g.F >= 0) {
               var6[var8 + var7 * var3] = this.g.F;
            } else if (b(var9)) {
               var6[var8 + var7 * var3] = var9;
            } else if (var9 == BiomeGenBase.D.az) {
               var6[var8 + var7 * var3] = var9;
            } else if (var9 == 1) {
               if (var10 > 0) {
                  if (this.a(3) == 0) {
                     var6[var8 + var7 * var3] = BiomeGenBase.ac.az;
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.ab.az;
                  }
               } else {
                  var6[var8 + var7 * var3] = this.c[this.a(this.c.length)].az;
               }
            } else if (var9 == 2) {
               if (var10 > 0) {
                  var6[var8 + var7 * var3] = BiomeGenBase.K.az;
               } else {
                  var6[var8 + var7 * var3] = this.d[this.a(this.d.length)].az;
               }
            } else if (var9 == 3) {
               if (var10 > 0) {
                  var6[var8 + var7 * var3] = BiomeGenBase.V.az;
               } else {
                  var6[var8 + var7 * var3] = this.e[this.a(this.e.length)].az;
               }
            } else if (var9 == 4) {
               var6[var8 + var7 * var3] = this.f[this.a(this.f.length)].az;
            } else {
               var6[var8 + var7 * var3] = BiomeGenBase.D.az;
            }
         }
      }

      return var6;
   }
}

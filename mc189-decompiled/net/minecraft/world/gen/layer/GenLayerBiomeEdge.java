package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerBiomeEdge extends GenLayer {
   public GenLayerBiomeEdge(long var1, GenLayer var3) {
      super(var1);
      this.a = var3;
   }

   @Override
   public int[] a(int var1, int var2, int var3, int var4) {
      int[] var5 = this.a.a(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
      int[] var6 = IntCache.a(var3 * var4);

      for (int var7 = 0; var7 < var4; var7++) {
         for (int var8 = 0; var8 < var3; var8++) {
            this.a(var8 + var1, var7 + var2);
            int var9 = var5[var8 + 1 + (var7 + 1) * (var3 + 2)];
            if (!this.a(var5, var6, var8, var7, var3, var9, BiomeGenBase.s.az, BiomeGenBase.J.az)
               && !this.b(var5, var6, var8, var7, var3, var9, BiomeGenBase.ab.az, BiomeGenBase.aa.az)
               && !this.b(var5, var6, var8, var7, var3, var9, BiomeGenBase.ac.az, BiomeGenBase.aa.az)
               && !this.b(var5, var6, var8, var7, var3, var9, BiomeGenBase.V.az, BiomeGenBase.u.az)) {
               if (var9 == BiomeGenBase.r.az) {
                  int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                  int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                  int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                  int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                  if (var10 != BiomeGenBase.B.az && var11 != BiomeGenBase.B.az && var12 != BiomeGenBase.B.az && var13 != BiomeGenBase.B.az) {
                     var6[var8 + var7 * var3] = var9;
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.X.az;
                  }
               } else if (var9 == BiomeGenBase.v.az) {
                  int var14 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                  int var15 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                  int var16 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                  int var17 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                  if (var14 == BiomeGenBase.r.az
                     || var15 == BiomeGenBase.r.az
                     || var16 == BiomeGenBase.r.az
                     || var17 == BiomeGenBase.r.az
                     || var14 == BiomeGenBase.T.az
                     || var15 == BiomeGenBase.T.az
                     || var16 == BiomeGenBase.T.az
                     || var17 == BiomeGenBase.T.az
                     || var14 == BiomeGenBase.B.az
                     || var15 == BiomeGenBase.B.az
                     || var16 == BiomeGenBase.B.az
                     || var17 == BiomeGenBase.B.az) {
                     var6[var8 + var7 * var3] = BiomeGenBase.q.az;
                  } else if (var14 != BiomeGenBase.K.az && var17 != BiomeGenBase.K.az && var15 != BiomeGenBase.K.az && var16 != BiomeGenBase.K.az) {
                     var6[var8 + var7 * var3] = var9;
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.M.az;
                  }
               } else {
                  var6[var8 + var7 * var3] = var9;
               }
            }
         }
      }

      return var6;
   }

   private boolean a(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (!a(var6, var7)) {
         return false;
      } else {
         int var9 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var12 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if (this.b(var9, var7) && this.b(var10, var7) && this.b(var11, var7) && this.b(var12, var7)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var8;
         }

         return true;
      }
   }

   private boolean b(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var6 != var7) {
         return false;
      } else {
         int var9 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var12 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if (a(var9, var7) && a(var10, var7) && a(var11, var7) && a(var12, var7)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var8;
         }

         return true;
      }
   }

   private boolean b(int var1, int var2) {
      if (a(var1, var2)) {
         return true;
      } else {
         BiomeGenBase var3 = BiomeGenBase.e(var1);
         BiomeGenBase var4 = BiomeGenBase.e(var2);
         if (var3 != null && var4 != null) {
            BiomeGenBase.TempCategory var5 = var3.m();
            BiomeGenBase.TempCategory var6 = var4.m();
            return var5 == var6 || var5 == BiomeGenBase.TempCategory.c || var6 == BiomeGenBase.TempCategory.c;
         } else {
            return false;
         }
      }
   }
}

package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;

public class GenLayerShore extends GenLayer {
   public GenLayerShore(long var1, GenLayer var3) {
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
            BiomeGenBase var10 = BiomeGenBase.e(var9);
            if (var9 == BiomeGenBase.D.az) {
               int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if (var11 != BiomeGenBase.p.az && var12 != BiomeGenBase.p.az && var13 != BiomeGenBase.p.az && var14 != BiomeGenBase.p.az) {
                  var6[var8 + var7 * var3] = var9;
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.E.az;
               }
            } else if (var10 != null && var10.l() == BiomeGenJungle.class) {
               int var17 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var20 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var23 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var26 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if (!this.c(var17) || !this.c(var20) || !this.c(var23) || !this.c(var26)) {
                  var6[var8 + var7 * var3] = BiomeGenBase.M.az;
               } else if (!b(var17) && !b(var20) && !b(var23) && !b(var26)) {
                  var6[var8 + var7 * var3] = var9;
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.F.az;
               }
            } else if (var9 == BiomeGenBase.s.az || var9 == BiomeGenBase.X.az || var9 == BiomeGenBase.J.az) {
               this.a(var5, var6, var8, var7, var3, var9, BiomeGenBase.O.az);
            } else if (var10 != null && var10.j()) {
               this.a(var5, var6, var8, var7, var3, var9, BiomeGenBase.P.az);
            } else if (var9 == BiomeGenBase.aa.az || var9 == BiomeGenBase.ab.az) {
               int var16 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var19 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var22 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var25 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if (b(var16) || b(var19) || b(var22) || b(var25)) {
                  var6[var8 + var7 * var3] = var9;
               } else if (this.d(var16) && this.d(var19) && this.d(var22) && this.d(var25)) {
                  var6[var8 + var7 * var3] = var9;
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.r.az;
               }
            } else if (var9 != BiomeGenBase.p.az && var9 != BiomeGenBase.N.az && var9 != BiomeGenBase.w.az && var9 != BiomeGenBase.v.az) {
               int var15 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var18 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var21 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var24 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if (!b(var15) && !b(var18) && !b(var21) && !b(var24)) {
                  var6[var8 + var7 * var3] = var9;
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.F.az;
               }
            } else {
               var6[var8 + var7 * var3] = var9;
            }
         }
      }

      return var6;
   }

   private void a(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7) {
      if (b(var6)) {
         var2[var3 + var4 * var5] = var6;
      } else {
         int var8 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var9 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if (!b(var8) && !b(var9) && !b(var10) && !b(var11)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var7;
         }
      }
   }

   private boolean c(int var1) {
      return BiomeGenBase.e(var1) != null && BiomeGenBase.e(var1).l() == BiomeGenJungle.class
         ? true
         : var1 == BiomeGenBase.M.az
            || var1 == BiomeGenBase.K.az
            || var1 == BiomeGenBase.L.az
            || var1 == BiomeGenBase.t.az
            || var1 == BiomeGenBase.u.az
            || b(var1);
   }

   private boolean d(int var1) {
      return BiomeGenBase.e(var1) instanceof BiomeGenMesa;
   }
}

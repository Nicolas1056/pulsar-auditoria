package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerHills extends GenLayer {
   private static final Logger c = LogManager.getLogger();
   private GenLayer d;

   public GenLayerHills(long var1, GenLayer var3, GenLayer var4) {
      super(var1);
      this.a = var3;
      this.d = var4;
   }

   @Override
   public int[] a(int var1, int var2, int var3, int var4) {
      int[] var5 = this.a.a(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
      int[] var6 = this.d.a(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
      int[] var7 = IntCache.a(var3 * var4);

      for (int var8 = 0; var8 < var4; var8++) {
         for (int var9 = 0; var9 < var3; var9++) {
            this.a(var9 + var1, var8 + var2);
            int var10 = var5[var9 + 1 + (var8 + 1) * (var3 + 2)];
            int var11 = var6[var9 + 1 + (var8 + 1) * (var3 + 2)];
            boolean var12 = (var11 - 2) % 29 == 0;
            if (var10 > 255) {
               c.debug("old! " + var10);
            }

            if (var10 != 0 && var11 >= 2 && (var11 - 2) % 29 == 1 && var10 < 128) {
               if (BiomeGenBase.e(var10 + 128) != null) {
                  var7[var9 + var8 * var3] = var10 + 128;
               } else {
                  var7[var9 + var8 * var3] = var10;
               }
            } else if (this.a(3) != 0 && !var12) {
               var7[var9 + var8 * var3] = var10;
            } else {
               int var13 = var10;
               if (var10 == BiomeGenBase.r.az) {
                  var13 = BiomeGenBase.G.az;
               } else if (var10 == BiomeGenBase.t.az) {
                  var13 = BiomeGenBase.H.az;
               } else if (var10 == BiomeGenBase.Q.az) {
                  var13 = BiomeGenBase.R.az;
               } else if (var10 == BiomeGenBase.S.az) {
                  var13 = BiomeGenBase.q.az;
               } else if (var10 == BiomeGenBase.u.az) {
                  var13 = BiomeGenBase.I.az;
               } else if (var10 == BiomeGenBase.V.az) {
                  var13 = BiomeGenBase.W.az;
               } else if (var10 == BiomeGenBase.T.az) {
                  var13 = BiomeGenBase.U.az;
               } else if (var10 == BiomeGenBase.q.az) {
                  if (this.a(3) == 0) {
                     var13 = BiomeGenBase.H.az;
                  } else {
                     var13 = BiomeGenBase.t.az;
                  }
               } else if (var10 == BiomeGenBase.B.az) {
                  var13 = BiomeGenBase.C.az;
               } else if (var10 == BiomeGenBase.K.az) {
                  var13 = BiomeGenBase.L.az;
               } else if (var10 == BiomeGenBase.p.az) {
                  var13 = BiomeGenBase.N.az;
               } else if (var10 == BiomeGenBase.s.az) {
                  var13 = BiomeGenBase.X.az;
               } else if (var10 == BiomeGenBase.Y.az) {
                  var13 = BiomeGenBase.Z.az;
               } else if (a(var10, BiomeGenBase.ab.az)) {
                  var13 = BiomeGenBase.aa.az;
               } else if (var10 == BiomeGenBase.N.az && this.a(3) == 0) {
                  int var14 = this.a(2);
                  if (var14 == 0) {
                     var13 = BiomeGenBase.q.az;
                  } else {
                     var13 = BiomeGenBase.t.az;
                  }
               }

               if (var12 && var13 != var10) {
                  if (BiomeGenBase.e(var13 + 128) != null) {
                     var13 += 128;
                  } else {
                     var13 = var10;
                  }
               }

               if (var13 == var10) {
                  var7[var9 + var8 * var3] = var10;
               } else {
                  int var19 = var5[var9 + 1 + (var8 + 1 - 1) * (var3 + 2)];
                  int var15 = var5[var9 + 1 + 1 + (var8 + 1) * (var3 + 2)];
                  int var16 = var5[var9 + 1 - 1 + (var8 + 1) * (var3 + 2)];
                  int var17 = var5[var9 + 1 + (var8 + 1 + 1) * (var3 + 2)];
                  int var18 = 0;
                  if (a(var19, var10)) {
                     var18++;
                  }

                  if (a(var15, var10)) {
                     var18++;
                  }

                  if (a(var16, var10)) {
                     var18++;
                  }

                  if (a(var17, var10)) {
                     var18++;
                  }

                  if (var18 >= 3) {
                     var7[var9 + var8 * var3] = var13;
                  } else {
                     var7[var9 + var8 * var3] = var10;
                  }
               }
            }
         }
      }

      return var7;
   }
}

package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public abstract class GenLayer {
   private long c;
   protected GenLayer a;
   private long d;
   protected long b;

   public static GenLayer[] a(long var0, WorldType var2, String var3) {
      GenLayerIsland var4 = new GenLayerIsland(1L);
      GenLayerFuzzyZoom var13 = new GenLayerFuzzyZoom(2000L, var4);
      GenLayerAddIsland var14 = new GenLayerAddIsland(1L, var13);
      GenLayerZoom var15 = new GenLayerZoom(2001L, var14);
      GenLayerAddIsland var16 = new GenLayerAddIsland(2L, var15);
      GenLayerAddIsland var17 = new GenLayerAddIsland(50L, var16);
      GenLayerAddIsland var18 = new GenLayerAddIsland(70L, var17);
      GenLayerRemoveTooMuchOcean var19 = new GenLayerRemoveTooMuchOcean(2L, var18);
      GenLayerAddSnow var20 = new GenLayerAddSnow(2L, var19);
      GenLayerAddIsland var21 = new GenLayerAddIsland(3L, var20);
      GenLayerEdge var22 = new GenLayerEdge(2L, var21, GenLayerEdge.Mode.a);
      GenLayerEdge var23 = new GenLayerEdge(2L, var22, GenLayerEdge.Mode.b);
      GenLayerEdge var24 = new GenLayerEdge(3L, var23, GenLayerEdge.Mode.c);
      GenLayerZoom var25 = new GenLayerZoom(2002L, var24);
      GenLayerZoom var26 = new GenLayerZoom(2003L, var25);
      GenLayerAddIsland var27 = new GenLayerAddIsland(4L, var26);
      GenLayerAddMushroomIsland var28 = new GenLayerAddMushroomIsland(5L, var27);
      GenLayerDeepOcean var29 = new GenLayerDeepOcean(4L, var28);
      GenLayer var30 = GenLayerZoom.b(1000L, var29, 0);
      ChunkProviderSettings var5 = null;
      int var6 = 4;
      int var7 = var6;
      if (var2 == WorldType.f && var3.length() > 0) {
         var5 = ChunkProviderSettings.Factory.a(var3).b();
         var6 = var5.G;
         var7 = var5.H;
      }

      if (var2 == WorldType.d) {
         var6 = 6;
      }

      GenLayer var8 = GenLayerZoom.b(1000L, var30, 0);
      GenLayerRiverInit var32 = new GenLayerRiverInit(100L, var8);
      GenLayerBiome var9 = new GenLayerBiome(200L, var30, var2, var3);
      GenLayer var37 = GenLayerZoom.b(1000L, var9, 2);
      GenLayerBiomeEdge var38 = new GenLayerBiomeEdge(1000L, var37);
      GenLayer var10 = GenLayerZoom.b(1000L, var32, 2);
      GenLayerHills var39 = new GenLayerHills(1000L, var38, var10);
      var8 = GenLayerZoom.b(1000L, var32, 2);
      var8 = GenLayerZoom.b(1000L, var8, var7);
      GenLayerRiver var35 = new GenLayerRiver(1L, var8);
      GenLayerSmooth var36 = new GenLayerSmooth(1000L, var35);
      var9 = new GenLayerRareBiome(1001L, var39);

      for (int var11 = 0; var11 < var6; var11++) {
         var9 = new GenLayerZoom(1000 + var11, var9);
         if (var11 == 0) {
            var9 = new GenLayerAddIsland(3L, var9);
         }

         if (var11 == 1 || var6 == 1) {
            var9 = new GenLayerShore(1000L, var9);
         }
      }

      GenLayerSmooth var41 = new GenLayerSmooth(1000L, var9);
      GenLayerRiverMix var42 = new GenLayerRiverMix(100L, var41, var36);
      GenLayerVoronoiZoom var12 = new GenLayerVoronoiZoom(10L, var42);
      var42.a(var0);
      var12.a(var0);
      return new GenLayer[]{var42, var12, var42};
   }

   public GenLayer(long var1) {
      this.b = var1;
      this.b = this.b * (this.b * 6364136223846793005L + 1442695040888963407L);
      this.b += var1;
      this.b = this.b * (this.b * 6364136223846793005L + 1442695040888963407L);
      this.b += var1;
      this.b = this.b * (this.b * 6364136223846793005L + 1442695040888963407L);
      this.b += var1;
   }

   public void a(long var1) {
      this.c = var1;
      if (this.a != null) {
         this.a.a(var1);
      }

      this.c = this.c * (this.c * 6364136223846793005L + 1442695040888963407L);
      this.c = this.c + this.b;
      this.c = this.c * (this.c * 6364136223846793005L + 1442695040888963407L);
      this.c = this.c + this.b;
      this.c = this.c * (this.c * 6364136223846793005L + 1442695040888963407L);
      this.c = this.c + this.b;
   }

   public void a(long var1, long var3) {
      this.d = this.c;
      this.d = this.d * (this.d * 6364136223846793005L + 1442695040888963407L);
      this.d += var1;
      this.d = this.d * (this.d * 6364136223846793005L + 1442695040888963407L);
      this.d += var3;
      this.d = this.d * (this.d * 6364136223846793005L + 1442695040888963407L);
      this.d += var1;
      this.d = this.d * (this.d * 6364136223846793005L + 1442695040888963407L);
      this.d += var3;
   }

   protected int a(int var1) {
      int var2 = (int)((this.d >> 24) % var1);
      if (var2 < 0) {
         var2 += var1;
      }

      this.d = this.d * (this.d * 6364136223846793005L + 1442695040888963407L);
      this.d = this.d + this.c;
      return var2;
   }

   public abstract int[] a(int var1, int var2, int var3, int var4);

   protected static boolean a(int var0, int var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 != BiomeGenBase.ab.az && var0 != BiomeGenBase.ac.az) {
         final BiomeGenBase var2 = BiomeGenBase.e(var0);
         final BiomeGenBase var3 = BiomeGenBase.e(var1);

         try {
            return var2 != null && var3 != null ? var2.a(var3) : false;
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.a(var7, "Comparing biomes");
            CrashReportCategory var6 = var5.a("Biomes being compared");
            var6.a("Biome A ID", var0);
            var6.a("Biome B ID", var1);
            var6.a("Biome A", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var2);
               }
            });
            var6.a("Biome B", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(var3);
               }
            });
            throw new ReportedException(var5);
         }
      } else {
         return var1 == BiomeGenBase.ab.az || var1 == BiomeGenBase.ac.az;
      }
   }

   protected static boolean b(int var0) {
      return var0 == BiomeGenBase.p.az || var0 == BiomeGenBase.N.az || var0 == BiomeGenBase.z.az;
   }

   protected int a(int... var1) {
      return var1[this.a(var1.length)];
   }

   protected int b(int var1, int var2, int var3, int var4) {
      if (var2 == var3 && var3 == var4) {
         return var2;
      } else if (var1 == var2 && var1 == var3) {
         return var1;
      } else if (var1 == var2 && var1 == var4) {
         return var1;
      } else if (var1 == var3 && var1 == var4) {
         return var1;
      } else if (var1 == var2 && var3 != var4) {
         return var1;
      } else if (var1 == var3 && var2 != var4) {
         return var1;
      } else if (var1 == var4 && var2 != var3) {
         return var1;
      } else if (var2 == var3 && var1 != var4) {
         return var2;
      } else if (var2 == var4 && var1 != var3) {
         return var2;
      } else {
         return var3 == var4 && var1 != var2 ? var3 : this.a(var1, var2, var3, var4);
      }
   }
}

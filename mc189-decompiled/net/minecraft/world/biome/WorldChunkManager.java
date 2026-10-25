package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManager {
   private GenLayer b;
   private GenLayer c;
   private BiomeCache d = new BiomeCache(this);
   private List<BiomeGenBase> e;
   private String f = "";

   protected WorldChunkManager() {
      this.e = Lists.newArrayList();
      this.e.add(BiomeGenBase.t);
      this.e.add(BiomeGenBase.q);
      this.e.add(BiomeGenBase.u);
      this.e.add(BiomeGenBase.I);
      this.e.add(BiomeGenBase.H);
      this.e.add(BiomeGenBase.K);
      this.e.add(BiomeGenBase.L);
   }

   public WorldChunkManager(long var1, WorldType var3, String var4) {
      this();
      this.f = var4;
      GenLayer[] var5 = GenLayer.a(var1, var3, var4);
      this.b = var5[0];
      this.c = var5[1];
   }

   public WorldChunkManager(World var1) {
      this(var1.J(), var1.P().u(), var1.P().B());
   }

   public List<BiomeGenBase> a() {
      return this.e;
   }

   public BiomeGenBase a(BlockPos var1) {
      return this.a(var1, null);
   }

   public BiomeGenBase a(BlockPos var1, BiomeGenBase var2) {
      return this.d.a(var1.n(), var1.p(), var2);
   }

   public float[] a(float[] var1, int var2, int var3, int var4, int var5) {
      IntCache.a();
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new float[var4 * var5];
      }

      int[] var6 = this.c.a(var2, var3, var4, var5);

      for (int var7 = 0; var7 < var4 * var5; var7++) {
         try {
            float var8 = BiomeGenBase.a(var6[var7], BiomeGenBase.ad).h() / 65536.0F;
            if (var8 > 1.0F) {
               var8 = 1.0F;
            }

            var1[var7] = var8;
         } catch (Throwable var11) {
            CrashReport var9 = CrashReport.a(var11, "Invalid Biome id");
            CrashReportCategory var10 = var9.a("DownfallBlock");
            var10.a("biome id", var7);
            var10.a("downfalls[] size", var1.length);
            var10.a("x", var2);
            var10.a("z", var3);
            var10.a("w", var4);
            var10.a("h", var5);
            throw new ReportedException(var9);
         }
      }

      return var1;
   }

   public float a(float var1, int var2) {
      return var1;
   }

   public BiomeGenBase[] a(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      IntCache.a();
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      int[] var6 = this.b.a(var2, var3, var4, var5);

      try {
         for (int var7 = 0; var7 < var4 * var5; var7++) {
            var1[var7] = BiomeGenBase.a(var6[var7], BiomeGenBase.ad);
         }

         return var1;
      } catch (Throwable var10) {
         CrashReport var8 = CrashReport.a(var10, "Invalid Biome id");
         CrashReportCategory var9 = var8.a("RawBiomeBlock");
         var9.a("biomes[] size", var1.length);
         var9.a("x", var2);
         var9.a("z", var3);
         var9.a("w", var4);
         var9.a("h", var5);
         throw new ReportedException(var8);
      }
   }

   public BiomeGenBase[] b(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      return this.a(var1, var2, var3, var4, var5, true);
   }

   public BiomeGenBase[] a(BiomeGenBase[] var1, int var2, int var3, int var4, int var5, boolean var6) {
      IntCache.a();
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      if (var6 && var4 == 16 && var5 == 16 && (var2 & 15) == 0 && (var3 & 15) == 0) {
         BiomeGenBase[] var9 = this.d.c(var2, var3);
         System.arraycopy(var9, 0, var1, 0, var4 * var5);
         return var1;
      } else {
         int[] var7 = this.c.a(var2, var3, var4, var5);

         for (int var8 = 0; var8 < var4 * var5; var8++) {
            var1[var8] = BiomeGenBase.a(var7[var8], BiomeGenBase.ad);
         }

         return var1;
      }
   }

   public boolean a(int var1, int var2, int var3, List<BiomeGenBase> var4) {
      IntCache.a();
      int var5 = var1 - var3 >> 2;
      int var6 = var2 - var3 >> 2;
      int var7 = var1 + var3 >> 2;
      int var8 = var2 + var3 >> 2;
      int var9 = var7 - var5 + 1;
      int var10 = var8 - var6 + 1;
      int[] var11 = this.b.a(var5, var6, var9, var10);

      try {
         for (int var12 = 0; var12 < var9 * var10; var12++) {
            BiomeGenBase var16 = BiomeGenBase.e(var11[var12]);
            if (!var4.contains(var16)) {
               return false;
            }
         }

         return true;
      } catch (Throwable var15) {
         CrashReport var13 = CrashReport.a(var15, "Invalid Biome id");
         CrashReportCategory var14 = var13.a("Layer");
         var14.a("Layer", this.b.toString());
         var14.a("x", var1);
         var14.a("z", var2);
         var14.a("radius", var3);
         var14.a("allowed", var4);
         throw new ReportedException(var13);
      }
   }

   public BlockPos a(int var1, int var2, int var3, List<BiomeGenBase> var4, Random var5) {
      IntCache.a();
      int var6 = var1 - var3 >> 2;
      int var7 = var2 - var3 >> 2;
      int var8 = var1 + var3 >> 2;
      int var9 = var2 + var3 >> 2;
      int var10 = var8 - var6 + 1;
      int var11 = var9 - var7 + 1;
      int[] var12 = this.b.a(var6, var7, var10, var11);
      BlockPos var13 = null;
      int var14 = 0;

      for (int var15 = 0; var15 < var10 * var11; var15++) {
         int var16 = var6 + var15 % var10 << 2;
         int var17 = var7 + var15 / var10 << 2;
         BiomeGenBase var18 = BiomeGenBase.e(var12[var15]);
         if (var4.contains(var18) && (var13 == null || var5.nextInt(var14 + 1) == 0)) {
            var13 = new BlockPos(var16, 0, var17);
            var14++;
         }
      }

      return var13;
   }

   public void b() {
      this.d.a();
   }
}

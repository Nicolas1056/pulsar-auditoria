package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   public static ChunkLoader.AnvilConverterData a(NBTTagCompound var0) {
      int var1 = var0.f("xPos");
      int var2 = var0.f("zPos");
      ChunkLoader.AnvilConverterData var3 = new ChunkLoader.AnvilConverterData(var1, var2);
      var3.g = var0.k("Blocks");
      var3.f = new NibbleArrayReader(var0.k("Data"), 7);
      var3.e = new NibbleArrayReader(var0.k("SkyLight"), 7);
      var3.d = new NibbleArrayReader(var0.k("BlockLight"), 7);
      var3.c = var0.k("HeightMap");
      var3.b = var0.n("TerrainPopulated");
      var3.h = var0.c("Entities", 10);
      var3.i = var0.c("TileEntities", 10);
      var3.j = var0.c("TileTicks", 10);

      try {
         var3.a = var0.g("LastUpdate");
      } catch (ClassCastException var5) {
         var3.a = var0.f("LastUpdate");
      }

      return var3;
   }

   public static void a(ChunkLoader.AnvilConverterData var0, NBTTagCompound var1, WorldChunkManager var2) {
      var1.a("xPos", var0.k);
      var1.a("zPos", var0.l);
      var1.a("LastUpdate", var0.a);
      int[] var3 = new int[var0.c.length];

      for (int var4 = 0; var4 < var0.c.length; var4++) {
         var3[var4] = var0.c[var4];
      }

      var1.a("HeightMap", var3);
      var1.a("TerrainPopulated", var0.b);
      NBTTagList var16 = new NBTTagList();

      for (int var5 = 0; var5 < 8; var5++) {
         boolean var6 = true;

         for (int var7 = 0; var7 < 16 && var6; var7++) {
            for (int var8 = 0; var8 < 16 && var6; var8++) {
               for (int var9 = 0; var9 < 16; var9++) {
                  int var10 = var7 << 11 | var9 << 7 | var8 + (var5 << 4);
                  byte var11 = var0.g[var10];
                  if (var11 != 0) {
                     var6 = false;
                     break;
                  }
               }
            }
         }

         if (!var6) {
            byte[] var19 = new byte[4096];
            NibbleArray var21 = new NibbleArray();
            NibbleArray var23 = new NibbleArray();
            NibbleArray var24 = new NibbleArray();

            for (int var25 = 0; var25 < 16; var25++) {
               for (int var12 = 0; var12 < 16; var12++) {
                  for (int var13 = 0; var13 < 16; var13++) {
                     int var14 = var25 << 11 | var13 << 7 | var12 + (var5 << 4);
                     byte var15 = var0.g[var14];
                     var19[var12 << 8 | var13 << 4 | var25] = (byte)(var15 & 255);
                     var21.a(var25, var12, var13, var0.f.a(var25, var12 + (var5 << 4), var13));
                     var23.a(var25, var12, var13, var0.e.a(var25, var12 + (var5 << 4), var13));
                     var24.a(var25, var12, var13, var0.d.a(var25, var12 + (var5 << 4), var13));
                  }
               }
            }

            NBTTagCompound var26 = new NBTTagCompound();
            var26.a("Y", (byte)(var5 & 0xFF));
            var26.a("Blocks", var19);
            var26.a("Data", var21.a());
            var26.a("SkyLight", var23.a());
            var26.a("BlockLight", var24.a());
            var16.a(var26);
         }
      }

      var1.a("Sections", var16);
      byte[] var17 = new byte[256];
      BlockPos.MutableBlockPos var18 = new BlockPos.MutableBlockPos();

      for (int var20 = 0; var20 < 16; var20++) {
         for (int var22 = 0; var22 < 16; var22++) {
            var18.c(var0.k << 4 | var20, 0, var0.l << 4 | var22);
            var17[var22 << 4 | var20] = (byte)(var2.a(var18, BiomeGenBase.ad).az & 0xFF);
         }
      }

      var1.a("Biomes", var17);
      var1.a("Entities", var0.h);
      var1.a("TileEntities", var0.i);
      if (var0.j != null) {
         var1.a("TileTicks", var0.j);
      }
   }

   public static class AnvilConverterData {
      public long a;
      public boolean b;
      public byte[] c;
      public NibbleArrayReader d;
      public NibbleArrayReader e;
      public NibbleArrayReader f;
      public byte[] g;
      public NBTTagList h;
      public NBTTagList i;
      public NBTTagList j;
      public final int k;
      public final int l;

      public AnvilConverterData(int var1, int var2) {
         this.k = var1;
         this.l = var2;
      }
   }
}

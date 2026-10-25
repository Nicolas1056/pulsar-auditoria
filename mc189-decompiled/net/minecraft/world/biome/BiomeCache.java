package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;

public class BiomeCache {
   private final WorldChunkManager a;
   private long b;
   private LongHashMap<BiomeCache.Block> c = new LongHashMap<>();
   private List<BiomeCache.Block> d = Lists.newArrayList();

   public BiomeCache(WorldChunkManager var1) {
      this.a = var1;
   }

   public BiomeCache.Block a(int var1, int var2) {
      var1 >>= 4;
      var2 >>= 4;
      long var3 = var1 & 4294967295L | (var2 & 4294967295L) << 32;
      BiomeCache.Block var5 = this.c.a(var3);
      if (var5 == null) {
         var5 = new BiomeCache.Block(var1, var2);
         this.c.a(var3, var5);
         this.d.add(var5);
      }

      var5.e = MinecraftServer.az();
      return var5;
   }

   public BiomeGenBase a(int var1, int var2, BiomeGenBase var3) {
      BiomeGenBase var4 = this.a(var1, var2).a(var1, var2);
      return var4 == null ? var3 : var4;
   }

   public void a() {
      long var1 = MinecraftServer.az();
      long var3 = var1 - this.b;
      if (var3 > 7500L || var3 < 0L) {
         this.b = var1;

         for (int var5 = 0; var5 < this.d.size(); var5++) {
            BiomeCache.Block var6 = this.d.get(var5);
            long var7 = var1 - var6.e;
            if (var7 > 30000L || var7 < 0L) {
               this.d.remove(var5--);
               long var9 = var6.c & 4294967295L | (var6.d & 4294967295L) << 32;
               this.c.d(var9);
            }
         }
      }
   }

   public BiomeGenBase[] c(int var1, int var2) {
      return this.a(var1, var2).b;
   }

   public class Block {
      public float[] a = new float[256];
      public BiomeGenBase[] b = new BiomeGenBase[256];
      public int c;
      public int d;
      public long e;

      public Block(int var2, int var3) {
         this.c = var2;
         this.d = var3;
         BiomeCache.this.a.a(this.a, var2 << 4, var3 << 4, 16, 16);
         BiomeCache.this.a.a(this.b, var2 << 4, var3 << 4, 16, 16, false);
      }

      public BiomeGenBase a(int var1, int var2) {
         return this.b[var1 & 15 | (var2 & 15) << 4];
      }
   }
}

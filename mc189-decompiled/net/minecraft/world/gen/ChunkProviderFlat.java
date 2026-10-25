package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

public class ChunkProviderFlat implements IChunkProvider {
   private World a;
   private Random b;
   private final IBlockState[] c = new IBlockState[256];
   private final FlatGeneratorInfo d;
   private final List<MapGenStructure> e = Lists.newArrayList();
   private final boolean f;
   private final boolean g;
   private WorldGenLakes h;
   private WorldGenLakes i;

   public ChunkProviderFlat(World var1, long var2, boolean var4, String var5) {
      this.a = var1;
      this.b = new Random(var2);
      this.d = FlatGeneratorInfo.a(var5);
      if (var4) {
         Map var6 = this.d.b();
         if (var6.containsKey("village")) {
            Map var7 = (Map)var6.get("village");
            if (!var7.containsKey("size")) {
               var7.put("size", "1");
            }

            this.e.add(new MapGenVillage(var7));
         }

         if (var6.containsKey("biome_1")) {
            this.e.add(new MapGenScatteredFeature((Map<String, String>)var6.get("biome_1")));
         }

         if (var6.containsKey("mineshaft")) {
            this.e.add(new MapGenMineshaft((Map<String, String>)var6.get("mineshaft")));
         }

         if (var6.containsKey("stronghold")) {
            this.e.add(new MapGenStronghold((Map<String, String>)var6.get("stronghold")));
         }

         if (var6.containsKey("oceanmonument")) {
            this.e.add(new StructureOceanMonument((Map<String, String>)var6.get("oceanmonument")));
         }
      }

      if (this.d.b().containsKey("lake")) {
         this.h = new WorldGenLakes(Blocks.j);
      }

      if (this.d.b().containsKey("lava_lake")) {
         this.i = new WorldGenLakes(Blocks.l);
      }

      this.g = this.d.b().containsKey("dungeon");
      int var13 = 0;
      int var14 = 0;
      boolean var8 = true;

      for (FlatLayerInfo var10 : this.d.c()) {
         for (int var11 = var10.d(); var11 < var10.d() + var10.b(); var11++) {
            IBlockState var12 = var10.c();
            if (var12.c() != Blocks.a) {
               var8 = false;
               this.c[var11] = var12;
            }
         }

         if (var10.c().c() == Blocks.a) {
            var14 += var10.b();
         } else {
            var13 += var10.b() + var14;
            var14 = 0;
         }
      }

      var1.b(var13);
      this.f = var8 ? false : this.d.b().containsKey("decoration");
   }

   @Override
   public Chunk d(int var1, int var2) {
      ChunkPrimer var3 = new ChunkPrimer();

      for (int var4 = 0; var4 < this.c.length; var4++) {
         IBlockState var5 = this.c[var4];
         if (var5 != null) {
            for (int var6 = 0; var6 < 16; var6++) {
               for (int var7 = 0; var7 < 16; var7++) {
                  var3.a(var6, var4, var7, var5);
               }
            }
         }
      }

      for (MapGenBase var10 : this.e) {
         var10.a(this, this.a, var1, var2, var3);
      }

      Chunk var9 = new Chunk(this.a, var3, var1, var2);
      BiomeGenBase[] var11 = this.a.v().b(null, var1 * 16, var2 * 16, 16, 16);
      byte[] var12 = var9.k();

      for (int var13 = 0; var13 < var12.length; var13++) {
         var12[var13] = (byte)var11[var13].az;
      }

      var9.b();
      return var9;
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
      int var4 = var2 * 16;
      int var5 = var3 * 16;
      BlockPos var6 = new BlockPos(var4, 0, var5);
      BiomeGenBase var7 = this.a.b(new BlockPos(var4 + 16, 0, var5 + 16));
      boolean var8 = false;
      this.b.setSeed(this.a.J());
      long var9 = this.b.nextLong() / 2L * 2L + 1L;
      long var11 = this.b.nextLong() / 2L * 2L + 1L;
      this.b.setSeed(var2 * var9 + var3 * var11 ^ this.a.J());
      ChunkCoordIntPair var13 = new ChunkCoordIntPair(var2, var3);

      for (MapGenStructure var15 : this.e) {
         boolean var16 = var15.a(this.a, this.b, var13);
         if (var15 instanceof MapGenVillage) {
            var8 |= var16;
         }
      }

      if (this.h != null && !var8 && this.b.nextInt(4) == 0) {
         this.h.b(this.a, this.b, var6.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
      }

      if (this.i != null && !var8 && this.b.nextInt(8) == 0) {
         BlockPos var17 = var6.a(this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(248) + 8), this.b.nextInt(16) + 8);
         if (var17.o() < this.a.F() || this.b.nextInt(10) == 0) {
            this.i.b(this.a, this.b, var17);
         }
      }

      if (this.g) {
         for (int var18 = 0; var18 < 8; var18++) {
            new WorldGenDungeons().b(this.a, this.b, var6.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
         }
      }

      if (this.f) {
         var7.a(this.a, this.b, var6);
      }
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   @Override
   public boolean a(boolean var1, IProgressUpdate var2) {
      return true;
   }

   @Override
   public void c() {
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean e() {
      return true;
   }

   @Override
   public String f() {
      return "FlatLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      BiomeGenBase var3 = this.a.b(var2);
      return var3.a(var1);
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      if ("Stronghold".equals(var2)) {
         for (MapGenStructure var5 : this.e) {
            if (var5 instanceof MapGenStronghold) {
               return var5.b(var1, var3);
            }
         }
      }

      return null;
   }

   @Override
   public int g() {
      return 0;
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
      for (MapGenStructure var5 : this.e) {
         var5.a(this, this.a, var2, var3, null);
      }
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }
}

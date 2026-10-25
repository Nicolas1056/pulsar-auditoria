package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderClient implements IChunkProvider {
   private static final Logger a = LogManager.getLogger();
   private Chunk b;
   private LongHashMap<Chunk> c = new LongHashMap<>();
   private List<Chunk> d = Lists.newArrayList();
   private World e;

   public ChunkProviderClient(World var1) {
      this.b = new EmptyChunk(var1, 0, 0);
      this.e = var1;
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   public void b(int var1, int var2) {
      Chunk var3 = this.d(var1, var2);
      if (!var3.f()) {
         var3.d();
      }

      this.c.d(ChunkCoordIntPair.a(var1, var2));
      this.d.remove(var3);
   }

   public Chunk c(int var1, int var2) {
      Chunk var3 = new Chunk(this.e, var1, var2);
      this.c.a(ChunkCoordIntPair.a(var1, var2), var3);
      this.d.add(var3);
      var3.c(true);
      return var3;
   }

   @Override
   public Chunk d(int var1, int var2) {
      Chunk var3 = this.c.a(ChunkCoordIntPair.a(var1, var2));
      return var3 == null ? this.b : var3;
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
      long var1 = System.currentTimeMillis();

      for (Chunk var4 : this.d) {
         var4.b(System.currentTimeMillis() - var1 > 5L);
      }

      if (System.currentTimeMillis() - var1 > 100L) {
         a.info("Warning: Clientside chunk ticking took {} ms", new Object[]{System.currentTimeMillis() - var1});
      }

      return false;
   }

   @Override
   public boolean e() {
      return false;
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      return false;
   }

   @Override
   public String f() {
      return "MultiplayerChunkCache: " + this.c.a() + ", " + this.d.size();
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      return null;
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      return null;
   }

   @Override
   public int g() {
      return this.d.size();
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }
}

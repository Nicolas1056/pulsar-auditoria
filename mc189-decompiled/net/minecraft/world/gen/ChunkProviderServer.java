package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider {
   private static final Logger b = LogManager.getLogger();
   private Set<Long> c = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private Chunk d;
   private IChunkProvider e;
   private IChunkLoader f;
   public boolean a = true;
   private LongHashMap<Chunk> g = new LongHashMap<>();
   private List<Chunk> h = Lists.newArrayList();
   private WorldServer i;

   public ChunkProviderServer(WorldServer var1, IChunkLoader var2, IChunkProvider var3) {
      this.d = new EmptyChunk(var1, 0, 0);
      this.i = var1;
      this.f = var2;
      this.e = var3;
   }

   @Override
   public boolean a(int var1, int var2) {
      return this.g.b(ChunkCoordIntPair.a(var1, var2));
   }

   public List<Chunk> a() {
      return this.h;
   }

   public void b(int var1, int var2) {
      if (this.i.t.e()) {
         if (!this.i.c(var1, var2)) {
            this.c.add(ChunkCoordIntPair.a(var1, var2));
         }
      } else {
         this.c.add(ChunkCoordIntPair.a(var1, var2));
      }
   }

   public void b() {
      for (Chunk var2 : this.h) {
         this.b(var2.a, var2.b);
      }
   }

   public Chunk c(int var1, int var2) {
      long var3 = ChunkCoordIntPair.a(var1, var2);
      this.c.remove(var3);
      Chunk var5 = this.g.a(var3);
      if (var5 == null) {
         var5 = this.e(var1, var2);
         if (var5 == null) {
            if (this.e == null) {
               var5 = this.d;
            } else {
               try {
                  var5 = this.e.d(var1, var2);
               } catch (Throwable var9) {
                  CrashReport var7 = CrashReport.a(var9, "Exception generating new chunk");
                  CrashReportCategory var8 = var7.a("Chunk to be generated");
                  var8.a("Location", String.format("%d,%d", var1, var2));
                  var8.a("Position hash", var3);
                  var8.a("Generator", this.e.f());
                  throw new ReportedException(var7);
               }
            }
         }

         this.g.a(var3, var5);
         this.h.add(var5);
         var5.c();
         var5.a(this, this, var1, var2);
      }

      return var5;
   }

   @Override
   public Chunk d(int var1, int var2) {
      Chunk var3 = this.g.a(ChunkCoordIntPair.a(var1, var2));
      if (var3 == null) {
         return !this.i.ad() && !this.a ? this.d : this.c(var1, var2);
      } else {
         return var3;
      }
   }

   private Chunk e(int var1, int var2) {
      if (this.f == null) {
         return null;
      } else {
         try {
            Chunk var3 = this.f.a(this.i, var1, var2);
            if (var3 != null) {
               var3.b(this.i.K());
               if (this.e != null) {
                  this.e.a(var3, var1, var2);
               }
            }

            return var3;
         } catch (Exception var4) {
            b.error("Couldn't load chunk", var4);
            return null;
         }
      }
   }

   private void a(Chunk var1) {
      if (this.f != null) {
         try {
            this.f.b(this.i, var1);
         } catch (Exception var3) {
            b.error("Couldn't save entities", var3);
         }
      }
   }

   private void b(Chunk var1) {
      if (this.f != null) {
         try {
            var1.b(this.i.K());
            this.f.a(this.i, var1);
         } catch (IOException var3) {
            b.error("Couldn't save chunk", var3);
         } catch (MinecraftException var4) {
            b.error("Couldn't save chunk; already in use by another instance of Minecraft?", var4);
         }
      }
   }

   @Override
   public void a(IChunkProvider var1, int var2, int var3) {
      Chunk var4 = this.d(var2, var3);
      if (!var4.t()) {
         var4.n();
         if (this.e != null) {
            this.e.a(var1, var2, var3);
            var4.e();
         }
      }
   }

   @Override
   public boolean a(IChunkProvider var1, Chunk var2, int var3, int var4) {
      if (this.e != null && this.e.a(var1, var2, var3, var4)) {
         Chunk var5 = this.d(var3, var4);
         var5.e();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(boolean var1, IProgressUpdate var2) {
      int var3 = 0;
      ArrayList var4 = Lists.newArrayList(this.h);

      for (int var5 = 0; var5 < var4.size(); var5++) {
         Chunk var6 = (Chunk)var4.get(var5);
         if (var1) {
            this.a(var6);
         }

         if (var6.a(var1)) {
            this.b(var6);
            var6.f(false);
            if (++var3 == 24 && !var1) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public void c() {
      if (this.f != null) {
         this.f.b();
      }
   }

   @Override
   public boolean d() {
      if (!this.i.c) {
         for (int var1 = 0; var1 < 100; var1++) {
            if (!this.c.isEmpty()) {
               Long var2 = this.c.iterator().next();
               Chunk var3 = this.g.a(var2);
               if (var3 != null) {
                  var3.d();
                  this.b(var3);
                  this.a(var3);
                  this.g.d(var2);
                  this.h.remove(var3);
               }

               this.c.remove(var2);
            }
         }

         if (this.f != null) {
            this.f.a();
         }
      }

      return this.e.d();
   }

   @Override
   public boolean e() {
      return !this.i.c;
   }

   @Override
   public String f() {
      return "ServerChunkCache: " + this.g.a() + " Drop: " + this.c.size();
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> a(EnumCreatureType var1, BlockPos var2) {
      return this.e.a(var1, var2);
   }

   @Override
   public BlockPos a(World var1, String var2, BlockPos var3) {
      return this.e.a(var1, var2, var3);
   }

   @Override
   public int g() {
      return this.g.a();
   }

   @Override
   public void a(Chunk var1, int var2, int var3) {
   }

   @Override
   public Chunk a(BlockPos var1) {
      return this.d(var1.n() >> 4, var1.p() >> 4);
   }
}

package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
   private static final Logger a = LogManager.getLogger();
   private Map<ChunkCoordIntPair, NBTTagCompound> b = new ConcurrentHashMap<>();
   private Set<ChunkCoordIntPair> c = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final File d;
   private boolean e = false;

   public AnvilChunkLoader(File var1) {
      this.d = var1;
   }

   @Override
   public Chunk a(World var1, int var2, int var3) throws IOException {
      ChunkCoordIntPair var4 = new ChunkCoordIntPair(var2, var3);
      NBTTagCompound var5 = this.b.get(var4);
      if (var5 == null) {
         DataInputStream var6 = RegionFileCache.c(this.d, var2, var3);
         if (var6 == null) {
            return null;
         }

         var5 = CompressedStreamTools.a(var6);
      }

      return this.a(var1, var2, var3, var5);
   }

   protected Chunk a(World var1, int var2, int var3, NBTTagCompound var4) {
      if (!var4.b("Level", 10)) {
         a.error("Chunk file at " + var2 + "," + var3 + " is missing level data, skipping");
         return null;
      } else {
         NBTTagCompound var5 = var4.m("Level");
         if (!var5.b("Sections", 9)) {
            a.error("Chunk file at " + var2 + "," + var3 + " is missing block data, skipping");
            return null;
         } else {
            Chunk var6 = this.a(var1, var5);
            if (!var6.a(var2, var3)) {
               a.error(
                  "Chunk file at "
                     + var2
                     + ","
                     + var3
                     + " is in the wrong location; relocating. (Expected "
                     + var2
                     + ", "
                     + var3
                     + ", got "
                     + var6.a
                     + ", "
                     + var6.b
                     + ")"
               );
               var5.a("xPos", var2);
               var5.a("zPos", var3);
               var6 = this.a(var1, var5);
            }

            return var6;
         }
      }
   }

   @Override
   public void a(World var1, Chunk var2) throws IOException, MinecraftException {
      var1.I();

      try {
         NBTTagCompound var3 = new NBTTagCompound();
         NBTTagCompound var4 = new NBTTagCompound();
         var3.a("Level", var4);
         this.a(var2, var1, var4);
         this.a(var2.j(), var3);
      } catch (Exception var5) {
         a.error("Failed to save chunk", var5);
      }
   }

   protected void a(ChunkCoordIntPair var1, NBTTagCompound var2) {
      if (!this.c.contains(var1)) {
         this.b.put(var1, var2);
      }

      ThreadedFileIOBase.a().a(this);
   }

   @Override
   public boolean c() {
      if (this.b.isEmpty()) {
         if (this.e) {
            a.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[]{this.d.getName()});
         }

         return false;
      } else {
         ChunkCoordIntPair var1 = this.b.keySet().iterator().next();

         boolean var3;
         try {
            this.c.add(var1);
            NBTTagCompound var2 = this.b.remove(var1);
            if (var2 != null) {
               try {
                  this.b(var1, var2);
               } catch (Exception var7) {
                  a.error("Failed to save chunk", var7);
               }
            }

            var3 = true;
         } finally {
            this.c.remove(var1);
         }

         return var3;
      }
   }

   private void b(ChunkCoordIntPair var1, NBTTagCompound var2) throws IOException {
      DataOutputStream var3 = RegionFileCache.d(this.d, var1.a, var1.b);
      CompressedStreamTools.a(var2, (DataOutput)var3);
      var3.close();
   }

   @Override
   public void b(World var1, Chunk var2) throws IOException {
   }

   @Override
   public void a() {
   }

   @Override
   public void b() {
      try {
         this.e = true;

         while (this.c()) {
         }
      } finally {
         this.e = false;
      }
   }

   private void a(Chunk var1, World var2, NBTTagCompound var3) {
      var3.a("V", (byte)1);
      var3.a("xPos", var1.a);
      var3.a("zPos", var1.b);
      var3.a("LastUpdate", var2.K());
      var3.a("HeightMap", var1.q());
      var3.a("TerrainPopulated", var1.t());
      var3.a("LightPopulated", var1.u());
      var3.a("InhabitedTime", var1.w());
      ExtendedBlockStorage[] var4 = var1.h();
      NBTTagList var5 = new NBTTagList();
      boolean var6 = !var2.t.o();

      for (ExtendedBlockStorage var10 : var4) {
         if (var10 != null) {
            NBTTagCompound var11 = new NBTTagCompound();
            var11.a("Y", (byte)(var10.d() >> 4 & 0xFF));
            byte[] var12 = new byte[var10.g().length];
            NibbleArray var13 = new NibbleArray();
            NibbleArray var14 = null;

            for (int var15 = 0; var15 < var10.g().length; var15++) {
               char var16 = var10.g()[var15];
               int var17 = var15 & 15;
               int var18 = var15 >> 8 & 15;
               int var19 = var15 >> 4 & 15;
               if (var16 >> '\f' != 0) {
                  if (var14 == null) {
                     var14 = new NibbleArray();
                  }

                  var14.a(var17, var18, var19, var16 >> '\f');
               }

               var12[var15] = (byte)(var16 >> 4 & 0xFF);
               var13.a(var17, var18, var19, var16 & 15);
            }

            var11.a("Blocks", var12);
            var11.a("Data", var13.a());
            if (var14 != null) {
               var11.a("Add", var14.a());
            }

            var11.a("BlockLight", var10.h().a());
            if (var6) {
               var11.a("SkyLight", var10.i().a());
            } else {
               var11.a("SkyLight", new byte[var10.h().a().length]);
            }

            var5.a(var11);
         }
      }

      var3.a("Sections", var5);
      var3.a("Biomes", var1.k());
      var1.g(false);
      NBTTagList var20 = new NBTTagList();

      for (int var21 = 0; var21 < var1.s().length; var21++) {
         for (Entity var26 : var1.s()[var21]) {
            NBTTagCompound var29 = new NBTTagCompound();
            if (var26.d(var29)) {
               var1.g(true);
               var20.a(var29);
            }
         }
      }

      var3.a("Entities", var20);
      NBTTagList var22 = new NBTTagList();

      for (TileEntity var27 : var1.r().values()) {
         NBTTagCompound var30 = new NBTTagCompound();
         var27.b(var30);
         var22.a(var30);
      }

      var3.a("TileEntities", var22);
      List var25 = var2.a(var1, false);
      if (var25 != null) {
         long var28 = var2.K();
         NBTTagList var31 = new NBTTagList();

         for (NextTickListEntry var33 : var25) {
            NBTTagCompound var34 = new NBTTagCompound();
            ResourceLocation var35 = Block.c.c(var33.a());
            var34.a("i", var35 == null ? "" : var35.toString());
            var34.a("x", var33.a.n());
            var34.a("y", var33.a.o());
            var34.a("z", var33.a.p());
            var34.a("t", (int)(var33.b - var28));
            var34.a("p", var33.c);
            var31.a(var34);
         }

         var3.a("TileTicks", var31);
      }
   }

   private Chunk a(World var1, NBTTagCompound var2) {
      int var3 = var2.f("xPos");
      int var4 = var2.f("zPos");
      Chunk var5 = new Chunk(var1, var3, var4);
      var5.a(var2.l("HeightMap"));
      var5.d(var2.n("TerrainPopulated"));
      var5.e(var2.n("LightPopulated"));
      var5.c(var2.g("InhabitedTime"));
      NBTTagList var6 = var2.c("Sections", 10);
      byte var7 = 16;
      ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
      boolean var9 = !var1.t.o();

      for (int var10 = 0; var10 < var6.c(); var10++) {
         NBTTagCompound var11 = var6.b(var10);
         byte var12 = var11.d("Y");
         ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
         byte[] var14 = var11.k("Blocks");
         NibbleArray var15 = new NibbleArray(var11.k("Data"));
         NibbleArray var16 = var11.b("Add", 7) ? new NibbleArray(var11.k("Add")) : null;
         char[] var17 = new char[var14.length];

         for (int var18 = 0; var18 < var17.length; var18++) {
            int var19 = var18 & 15;
            int var20 = var18 >> 8 & 15;
            int var21 = var18 >> 4 & 15;
            int var22 = var16 != null ? var16.a(var19, var20, var21) : 0;
            var17[var18] = (char)(var22 << 12 | (var14[var18] & 255) << 4 | var15.a(var19, var20, var21));
         }

         var13.a(var17);
         var13.a(new NibbleArray(var11.k("BlockLight")));
         if (var9) {
            var13.b(new NibbleArray(var11.k("SkyLight")));
         }

         var13.e();
         var8[var12] = var13;
      }

      var5.a(var8);
      if (var2.b("Biomes", 7)) {
         var5.a(var2.k("Biomes"));
      }

      NBTTagList var23 = var2.c("Entities", 10);
      if (var23 != null) {
         for (int var24 = 0; var24 < var23.c(); var24++) {
            NBTTagCompound var26 = var23.b(var24);
            Entity var29 = EntityList.a(var26, var1);
            var5.g(true);
            if (var29 != null) {
               var5.a(var29);
               Entity var32 = var29;

               for (NBTTagCompound var35 = var26; var35.b("Riding", 10); var35 = var35.m("Riding")) {
                  Entity var37 = EntityList.a(var35.m("Riding"), var1);
                  if (var37 != null) {
                     var5.a(var37);
                     var32.a(var37);
                  }

                  var32 = var37;
               }
            }
         }
      }

      NBTTagList var25 = var2.c("TileEntities", 10);
      if (var25 != null) {
         for (int var27 = 0; var27 < var25.c(); var27++) {
            NBTTagCompound var30 = var25.b(var27);
            TileEntity var33 = TileEntity.c(var30);
            if (var33 != null) {
               var5.a(var33);
            }
         }
      }

      if (var2.b("TileTicks", 9)) {
         NBTTagList var28 = var2.c("TileTicks", 10);
         if (var28 != null) {
            for (int var31 = 0; var31 < var28.c(); var31++) {
               NBTTagCompound var34 = var28.b(var31);
               Block var36;
               if (var34.b("i", 8)) {
                  var36 = Block.b(var34.j("i"));
               } else {
                  var36 = Block.c(var34.f("i"));
               }

               var1.b(new BlockPos(var34.f("x"), var34.f("y"), var34.f("z")), var36, var34.f("t"), var34.f("p"));
            }
         }
      }

      return var5;
   }
}

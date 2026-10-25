package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public abstract class MapGenStructure extends MapGenBase {
   private MapGenStructureData d;
   protected Map<Long, StructureStart> e = Maps.newHashMap();

   public abstract String a();

   @Override
   protected final void a(World var1, final int var2, final int var3, int var4, int var5, ChunkPrimer var6) {
      this.a(var1);
      if (!this.e.containsKey(ChunkCoordIntPair.a(var2, var3))) {
         this.b.nextInt();

         try {
            if (this.a(var2, var3)) {
               StructureStart var7 = this.b(var2, var3);
               this.e.put(ChunkCoordIntPair.a(var2, var3), var7);
               this.a(var2, var3, var7);
            }
         } catch (Throwable var10) {
            CrashReport var8 = CrashReport.a(var10, "Exception preparing structure feature");
            CrashReportCategory var9 = var8.a("Feature being prepared");
            var9.a("Is feature chunk", new Callable<String>() {
               public String a() throws Exception {
                  return MapGenStructure.this.a(var2, var3) ? "True" : "False";
               }
            });
            var9.a("Chunk location", String.format("%d,%d", var2, var3));
            var9.a("Chunk pos hash", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(ChunkCoordIntPair.a(var2, var3));
               }
            });
            var9.a("Structure type", new Callable<String>() {
               public String a() throws Exception {
                  return MapGenStructure.this.getClass().getCanonicalName();
               }
            });
            throw new ReportedException(var8);
         }
      }
   }

   public boolean a(World var1, Random var2, ChunkCoordIntPair var3) {
      this.a(var1);
      int var4 = (var3.a << 4) + 8;
      int var5 = (var3.b << 4) + 8;
      boolean var6 = false;

      for (StructureStart var8 : this.e.values()) {
         if (var8.d() && var8.a(var3) && var8.a().a(var4, var5, var4 + 15, var5 + 15)) {
            var8.a(var1, var2, new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
            var8.b(var3);
            var6 = true;
            this.a(var8.e(), var8.f(), var8);
         }
      }

      return var6;
   }

   public boolean b(BlockPos var1) {
      this.a(this.c);
      return this.c(var1) != null;
   }

   protected StructureStart c(BlockPos var1) {
      for (StructureStart var3 : this.e.values()) {
         if (var3.d() && var3.a().b(var1)) {
            for (StructureComponent var5 : var3.b()) {
               if (var5.c().b(var1)) {
                  return var3;
               }
            }
         }
      }

      return null;
   }

   public boolean a(World var1, BlockPos var2) {
      this.a(var1);

      for (StructureStart var4 : this.e.values()) {
         if (var4.d() && var4.a().b(var2)) {
            return true;
         }
      }

      return false;
   }

   public BlockPos b(World var1, BlockPos var2) {
      this.c = var1;
      this.a(var1);
      this.b.setSeed(var1.J());
      long var3 = this.b.nextLong();
      long var5 = this.b.nextLong();
      long var7 = (var2.n() >> 4) * var3;
      long var9 = (var2.p() >> 4) * var5;
      this.b.setSeed(var7 ^ var9 ^ var1.J());
      this.a(var1, var2.n() >> 4, var2.p() >> 4, 0, 0, null);
      double var11 = Double.MAX_VALUE;
      BlockPos var13 = null;

      for (StructureStart var15 : this.e.values()) {
         if (var15.d()) {
            StructureComponent var16 = var15.b().get(0);
            BlockPos var17 = var16.a();
            double var18 = var17.i(var2);
            if (var18 < var11) {
               var11 = var18;
               var13 = var17;
            }
         }
      }

      if (var13 != null) {
         return var13;
      } else {
         List var20 = this.z_();
         if (var20 != null) {
            BlockPos var21 = null;

            for (BlockPos var23 : var20) {
               double var24 = var23.i(var2);
               if (var24 < var11) {
                  var11 = var24;
                  var21 = var23;
               }
            }

            return var21;
         } else {
            return null;
         }
      }
   }

   protected List<BlockPos> z_() {
      return null;
   }

   private void a(World var1) {
      if (this.d == null) {
         this.d = (MapGenStructureData)var1.a(MapGenStructureData.class, this.a());
         if (this.d == null) {
            this.d = new MapGenStructureData(this.a());
            var1.a(this.a(), this.d);
         } else {
            NBTTagCompound var2 = this.d.a();

            for (String var4 : var2.c()) {
               NBTBase var5 = var2.a(var4);
               if (var5.a() == 10) {
                  NBTTagCompound var6 = (NBTTagCompound)var5;
                  if (var6.c("ChunkX") && var6.c("ChunkZ")) {
                     int var7 = var6.f("ChunkX");
                     int var8 = var6.f("ChunkZ");
                     StructureStart var9 = MapGenStructureIO.a(var6, var1);
                     if (var9 != null) {
                        this.e.put(ChunkCoordIntPair.a(var7, var8), var9);
                     }
                  }
               }
            }
         }
      }
   }

   private void a(int var1, int var2, StructureStart var3) {
      this.d.a(var3.a(var1, var2), var1, var2);
      this.d.c();
   }

   protected abstract boolean a(int var1, int var2);

   protected abstract StructureStart b(int var1, int var2);
}

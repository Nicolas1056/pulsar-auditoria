package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureOceanMonument extends MapGenStructure {
   private int f = 32;
   private int g = 5;
   public static final List<BiomeGenBase> d = Arrays.asList(BiomeGenBase.p, BiomeGenBase.N, BiomeGenBase.w, BiomeGenBase.z, BiomeGenBase.A);
   private static final List<BiomeGenBase.SpawnListEntry> h = Lists.newArrayList();

   public StructureOceanMonument() {
   }

   public StructureOceanMonument(Map<String, String> var1) {
      this();

      for (Entry var3 : var1.entrySet()) {
         if (((String)var3.getKey()).equals("spacing")) {
            this.f = MathHelper.a((String)var3.getValue(), this.f, 1);
         } else if (((String)var3.getKey()).equals("separation")) {
            this.g = MathHelper.a((String)var3.getValue(), this.g, 1);
         }
      }
   }

   @Override
   public String a() {
      return "Monument";
   }

   @Override
   protected boolean a(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      if (var1 < 0) {
         var1 -= this.f - 1;
      }

      if (var2 < 0) {
         var2 -= this.f - 1;
      }

      int var5 = var1 / this.f;
      int var6 = var2 / this.f;
      Random var7 = this.c.a(var5, var6, 10387313);
      var5 *= this.f;
      var6 *= this.f;
      var5 += (var7.nextInt(this.f - this.g) + var7.nextInt(this.f - this.g)) / 2;
      var6 += (var7.nextInt(this.f - this.g) + var7.nextInt(this.f - this.g)) / 2;
      if (var3 == var5 && var4 == var6) {
         if (this.c.v().a(new BlockPos(var3 * 16 + 8, 64, var4 * 16 + 8), null) != BiomeGenBase.N) {
            return false;
         }

         boolean var8 = this.c.v().a(var3 * 16 + 8, var4 * 16 + 8, 29, d);
         if (var8) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected StructureStart b(int var1, int var2) {
      return new StructureOceanMonument.StartMonument(this.c, this.b, var1, var2);
   }

   public List<BiomeGenBase.SpawnListEntry> b() {
      return h;
   }

   static {
      h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
   }

   public static class StartMonument extends StructureStart {
      private Set<ChunkCoordIntPair> c = Sets.newHashSet();
      private boolean d;

      public StartMonument() {
      }

      public StartMonument(World var1, Random var2, int var3, int var4) {
         super(var3, var4);
         this.b(var1, var2, var3, var4);
      }

      private void b(World var1, Random var2, int var3, int var4) {
         var2.setSeed(var1.J());
         long var5 = var2.nextLong();
         long var7 = var2.nextLong();
         long var9 = var3 * var5;
         long var11 = var4 * var7;
         var2.setSeed(var9 ^ var11 ^ var1.J());
         int var13 = var3 * 16 + 8 - 29;
         int var14 = var4 * 16 + 8 - 29;
         EnumFacing var15 = EnumFacing.Plane.a.a(var2);
         this.a.add(new StructureOceanMonumentPieces.MonumentBuilding(var2, var13, var14, var15));
         this.c();
         this.d = true;
      }

      @Override
      public void a(World var1, Random var2, StructureBoundingBox var3) {
         if (!this.d) {
            this.a.clear();
            this.b(var1, var2, this.e(), this.f());
         }

         super.a(var1, var2, var3);
      }

      @Override
      public boolean a(ChunkCoordIntPair var1) {
         return this.c.contains(var1) ? false : super.a(var1);
      }

      @Override
      public void b(ChunkCoordIntPair var1) {
         super.b(var1);
         this.c.add(var1);
      }

      @Override
      public void a(NBTTagCompound var1) {
         super.a(var1);
         NBTTagList var2 = new NBTTagList();

         for (ChunkCoordIntPair var4 : this.c) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.a("X", var4.a);
            var5.a("Z", var4.b);
            var2.a(var5);
         }

         var1.a("Processed", var2);
      }

      @Override
      public void b(NBTTagCompound var1) {
         super.b(var1);
         if (var1.b("Processed", 9)) {
            NBTTagList var2 = var1.c("Processed", 10);

            for (int var3 = 0; var3 < var2.c(); var3++) {
               NBTTagCompound var4 = var2.b(var3);
               this.c.add(new ChunkCoordIntPair(var4.f("X"), var4.f("Z")));
            }
         }
      }
   }
}

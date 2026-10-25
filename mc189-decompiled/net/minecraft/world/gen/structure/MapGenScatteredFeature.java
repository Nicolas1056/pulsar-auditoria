package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenScatteredFeature extends MapGenStructure {
   private static final List<BiomeGenBase> d = Arrays.asList(BiomeGenBase.r, BiomeGenBase.G, BiomeGenBase.K, BiomeGenBase.L, BiomeGenBase.v);
   private List<BiomeGenBase.SpawnListEntry> f = Lists.newArrayList();
   private int g = 32;
   private int h = 8;

   public MapGenScatteredFeature() {
      this.f.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
   }

   public MapGenScatteredFeature(Map<String, String> var1) {
      this();

      for (Entry var3 : var1.entrySet()) {
         if (((String)var3.getKey()).equals("distance")) {
            this.g = MathHelper.a((String)var3.getValue(), this.g, this.h + 1);
         }
      }
   }

   @Override
   public String a() {
      return "Temple";
   }

   @Override
   protected boolean a(int var1, int var2) {
      int var3 = var1;
      int var4 = var2;
      if (var1 < 0) {
         var1 -= this.g - 1;
      }

      if (var2 < 0) {
         var2 -= this.g - 1;
      }

      int var5 = var1 / this.g;
      int var6 = var2 / this.g;
      Random var7 = this.c.a(var5, var6, 14357617);
      var5 *= this.g;
      var6 *= this.g;
      var5 += var7.nextInt(this.g - this.h);
      var6 += var7.nextInt(this.g - this.h);
      if (var3 == var5 && var4 == var6) {
         BiomeGenBase var8 = this.c.v().a(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
         if (var8 == null) {
            return false;
         }

         for (BiomeGenBase var10 : d) {
            if (var8 == var10) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   protected StructureStart b(int var1, int var2) {
      return new MapGenScatteredFeature.Start(this.c, this.b, var1, var2);
   }

   public boolean a(BlockPos var1) {
      StructureStart var2 = this.c(var1);
      if (var2 != null && var2 instanceof MapGenScatteredFeature.Start && !var2.a.isEmpty()) {
         StructureComponent var3 = var2.a.getFirst();
         return var3 instanceof ComponentScatteredFeaturePieces.SwampHut;
      } else {
         return false;
      }
   }

   public List<BiomeGenBase.SpawnListEntry> b() {
      return this.f;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World var1, Random var2, int var3, int var4) {
         super(var3, var4);
         BiomeGenBase var5 = var1.b(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
         if (var5 == BiomeGenBase.K || var5 == BiomeGenBase.L) {
            ComponentScatteredFeaturePieces.JunglePyramid var8 = new ComponentScatteredFeaturePieces.JunglePyramid(var2, var3 * 16, var4 * 16);
            this.a.add(var8);
         } else if (var5 == BiomeGenBase.v) {
            ComponentScatteredFeaturePieces.SwampHut var6 = new ComponentScatteredFeaturePieces.SwampHut(var2, var3 * 16, var4 * 16);
            this.a.add(var6);
         } else if (var5 == BiomeGenBase.r || var5 == BiomeGenBase.G) {
            ComponentScatteredFeaturePieces.DesertPyramid var7 = new ComponentScatteredFeaturePieces.DesertPyramid(var2, var3 * 16, var4 * 16);
            this.a.add(var7);
         }

         this.c();
      }
   }
}

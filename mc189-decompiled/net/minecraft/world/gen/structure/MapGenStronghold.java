package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenStronghold extends MapGenStructure {
   private List<BiomeGenBase> d;
   private boolean f;
   private ChunkCoordIntPair[] g = new ChunkCoordIntPair[3];
   private double h = 32.0;
   private int i = 3;

   public MapGenStronghold() {
      this.d = Lists.newArrayList();

      for (BiomeGenBase var4 : BiomeGenBase.n()) {
         if (var4 != null && var4.an > 0.0F) {
            this.d.add(var4);
         }
      }
   }

   public MapGenStronghold(Map<String, String> var1) {
      this();

      for (Entry var3 : var1.entrySet()) {
         if (((String)var3.getKey()).equals("distance")) {
            this.h = MathHelper.a((String)var3.getValue(), this.h, 1.0);
         } else if (((String)var3.getKey()).equals("count")) {
            this.g = new ChunkCoordIntPair[MathHelper.a((String)var3.getValue(), this.g.length, 1)];
         } else if (((String)var3.getKey()).equals("spread")) {
            this.i = MathHelper.a((String)var3.getValue(), this.i, 1);
         }
      }
   }

   @Override
   public String a() {
      return "Stronghold";
   }

   @Override
   protected boolean a(int var1, int var2) {
      if (!this.f) {
         Random var3 = new Random();
         var3.setSeed(this.c.J());
         double var4 = var3.nextDouble() * Math.PI * 2.0;
         int var6 = 1;

         for (int var7 = 0; var7 < this.g.length; var7++) {
            double var8 = (1.25 * var6 + var3.nextDouble()) * (this.h * var6);
            int var10 = (int)Math.round(Math.cos(var4) * var8);
            int var11 = (int)Math.round(Math.sin(var4) * var8);
            BlockPos var12 = this.c.v().a((var10 << 4) + 8, (var11 << 4) + 8, 112, this.d, var3);
            if (var12 != null) {
               var10 = var12.n() >> 4;
               var11 = var12.p() >> 4;
            }

            this.g[var7] = new ChunkCoordIntPair(var10, var11);
            var4 += (Math.PI * 2) * var6 / this.i;
            if (var7 == this.i) {
               var6 += 2 + var3.nextInt(5);
               this.i = this.i + 1 + var3.nextInt(2);
            }
         }

         this.f = true;
      }

      for (ChunkCoordIntPair var15 : this.g) {
         if (var1 == var15.a && var2 == var15.b) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected List<BlockPos> z_() {
      ArrayList var1 = Lists.newArrayList();

      for (ChunkCoordIntPair var5 : this.g) {
         if (var5 != null) {
            var1.add(var5.a(64));
         }
      }

      return var1;
   }

   @Override
   protected StructureStart b(int var1, int var2) {
      MapGenStronghold.Start var3 = new MapGenStronghold.Start(this.c, this.b, var1, var2);

      while (var3.b().isEmpty() || ((StructureStrongholdPieces.Stairs2)var3.b().get(0)).b == null) {
         var3 = new MapGenStronghold.Start(this.c, this.b, var1, var2);
      }

      return var3;
   }

   public static class Start extends StructureStart {
      public Start() {
      }

      public Start(World var1, Random var2, int var3, int var4) {
         super(var3, var4);
         StructureStrongholdPieces.b();
         StructureStrongholdPieces.Stairs2 var5 = new StructureStrongholdPieces.Stairs2(0, var2, (var3 << 4) + 2, (var4 << 4) + 2);
         this.a.add(var5);
         var5.a(var5, this.a, var2);
         List var6 = var5.c;

         while (!var6.isEmpty()) {
            int var7 = var2.nextInt(var6.size());
            StructureComponent var8 = (StructureComponent)var6.remove(var7);
            var8.a(var5, this.a, var2);
         }

         this.c();
         this.a(var1, var2, 10);
      }
   }
}

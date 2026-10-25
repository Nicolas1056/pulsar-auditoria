package net.minecraft.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenVillage extends MapGenStructure {
   public static final List<BiomeGenBase> d = Arrays.asList(BiomeGenBase.q, BiomeGenBase.r, BiomeGenBase.Y);
   private int f;
   private int g = 32;
   private int h = 8;

   public MapGenVillage() {
   }

   public MapGenVillage(Map<String, String> var1) {
      this();

      for (Entry var3 : var1.entrySet()) {
         if (((String)var3.getKey()).equals("size")) {
            this.f = MathHelper.a((String)var3.getValue(), this.f, 0);
         } else if (((String)var3.getKey()).equals("distance")) {
            this.g = MathHelper.a((String)var3.getValue(), this.g, this.h + 1);
         }
      }
   }

   @Override
   public String a() {
      return "Village";
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
      Random var7 = this.c.a(var5, var6, 10387312);
      var5 *= this.g;
      var6 *= this.g;
      var5 += var7.nextInt(this.g - this.h);
      var6 += var7.nextInt(this.g - this.h);
      if (var3 == var5 && var4 == var6) {
         boolean var8 = this.c.v().a(var3 * 16 + 8, var4 * 16 + 8, 0, d);
         if (var8) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected StructureStart b(int var1, int var2) {
      return new MapGenVillage.Start(this.c, this.b, var1, var2, this.f);
   }

   public static class Start extends StructureStart {
      private boolean c;

      public Start() {
      }

      public Start(World var1, Random var2, int var3, int var4, int var5) {
         super(var3, var4);
         List var6 = StructureVillagePieces.a(var2, var5);
         StructureVillagePieces.Start var7 = new StructureVillagePieces.Start(var1.v(), 0, var2, (var3 << 4) + 2, (var4 << 4) + 2, var6, var5);
         this.a.add(var7);
         var7.a(var7, this.a, var2);
         List var8 = var7.g;
         List var9 = var7.f;

         while (!var8.isEmpty() || !var9.isEmpty()) {
            if (var8.isEmpty()) {
               int var10 = var2.nextInt(var9.size());
               StructureComponent var11 = (StructureComponent)var9.remove(var10);
               var11.a(var7, this.a, var2);
            } else {
               int var13 = var2.nextInt(var8.size());
               StructureComponent var15 = (StructureComponent)var8.remove(var13);
               var15.a(var7, this.a, var2);
            }
         }

         this.c();
         int var14 = 0;

         for (StructureComponent var12 : this.a) {
            if (!(var12 instanceof StructureVillagePieces.Road)) {
               var14++;
            }
         }

         this.c = var14 > 2;
      }

      @Override
      public boolean d() {
         return this.c;
      }

      @Override
      public void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Valid", this.c);
      }

      @Override
      public void b(NBTTagCompound var1) {
         super.b(var1);
         this.c = var1.n("Valid");
      }
   }
}

package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

public abstract class StructureStart {
   protected LinkedList<StructureComponent> a = new LinkedList<>();
   protected StructureBoundingBox b;
   private int c;
   private int d;

   public StructureStart() {
   }

   public StructureStart(int var1, int var2) {
      this.c = var1;
      this.d = var2;
   }

   public StructureBoundingBox a() {
      return this.b;
   }

   public LinkedList<StructureComponent> b() {
      return this.a;
   }

   public void a(World var1, Random var2, StructureBoundingBox var3) {
      Iterator var4 = this.a.iterator();

      while (var4.hasNext()) {
         StructureComponent var5 = (StructureComponent)var4.next();
         if (var5.c().a(var3) && !var5.a(var1, var2, var3)) {
            var4.remove();
         }
      }
   }

   protected void c() {
      this.b = StructureBoundingBox.a();

      for (StructureComponent var2 : this.a) {
         this.b.b(var2.c());
      }
   }

   public NBTTagCompound a(int var1, int var2) {
      NBTTagCompound var3 = new NBTTagCompound();
      var3.a("id", MapGenStructureIO.a(this));
      var3.a("ChunkX", var1);
      var3.a("ChunkZ", var2);
      var3.a("BB", this.b.g());
      NBTTagList var4 = new NBTTagList();

      for (StructureComponent var6 : this.a) {
         var4.a(var6.b());
      }

      var3.a("Children", var4);
      this.a(var3);
      return var3;
   }

   public void a(NBTTagCompound var1) {
   }

   public void a(World var1, NBTTagCompound var2) {
      this.c = var2.f("ChunkX");
      this.d = var2.f("ChunkZ");
      if (var2.c("BB")) {
         this.b = new StructureBoundingBox(var2.l("BB"));
      }

      NBTTagList var3 = var2.c("Children", 10);

      for (int var4 = 0; var4 < var3.c(); var4++) {
         this.a.add(MapGenStructureIO.b(var3.b(var4), var1));
      }

      this.b(var2);
   }

   public void b(NBTTagCompound var1) {
   }

   protected void a(World var1, Random var2, int var3) {
      int var4 = var1.F() - var3;
      int var5 = this.b.d() + 1;
      if (var5 < var4) {
         var5 += var2.nextInt(var4 - var5);
      }

      int var6 = var5 - this.b.e;
      this.b.a(0, var6, 0);

      for (StructureComponent var8 : this.a) {
         var8.a(0, var6, 0);
      }
   }

   protected void a(World var1, Random var2, int var3, int var4) {
      int var5 = var4 - var3 + 1 - this.b.d();
      int var6 = 1;
      if (var5 > 1) {
         var6 = var3 + var2.nextInt(var5);
      } else {
         var6 = var3;
      }

      int var7 = var6 - this.b.b;
      this.b.a(0, var7, 0);

      for (StructureComponent var9 : this.a) {
         var9.a(0, var7, 0);
      }
   }

   public boolean d() {
      return true;
   }

   public boolean a(ChunkCoordIntPair var1) {
      return true;
   }

   public void b(ChunkCoordIntPair var1) {
   }

   public int e() {
      return this.c;
   }

   public int f() {
      return this.d;
   }
}

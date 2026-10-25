package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;

public class VillageCollection extends WorldSavedData {
   private World b;
   private final List<BlockPos> c = Lists.newArrayList();
   private final List<VillageDoorInfo> d = Lists.newArrayList();
   private final List<Village> e = Lists.newArrayList();
   private int f;

   public VillageCollection(String var1) {
      super(var1);
   }

   public VillageCollection(World var1) {
      super(a(var1.t));
      this.b = var1;
      this.c();
   }

   public void a(World var1) {
      this.b = var1;

      for (Village var3 : this.e) {
         var3.a(var1);
      }
   }

   public void a(BlockPos var1) {
      if (this.c.size() <= 64) {
         if (!this.e(var1)) {
            this.c.add(var1);
         }
      }
   }

   public void a() {
      this.f++;

      for (Village var2 : this.e) {
         var2.a(this.f);
      }

      this.e();
      this.f();
      this.g();
      if (this.f % 400 == 0) {
         this.c();
      }
   }

   private void e() {
      Iterator var1 = this.e.iterator();

      while (var1.hasNext()) {
         Village var2 = (Village)var1.next();
         if (var2.g()) {
            var1.remove();
            this.c();
         }
      }
   }

   public List<Village> b() {
      return this.e;
   }

   public Village a(BlockPos var1, int var2) {
      Village var3 = null;
      double var4 = Float.MAX_VALUE;

      for (Village var7 : this.e) {
         double var8 = var7.a().i(var1);
         if (!(var8 >= var4)) {
            float var10 = var2 + var7.b();
            if (!(var8 > var10 * var10)) {
               var3 = var7;
               var4 = var8;
            }
         }
      }

      return var3;
   }

   private void f() {
      if (!this.c.isEmpty()) {
         this.b(this.c.remove(0));
      }
   }

   private void g() {
      for (int var1 = 0; var1 < this.d.size(); var1++) {
         VillageDoorInfo var2 = this.d.get(var1);
         Village var3 = this.a(var2.d(), 32);
         if (var3 == null) {
            var3 = new Village(this.b);
            this.e.add(var3);
            this.c();
         }

         var3.a(var2);
      }

      this.d.clear();
   }

   private void b(BlockPos var1) {
      byte var2 = 16;
      byte var3 = 4;
      byte var4 = 16;

      for (int var5 = -var2; var5 < var2; var5++) {
         for (int var6 = -var3; var6 < var3; var6++) {
            for (int var7 = -var4; var7 < var4; var7++) {
               BlockPos var8 = var1.a(var5, var6, var7);
               if (this.f(var8)) {
                  VillageDoorInfo var9 = this.c(var8);
                  if (var9 == null) {
                     this.d(var8);
                  } else {
                     var9.a(this.f);
                  }
               }
            }
         }
      }
   }

   private VillageDoorInfo c(BlockPos var1) {
      for (VillageDoorInfo var3 : this.d) {
         if (var3.d().n() == var1.n() && var3.d().p() == var1.p() && Math.abs(var3.d().o() - var1.o()) <= 1) {
            return var3;
         }
      }

      for (Village var6 : this.e) {
         VillageDoorInfo var4 = var6.e(var1);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   private void d(BlockPos var1) {
      EnumFacing var2 = BlockDoor.h(this.b, var1);
      EnumFacing var3 = var2.d();
      int var4 = this.a(var1, var2, 5);
      int var5 = this.a(var1, var3, var4 + 1);
      if (var4 != var5) {
         this.d.add(new VillageDoorInfo(var1, var4 < var5 ? var2 : var3, this.f));
      }
   }

   private int a(BlockPos var1, EnumFacing var2, int var3) {
      int var4 = 0;

      for (int var5 = 1; var5 <= 5; var5++) {
         if (this.b.i(var1.a(var2, var5))) {
            if (++var4 >= var3) {
               return var4;
            }
         }
      }

      return var4;
   }

   private boolean e(BlockPos var1) {
      for (BlockPos var3 : this.c) {
         if (var3.equals(var1)) {
            return true;
         }
      }

      return false;
   }

   private boolean f(BlockPos var1) {
      Block var2 = this.b.p(var1).c();
      return var2 instanceof BlockDoor ? var2.t() == Material.d : false;
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.f = var1.f("Tick");
      NBTTagList var2 = var1.c("Villages", 10);

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         Village var5 = new Village();
         var5.a(var4);
         this.e.add(var5);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Tick", this.f);
      NBTTagList var2 = new NBTTagList();

      for (Village var4 : this.e) {
         NBTTagCompound var5 = new NBTTagCompound();
         var4.b(var5);
         var2.a(var5);
      }

      var1.a("Villages", var2);
   }

   public static String a(WorldProvider var0) {
      return "villages" + var0.l();
   }
}

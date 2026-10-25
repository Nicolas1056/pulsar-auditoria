package net.minecraft.village;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Village {
   private World a;
   private final List<VillageDoorInfo> b = Lists.newArrayList();
   private BlockPos c = BlockPos.a;
   private BlockPos d = BlockPos.a;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private TreeMap<String, Integer> j = new TreeMap<>();
   private List<Village.VillageAggressor> k = Lists.newArrayList();
   private int l;

   public Village() {
   }

   public Village(World var1) {
      this.a = var1;
   }

   public void a(World var1) {
      this.a = var1;
   }

   public void a(int var1) {
      this.g = var1;
      this.m();
      this.l();
      if (var1 % 20 == 0) {
         this.k();
      }

      if (var1 % 30 == 0) {
         this.j();
      }

      int var2 = this.h / 10;
      if (this.l < var2 && this.b.size() > 20 && this.a.s.nextInt(7000) == 0) {
         Vec3 var3 = this.a(this.d, 2, 4, 2);
         if (var3 != null) {
            EntityIronGolem var4 = new EntityIronGolem(this.a);
            var4.b(var3.a, var3.b, var3.c);
            this.a.d(var4);
            this.l++;
         }
      }
   }

   private Vec3 a(BlockPos var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < 10; var5++) {
         BlockPos var6 = var1.a(this.a.s.nextInt(16) - 8, this.a.s.nextInt(6) - 3, this.a.s.nextInt(16) - 8);
         if (this.a(var6) && this.a(new BlockPos(var2, var3, var4), var6)) {
            return new Vec3(var6.n(), var6.o(), var6.p());
         }
      }

      return null;
   }

   private boolean a(BlockPos var1, BlockPos var2) {
      if (!World.a(this.a, var2.b())) {
         return false;
      } else {
         int var3 = var2.n() - var1.n() / 2;
         int var4 = var2.p() - var1.p() / 2;

         for (int var5 = var3; var5 < var3 + var1.n(); var5++) {
            for (int var6 = var2.o(); var6 < var2.o() + var1.o(); var6++) {
               for (int var7 = var4; var7 < var4 + var1.p(); var7++) {
                  if (this.a.p(new BlockPos(var5, var6, var7)).c().v()) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private void j() {
      List var1 = this.a
         .a(
            EntityIronGolem.class,
            new AxisAlignedBB(this.d.n() - this.e, this.d.o() - 4, this.d.p() - this.e, this.d.n() + this.e, this.d.o() + 4, this.d.p() + this.e)
         );
      this.l = var1.size();
   }

   private void k() {
      List var1 = this.a
         .a(
            EntityVillager.class,
            new AxisAlignedBB(this.d.n() - this.e, this.d.o() - 4, this.d.p() - this.e, this.d.n() + this.e, this.d.o() + 4, this.d.p() + this.e)
         );
      this.h = var1.size();
      if (this.h == 0) {
         this.j.clear();
      }
   }

   public BlockPos a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public int c() {
      return this.b.size();
   }

   public int d() {
      return this.g - this.f;
   }

   public int e() {
      return this.h;
   }

   public boolean a(BlockPos var1) {
      return this.d.i(var1) < this.e * this.e;
   }

   public List<VillageDoorInfo> f() {
      return this.b;
   }

   public VillageDoorInfo b(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for (VillageDoorInfo var5 : this.b) {
         int var6 = var5.a(var1);
         if (var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   public VillageDoorInfo c(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for (VillageDoorInfo var5 : this.b) {
         int var6 = var5.a(var1);
         if (var6 > 256) {
            var6 *= 1000;
         } else {
            var6 = var5.c();
         }

         if (var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   public VillageDoorInfo e(BlockPos var1) {
      if (this.d.i(var1) > this.e * this.e) {
         return null;
      } else {
         for (VillageDoorInfo var3 : this.b) {
            if (var3.d().n() == var1.n() && var3.d().p() == var1.p() && Math.abs(var3.d().o() - var1.o()) <= 1) {
               return var3;
            }
         }

         return null;
      }
   }

   public void a(VillageDoorInfo var1) {
      this.b.add(var1);
      this.c = this.c.a(var1.d());
      this.n();
      this.f = var1.h();
   }

   public boolean g() {
      return this.b.isEmpty();
   }

   public void a(EntityLivingBase var1) {
      for (Village.VillageAggressor var3 : this.k) {
         if (var3.a == var1) {
            var3.b = this.g;
            return;
         }
      }

      this.k.add(new Village.VillageAggressor(var1, this.g));
   }

   public EntityLivingBase b(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      Village.VillageAggressor var4 = null;

      for (int var5 = 0; var5 < this.k.size(); var5++) {
         Village.VillageAggressor var6 = this.k.get(var5);
         double var7 = var6.a.h(var1);
         if (!(var7 > var2)) {
            var4 = var6;
            var2 = var7;
         }
      }

      return var4 != null ? var4.a : null;
   }

   public EntityPlayer c(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      EntityPlayer var4 = null;

      for (String var6 : this.j.keySet()) {
         if (this.d(var6)) {
            EntityPlayer var7 = this.a.a(var6);
            if (var7 != null) {
               double var8 = var7.h(var1);
               if (!(var8 > var2)) {
                  var4 = var7;
                  var2 = var8;
               }
            }
         }
      }

      return var4;
   }

   private void l() {
      Iterator var1 = this.k.iterator();

      while (var1.hasNext()) {
         Village.VillageAggressor var2 = (Village.VillageAggressor)var1.next();
         if (!var2.a.ai() || Math.abs(this.g - var2.b) > 300) {
            var1.remove();
         }
      }
   }

   private void m() {
      boolean var1 = false;
      boolean var2 = this.a.s.nextInt(50) == 0;
      Iterator var3 = this.b.iterator();

      while (var3.hasNext()) {
         VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
         if (var2) {
            var4.a();
         }

         if (!this.f(var4.d()) || Math.abs(this.g - var4.h()) > 1200) {
            this.c = this.c.b(var4.d());
            var1 = true;
            var4.a(true);
            var3.remove();
         }
      }

      if (var1) {
         this.n();
      }
   }

   private boolean f(BlockPos var1) {
      Block var2 = this.a.p(var1).c();
      return var2 instanceof BlockDoor ? var2.t() == Material.d : false;
   }

   private void n() {
      int var1 = this.b.size();
      if (var1 == 0) {
         this.d = new BlockPos(0, 0, 0);
         this.e = 0;
      } else {
         this.d = new BlockPos(this.c.n() / var1, this.c.o() / var1, this.c.p() / var1);
         int var2 = 0;

         for (VillageDoorInfo var4 : this.b) {
            var2 = Math.max(var4.a(this.d), var2);
         }

         this.e = Math.max(32, (int)Math.sqrt(var2) + 1);
      }
   }

   public int a(String var1) {
      Integer var2 = this.j.get(var1);
      return var2 != null ? var2 : 0;
   }

   public int a(String var1, int var2) {
      int var3 = this.a(var1);
      int var4 = MathHelper.a(var3 + var2, -30, 10);
      this.j.put(var1, var4);
      return var4;
   }

   public boolean d(String var1) {
      return this.a(var1) <= -15;
   }

   public void a(NBTTagCompound var1) {
      this.h = var1.f("PopSize");
      this.e = var1.f("Radius");
      this.l = var1.f("Golems");
      this.f = var1.f("Stable");
      this.g = var1.f("Tick");
      this.i = var1.f("MTick");
      this.d = new BlockPos(var1.f("CX"), var1.f("CY"), var1.f("CZ"));
      this.c = new BlockPos(var1.f("ACX"), var1.f("ACY"), var1.f("ACZ"));
      NBTTagList var2 = var1.c("Doors", 10);

      for (int var3 = 0; var3 < var2.c(); var3++) {
         NBTTagCompound var4 = var2.b(var3);
         VillageDoorInfo var5 = new VillageDoorInfo(new BlockPos(var4.f("X"), var4.f("Y"), var4.f("Z")), var4.f("IDX"), var4.f("IDZ"), var4.f("TS"));
         this.b.add(var5);
      }

      NBTTagList var8 = var1.c("Players", 10);

      for (int var9 = 0; var9 < var8.c(); var9++) {
         NBTTagCompound var10 = var8.b(var9);
         if (var10.c("UUID")) {
            PlayerProfileCache var6 = MinecraftServer.N().aF();
            GameProfile var7 = var6.a(UUID.fromString(var10.j("UUID")));
            if (var7 != null) {
               this.j.put(var7.getName(), var10.f("S"));
            }
         } else {
            this.j.put(var10.j("Name"), var10.f("S"));
         }
      }
   }

   public void b(NBTTagCompound var1) {
      var1.a("PopSize", this.h);
      var1.a("Radius", this.e);
      var1.a("Golems", this.l);
      var1.a("Stable", this.f);
      var1.a("Tick", this.g);
      var1.a("MTick", this.i);
      var1.a("CX", this.d.n());
      var1.a("CY", this.d.o());
      var1.a("CZ", this.d.p());
      var1.a("ACX", this.c.n());
      var1.a("ACY", this.c.o());
      var1.a("ACZ", this.c.p());
      NBTTagList var2 = new NBTTagList();

      for (VillageDoorInfo var4 : this.b) {
         NBTTagCompound var5 = new NBTTagCompound();
         var5.a("X", var4.d().n());
         var5.a("Y", var4.d().o());
         var5.a("Z", var4.d().p());
         var5.a("IDX", var4.f());
         var5.a("IDZ", var4.g());
         var5.a("TS", var4.h());
         var2.a(var5);
      }

      var1.a("Doors", var2);
      NBTTagList var9 = new NBTTagList();

      for (String var11 : this.j.keySet()) {
         NBTTagCompound var6 = new NBTTagCompound();
         PlayerProfileCache var7 = MinecraftServer.N().aF();
         GameProfile var8 = var7.a(var11);
         if (var8 != null) {
            var6.a("UUID", var8.getId().toString());
            var6.a("S", this.j.get(var11));
            var9.a(var6);
         }
      }

      var1.a("Players", var9);
   }

   public void h() {
      this.i = this.g;
   }

   public boolean i() {
      return this.i == 0 || this.g - this.i >= 3600;
   }

   public void b(int var1) {
      for (String var3 : this.j.keySet()) {
         this.a(var3, var1);
      }
   }

   class VillageAggressor {
      public EntityLivingBase a;
      public int b;

      VillageAggressor(EntityLivingBase var2, int var3) {
         this.a = var2;
         this.b = var3;
      }
   }
}

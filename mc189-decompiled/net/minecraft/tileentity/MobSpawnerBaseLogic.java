package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class MobSpawnerBaseLogic {
   private int a = 20;
   private String b = "Pig";
   private final List<MobSpawnerBaseLogic.WeightedRandomMinecart> c = Lists.newArrayList();
   private MobSpawnerBaseLogic.WeightedRandomMinecart d;
   private double e;
   private double f;
   private int g = 200;
   private int h = 800;
   private int i = 4;
   private Entity j;
   private int k = 6;
   private int l = 16;
   private int m = 4;

   private String f() {
      if (this.i() == null) {
         if (this.b != null && this.b.equals("Minecart")) {
            this.b = "MinecartRideable";
         }

         return this.b;
      } else {
         return this.i().d;
      }
   }

   public void a(String var1) {
      this.b = var1;
   }

   private boolean g() {
      BlockPos var1 = this.b();
      return this.a().b(var1.n() + 0.5, var1.o() + 0.5, var1.p() + 0.5, this.l);
   }

   public void c() {
      if (this.g()) {
         BlockPos var1 = this.b();
         if (this.a().D) {
            double var2 = var1.n() + this.a().s.nextFloat();
            double var4 = var1.o() + this.a().s.nextFloat();
            double var6 = var1.p() + this.a().s.nextFloat();
            this.a().a(EnumParticleTypes.l, var2, var4, var6, 0.0, 0.0, 0.0);
            this.a().a(EnumParticleTypes.A, var2, var4, var6, 0.0, 0.0, 0.0);
            if (this.a > 0) {
               this.a--;
            }

            this.f = this.e;
            this.e = (this.e + 1000.0F / (this.a + 200.0F)) % 360.0;
         } else {
            if (this.a == -1) {
               this.h();
            }

            if (this.a > 0) {
               this.a--;
               return;
            }

            boolean var13 = false;

            for (int var3 = 0; var3 < this.i; var3++) {
               Entity var14 = EntityList.a(this.f(), this.a());
               if (var14 == null) {
                  return;
               }

               int var5 = this.a()
                  .a(var14.getClass(), new AxisAlignedBB(var1.n(), var1.o(), var1.p(), var1.n() + 1, var1.o() + 1, var1.p() + 1).b(this.m, this.m, this.m))
                  .size();
               if (var5 >= this.k) {
                  this.h();
                  return;
               }

               double var15 = var1.n() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * this.m + 0.5;
               double var8 = var1.o() + this.a().s.nextInt(3) - 1;
               double var10 = var1.p() + (this.a().s.nextDouble() - this.a().s.nextDouble()) * this.m + 0.5;
               EntityLiving var12 = var14 instanceof EntityLiving ? (EntityLiving)var14 : null;
               var14.b(var15, var8, var10, this.a().s.nextFloat() * 360.0F, 0.0F);
               if (var12 == null || var12.bR() && var12.bS()) {
                  this.a(var14, true);
                  this.a().b(2004, var1, 0);
                  if (var12 != null) {
                     var12.y();
                  }

                  var13 = true;
               }
            }

            if (var13) {
               this.h();
            }
         }
      }
   }

   private Entity a(Entity var1, boolean var2) {
      if (this.i() != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         var1.d(var3);

         for (String var5 : this.i().c.c()) {
            NBTBase var6 = this.i().c.a(var5);
            var3.a(var5, var6.b());
         }

         var1.f(var3);
         if (var1.o != null && var2) {
            var1.o.d(var1);
         }

         Entity var11 = var1;

         while (var3.b("Riding", 10)) {
            NBTTagCompound var12 = var3.m("Riding");
            Entity var13 = EntityList.a(var12.j("id"), var1.o);
            if (var13 != null) {
               NBTTagCompound var7 = new NBTTagCompound();
               var13.d(var7);

               for (String var9 : var12.c()) {
                  NBTBase var10 = var12.a(var9);
                  var7.a(var9, var10.b());
               }

               var13.f(var7);
               var13.b(var11.s, var11.t, var11.u, var11.y, var11.z);
               if (var1.o != null && var2) {
                  var1.o.d(var13);
               }

               var11.a(var13);
            }

            var11 = var13;
            var3 = var12;
         }
      } else if (var1 instanceof EntityLivingBase && var1.o != null && var2) {
         if (var1 instanceof EntityLiving) {
            ((EntityLiving)var1).a(var1.o.E(new BlockPos(var1)), null);
         }

         var1.o.d(var1);
      }

      return var1;
   }

   private void h() {
      if (this.h <= this.g) {
         this.a = this.g;
      } else {
         this.a = this.g + this.a().s.nextInt(this.h - this.g);
      }

      if (this.c.size() > 0) {
         this.a(WeightedRandom.a(this.a().s, this.c));
      }

      this.a(1);
   }

   public void a(NBTTagCompound var1) {
      this.b = var1.j("EntityId");
      this.a = var1.e("Delay");
      this.c.clear();
      if (var1.b("SpawnPotentials", 9)) {
         NBTTagList var2 = var1.c("SpawnPotentials", 10);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            this.c.add(new MobSpawnerBaseLogic.WeightedRandomMinecart(var2.b(var3)));
         }
      }

      if (var1.b("SpawnData", 10)) {
         this.a(new MobSpawnerBaseLogic.WeightedRandomMinecart(var1.m("SpawnData"), this.b));
      } else {
         this.a(null);
      }

      if (var1.b("MinSpawnDelay", 99)) {
         this.g = var1.e("MinSpawnDelay");
         this.h = var1.e("MaxSpawnDelay");
         this.i = var1.e("SpawnCount");
      }

      if (var1.b("MaxNearbyEntities", 99)) {
         this.k = var1.e("MaxNearbyEntities");
         this.l = var1.e("RequiredPlayerRange");
      }

      if (var1.b("SpawnRange", 99)) {
         this.m = var1.e("SpawnRange");
      }

      if (this.a() != null) {
         this.j = null;
      }
   }

   public void b(NBTTagCompound var1) {
      String var2 = this.f();
      if (!StringUtils.b(var2)) {
         var1.a("EntityId", var2);
         var1.a("Delay", (short)this.a);
         var1.a("MinSpawnDelay", (short)this.g);
         var1.a("MaxSpawnDelay", (short)this.h);
         var1.a("SpawnCount", (short)this.i);
         var1.a("MaxNearbyEntities", (short)this.k);
         var1.a("RequiredPlayerRange", (short)this.l);
         var1.a("SpawnRange", (short)this.m);
         if (this.i() != null) {
            var1.a("SpawnData", this.i().c.b());
         }

         if (this.i() != null || this.c.size() > 0) {
            NBTTagList var3 = new NBTTagList();
            if (this.c.size() > 0) {
               for (MobSpawnerBaseLogic.WeightedRandomMinecart var5 : this.c) {
                  var3.a(var5.a());
               }
            } else {
               var3.a(this.i().a());
            }

            var1.a("SpawnPotentials", var3);
         }
      }
   }

   public Entity a(World var1) {
      if (this.j == null) {
         Entity var2 = EntityList.a(this.f(), var1);
         if (var2 != null) {
            var2 = this.a(var2, false);
            this.j = var2;
         }
      }

      return this.j;
   }

   public boolean b(int var1) {
      if (var1 == 1 && this.a().D) {
         this.a = this.g;
         return true;
      } else {
         return false;
      }
   }

   private MobSpawnerBaseLogic.WeightedRandomMinecart i() {
      return this.d;
   }

   public void a(MobSpawnerBaseLogic.WeightedRandomMinecart var1) {
      this.d = var1;
   }

   public abstract void a(int var1);

   public abstract World a();

   public abstract BlockPos b();

   public double d() {
      return this.e;
   }

   public double e() {
      return this.f;
   }

   public class WeightedRandomMinecart extends WeightedRandom.Item {
      private final NBTTagCompound c;
      private final String d;

      public WeightedRandomMinecart(NBTTagCompound var2) {
         this(var2.m("Properties"), var2.j("Type"), var2.f("Weight"));
      }

      public WeightedRandomMinecart(NBTTagCompound var2, String var3) {
         this(var2, var3, 1);
      }

      private WeightedRandomMinecart(NBTTagCompound var2, String var3, int var4) {
         super(var4);
         if (var3.equals("Minecart")) {
            if (var2 != null) {
               var3 = EntityMinecart.EnumMinecartType.a(var2.f("Type")).b();
            } else {
               var3 = "MinecartRideable";
            }
         }

         this.c = var2;
         this.d = var3;
      }

      public NBTTagCompound a() {
         NBTTagCompound var1 = new NBTTagCompound();
         var1.a("Properties", this.c);
         var1.a("Type", this.d);
         var1.a("Weight", this.a);
         return var1;
      }
   }
}

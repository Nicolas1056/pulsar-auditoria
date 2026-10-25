package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem extends Entity {
   private static final Logger b = LogManager.getLogger();
   private int c;
   private int d;
   private int e = 5;
   private String f;
   private String g;
   public float a = (float)(Math.random() * Math.PI * 2.0);

   public EntityItem(World var1, double var2, double var4, double var6) {
      super(var1);
      this.a(0.25F, 0.25F);
      this.b(var2, var4, var6);
      this.y = (float)(Math.random() * 360.0);
      this.v = (float)(Math.random() * 0.2F - 0.1F);
      this.w = 0.2F;
      this.x = (float)(Math.random() * 0.2F - 0.1F);
   }

   public EntityItem(World var1, double var2, double var4, double var6, ItemStack var8) {
      this(var1, var2, var4, var6);
      this.a(var8);
   }

   @Override
   protected boolean s_() {
      return false;
   }

   public EntityItem(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
      this.a(new ItemStack(Blocks.a, 0));
   }

   @Override
   protected void h() {
      this.H().a(10, 5);
   }

   @Override
   public void t_() {
      if (this.l() == null) {
         this.J();
      } else {
         super.t_();
         if (this.d > 0 && this.d != 32767) {
            this.d--;
         }

         this.p = this.s;
         this.q = this.t;
         this.r = this.u;
         this.w -= 0.04F;
         this.T = this.j(this.s, (this.aR().b + this.aR().e) / 2.0, this.u);
         this.d(this.v, this.w, this.x);
         boolean var1 = (int)this.p != (int)this.s || (int)this.q != (int)this.t || (int)this.r != (int)this.u;
         if (var1 || this.W % 25 == 0) {
            if (this.o.p(new BlockPos(this)).c().t() == Material.i) {
               this.w = 0.2F;
               this.v = (this.V.nextFloat() - this.V.nextFloat()) * 0.2F;
               this.x = (this.V.nextFloat() - this.V.nextFloat()) * 0.2F;
               this.a("random.fizz", 0.4F, 2.0F + this.V.nextFloat() * 0.4F);
            }

            if (!this.o.D) {
               this.w();
            }
         }

         float var2 = 0.98F;
         if (this.C) {
            var2 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aR().b) - 1, MathHelper.c(this.u))).c().L * 0.98F;
         }

         this.v *= var2;
         this.w *= 0.98F;
         this.x *= var2;
         if (this.C) {
            this.w *= -0.5;
         }

         if (this.c != -32768) {
            this.c++;
         }

         this.W();
         if (!this.o.D && this.c >= 6000) {
            this.J();
         }
      }
   }

   private void w() {
      for (EntityItem var2 : this.o.a(EntityItem.class, this.aR().b(0.5, 0.0, 0.5))) {
         this.a(var2);
      }
   }

   private boolean a(EntityItem var1) {
      if (var1 == this) {
         return false;
      } else if (var1.ai() && this.ai()) {
         ItemStack var2 = this.l();
         ItemStack var3 = var1.l();
         if (this.d == 32767 || var1.d == 32767) {
            return false;
         } else if (this.c != -32768 && var1.c != -32768) {
            if (var3.b() != var2.b()) {
               return false;
            } else if (var3.n() ^ var2.n()) {
               return false;
            } else if (var3.n() && !var3.o().equals(var2.o())) {
               return false;
            } else if (var3.b() == null) {
               return false;
            } else if (var3.b().k() && var3.i() != var2.i()) {
               return false;
            } else if (var3.b < var2.b) {
               return var1.a(this);
            } else if (var3.b + var2.b > var3.c()) {
               return false;
            } else {
               var3.b = var3.b + var2.b;
               var1.d = Math.max(var1.d, this.d);
               var1.c = Math.min(var1.c, this.c);
               var1.a(var3);
               this.J();
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void j() {
      this.c = 4800;
   }

   @Override
   public boolean W() {
      if (this.o.a(this.aR(), Material.h, this)) {
         if (!this.Y && !this.aa) {
            this.X();
         }

         this.Y = true;
      } else {
         this.Y = false;
      }

      return this.Y;
   }

   @Override
   protected void f(int var1) {
      this.a(DamageSource.a, var1);
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (this.l() != null && this.l().b() == Items.bZ && var1.c()) {
         return false;
      } else {
         this.ac();
         this.e = (int)(this.e - var2);
         if (this.e <= 0) {
            this.J();
         }

         return false;
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Health", (short)((byte)this.e));
      var1.a("Age", (short)this.c);
      var1.a("PickupDelay", (short)this.d);
      if (this.n() != null) {
         var1.a("Thrower", this.f);
      }

      if (this.m() != null) {
         var1.a("Owner", this.g);
      }

      if (this.l() != null) {
         var1.a("Item", this.l().b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.e = var1.e("Health") & 255;
      this.c = var1.e("Age");
      if (var1.c("PickupDelay")) {
         this.d = var1.e("PickupDelay");
      }

      if (var1.c("Owner")) {
         this.g = var1.j("Owner");
      }

      if (var1.c("Thrower")) {
         this.f = var1.j("Thrower");
      }

      NBTTagCompound var2 = var1.m("Item");
      this.a(ItemStack.a(var2));
      if (this.l() == null) {
         this.J();
      }
   }

   @Override
   public void d(EntityPlayer var1) {
      if (!this.o.D) {
         ItemStack var2 = this.l();
         int var3 = var2.b;
         if (this.d == 0 && (this.g == null || 6000 - this.c <= 200 || this.g.equals(var1.e_())) && var1.bi.a(var2)) {
            if (var2.b() == Item.a(Blocks.r)) {
               var1.b(AchievementList.g);
            }

            if (var2.b() == Item.a(Blocks.s)) {
               var1.b(AchievementList.g);
            }

            if (var2.b() == Items.aF) {
               var1.b(AchievementList.t);
            }

            if (var2.b() == Items.i) {
               var1.b(AchievementList.w);
            }

            if (var2.b() == Items.bv) {
               var1.b(AchievementList.A);
            }

            if (var2.b() == Items.i && this.n() != null) {
               EntityPlayer var4 = this.o.a(this.n());
               if (var4 != null && var4 != var1) {
                  var4.b(AchievementList.x);
               }
            }

            if (!this.R()) {
               this.o.a((Entity)var1, "random.pop", 0.2F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            }

            var1.a(this, var3);
            if (var2.b <= 0) {
               this.J();
            }
         }
      }
   }

   @Override
   public String e_() {
      return this.l_() ? this.aM() : StatCollector.a("item." + this.l().a());
   }

   @Override
   public boolean aD() {
      return false;
   }

   @Override
   public void c(int var1) {
      super.c(var1);
      if (!this.o.D) {
         this.w();
      }
   }

   public ItemStack l() {
      ItemStack var1 = this.H().f(10);
      if (var1 == null) {
         if (this.o != null) {
            b.error("Item entity " + this.F() + " has no item?!");
         }

         return new ItemStack(Blocks.b);
      } else {
         return var1;
      }
   }

   public void a(ItemStack var1) {
      this.H().b(10, var1);
      this.H().i(10);
   }

   public String m() {
      return this.g;
   }

   public void b(String var1) {
      this.g = var1;
   }

   public String n() {
      return this.f;
   }

   public void c(String var1) {
      this.f = var1;
   }

   public int o() {
      return this.c;
   }

   public void p() {
      this.d = 10;
   }

   public void q() {
      this.d = 0;
   }

   public void r() {
      this.d = 32767;
   }

   public void a(int var1) {
      this.d = var1;
   }

   public boolean s() {
      return this.d > 0;
   }

   public void u() {
      this.c = -6000;
   }

   public void v() {
      this.r();
      this.c = 5999;
   }
}

package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityArmorStand extends EntityLivingBase {
   private static final Rotations a = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations b = new Rotations(0.0F, 0.0F, 0.0F);
   private static final Rotations c = new Rotations(-10.0F, 0.0F, -10.0F);
   private static final Rotations d = new Rotations(-15.0F, 0.0F, 10.0F);
   private static final Rotations e = new Rotations(-1.0F, 0.0F, -1.0F);
   private static final Rotations f = new Rotations(1.0F, 0.0F, 1.0F);
   private final ItemStack[] g = new ItemStack[5];
   private boolean h;
   private long i;
   private int bi;
   private boolean bj;
   private Rotations bk = a;
   private Rotations bl = b;
   private Rotations bm = c;
   private Rotations bn = d;
   private Rotations bo = e;
   private Rotations bp = f;

   public EntityArmorStand(World var1) {
      super(var1);
      this.b(true);
      this.T = this.p();
      this.a(0.5F, 1.975F);
   }

   public EntityArmorStand(World var1, double var2, double var4, double var6) {
      this(var1);
      this.b(var2, var4, var6);
   }

   @Override
   public boolean bM() {
      return super.bM() && !this.p();
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(10, Byte.valueOf((byte)0));
      this.ac.a(11, a);
      this.ac.a(12, b);
      this.ac.a(13, c);
      this.ac.a(14, d);
      this.ac.a(15, e);
      this.ac.a(16, f);
   }

   @Override
   public ItemStack bA() {
      return this.g[0];
   }

   @Override
   public ItemStack p(int var1) {
      return this.g[var1];
   }

   @Override
   public ItemStack q(int var1) {
      return this.g[var1 + 1];
   }

   @Override
   public void c(int var1, ItemStack var2) {
      this.g[var1] = var2;
   }

   @Override
   public ItemStack[] as() {
      return this.g;
   }

   @Override
   public boolean d(int var1, ItemStack var2) {
      int var3;
      if (var1 == 99) {
         var3 = 0;
      } else {
         var3 = var1 - 100 + 1;
         if (var3 < 0 || var3 >= this.g.length) {
            return false;
         }
      }

      if (var2 != null && EntityLiving.c(var2) != var3 && (var3 != 4 || !(var2.b() instanceof ItemBlock))) {
         return false;
      } else {
         this.c(var3, var2);
         return true;
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      NBTTagList var2 = new NBTTagList();

      for (int var3 = 0; var3 < this.g.length; var3++) {
         NBTTagCompound var4 = new NBTTagCompound();
         if (this.g[var3] != null) {
            this.g[var3].b(var4);
         }

         var2.a(var4);
      }

      var1.a("Equipment", var2);
      if (this.aN() && (this.aM() == null || this.aM().length() == 0)) {
         var1.a("CustomNameVisible", this.aN());
      }

      var1.a("Invisible", this.ax());
      var1.a("Small", this.n());
      var1.a("ShowArms", this.q());
      var1.a("DisabledSlots", this.bi);
      var1.a("NoGravity", this.p());
      var1.a("NoBasePlate", this.r());
      if (this.s()) {
         var1.a("Marker", this.s());
      }

      var1.a("Pose", this.z());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("Equipment", 9)) {
         NBTTagList var2 = var1.c("Equipment", 10);

         for (int var3 = 0; var3 < this.g.length; var3++) {
            this.g[var3] = ItemStack.a(var2.b(var3));
         }
      }

      this.e(var1.n("Invisible"));
      this.j(var1.n("Small"));
      this.l(var1.n("ShowArms"));
      this.bi = var1.f("DisabledSlots");
      this.k(var1.n("NoGravity"));
      this.m(var1.n("NoBasePlate"));
      this.n(var1.n("Marker"));
      this.bj = !this.s();
      this.T = this.p();
      NBTTagCompound var4 = var1.m("Pose");
      this.h(var4);
   }

   private void h(NBTTagCompound var1) {
      NBTTagList var2 = var1.c("Head", 5);
      if (var2.c() > 0) {
         this.a(new Rotations(var2));
      } else {
         this.a(a);
      }

      NBTTagList var3 = var1.c("Body", 5);
      if (var3.c() > 0) {
         this.b(new Rotations(var3));
      } else {
         this.b(b);
      }

      NBTTagList var4 = var1.c("LeftArm", 5);
      if (var4.c() > 0) {
         this.c(new Rotations(var4));
      } else {
         this.c(c);
      }

      NBTTagList var5 = var1.c("RightArm", 5);
      if (var5.c() > 0) {
         this.d(new Rotations(var5));
      } else {
         this.d(d);
      }

      NBTTagList var6 = var1.c("LeftLeg", 5);
      if (var6.c() > 0) {
         this.e(new Rotations(var6));
      } else {
         this.e(e);
      }

      NBTTagList var7 = var1.c("RightLeg", 5);
      if (var7.c() > 0) {
         this.f(new Rotations(var7));
      } else {
         this.f(f);
      }
   }

   private NBTTagCompound z() {
      NBTTagCompound var1 = new NBTTagCompound();
      if (!a.equals(this.bk)) {
         var1.a("Head", this.bk.a());
      }

      if (!b.equals(this.bl)) {
         var1.a("Body", this.bl.a());
      }

      if (!c.equals(this.bm)) {
         var1.a("LeftArm", this.bm.a());
      }

      if (!d.equals(this.bn)) {
         var1.a("RightArm", this.bn.a());
      }

      if (!e.equals(this.bo)) {
         var1.a("LeftLeg", this.bo.a());
      }

      if (!f.equals(this.bp)) {
         var1.a("RightLeg", this.bp.a());
      }

      return var1;
   }

   @Override
   public boolean ae() {
      return false;
   }

   @Override
   protected void s(Entity var1) {
   }

   @Override
   protected void bL() {
      List var1 = this.o.b(this, this.aR());
      if (var1 != null && !var1.isEmpty()) {
         for (int var2 = 0; var2 < var1.size(); var2++) {
            Entity var3 = (Entity)var1.get(var2);
            if (var3 instanceof EntityMinecart && ((EntityMinecart)var3).s() == EntityMinecart.EnumMinecartType.a && this.h(var3) <= 0.2) {
               var3.i(this);
            }
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1, Vec3 var2) {
      if (this.s()) {
         return false;
      } else if (!this.o.D && !var1.v()) {
         byte var3 = 0;
         ItemStack var4 = var1.bZ();
         boolean var5 = var4 != null;
         if (var5 && var4.b() instanceof ItemArmor) {
            ItemArmor var6 = (ItemArmor)var4.b();
            if (var6.b == 3) {
               var3 = 1;
            } else if (var6.b == 2) {
               var3 = 2;
            } else if (var6.b == 1) {
               var3 = 3;
            } else if (var6.b == 0) {
               var3 = 4;
            }
         }

         if (var5 && (var4.b() == Items.bX || var4.b() == Item.a(Blocks.aU))) {
            var3 = 4;
         }

         double var19 = 0.1;
         double var8 = 0.9;
         double var10 = 0.4;
         double var12 = 1.6;
         byte var14 = 0;
         boolean var15 = this.n();
         double var16 = var15 ? var2.b * 2.0 : var2.b;
         if (var16 >= 0.1 && var16 < 0.1 + (var15 ? 0.8 : 0.45) && this.g[1] != null) {
            var14 = 1;
         } else if (var16 >= 0.9 + (var15 ? 0.3 : 0.0) && var16 < 0.9 + (var15 ? 1.0 : 0.7) && this.g[3] != null) {
            var14 = 3;
         } else if (var16 >= 0.4 && var16 < 0.4 + (var15 ? 1.0 : 0.8) && this.g[2] != null) {
            var14 = 2;
         } else if (var16 >= 1.6 && this.g[4] != null) {
            var14 = 4;
         }

         boolean var18 = this.g[var14] != null;
         if ((this.bi & 1 << var14) != 0 || (this.bi & 1 << var3) != 0) {
            var14 = var3;
            if ((this.bi & 1 << var3) != 0) {
               if ((this.bi & 1) != 0) {
                  return true;
               }

               var14 = 0;
            }
         }

         if (var5 && var3 == 0 && !this.q()) {
            return true;
         } else {
            if (var5) {
               this.a(var1, var3);
            } else if (var18) {
               this.a(var1, var14);
            }

            return true;
         }
      } else {
         return true;
      }
   }

   private void a(EntityPlayer var1, int var2) {
      ItemStack var3 = this.g[var2];
      if (var3 == null || (this.bi & 1 << var2 + 8) == 0) {
         if (var3 != null || (this.bi & 1 << var2 + 16) == 0) {
            int var4 = var1.bi.c;
            ItemStack var5 = var1.bi.a(var4);
            if (var1.bA.d && (var3 == null || var3.b() == Item.a(Blocks.a)) && var5 != null) {
               ItemStack var7 = var5.k();
               var7.b = 1;
               this.c(var2, var7);
            } else if (var5 == null || var5.b <= 1) {
               this.c(var2, var5);
               var1.bi.a(var4, var3);
            } else if (var3 == null) {
               ItemStack var6 = var5.k();
               var6.b = 1;
               this.c(var2, var6);
               var5.b--;
            }
         }
      }
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.o.D) {
         return false;
      } else if (DamageSource.j.equals(var1)) {
         this.J();
         return false;
      } else if (this.b(var1) || this.h || this.s()) {
         return false;
      } else if (var1.c()) {
         this.D();
         this.J();
         return false;
      } else if (DamageSource.a.equals(var1)) {
         if (!this.at()) {
            this.e(5);
         } else {
            this.a(0.15F);
         }

         return false;
      } else if (DamageSource.c.equals(var1) && this.bn() > 0.5F) {
         this.a(4.0F);
         return false;
      } else {
         boolean var3 = "arrow".equals(var1.p());
         boolean var4 = "player".equals(var1.p());
         if (!var4 && !var3) {
            return false;
         } else {
            if (var1.i() instanceof EntityArrow) {
               var1.i().J();
            }

            if (var1.j() instanceof EntityPlayer && !((EntityPlayer)var1.j()).bA.e) {
               return false;
            } else if (var1.u()) {
               this.A();
               this.J();
               return false;
            } else {
               long var5 = this.o.K();
               if (var5 - this.i > 5L && !var3) {
                  this.i = var5;
               } else {
                  this.C();
                  this.A();
                  this.J();
               }

               return false;
            }
         }
      }
   }

   @Override
   public boolean a(double var1) {
      double var3 = this.aR().a() * 4.0;
      if (Double.isNaN(var3) || var3 == 0.0) {
         var3 = 4.0;
      }

      var3 *= 64.0;
      return var1 < var3 * var3;
   }

   private void A() {
      if (this.o instanceof WorldServer) {
         ((WorldServer)this.o)
            .a(EnumParticleTypes.M, this.s, this.t + this.K / 1.5, this.u, 10, this.J / 4.0F, this.K / 4.0F, this.J / 4.0F, 0.05, Block.f(Blocks.f.Q()));
      }
   }

   private void a(float var1) {
      float var2 = this.bn();
      var2 -= var1;
      if (var2 <= 0.5F) {
         this.D();
         this.J();
      } else {
         this.i(var2);
      }
   }

   private void C() {
      Block.a(this.o, new BlockPos(this), new ItemStack(Items.cj));
      this.D();
   }

   private void D() {
      for (int var1 = 0; var1 < this.g.length; var1++) {
         if (this.g[var1] != null && this.g[var1].b > 0) {
            if (this.g[var1] != null) {
               Block.a(this.o, new BlockPos(this).a(), this.g[var1]);
            }

            this.g[var1] = null;
         }
      }
   }

   @Override
   protected float h(float var1, float var2) {
      this.aJ = this.A;
      this.aI = this.y;
      return 0.0F;
   }

   @Override
   public float aS() {
      return this.j_() ? this.K * 0.5F : this.K * 0.9F;
   }

   @Override
   public void g(float var1, float var2) {
      if (!this.p()) {
         super.g(var1, var2);
      }
   }

   @Override
   public void t_() {
      super.t_();
      Rotations var1 = this.ac.h(11);
      if (!this.bk.equals(var1)) {
         this.a(var1);
      }

      Rotations var2 = this.ac.h(12);
      if (!this.bl.equals(var2)) {
         this.b(var2);
      }

      Rotations var3 = this.ac.h(13);
      if (!this.bm.equals(var3)) {
         this.c(var3);
      }

      Rotations var4 = this.ac.h(14);
      if (!this.bn.equals(var4)) {
         this.d(var4);
      }

      Rotations var5 = this.ac.h(15);
      if (!this.bo.equals(var5)) {
         this.e(var5);
      }

      Rotations var6 = this.ac.h(16);
      if (!this.bp.equals(var6)) {
         this.f(var6);
      }

      boolean var7 = this.s();
      if (!this.bj && var7) {
         this.a(false);
      } else {
         if (!this.bj || var7) {
            return;
         }

         this.a(true);
      }

      this.bj = var7;
   }

   private void a(boolean var1) {
      double var2 = this.s;
      double var4 = this.t;
      double var6 = this.u;
      if (var1) {
         this.a(0.5F, 1.975F);
      } else {
         this.a(0.0F, 0.0F);
      }

      this.b(var2, var4, var6);
   }

   @Override
   protected void B() {
      this.e(this.h);
   }

   @Override
   public void e(boolean var1) {
      this.h = var1;
      super.e(var1);
   }

   @Override
   public boolean j_() {
      return this.n();
   }

   @Override
   public void G() {
      this.J();
   }

   @Override
   public boolean aW() {
      return this.ax();
   }

   private void j(boolean var1) {
      byte var2 = this.ac.a(10);
      if (var1) {
         var2 = (byte)(var2 | 1);
      } else {
         var2 = (byte)(var2 & -2);
      }

      this.ac.b(10, var2);
   }

   public boolean n() {
      return (this.ac.a(10) & 1) != 0;
   }

   private void k(boolean var1) {
      byte var2 = this.ac.a(10);
      if (var1) {
         var2 = (byte)(var2 | 2);
      } else {
         var2 = (byte)(var2 & -3);
      }

      this.ac.b(10, var2);
   }

   public boolean p() {
      return (this.ac.a(10) & 2) != 0;
   }

   private void l(boolean var1) {
      byte var2 = this.ac.a(10);
      if (var1) {
         var2 = (byte)(var2 | 4);
      } else {
         var2 = (byte)(var2 & -5);
      }

      this.ac.b(10, var2);
   }

   public boolean q() {
      return (this.ac.a(10) & 4) != 0;
   }

   private void m(boolean var1) {
      byte var2 = this.ac.a(10);
      if (var1) {
         var2 = (byte)(var2 | 8);
      } else {
         var2 = (byte)(var2 & -9);
      }

      this.ac.b(10, var2);
   }

   public boolean r() {
      return (this.ac.a(10) & 8) != 0;
   }

   private void n(boolean var1) {
      byte var2 = this.ac.a(10);
      if (var1) {
         var2 = (byte)(var2 | 16);
      } else {
         var2 = (byte)(var2 & -17);
      }

      this.ac.b(10, var2);
   }

   public boolean s() {
      return (this.ac.a(10) & 16) != 0;
   }

   public void a(Rotations var1) {
      this.bk = var1;
      this.ac.b(11, var1);
   }

   public void b(Rotations var1) {
      this.bl = var1;
      this.ac.b(12, var1);
   }

   public void c(Rotations var1) {
      this.bm = var1;
      this.ac.b(13, var1);
   }

   public void d(Rotations var1) {
      this.bn = var1;
      this.ac.b(14, var1);
   }

   public void e(Rotations var1) {
      this.bo = var1;
      this.ac.b(15, var1);
   }

   public void f(Rotations var1) {
      this.bp = var1;
      this.ac.b(16, var1);
   }

   public Rotations t() {
      return this.bk;
   }

   public Rotations u() {
      return this.bl;
   }

   public Rotations v() {
      return this.bm;
   }

   public Rotations w() {
      return this.bn;
   }

   public Rotations x() {
      return this.bo;
   }

   public Rotations y() {
      return this.bp;
   }

   @Override
   public boolean ad() {
      return super.ad() && !this.s();
   }
}

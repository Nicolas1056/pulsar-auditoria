package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
   private int c;
   protected BlockPos a;
   public EnumFacing b;

   public EntityHanging(World var1) {
      super(var1);
      this.a(0.5F, 0.5F);
   }

   public EntityHanging(World var1, BlockPos var2) {
      this(var1);
      this.a = var2;
   }

   @Override
   protected void h() {
   }

   protected void a(EnumFacing var1) {
      Validate.notNull(var1);
      Validate.isTrue(var1.k().c());
      this.b = var1;
      this.A = this.y = this.b.b() * 90;
      this.o();
   }

   private void o() {
      if (this.b != null) {
         double var1 = this.a.n() + 0.5;
         double var3 = this.a.o() + 0.5;
         double var5 = this.a.p() + 0.5;
         double var7 = 0.46875;
         double var9 = this.a(this.l());
         double var11 = this.a(this.m());
         var1 -= this.b.g() * 0.46875;
         var5 -= this.b.i() * 0.46875;
         var3 += var11;
         EnumFacing var13 = this.b.f();
         var1 += var9 * var13.g();
         var5 += var9 * var13.i();
         this.s = var1;
         this.t = var3;
         this.u = var5;
         double var14 = this.l();
         double var16 = this.m();
         double var18 = this.l();
         if (this.b.k() == EnumFacing.Axis.c) {
            var18 = 1.0;
         } else {
            var14 = 1.0;
         }

         var14 /= 32.0;
         var16 /= 32.0;
         var18 /= 32.0;
         this.a(new AxisAlignedBB(var1 - var14, var3 - var16, var5 - var18, var1 + var14, var3 + var16, var5 + var18));
      }
   }

   private double a(int var1) {
      return var1 % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.c++ == 100 && !this.o.D) {
         this.c = 0;
         if (!this.I && !this.j()) {
            this.J();
            this.b(null);
         }
      }
   }

   public boolean j() {
      if (!this.o.a(this, this.aR()).isEmpty()) {
         return false;
      } else {
         int var1 = Math.max(1, this.l() / 16);
         int var2 = Math.max(1, this.m() / 16);
         BlockPos var3 = this.a.a(this.b.d());
         EnumFacing var4 = this.b.f();

         for (int var5 = 0; var5 < var1; var5++) {
            for (int var6 = 0; var6 < var2; var6++) {
               BlockPos var7 = var3.a(var4, var5).b(var6);
               Block var8 = this.o.p(var7).c();
               if (!var8.t().a() && !BlockRedstoneDiode.d(var8)) {
                  return false;
               }
            }
         }

         for (Entity var11 : this.o.b(this, this.aR())) {
            if (var11 instanceof EntityHanging) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public boolean ad() {
      return true;
   }

   @Override
   public boolean l(Entity var1) {
      return var1 instanceof EntityPlayer ? this.a(DamageSource.a((EntityPlayer)var1), 0.0F) : false;
   }

   @Override
   public EnumFacing aP() {
      return this.b;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         if (!this.I && !this.o.D) {
            this.J();
            this.ac();
            this.b(var1.j());
         }

         return true;
      }
   }

   @Override
   public void d(double var1, double var3, double var5) {
      if (!this.o.D && !this.I && var1 * var1 + var3 * var3 + var5 * var5 > 0.0) {
         this.J();
         this.b(null);
      }
   }

   @Override
   public void g(double var1, double var3, double var5) {
      if (!this.o.D && !this.I && var1 * var1 + var3 * var3 + var5 * var5 > 0.0) {
         this.J();
         this.b(null);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Facing", (byte)this.b.b());
      var1.a("TileX", this.n().n());
      var1.a("TileY", this.n().o());
      var1.a("TileZ", this.n().p());
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.a = new BlockPos(var1.f("TileX"), var1.f("TileY"), var1.f("TileZ"));
      EnumFacing var2;
      if (var1.b("Direction", 99)) {
         var2 = EnumFacing.b(var1.d("Direction"));
         this.a = this.a.a(var2);
      } else if (var1.b("Facing", 99)) {
         var2 = EnumFacing.b(var1.d("Facing"));
      } else {
         var2 = EnumFacing.b(var1.d("Dir"));
      }

      this.a(var2);
   }

   public abstract int l();

   public abstract int m();

   public abstract void b(Entity var1);

   @Override
   protected boolean af() {
      return false;
   }

   @Override
   public void b(double var1, double var3, double var5) {
      this.s = var1;
      this.t = var3;
      this.u = var5;
      BlockPos var7 = this.a;
      this.a = new BlockPos(var1, var3, var5);
      if (!this.a.equals(var7)) {
         this.o();
         this.ai = true;
      }
   }

   public BlockPos n() {
      return this.a;
   }
}

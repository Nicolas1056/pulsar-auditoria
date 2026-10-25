package net.minecraft.entity.projectile;

import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityThrowable extends Entity implements IProjectile {
   private int c = -1;
   private int d = -1;
   private int e = -1;
   private Block f;
   protected boolean a;
   public int b;
   private EntityLivingBase g;
   private String h;
   private int i;
   private int ar;

   public EntityThrowable(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
   }

   @Override
   protected void h() {
   }

   @Override
   public boolean a(double var1) {
      double var3 = this.aR().a() * 4.0;
      if (Double.isNaN(var3)) {
         var3 = 4.0;
      }

      var3 *= 64.0;
      return var1 < var3 * var3;
   }

   public EntityThrowable(World var1, EntityLivingBase var2) {
      super(var1);
      this.g = var2;
      this.a(0.25F, 0.25F);
      this.b(var2.s, var2.t + var2.aS(), var2.u, var2.y, var2.z);
      this.s = this.s - MathHelper.b(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.t -= 0.1F;
      this.u = this.u - MathHelper.a(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.b(this.s, this.t, this.u);
      float var3 = 0.4F;
      this.v = -MathHelper.a(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var3;
      this.x = MathHelper.b(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var3;
      this.w = -MathHelper.a((this.z + this.l()) / 180.0F * (float) Math.PI) * var3;
      this.c(this.v, this.w, this.x, this.j(), 1.0F);
   }

   public EntityThrowable(World var1, double var2, double var4, double var6) {
      super(var1);
      this.i = 0;
      this.a(0.25F, 0.25F);
      this.b(var2, var4, var6);
   }

   protected float j() {
      return 1.5F;
   }

   protected float l() {
      return 0.0F;
   }

   @Override
   public void c(double var1, double var3, double var5, float var7, float var8) {
      float var9 = MathHelper.a(var1 * var1 + var3 * var3 + var5 * var5);
      var1 /= var9;
      var3 /= var9;
      var5 /= var9;
      var1 += this.V.nextGaussian() * 0.0075F * var8;
      var3 += this.V.nextGaussian() * 0.0075F * var8;
      var5 += this.V.nextGaussian() * 0.0075F * var8;
      var1 *= var7;
      var3 *= var7;
      var5 *= var7;
      this.v = var1;
      this.w = var3;
      this.x = var5;
      float var10 = MathHelper.a(var1 * var1 + var5 * var5);
      this.A = this.y = (float)(MathHelper.b(var1, var5) * 180.0 / (float) Math.PI);
      this.B = this.z = (float)(MathHelper.b(var3, (double)var10) * 180.0 / (float) Math.PI);
      this.i = 0;
   }

   @Override
   public void i(double var1, double var3, double var5) {
      this.v = var1;
      this.w = var3;
      this.x = var5;
      if (this.B == 0.0F && this.A == 0.0F) {
         float var7 = MathHelper.a(var1 * var1 + var5 * var5);
         this.A = this.y = (float)(MathHelper.b(var1, var5) * 180.0 / (float) Math.PI);
         this.B = this.z = (float)(MathHelper.b(var3, (double)var7) * 180.0 / (float) Math.PI);
      }
   }

   @Override
   public void t_() {
      this.P = this.s;
      this.Q = this.t;
      this.R = this.u;
      super.t_();
      if (this.b > 0) {
         this.b--;
      }

      if (this.a) {
         if (this.o.p(new BlockPos(this.c, this.d, this.e)).c() == this.f) {
            this.i++;
            if (this.i == 1200) {
               this.J();
            }

            return;
         }

         this.a = false;
         this.v = this.v * (this.V.nextFloat() * 0.2F);
         this.w = this.w * (this.V.nextFloat() * 0.2F);
         this.x = this.x * (this.V.nextFloat() * 0.2F);
         this.i = 0;
         this.ar = 0;
      } else {
         this.ar++;
      }

      Vec3 var1 = new Vec3(this.s, this.t, this.u);
      Vec3 var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
      MovingObjectPosition var3 = this.o.a(var1, var2);
      var1 = new Vec3(this.s, this.t, this.u);
      var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
      if (var3 != null) {
         var2 = new Vec3(var3.c.a, var3.c.b, var3.c.c);
      }

      if (!this.o.D) {
         Entity var4 = null;
         List var5 = this.o.b(this, this.aR().a(this.v, this.w, this.x).b(1.0, 1.0, 1.0));
         double var6 = 0.0;
         EntityLivingBase var8 = this.n();

         for (int var9 = 0; var9 < var5.size(); var9++) {
            Entity var10 = (Entity)var5.get(var9);
            if (var10.ad() && (var10 != var8 || this.ar >= 5)) {
               float var11 = 0.3F;
               AxisAlignedBB var12 = var10.aR().b(var11, var11, var11);
               MovingObjectPosition var13 = var12.a(var1, var2);
               if (var13 != null) {
                  double var14 = var1.g(var13.c);
                  if (var14 < var6 || var6 == 0.0) {
                     var4 = var10;
                     var6 = var14;
                  }
               }
            }
         }

         if (var4 != null) {
            var3 = new MovingObjectPosition(var4);
         }
      }

      if (var3 != null) {
         if (var3.a == MovingObjectPosition.MovingObjectType.b && this.o.p(var3.a()).c() == Blocks.aY) {
            this.d(var3.a());
         } else {
            this.a(var3);
         }
      }

      this.s = this.s + this.v;
      this.t = this.t + this.w;
      this.u = this.u + this.x;
      float var18 = MathHelper.a(this.v * this.v + this.x * this.x);
      this.y = (float)(MathHelper.b(this.v, this.x) * 180.0 / (float) Math.PI);
      this.z = (float)(MathHelper.b(this.w, (double)var18) * 180.0 / (float) Math.PI);

      while (this.z - this.B < -180.0F) {
         this.B -= 360.0F;
      }

      while (this.z - this.B >= 180.0F) {
         this.B += 360.0F;
      }

      while (this.y - this.A < -180.0F) {
         this.A -= 360.0F;
      }

      while (this.y - this.A >= 180.0F) {
         this.A += 360.0F;
      }

      this.z = this.B + (this.z - this.B) * 0.2F;
      this.y = this.A + (this.y - this.A) * 0.2F;
      float var19 = 0.99F;
      float var20 = this.m();
      if (this.V()) {
         for (int var7 = 0; var7 < 4; var7++) {
            float var21 = 0.25F;
            this.o.a(EnumParticleTypes.e, this.s - this.v * var21, this.t - this.w * var21, this.u - this.x * var21, this.v, this.w, this.x);
         }

         var19 = 0.8F;
      }

      this.v *= var19;
      this.w *= var19;
      this.x *= var19;
      this.w -= var20;
      this.b(this.s, this.t, this.u);
   }

   protected float m() {
      return 0.03F;
   }

   protected abstract void a(MovingObjectPosition var1);

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("xTile", (short)this.c);
      var1.a("yTile", (short)this.d);
      var1.a("zTile", (short)this.e);
      ResourceLocation var2 = Block.c.c(this.f);
      var1.a("inTile", var2 == null ? "" : var2.toString());
      var1.a("shake", (byte)this.b);
      var1.a("inGround", (byte)(this.a ? 1 : 0));
      if ((this.h == null || this.h.length() == 0) && this.g instanceof EntityPlayer) {
         this.h = this.g.e_();
      }

      var1.a("ownerName", this.h == null ? "" : this.h);
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.c = var1.e("xTile");
      this.d = var1.e("yTile");
      this.e = var1.e("zTile");
      if (var1.b("inTile", 8)) {
         this.f = Block.b(var1.j("inTile"));
      } else {
         this.f = Block.c(var1.d("inTile") & 255);
      }

      this.b = var1.d("shake") & 255;
      this.a = var1.d("inGround") == 1;
      this.g = null;
      this.h = var1.j("ownerName");
      if (this.h != null && this.h.length() == 0) {
         this.h = null;
      }

      this.g = this.n();
   }

   public EntityLivingBase n() {
      if (this.g == null && this.h != null && this.h.length() > 0) {
         this.g = this.o.a(this.h);
         if (this.g == null && this.o instanceof WorldServer) {
            try {
               Entity var1 = ((WorldServer)this.o).a(UUID.fromString(this.h));
               if (var1 instanceof EntityLivingBase) {
                  this.g = (EntityLivingBase)var1;
               }
            } catch (Throwable var2) {
               this.g = null;
            }
         }
      }

      return this.g;
   }
}

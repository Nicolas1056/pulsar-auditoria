package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityArrow extends Entity implements IProjectile {
   private int d = -1;
   private int e = -1;
   private int f = -1;
   private Block g;
   private int h;
   private boolean i;
   public int a;
   public int b;
   public Entity c;
   private int ar;
   private int as;
   private double at = 2.0;
   private int au;

   public EntityArrow(World var1) {
      super(var1);
      this.j = 10.0;
      this.a(0.5F, 0.5F);
   }

   public EntityArrow(World var1, double var2, double var4, double var6) {
      super(var1);
      this.j = 10.0;
      this.a(0.5F, 0.5F);
      this.b(var2, var4, var6);
   }

   public EntityArrow(World var1, EntityLivingBase var2, EntityLivingBase var3, float var4, float var5) {
      super(var1);
      this.j = 10.0;
      this.c = var2;
      if (var2 instanceof EntityPlayer) {
         this.a = 1;
      }

      this.t = var2.t + var2.aS() - 0.1F;
      double var6 = var3.s - var2.s;
      double var8 = var3.aR().b + var3.K / 3.0F - this.t;
      double var10 = var3.u - var2.u;
      double var12 = MathHelper.a(var6 * var6 + var10 * var10);
      if (!(var12 < 1.0E-7)) {
         float var14 = (float)(MathHelper.b(var10, var6) * 180.0 / (float) Math.PI) - 90.0F;
         float var15 = (float)(-(MathHelper.b(var8, var12) * 180.0 / (float) Math.PI));
         double var16 = var6 / var12;
         double var18 = var10 / var12;
         this.b(var2.s + var16, this.t, var2.u + var18, var14, var15);
         float var20 = (float)(var12 * 0.2F);
         this.c(var6, var8 + var20, var10, var4, var5);
      }
   }

   public EntityArrow(World var1, EntityLivingBase var2, float var3) {
      super(var1);
      this.j = 10.0;
      this.c = var2;
      if (var2 instanceof EntityPlayer) {
         this.a = 1;
      }

      this.a(0.5F, 0.5F);
      this.b(var2.s, var2.t + var2.aS(), var2.u, var2.y, var2.z);
      this.s = this.s - MathHelper.b(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.t -= 0.1F;
      this.u = this.u - MathHelper.a(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.b(this.s, this.t, this.u);
      this.v = -MathHelper.a(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI);
      this.x = MathHelper.b(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI);
      this.w = -MathHelper.a(this.z / 180.0F * (float) Math.PI);
      this.c(this.v, this.w, this.x, var3 * 1.5F, 1.0F);
   }

   @Override
   protected void h() {
      this.ac.a(16, Byte.valueOf((byte)0));
   }

   @Override
   public void c(double var1, double var3, double var5, float var7, float var8) {
      float var9 = MathHelper.a(var1 * var1 + var3 * var3 + var5 * var5);
      var1 /= var9;
      var3 /= var9;
      var5 /= var9;
      var1 += this.V.nextGaussian() * (this.V.nextBoolean() ? -1 : 1) * 0.0075F * var8;
      var3 += this.V.nextGaussian() * (this.V.nextBoolean() ? -1 : 1) * 0.0075F * var8;
      var5 += this.V.nextGaussian() * (this.V.nextBoolean() ? -1 : 1) * 0.0075F * var8;
      var1 *= var7;
      var3 *= var7;
      var5 *= var7;
      this.v = var1;
      this.w = var3;
      this.x = var5;
      float var10 = MathHelper.a(var1 * var1 + var5 * var5);
      this.A = this.y = (float)(MathHelper.b(var1, var5) * 180.0 / (float) Math.PI);
      this.B = this.z = (float)(MathHelper.b(var3, (double)var10) * 180.0 / (float) Math.PI);
      this.ar = 0;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.b(var1, var3, var5);
      this.b(var7, var8);
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
         this.B = this.z;
         this.A = this.y;
         this.b(this.s, this.t, this.u, this.y, this.z);
         this.ar = 0;
      }
   }

   @Override
   public void t_() {
      super.t_();
      if (this.B == 0.0F && this.A == 0.0F) {
         float var1 = MathHelper.a(this.v * this.v + this.x * this.x);
         this.A = this.y = (float)(MathHelper.b(this.v, this.x) * 180.0 / (float) Math.PI);
         this.B = this.z = (float)(MathHelper.b(this.w, (double)var1) * 180.0 / (float) Math.PI);
      }

      BlockPos var18 = new BlockPos(this.d, this.e, this.f);
      IBlockState var2 = this.o.p(var18);
      Block var3 = var2.c();
      if (var3.t() != Material.a) {
         var3.a((IBlockAccess)this.o, var18);
         AxisAlignedBB var4 = var3.a(this.o, var18, var2);
         if (var4 != null && var4.a(new Vec3(this.s, this.t, this.u))) {
            this.i = true;
         }
      }

      if (this.b > 0) {
         this.b--;
      }

      if (this.i) {
         int var21 = var3.c(var2);
         if (var3 == this.g && var21 == this.h) {
            this.ar++;
            if (this.ar >= 1200) {
               this.J();
            }
         } else {
            this.i = false;
            this.v = this.v * (this.V.nextFloat() * 0.2F);
            this.w = this.w * (this.V.nextFloat() * 0.2F);
            this.x = this.x * (this.V.nextFloat() * 0.2F);
            this.ar = 0;
            this.as = 0;
         }
      } else {
         this.as++;
         Vec3 var19 = new Vec3(this.s, this.t, this.u);
         Vec3 var5 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         MovingObjectPosition var6 = this.o.a(var19, var5, false, true, false);
         var19 = new Vec3(this.s, this.t, this.u);
         var5 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         if (var6 != null) {
            var5 = new Vec3(var6.c.a, var6.c.b, var6.c.c);
         }

         Entity var7 = null;
         List var8 = this.o.b(this, this.aR().a(this.v, this.w, this.x).b(1.0, 1.0, 1.0));
         double var9 = 0.0;

         for (int var11 = 0; var11 < var8.size(); var11++) {
            Entity var12 = (Entity)var8.get(var11);
            if (var12.ad() && (var12 != this.c || this.as >= 5)) {
               float var13 = 0.3F;
               AxisAlignedBB var14 = var12.aR().b(var13, var13, var13);
               MovingObjectPosition var15 = var14.a(var19, var5);
               if (var15 != null) {
                  double var16 = var19.g(var15.c);
                  if (var16 < var9 || var9 == 0.0) {
                     var7 = var12;
                     var9 = var16;
                  }
               }
            }
         }

         if (var7 != null) {
            var6 = new MovingObjectPosition(var7);
         }

         if (var6 != null && var6.d != null && var6.d instanceof EntityPlayer) {
            EntityPlayer var23 = (EntityPlayer)var6.d;
            if (var23.bA.a || this.c instanceof EntityPlayer && !((EntityPlayer)this.c).a(var23)) {
               var6 = null;
            }
         }

         if (var6 != null) {
            if (var6.d != null) {
               float var24 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
               int var28 = MathHelper.f(var24 * this.at);
               if (this.l()) {
                  var28 += this.V.nextInt(var28 / 2 + 2);
               }

               DamageSource var31;
               if (this.c == null) {
                  var31 = DamageSource.a(this, this);
               } else {
                  var31 = DamageSource.a(this, this.c);
               }

               if (this.at() && !(var6.d instanceof EntityEnderman)) {
                  var6.d.e(5);
               }

               if (var6.d.a(var31, (float)var28)) {
                  if (var6.d instanceof EntityLivingBase) {
                     EntityLivingBase var34 = (EntityLivingBase)var6.d;
                     if (!this.o.D) {
                        var34.o(var34.bv() + 1);
                     }

                     if (this.au > 0) {
                        float var36 = MathHelper.a(this.v * this.v + this.x * this.x);
                        if (var36 > 0.0F) {
                           var6.d.g(this.v * this.au * 0.6F / var36, 0.1, this.x * this.au * 0.6F / var36);
                        }
                     }

                     if (this.c instanceof EntityLivingBase) {
                        EnchantmentHelper.a(var34, this.c);
                        EnchantmentHelper.b((EntityLivingBase)this.c, var34);
                     }

                     if (this.c != null && var6.d != this.c && var6.d instanceof EntityPlayer && this.c instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.c).a.a(new S2BPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.a("random.bowhit", 1.0F, 1.2F / (this.V.nextFloat() * 0.2F + 0.9F));
                  if (!(var6.d instanceof EntityEnderman)) {
                     this.J();
                  }
               } else {
                  this.v *= -0.1F;
                  this.w *= -0.1F;
                  this.x *= -0.1F;
                  this.y += 180.0F;
                  this.A += 180.0F;
                  this.as = 0;
               }
            } else {
               BlockPos var25 = var6.a();
               this.d = var25.n();
               this.e = var25.o();
               this.f = var25.p();
               IBlockState var29 = this.o.p(var25);
               this.g = var29.c();
               this.h = this.g.c(var29);
               this.v = (float)(var6.c.a - this.s);
               this.w = (float)(var6.c.b - this.t);
               this.x = (float)(var6.c.c - this.u);
               float var32 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
               this.s = this.s - this.v / var32 * 0.05F;
               this.t = this.t - this.w / var32 * 0.05F;
               this.u = this.u - this.x / var32 * 0.05F;
               this.a("random.bowhit", 1.0F, 1.2F / (this.V.nextFloat() * 0.2F + 0.9F));
               this.i = true;
               this.b = 7;
               this.a(false);
               if (this.g.t() != Material.a) {
                  this.g.a(this.o, var25, var29, this);
               }
            }
         }

         if (this.l()) {
            for (int var26 = 0; var26 < 4; var26++) {
               this.o
                  .a(
                     EnumParticleTypes.j,
                     this.s + this.v * var26 / 4.0,
                     this.t + this.w * var26 / 4.0,
                     this.u + this.x * var26 / 4.0,
                     -this.v,
                     -this.w + 0.2,
                     -this.x
                  );
            }
         }

         this.s = this.s + this.v;
         this.t = this.t + this.w;
         this.u = this.u + this.x;
         float var27 = MathHelper.a(this.v * this.v + this.x * this.x);
         this.y = (float)(MathHelper.b(this.v, this.x) * 180.0 / (float) Math.PI);
         this.z = (float)(MathHelper.b(this.w, (double)var27) * 180.0 / (float) Math.PI);

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
         float var30 = 0.99F;
         float var33 = 0.05F;
         if (this.V()) {
            for (int var35 = 0; var35 < 4; var35++) {
               float var37 = 0.25F;
               this.o.a(EnumParticleTypes.e, this.s - this.v * var37, this.t - this.w * var37, this.u - this.x * var37, this.v, this.w, this.x);
            }

            var30 = 0.6F;
         }

         if (this.U()) {
            this.N();
         }

         this.v *= var30;
         this.w *= var30;
         this.x *= var30;
         this.w -= var33;
         this.b(this.s, this.t, this.u);
         this.Q();
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("xTile", (short)this.d);
      var1.a("yTile", (short)this.e);
      var1.a("zTile", (short)this.f);
      var1.a("life", (short)this.ar);
      ResourceLocation var2 = Block.c.c(this.g);
      var1.a("inTile", var2 == null ? "" : var2.toString());
      var1.a("inData", (byte)this.h);
      var1.a("shake", (byte)this.b);
      var1.a("inGround", (byte)(this.i ? 1 : 0));
      var1.a("pickup", (byte)this.a);
      var1.a("damage", this.at);
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.d = var1.e("xTile");
      this.e = var1.e("yTile");
      this.f = var1.e("zTile");
      this.ar = var1.e("life");
      if (var1.b("inTile", 8)) {
         this.g = Block.b(var1.j("inTile"));
      } else {
         this.g = Block.c(var1.d("inTile") & 255);
      }

      this.h = var1.d("inData") & 255;
      this.b = var1.d("shake") & 255;
      this.i = var1.d("inGround") == 1;
      if (var1.b("damage", 99)) {
         this.at = var1.i("damage");
      }

      if (var1.b("pickup", 99)) {
         this.a = var1.d("pickup");
      } else if (var1.b("player", 99)) {
         this.a = var1.n("player") ? 1 : 0;
      }
   }

   @Override
   public void d(EntityPlayer var1) {
      if (!this.o.D && this.i && this.b <= 0) {
         boolean var2 = this.a == 1 || this.a == 2 && var1.bA.d;
         if (this.a == 1 && !var1.bi.a(new ItemStack(Items.g, 1))) {
            var2 = false;
         }

         if (var2) {
            this.a("random.pop", 0.2F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            var1.a(this, 1);
            this.J();
         }
      }
   }

   @Override
   protected boolean s_() {
      return false;
   }

   public void b(double var1) {
      this.at = var1;
   }

   public double j() {
      return this.at;
   }

   public void a(int var1) {
      this.au = var1;
   }

   @Override
   public boolean aD() {
      return false;
   }

   @Override
   public float aS() {
      return 0.0F;
   }

   public void a(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 1));
      } else {
         this.ac.b(16, (byte)(var2 & -2));
      }
   }

   public boolean l() {
      byte var1 = this.ac.a(16);
      return (var1 & 1) != 0;
   }
}

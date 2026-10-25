package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBoat extends Entity {
   private boolean a = true;
   private double b = 0.07;
   private int c;
   private double d;
   private double e;
   private double f;
   private double g;
   private double h;
   private double i;
   private double ar;
   private double as;

   public EntityBoat(World var1) {
      super(var1);
      this.k = true;
      this.a(1.5F, 0.6F);
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected void h() {
      this.ac.a(17, new Integer(0));
      this.ac.a(18, new Integer(1));
      this.ac.a(19, new Float(0.0F));
   }

   @Override
   public AxisAlignedBB j(Entity var1) {
      return var1.aR();
   }

   @Override
   public AxisAlignedBB S() {
      return this.aR();
   }

   @Override
   public boolean ae() {
      return true;
   }

   public EntityBoat(World var1, double var2, double var4, double var6) {
      this(var1);
      this.b(var2, var4, var6);
      this.v = 0.0;
      this.w = 0.0;
      this.x = 0.0;
      this.p = var2;
      this.q = var4;
      this.r = var6;
   }

   @Override
   public double an() {
      return -0.3;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (!this.o.D && !this.I) {
         if (this.l != null && this.l == var1.j() && var1 instanceof EntityDamageSourceIndirect) {
            return false;
         } else {
            this.b(-this.m());
            this.a(10);
            this.a(this.j() + var2 * 10.0F);
            this.ac();
            boolean var3 = var1.j() instanceof EntityPlayer && ((EntityPlayer)var1.j()).bA.d;
            if (var3 || this.j() > 40.0F) {
               if (this.l != null) {
                  this.l.a(this);
               }

               if (!var3 && this.o.Q().b("doEntityDrops")) {
                  this.a(Items.aE, 1, 0.0F);
               }

               this.J();
            }

            return true;
         }
      } else {
         return true;
      }
   }

   @Override
   public void ar() {
      this.b(-this.m());
      this.a(10);
      this.a(this.j() * 11.0F);
   }

   @Override
   public boolean ad() {
      return !this.I;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      if (var10 && this.l != null) {
         this.p = this.s = var1;
         this.q = this.t = var3;
         this.r = this.u = var5;
         this.y = var7;
         this.z = var8;
         this.c = 0;
         this.b(var1, var3, var5);
         this.v = this.i = 0.0;
         this.w = this.ar = 0.0;
         this.x = this.as = 0.0;
      } else {
         if (this.a) {
            this.c = var9 + 5;
         } else {
            double var11 = var1 - this.s;
            double var13 = var3 - this.t;
            double var15 = var5 - this.u;
            double var17 = var11 * var11 + var13 * var13 + var15 * var15;
            if (!(var17 > 1.0)) {
               return;
            }

            this.c = 3;
         }

         this.d = var1;
         this.e = var3;
         this.f = var5;
         this.g = var7;
         this.h = var8;
         this.v = this.i;
         this.w = this.ar;
         this.x = this.as;
      }
   }

   @Override
   public void i(double var1, double var3, double var5) {
      this.i = this.v = var1;
      this.ar = this.w = var3;
      this.as = this.x = var5;
   }

   @Override
   public void t_() {
      super.t_();
      if (this.l() > 0) {
         this.a(this.l() - 1);
      }

      if (this.j() > 0.0F) {
         this.a(this.j() - 1.0F);
      }

      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      byte var1 = 5;
      double var2 = 0.0;

      for (int var4 = 0; var4 < var1; var4++) {
         double var5 = this.aR().b + (this.aR().e - this.aR().b) * (var4 + 0) / var1 - 0.125;
         double var7 = this.aR().b + (this.aR().e - this.aR().b) * (var4 + 1) / var1 - 0.125;
         AxisAlignedBB var9 = new AxisAlignedBB(this.aR().a, var5, this.aR().c, this.aR().d, var7, this.aR().f);
         if (this.o.b(var9, Material.h)) {
            var2 += 1.0 / var1;
         }
      }

      double var19 = Math.sqrt(this.v * this.v + this.x * this.x);
      if (var19 > 0.2975) {
         double var6 = Math.cos(this.y * Math.PI / 180.0);
         double var8 = Math.sin(this.y * Math.PI / 180.0);

         for (int var10 = 0; var10 < 1.0 + var19 * 60.0; var10++) {
            double var11 = this.V.nextFloat() * 2.0F - 1.0F;
            double var13 = (this.V.nextInt(2) * 2 - 1) * 0.7;
            if (this.V.nextBoolean()) {
               double var15 = this.s - var6 * var11 * 0.8 + var8 * var13;
               double var17 = this.u - var8 * var11 * 0.8 - var6 * var13;
               this.o.a(EnumParticleTypes.f, var15, this.t - 0.125, var17, this.v, this.w, this.x);
            } else {
               double var43 = this.s + var6 + var8 * var11 * 0.7;
               double var44 = this.u + var8 - var6 * var11 * 0.7;
               this.o.a(EnumParticleTypes.f, var43, this.t - 0.125, var44, this.v, this.w, this.x);
            }
         }
      }

      if (this.o.D && this.a) {
         if (this.c > 0) {
            double var23 = this.s + (this.d - this.s) / this.c;
            double var31 = this.t + (this.e - this.t) / this.c;
            double var36 = this.u + (this.f - this.u) / this.c;
            double var40 = MathHelper.g(this.g - this.y);
            this.y = (float)(this.y + var40 / this.c);
            this.z = (float)(this.z + (this.h - this.z) / this.c);
            this.c--;
            this.b(var23, var31, var36);
            this.b(this.y, this.z);
         } else {
            double var24 = this.s + this.v;
            double var32 = this.t + this.w;
            double var37 = this.u + this.x;
            this.b(var24, var32, var37);
            if (this.C) {
               this.v *= 0.5;
               this.w *= 0.5;
               this.x *= 0.5;
            }

            this.v *= 0.99F;
            this.w *= 0.95F;
            this.x *= 0.99F;
         }
      } else {
         if (var2 < 1.0) {
            double var20 = var2 * 2.0 - 1.0;
            this.w += 0.04F * var20;
         } else {
            if (this.w < 0.0) {
               this.w /= 2.0;
            }

            this.w += 0.007F;
         }

         if (this.l instanceof EntityLivingBase) {
            EntityLivingBase var21 = (EntityLivingBase)this.l;
            float var25 = this.l.y + -var21.aZ * 90.0F;
            this.v = this.v + -Math.sin(var25 * (float) Math.PI / 180.0F) * this.b * var21.ba * 0.05F;
            this.x = this.x + Math.cos(var25 * (float) Math.PI / 180.0F) * this.b * var21.ba * 0.05F;
         }

         double var22 = Math.sqrt(this.v * this.v + this.x * this.x);
         if (var22 > 0.35) {
            double var26 = 0.35 / var22;
            this.v *= var26;
            this.x *= var26;
            var22 = 0.35;
         }

         if (var22 > var19 && this.b < 0.35) {
            this.b = this.b + (0.35 - this.b) / 35.0;
            if (this.b > 0.35) {
               this.b = 0.35;
            }
         } else {
            this.b = this.b - (this.b - 0.07) / 35.0;
            if (this.b < 0.07) {
               this.b = 0.07;
            }
         }

         for (int var27 = 0; var27 < 4; var27++) {
            int var33 = MathHelper.c(this.s + (var27 % 2 - 0.5) * 0.8);
            int var34 = MathHelper.c(this.u + (var27 / 2 - 0.5) * 0.8);

            for (int var38 = 0; var38 < 2; var38++) {
               int var12 = MathHelper.c(this.t) + var38;
               BlockPos var41 = new BlockPos(var33, var12, var34);
               Block var14 = this.o.p(var41).c();
               if (var14 == Blocks.aH) {
                  this.o.g(var41);
                  this.D = false;
               } else if (var14 == Blocks.bx) {
                  this.o.b(var41, true);
                  this.D = false;
               }
            }
         }

         if (this.C) {
            this.v *= 0.5;
            this.w *= 0.5;
            this.x *= 0.5;
         }

         this.d(this.v, this.w, this.x);
         if (!this.D || !(var19 > 0.2975)) {
            this.v *= 0.99F;
            this.w *= 0.95F;
            this.x *= 0.99F;
         } else if (!this.o.D && !this.I) {
            this.J();
            if (this.o.Q().b("doEntityDrops")) {
               for (int var28 = 0; var28 < 3; var28++) {
                  this.a(Item.a(Blocks.f), 1, 0.0F);
               }

               for (int var29 = 0; var29 < 2; var29++) {
                  this.a(Items.y, 1, 0.0F);
               }
            }
         }

         this.z = 0.0F;
         double var30 = this.y;
         double var35 = this.p - this.s;
         double var39 = this.r - this.u;
         if (var35 * var35 + var39 * var39 > 0.001) {
            var30 = (float)(MathHelper.b(var39, var35) * 180.0 / Math.PI);
         }

         double var42 = MathHelper.g(var30 - this.y);
         if (var42 > 20.0) {
            var42 = 20.0;
         }

         if (var42 < -20.0) {
            var42 = -20.0;
         }

         this.y = (float)(this.y + var42);
         this.b(this.y, this.z);
         if (!this.o.D) {
            List var16 = this.o.b(this, this.aR().b(0.2F, 0.0, 0.2F));
            if (var16 != null && !var16.isEmpty()) {
               for (int var45 = 0; var45 < var16.size(); var45++) {
                  Entity var18 = (Entity)var16.get(var45);
                  if (var18 != this.l && var18.ae() && var18 instanceof EntityBoat) {
                     var18.i(this);
                  }
               }
            }

            if (this.l != null && this.l.I) {
               this.l = null;
            }
         }
      }
   }

   @Override
   public void al() {
      if (this.l != null) {
         double var1 = Math.cos(this.y * Math.PI / 180.0) * 0.4;
         double var3 = Math.sin(this.y * Math.PI / 180.0) * 0.4;
         this.l.b(this.s + var1, this.t + this.an() + this.l.am(), this.u + var3);
      }
   }

   @Override
   protected void b(NBTTagCompound var1) {
   }

   @Override
   protected void a(NBTTagCompound var1) {
   }

   @Override
   public boolean e(EntityPlayer var1) {
      if (this.l != null && this.l instanceof EntityPlayer && this.l != var1) {
         return true;
      } else {
         if (!this.o.D) {
            var1.a(this);
         }

         return true;
      }
   }

   @Override
   protected void a(double var1, boolean var3, Block var4, BlockPos var5) {
      if (var3) {
         if (this.O > 3.0F) {
            this.e(this.O, 1.0F);
            if (!this.o.D && !this.I) {
               this.J();
               if (this.o.Q().b("doEntityDrops")) {
                  for (int var6 = 0; var6 < 3; var6++) {
                     this.a(Item.a(Blocks.f), 1, 0.0F);
                  }

                  for (int var7 = 0; var7 < 2; var7++) {
                     this.a(Items.y, 1, 0.0F);
                  }
               }
            }

            this.O = 0.0F;
         }
      } else if (this.o.p(new BlockPos(this).b()).c().t() != Material.h && var1 < 0.0) {
         this.O = (float)(this.O - var1);
      }
   }

   public void a(float var1) {
      this.ac.b(19, var1);
   }

   public float j() {
      return this.ac.d(19);
   }

   public void a(int var1) {
      this.ac.b(17, var1);
   }

   public int l() {
      return this.ac.c(17);
   }

   public void b(int var1) {
      this.ac.b(18, var1);
   }

   public int m() {
      return this.ac.c(18);
   }

   public void a(boolean var1) {
      this.a = var1;
   }
}

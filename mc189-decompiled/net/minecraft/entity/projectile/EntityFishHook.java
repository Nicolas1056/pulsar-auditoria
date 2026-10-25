package net.minecraft.entity.projectile;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityFishHook extends Entity {
   private static final List<WeightedRandomFishable> d = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.T), 10).a(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.aF), 10),
      new WeightedRandomFishable(new ItemStack(Items.aX), 10),
      new WeightedRandomFishable(new ItemStack(Items.bz), 10),
      new WeightedRandomFishable(new ItemStack(Items.F), 5),
      new WeightedRandomFishable(new ItemStack(Items.aR), 2).a(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.z), 10),
      new WeightedRandomFishable(new ItemStack(Items.y), 5),
      new WeightedRandomFishable(new ItemStack(Items.aW, 10, EnumDyeColor.p.b()), 1),
      new WeightedRandomFishable(new ItemStack(Blocks.bR), 10),
      new WeightedRandomFishable(new ItemStack(Items.bt), 10)
   );
   private static final List<WeightedRandomFishable> e = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Blocks.bx), 1),
      new WeightedRandomFishable(new ItemStack(Items.co), 1),
      new WeightedRandomFishable(new ItemStack(Items.aA), 1),
      new WeightedRandomFishable(new ItemStack(Items.f), 1).a(0.25F).a(),
      new WeightedRandomFishable(new ItemStack(Items.aR), 1).a(0.25F).a(),
      new WeightedRandomFishable(new ItemStack(Items.aL), 1).a()
   );
   private static final List<WeightedRandomFishable> f = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.aU, 1, ItemFishFood.FishType.a.a()), 60),
      new WeightedRandomFishable(new ItemStack(Items.aU, 1, ItemFishFood.FishType.b.a()), 25),
      new WeightedRandomFishable(new ItemStack(Items.aU, 1, ItemFishFood.FishType.c.a()), 2),
      new WeightedRandomFishable(new ItemStack(Items.aU, 1, ItemFishFood.FishType.d.a()), 13)
   );
   private int g = -1;
   private int h = -1;
   private int i = -1;
   private Block ar;
   private boolean as;
   public int a;
   public EntityPlayer b;
   private int at;
   private int au;
   private int av;
   private int aw;
   private int ax;
   private float ay;
   public Entity c;
   private int az;
   private double aA;
   private double aB;
   private double aC;
   private double aD;
   private double aE;
   private double aF;
   private double aG;
   private double aH;

   public static List<WeightedRandomFishable> j() {
      return f;
   }

   public EntityFishHook(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
      this.ah = true;
   }

   public EntityFishHook(World var1, double var2, double var4, double var6, EntityPlayer var8) {
      this(var1);
      this.b(var2, var4, var6);
      this.ah = true;
      this.b = var8;
      var8.bG = this;
   }

   public EntityFishHook(World var1, EntityPlayer var2) {
      super(var1);
      this.ah = true;
      this.b = var2;
      this.b.bG = this;
      this.a(0.25F, 0.25F);
      this.b(var2.s, var2.t + var2.aS(), var2.u, var2.y, var2.z);
      this.s = this.s - MathHelper.b(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.t -= 0.1F;
      this.u = this.u - MathHelper.a(this.y / 180.0F * (float) Math.PI) * 0.16F;
      this.b(this.s, this.t, this.u);
      float var3 = 0.4F;
      this.v = -MathHelper.a(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var3;
      this.x = MathHelper.b(this.y / 180.0F * (float) Math.PI) * MathHelper.b(this.z / 180.0F * (float) Math.PI) * var3;
      this.w = -MathHelper.a(this.z / 180.0F * (float) Math.PI) * var3;
      this.c(this.v, this.w, this.x, 1.5F, 1.0F);
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
      this.at = 0;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.aA = var1;
      this.aB = var3;
      this.aC = var5;
      this.aD = var7;
      this.aE = var8;
      this.az = var9;
      this.v = this.aF;
      this.w = this.aG;
      this.x = this.aH;
   }

   @Override
   public void i(double var1, double var3, double var5) {
      this.aF = this.v = var1;
      this.aG = this.w = var3;
      this.aH = this.x = var5;
   }

   @Override
   public void t_() {
      super.t_();
      if (this.az > 0) {
         double var30 = this.s + (this.aA - this.s) / this.az;
         double var32 = this.t + (this.aB - this.t) / this.az;
         double var33 = this.u + (this.aC - this.u) / this.az;
         double var7 = MathHelper.g(this.aD - this.y);
         this.y = (float)(this.y + var7 / this.az);
         this.z = (float)(this.z + (this.aE - this.z) / this.az);
         this.az--;
         this.b(var30, var32, var33);
         this.b(this.y, this.z);
      } else {
         if (!this.o.D) {
            ItemStack var1 = this.b.bZ();
            if (this.b.I || !this.b.ai() || var1 == null || var1.b() != Items.aR || this.h(this.b) > 1024.0) {
               this.J();
               this.b.bG = null;
               return;
            }

            if (this.c != null) {
               if (!this.c.I) {
                  this.s = this.c.s;
                  double var10002 = this.c.K;
                  this.t = this.c.aR().b + var10002 * 0.8;
                  this.u = this.c.u;
                  return;
               }

               this.c = null;
            }
         }

         if (this.a > 0) {
            this.a--;
         }

         if (this.as) {
            if (this.o.p(new BlockPos(this.g, this.h, this.i)).c() == this.ar) {
               this.at++;
               if (this.at == 1200) {
                  this.J();
               }

               return;
            }

            this.as = false;
            this.v = this.v * (this.V.nextFloat() * 0.2F);
            this.w = this.w * (this.V.nextFloat() * 0.2F);
            this.x = this.x * (this.V.nextFloat() * 0.2F);
            this.at = 0;
            this.au = 0;
         } else {
            this.au++;
         }

         Vec3 var28 = new Vec3(this.s, this.t, this.u);
         Vec3 var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         MovingObjectPosition var3 = this.o.a(var28, var2);
         var28 = new Vec3(this.s, this.t, this.u);
         var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         if (var3 != null) {
            var2 = new Vec3(var3.c.a, var3.c.b, var3.c.c);
         }

         Entity var4 = null;
         List var5 = this.o.b(this, this.aR().a(this.v, this.w, this.x).b(1.0, 1.0, 1.0));
         double var6 = 0.0;

         for (int var8 = 0; var8 < var5.size(); var8++) {
            Entity var9 = (Entity)var5.get(var8);
            if (var9.ad() && (var9 != this.b || this.au >= 5)) {
               float var10 = 0.3F;
               AxisAlignedBB var11 = var9.aR().b(var10, var10, var10);
               MovingObjectPosition var12 = var11.a(var28, var2);
               if (var12 != null) {
                  double var13 = var28.g(var12.c);
                  if (var13 < var6 || var6 == 0.0) {
                     var4 = var9;
                     var6 = var13;
                  }
               }
            }
         }

         if (var4 != null) {
            var3 = new MovingObjectPosition(var4);
         }

         if (var3 != null) {
            if (var3.d != null) {
               if (var3.d.a(DamageSource.a(this, this.b), 0.0F)) {
                  this.c = var3.d;
               }
            } else {
               this.as = true;
            }
         }

         if (!this.as) {
            this.d(this.v, this.w, this.x);
            float var34 = MathHelper.a(this.v * this.v + this.x * this.x);
            this.y = (float)(MathHelper.b(this.v, this.x) * 180.0 / (float) Math.PI);
            this.z = (float)(MathHelper.b(this.w, (double)var34) * 180.0 / (float) Math.PI);

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
            float var35 = 0.92F;
            if (this.C || this.D) {
               var35 = 0.5F;
            }

            byte var36 = 5;
            double var37 = 0.0;

            for (int var38 = 0; var38 < var36; var38++) {
               AxisAlignedBB var14 = this.aR();
               double var15 = var14.e - var14.b;
               double var17 = var14.b + var15 * var38 / var36;
               double var19 = var14.b + var15 * (var38 + 1) / var36;
               AxisAlignedBB var21 = new AxisAlignedBB(var14.a, var17, var14.c, var14.d, var19, var14.f);
               if (this.o.b(var21, Material.h)) {
                  var37 += 1.0 / var36;
               }
            }

            if (!this.o.D && var37 > 0.0) {
               WorldServer var39 = (WorldServer)this.o;
               int var41 = 1;
               BlockPos var42 = new BlockPos(this).a();
               if (this.V.nextFloat() < 0.25F && this.o.C(var42)) {
                  var41 = 2;
               }

               if (this.V.nextFloat() < 0.5F && !this.o.i(var42)) {
                  var41--;
               }

               if (this.av > 0) {
                  this.av--;
                  if (this.av <= 0) {
                     this.aw = 0;
                     this.ax = 0;
                  }
               } else if (this.ax > 0) {
                  this.ax -= var41;
                  if (this.ax <= 0) {
                     this.w -= 0.2F;
                     this.a("random.splash", 0.25F, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
                     float var44 = MathHelper.c(this.aR().b);
                     var39.a(EnumParticleTypes.e, this.s, var44 + 1.0F, this.u, (int)(1.0F + this.J * 20.0F), this.J, 0.0, this.J, 0.2F);
                     var39.a(EnumParticleTypes.g, this.s, var44 + 1.0F, this.u, (int)(1.0F + this.J * 20.0F), this.J, 0.0, this.J, 0.2F);
                     this.av = MathHelper.a(this.V, 10, 30);
                  } else {
                     this.ay = (float)(this.ay + this.V.nextGaussian() * 4.0);
                     float var43 = this.ay * (float) (Math.PI / 180.0);
                     float var46 = MathHelper.a(var43);
                     float var47 = MathHelper.b(var43);
                     double var49 = this.s + var46 * this.ax * 0.1F;
                     double var51 = MathHelper.c(this.aR().b) + 1.0F;
                     double var52 = this.u + var47 * this.ax * 0.1F;
                     Block var53 = var39.p(new BlockPos((int)var49, (int)var51 - 1, (int)var52)).c();
                     if (var53 == Blocks.j || var53 == Blocks.i) {
                        if (this.V.nextFloat() < 0.15F) {
                           var39.a(EnumParticleTypes.e, var49, var51 - 0.1F, var52, 1, var46, 0.1, var47, 0.0);
                        }

                        float var26 = var46 * 0.04F;
                        float var27 = var47 * 0.04F;
                        var39.a(EnumParticleTypes.g, var49, var51, var52, 0, var27, 0.01, -var26, 1.0);
                        var39.a(EnumParticleTypes.g, var49, var51, var52, 0, -var27, 0.01, var26, 1.0);
                     }
                  }
               } else if (this.aw > 0) {
                  this.aw -= var41;
                  float var16 = 0.15F;
                  if (this.aw < 20) {
                     var16 = (float)(var16 + (20 - this.aw) * 0.05);
                  } else if (this.aw < 40) {
                     var16 = (float)(var16 + (40 - this.aw) * 0.02);
                  } else if (this.aw < 60) {
                     var16 = (float)(var16 + (60 - this.aw) * 0.01);
                  }

                  if (this.V.nextFloat() < var16) {
                     float var45 = MathHelper.a(this.V, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
                     float var18 = MathHelper.a(this.V, 25.0F, 60.0F);
                     double var48 = this.s + MathHelper.a(var45) * var18 * 0.1F;
                     double var50 = MathHelper.c(this.aR().b) + 1.0F;
                     double var23 = this.u + MathHelper.b(var45) * var18 * 0.1F;
                     Block var25 = var39.p(new BlockPos((int)var48, (int)var50 - 1, (int)var23)).c();
                     if (var25 == Blocks.j || var25 == Blocks.i) {
                        var39.a(EnumParticleTypes.f, var48, var50, var23, 2 + this.V.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
                     }
                  }

                  if (this.aw <= 0) {
                     this.ay = MathHelper.a(this.V, 0.0F, 360.0F);
                     this.ax = MathHelper.a(this.V, 20, 80);
                  }
               } else {
                  this.aw = MathHelper.a(this.V, 100, 900);
                  this.aw = this.aw - EnchantmentHelper.h(this.b) * 20 * 5;
               }

               if (this.av > 0) {
                  this.w = this.w - this.V.nextFloat() * this.V.nextFloat() * this.V.nextFloat() * 0.2;
               }
            }

            double var40 = var37 * 2.0 - 1.0;
            this.w += 0.04F * var40;
            if (var37 > 0.0) {
               var35 = (float)(var35 * 0.9);
               this.w *= 0.8;
            }

            this.v *= var35;
            this.w *= var35;
            this.x *= var35;
            this.b(this.s, this.t, this.u);
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("xTile", (short)this.g);
      var1.a("yTile", (short)this.h);
      var1.a("zTile", (short)this.i);
      ResourceLocation var2 = Block.c.c(this.ar);
      var1.a("inTile", var2 == null ? "" : var2.toString());
      var1.a("shake", (byte)this.a);
      var1.a("inGround", (byte)(this.as ? 1 : 0));
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.g = var1.e("xTile");
      this.h = var1.e("yTile");
      this.i = var1.e("zTile");
      if (var1.b("inTile", 8)) {
         this.ar = Block.b(var1.j("inTile"));
      } else {
         this.ar = Block.c(var1.d("inTile") & 255);
      }

      this.a = var1.d("shake") & 255;
      this.as = var1.d("inGround") == 1;
   }

   public int l() {
      if (this.o.D) {
         return 0;
      } else {
         byte var1 = 0;
         if (this.c != null) {
            double var2 = this.b.s - this.s;
            double var4 = this.b.t - this.t;
            double var6 = this.b.u - this.u;
            double var8 = MathHelper.a(var2 * var2 + var4 * var4 + var6 * var6);
            double var10 = 0.1;
            this.c.v += var2 * var10;
            this.c.w = this.c.w + (var4 * var10 + MathHelper.a(var8) * 0.08);
            this.c.x += var6 * var10;
            var1 = 3;
         } else if (this.av > 0) {
            EntityItem var13 = new EntityItem(this.o, this.s, this.t, this.u, this.m());
            double var3 = this.b.s - this.s;
            double var5 = this.b.t - this.t;
            double var7 = this.b.u - this.u;
            double var9 = MathHelper.a(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 0.1;
            var13.v = var3 * var11;
            var13.w = var5 * var11 + MathHelper.a(var9) * 0.08;
            var13.x = var7 * var11;
            this.o.d(var13);
            this.b.o.d(new EntityXPOrb(this.b.o, this.b.s, this.b.t + 0.5, this.b.u + 0.5, this.V.nextInt(6) + 1));
            var1 = 1;
         }

         if (this.as) {
            var1 = 2;
         }

         this.J();
         this.b.bG = null;
         return var1;
      }
   }

   private ItemStack m() {
      float var1 = this.o.s.nextFloat();
      int var2 = EnchantmentHelper.g(this.b);
      int var3 = EnchantmentHelper.h(this.b);
      float var4 = 0.1F - var2 * 0.025F - var3 * 0.01F;
      float var5 = 0.05F + var2 * 0.01F - var3 * 0.01F;
      var4 = MathHelper.a(var4, 0.0F, 1.0F);
      var5 = MathHelper.a(var5, 0.0F, 1.0F);
      if (var1 < var4) {
         this.b.b(StatList.D);
         return WeightedRandom.a(this.V, d).a(this.V);
      } else {
         var1 -= var4;
         if (var1 < var5) {
            this.b.b(StatList.E);
            return WeightedRandom.a(this.V, e).a(this.V);
         } else {
            var1 -= var5;
            this.b.b(StatList.C);
            return WeightedRandom.a(this.V, f).a(this.V);
         }
      }
   }

   @Override
   public void J() {
      super.J();
      if (this.b != null) {
         this.b.bG = null;
      }
   }
}

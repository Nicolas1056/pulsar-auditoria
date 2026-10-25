package net.minecraft.entity.boss;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob {
   public double a;
   public double b;
   public double c;
   public double[][] bk = new double[64][3];
   public int bl = -1;
   public EntityDragonPart[] bm;
   public EntityDragonPart bn;
   public EntityDragonPart bo;
   public EntityDragonPart bp;
   public EntityDragonPart bq;
   public EntityDragonPart br;
   public EntityDragonPart bs;
   public EntityDragonPart bt;
   public float bu;
   public float bv;
   public boolean bw;
   public boolean bx;
   private Entity bA;
   public int by;
   public EntityEnderCrystal bz;

   public EntityDragon(World var1) {
      super(var1);
      this.bm = new EntityDragonPart[]{
         this.bn = new EntityDragonPart(this, "head", 6.0F, 6.0F),
         this.bo = new EntityDragonPart(this, "body", 8.0F, 8.0F),
         this.bp = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
         this.bq = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
         this.br = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
         this.bs = new EntityDragonPart(this, "wing", 4.0F, 4.0F),
         this.bt = new EntityDragonPart(this, "wing", 4.0F, 4.0F)
      };
      this.i(this.bu());
      this.a(16.0F, 8.0F);
      this.T = true;
      this.ab = true;
      this.b = 100.0;
      this.ah = true;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(200.0);
   }

   @Override
   protected void h() {
      super.h();
   }

   public double[] b(int var1, float var2) {
      if (this.bn() <= 0.0F) {
         var2 = 0.0F;
      }

      var2 = 1.0F - var2;
      int var3 = this.bl - var1 * 1 & 63;
      int var4 = this.bl - var1 * 1 - 1 & 63;
      double[] var5 = new double[3];
      double var6 = this.bk[var3][0];
      double var8 = MathHelper.g(this.bk[var4][0] - var6);
      var5[0] = var6 + var8 * var2;
      var6 = this.bk[var3][1];
      var8 = this.bk[var4][1] - var6;
      var5[1] = var6 + var8 * var2;
      var5[2] = this.bk[var3][2] + (this.bk[var4][2] - this.bk[var3][2]) * var2;
      return var5;
   }

   @Override
   public void m() {
      if (this.o.D) {
         float var1 = MathHelper.b(this.bv * (float) Math.PI * 2.0F);
         float var2 = MathHelper.b(this.bu * (float) Math.PI * 2.0F);
         if (var2 <= -0.3F && var1 >= -0.3F && !this.R()) {
            this.o.a(this.s, this.t, this.u, "mob.enderdragon.wings", 5.0F, 0.8F + this.V.nextFloat() * 0.3F, false);
         }
      }

      this.bu = this.bv;
      if (this.bn() <= 0.0F) {
         float var29 = (this.V.nextFloat() - 0.5F) * 8.0F;
         float var34 = (this.V.nextFloat() - 0.5F) * 4.0F;
         float var35 = (this.V.nextFloat() - 0.5F) * 8.0F;
         this.o.a(EnumParticleTypes.b, this.s + var29, this.t + 2.0 + var34, this.u + var35, 0.0, 0.0, 0.0);
      } else {
         this.n();
         float var27 = 0.2F / (MathHelper.a(this.v * this.v + this.x * this.x) * 10.0F + 1.0F);
         var27 *= (float)Math.pow(2.0, this.w);
         if (this.bx) {
            this.bv += var27 * 0.5F;
         } else {
            this.bv += var27;
         }

         this.y = MathHelper.g(this.y);
         if (this.ce()) {
            this.bv = 0.5F;
         } else {
            if (this.bl < 0) {
               for (int var30 = 0; var30 < this.bk.length; var30++) {
                  this.bk[var30][0] = this.y;
                  this.bk[var30][1] = this.t;
               }
            }

            if (++this.bl == this.bk.length) {
               this.bl = 0;
            }

            this.bk[this.bl][0] = this.y;
            this.bk[this.bl][1] = this.t;
            if (this.o.D) {
               if (this.bc > 0) {
                  double var31 = this.s + (this.bd - this.s) / this.bc;
                  double var4 = this.t + (this.be - this.t) / this.bc;
                  double var6 = this.u + (this.bf - this.u) / this.bc;
                  double var8 = MathHelper.g(this.bg - this.y);
                  this.y = (float)(this.y + var8 / this.bc);
                  this.z = (float)(this.z + (this.bh - this.z) / this.bc);
                  this.bc--;
                  this.b(var31, var4, var6);
                  this.b(this.y, this.z);
               }
            } else {
               double var32 = this.a - this.s;
               double var36 = this.b - this.t;
               double var40 = this.c - this.u;
               double var42 = var32 * var32 + var36 * var36 + var40 * var40;
               if (this.bA != null) {
                  this.a = this.bA.s;
                  this.c = this.bA.u;
                  double var10 = this.a - this.s;
                  double var12 = this.c - this.u;
                  double var14 = Math.sqrt(var10 * var10 + var12 * var12);
                  double var16 = 0.4F + var14 / 80.0 - 1.0;
                  if (var16 > 10.0) {
                     var16 = 10.0;
                  }

                  this.b = this.bA.aR().b + var16;
               } else {
                  this.a = this.a + this.V.nextGaussian() * 2.0;
                  this.c = this.c + this.V.nextGaussian() * 2.0;
               }

               if (this.bw || var42 < 100.0 || var42 > 22500.0 || this.D || this.E) {
                  this.cf();
               }

               var36 /= MathHelper.a(var32 * var32 + var40 * var40);
               float var45 = 0.6F;
               var36 = MathHelper.a(var36, (double)(-var45), (double)var45);
               this.w += var36 * 0.1F;
               this.y = MathHelper.g(this.y);
               double var11 = 180.0 - MathHelper.b(var32, var40) * 180.0 / (float) Math.PI;
               double var13 = MathHelper.g(var11 - this.y);
               if (var13 > 50.0) {
                  var13 = 50.0;
               }

               if (var13 < -50.0) {
                  var13 = -50.0;
               }

               Vec3 var15 = new Vec3(this.a - this.s, this.b - this.t, this.c - this.u).a();
               double var54 = -MathHelper.b(this.y * (float) Math.PI / 180.0F);
               Vec3 var18 = new Vec3(MathHelper.a(this.y * (float) Math.PI / 180.0F), this.w, var54).a();
               float var19 = ((float)var18.b(var15) + 0.5F) / 1.5F;
               if (var19 < 0.0F) {
                  var19 = 0.0F;
               }

               this.bb *= 0.8F;
               float var20 = MathHelper.a(this.v * this.v + this.x * this.x) * 1.0F + 1.0F;
               double var21 = Math.sqrt(this.v * this.v + this.x * this.x) * 1.0 + 1.0;
               if (var21 > 40.0) {
                  var21 = 40.0;
               }

               this.bb = (float)(this.bb + var13 * (0.7F / var21 / var20));
               this.y = this.y + this.bb * 0.1F;
               float var23 = (float)(2.0 / (var21 + 1.0));
               float var24 = 0.06F;
               this.a(0.0F, -1.0F, var24 * (var19 * var23 + (1.0F - var23)));
               if (this.bx) {
                  this.d(this.v * 0.8F, this.w * 0.8F, this.x * 0.8F);
               } else {
                  this.d(this.v, this.w, this.x);
               }

               Vec3 var25 = new Vec3(this.v, this.w, this.x).a();
               float var26 = ((float)var25.b(var18) + 1.0F) / 2.0F;
               var26 = 0.8F + 0.15F * var26;
               this.v *= var26;
               this.x *= var26;
               this.w *= 0.91F;
            }

            this.aI = this.y;
            this.bn.J = this.bn.K = 3.0F;
            this.bp.J = this.bp.K = 2.0F;
            this.bq.J = this.bq.K = 2.0F;
            this.br.J = this.br.K = 2.0F;
            this.bo.K = 3.0F;
            this.bo.J = 5.0F;
            this.bs.K = 2.0F;
            this.bs.J = 4.0F;
            this.bt.K = 3.0F;
            this.bt.J = 4.0F;
            float var33 = (float)(this.b(5, 1.0F)[1] - this.b(10, 1.0F)[1]) * 10.0F / 180.0F * (float) Math.PI;
            float var3 = MathHelper.b(var33);
            float var39 = -MathHelper.a(var33);
            float var5 = this.y * (float) Math.PI / 180.0F;
            float var41 = MathHelper.a(var5);
            float var7 = MathHelper.b(var5);
            this.bo.t_();
            this.bo.b(this.s + var41 * 0.5F, this.t, this.u - var7 * 0.5F, 0.0F, 0.0F);
            this.bs.t_();
            this.bs.b(this.s + var7 * 4.5F, this.t + 2.0, this.u + var41 * 4.5F, 0.0F, 0.0F);
            this.bt.t_();
            this.bt.b(this.s - var7 * 4.5F, this.t + 2.0, this.u - var41 * 4.5F, 0.0F, 0.0F);
            if (!this.o.D && this.au == 0) {
               this.a(this.o.b(this, this.bs.aR().b(4.0, 2.0, 4.0).c(0.0, -2.0, 0.0)));
               this.a(this.o.b(this, this.bt.aR().b(4.0, 2.0, 4.0).c(0.0, -2.0, 0.0)));
               this.b(this.o.b(this, this.bn.aR().b(1.0, 1.0, 1.0)));
            }

            double[] var43 = this.b(5, 1.0F);
            double[] var9 = this.b(0, 1.0F);
            float var46 = MathHelper.a(this.y * (float) Math.PI / 180.0F - this.bb * 0.01F);
            float var48 = MathHelper.b(this.y * (float) Math.PI / 180.0F - this.bb * 0.01F);
            this.bn.t_();
            this.bn.b(this.s + var46 * 5.5F * var3, this.t + (var9[1] - var43[1]) * 1.0 + var39 * 5.5F, this.u - var48 * 5.5F * var3, 0.0F, 0.0F);

            for (int var44 = 0; var44 < 3; var44++) {
               EntityDragonPart var47 = null;
               if (var44 == 0) {
                  var47 = this.bp;
               }

               if (var44 == 1) {
                  var47 = this.bq;
               }

               if (var44 == 2) {
                  var47 = this.br;
               }

               double[] var49 = this.b(12 + var44 * 2, 1.0F);
               float var50 = this.y * (float) Math.PI / 180.0F + this.b(var49[0] - var43[0]) * (float) Math.PI / 180.0F * 1.0F;
               float var51 = MathHelper.a(var50);
               float var52 = MathHelper.b(var50);
               float var53 = 1.5F;
               float var55 = (var44 + 1) * 2.0F;
               var47.t_();
               var47.b(
                  this.s - (var41 * var53 + var51 * var55) * var3,
                  this.t + (var49[1] - var43[1]) * 1.0 - (var55 + var53) * var39 + 1.5,
                  this.u + (var7 * var53 + var52 * var55) * var3,
                  0.0F,
                  0.0F
               );
            }

            if (!this.o.D) {
               this.bx = this.b(this.bn.aR()) | this.b(this.bo.aR());
            }
         }
      }
   }

   private void n() {
      if (this.bz != null) {
         if (this.bz.I) {
            if (!this.o.D) {
               this.a(this.bn, DamageSource.a(null), 10.0F);
            }

            this.bz = null;
         } else if (this.W % 10 == 0 && this.bn() < this.bu()) {
            this.i(this.bn() + 1.0F);
         }
      }

      if (this.V.nextInt(10) == 0) {
         float var1 = 32.0F;
         List var2 = this.o.a(EntityEnderCrystal.class, this.aR().b(var1, var1, var1));
         EntityEnderCrystal var3 = null;
         double var4 = Double.MAX_VALUE;

         for (EntityEnderCrystal var7 : var2) {
            double var8 = var7.h(this);
            if (var8 < var4) {
               var4 = var8;
               var3 = var7;
            }
         }

         this.bz = var3;
      }
   }

   private void a(List<Entity> var1) {
      double var2 = (this.bo.aR().a + this.bo.aR().d) / 2.0;
      double var4 = (this.bo.aR().c + this.bo.aR().f) / 2.0;

      for (Entity var7 : var1) {
         if (var7 instanceof EntityLivingBase) {
            double var8 = var7.s - var2;
            double var10 = var7.u - var4;
            double var12 = var8 * var8 + var10 * var10;
            var7.g(var8 / var12 * 4.0, 0.2F, var10 / var12 * 4.0);
         }
      }
   }

   private void b(List<Entity> var1) {
      for (int var2 = 0; var2 < var1.size(); var2++) {
         Entity var3 = (Entity)var1.get(var2);
         if (var3 instanceof EntityLivingBase) {
            var3.a(DamageSource.a((EntityLivingBase)this), 10.0F);
            this.a(this, var3);
         }
      }
   }

   private void cf() {
      this.bw = false;
      ArrayList var1 = Lists.newArrayList(this.o.j);
      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         if (((EntityPlayer)var2.next()).v()) {
            var2.remove();
         }
      }

      if (this.V.nextInt(2) == 0 && !var1.isEmpty()) {
         this.bA = (Entity)var1.get(this.V.nextInt(var1.size()));
      } else {
         boolean var3;
         do {
            this.a = 0.0;
            this.b = 70.0F + this.V.nextFloat() * 50.0F;
            this.c = 0.0;
            this.a = this.a + (this.V.nextFloat() * 120.0F - 60.0F);
            this.c = this.c + (this.V.nextFloat() * 120.0F - 60.0F);
            double var4 = this.s - this.a;
            double var6 = this.t - this.b;
            double var8 = this.u - this.c;
            var3 = var4 * var4 + var6 * var6 + var8 * var8 > 100.0;
         } while (!var3);

         this.bA = null;
      }
   }

   private float b(double var1) {
      return (float)MathHelper.g(var1);
   }

   private boolean b(AxisAlignedBB var1) {
      int var2 = MathHelper.c(var1.a);
      int var3 = MathHelper.c(var1.b);
      int var4 = MathHelper.c(var1.c);
      int var5 = MathHelper.c(var1.d);
      int var6 = MathHelper.c(var1.e);
      int var7 = MathHelper.c(var1.f);
      boolean var8 = false;
      boolean var9 = false;

      for (int var10 = var2; var10 <= var5; var10++) {
         for (int var11 = var3; var11 <= var6; var11++) {
            for (int var12 = var4; var12 <= var7; var12++) {
               BlockPos var13 = new BlockPos(var10, var11, var12);
               Block var14 = this.o.p(var13).c();
               if (var14.t() != Material.a) {
                  if (var14 != Blocks.cv && var14 != Blocks.Z && var14 != Blocks.bH && var14 != Blocks.h && var14 != Blocks.bX && this.o.Q().b("mobGriefing")) {
                     var9 = this.o.g(var13) || var9;
                  } else {
                     var8 = true;
                  }
               }
            }
         }
      }

      if (var9) {
         double var16 = var1.a + (var1.d - var1.a) * this.V.nextFloat();
         double var17 = var1.b + (var1.e - var1.b) * this.V.nextFloat();
         double var18 = var1.c + (var1.f - var1.c) * this.V.nextFloat();
         this.o.a(EnumParticleTypes.b, var16, var17, var18, 0.0, 0.0, 0.0);
      }

      return var8;
   }

   @Override
   public boolean a(EntityDragonPart var1, DamageSource var2, float var3) {
      if (var1 != this.bn) {
         var3 = var3 / 4.0F + 1.0F;
      }

      float var4 = this.y * (float) Math.PI / 180.0F;
      float var5 = MathHelper.a(var4);
      float var6 = MathHelper.b(var4);
      this.a = this.s + var5 * 5.0F + (this.V.nextFloat() - 0.5F) * 2.0F;
      this.b = this.t + this.V.nextFloat() * 3.0F + 1.0;
      this.c = this.u - var6 * 5.0F + (this.V.nextFloat() - 0.5F) * 2.0F;
      this.bA = null;
      if (var2.j() instanceof EntityPlayer || var2.c()) {
         this.e(var2, var3);
      }

      return true;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (var1 instanceof EntityDamageSource && ((EntityDamageSource)var1).w()) {
         this.e(var1, var2);
      }

      return false;
   }

   protected boolean e(DamageSource var1, float var2) {
      return super.a(var1, var2);
   }

   @Override
   public void G() {
      this.J();
   }

   @Override
   protected void aZ() {
      this.by++;
      if (this.by >= 180 && this.by <= 200) {
         float var1 = (this.V.nextFloat() - 0.5F) * 8.0F;
         float var2 = (this.V.nextFloat() - 0.5F) * 4.0F;
         float var3 = (this.V.nextFloat() - 0.5F) * 8.0F;
         this.o.a(EnumParticleTypes.c, this.s + var1, this.t + 2.0 + var2, this.u + var3, 0.0, 0.0, 0.0);
      }

      boolean var4 = this.o.Q().b("doMobLoot");
      if (!this.o.D) {
         if (this.by > 150 && this.by % 5 == 0 && var4) {
            int var5 = 1000;

            while (var5 > 0) {
               int var7 = EntityXPOrb.a(var5);
               var5 -= var7;
               this.o.d(new EntityXPOrb(this.o, this.s, this.t, this.u, var7));
            }
         }

         if (this.by == 1) {
            this.o.a(1018, new BlockPos(this), 0);
         }
      }

      this.d(0.0, 0.1F, 0.0);
      this.aI = this.y += 20.0F;
      if (this.by == 200 && !this.o.D) {
         if (var4) {
            int var6 = 2000;

            while (var6 > 0) {
               int var8 = EntityXPOrb.a(var6);
               var6 -= var8;
               this.o.d(new EntityXPOrb(this.o, this.s, this.t, this.u, var8));
            }
         }

         this.a(new BlockPos(this.s, 64.0, this.u));
         this.J();
      }
   }

   private void a(BlockPos var1) {
      byte var2 = 4;
      double var3 = 12.25;
      double var5 = 6.25;

      for (int var7 = -1; var7 <= 32; var7++) {
         for (int var8 = -4; var8 <= 4; var8++) {
            for (int var9 = -4; var9 <= 4; var9++) {
               double var10 = var8 * var8 + var9 * var9;
               if (!(var10 > 12.25)) {
                  BlockPos var12 = var1.a(var8, var7, var9);
                  if (var7 < 0) {
                     if (var10 <= 6.25) {
                        this.o.a(var12, Blocks.h.Q());
                     }
                  } else if (var7 > 0) {
                     this.o.a(var12, Blocks.a.Q());
                  } else if (var10 > 6.25) {
                     this.o.a(var12, Blocks.h.Q());
                  } else {
                     this.o.a(var12, Blocks.bF.Q());
                  }
               }
            }
         }
      }

      this.o.a(var1, Blocks.h.Q());
      this.o.a(var1.a(), Blocks.h.Q());
      BlockPos var13 = var1.b(2);
      this.o.a(var13, Blocks.h.Q());
      this.o.a(var13.e(), Blocks.aa.Q().a(BlockTorch.a, EnumFacing.f));
      this.o.a(var13.f(), Blocks.aa.Q().a(BlockTorch.a, EnumFacing.e));
      this.o.a(var13.c(), Blocks.aa.Q().a(BlockTorch.a, EnumFacing.d));
      this.o.a(var13.d(), Blocks.aa.Q().a(BlockTorch.a, EnumFacing.c));
      this.o.a(var1.b(3), Blocks.h.Q());
      this.o.a(var1.b(4), Blocks.bI.Q());
   }

   @Override
   protected void D() {
   }

   @Override
   public Entity[] aB() {
      return this.bm;
   }

   @Override
   public boolean ad() {
      return false;
   }

   @Override
   public World a() {
      return this.o;
   }

   @Override
   protected String z() {
      return "mob.enderdragon.growl";
   }

   @Override
   protected String bo() {
      return "mob.enderdragon.hit";
   }

   @Override
   protected float bB() {
      return 5.0F;
   }
}

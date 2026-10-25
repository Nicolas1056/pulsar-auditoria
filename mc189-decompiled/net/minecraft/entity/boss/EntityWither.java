package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob {
   private float[] a = new float[2];
   private float[] b = new float[2];
   private float[] c = new float[2];
   private float[] bm = new float[2];
   private int[] bn = new int[2];
   private int[] bo = new int[2];
   private int bp;
   private static final Predicate<Entity> bq = new Predicate<Entity>() {
      public boolean a(Entity var1) {
         return var1 instanceof EntityLivingBase && ((EntityLivingBase)var1).bz() != EnumCreatureAttribute.b;
      }
   };

   public EntityWither(World var1) {
      super(var1);
      this.i(this.bu());
      this.a(0.9F, 3.5F);
      this.ab = true;
      ((PathNavigateGround)this.s()).d(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0F));
      this.i.a(5, new EntityAIWander(this, 1.0));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(7, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, false));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 0, false, false, bq));
      this.b_ = 50;
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(17, new Integer(0));
      this.ac.a(18, new Integer(0));
      this.ac.a(19, new Integer(0));
      this.ac.a(20, new Integer(0));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Invul", this.cl());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.r(var1.f("Invul"));
   }

   @Override
   protected String z() {
      return "mob.wither.idle";
   }

   @Override
   protected String bo() {
      return "mob.wither.hurt";
   }

   @Override
   protected String bp() {
      return "mob.wither.death";
   }

   @Override
   public void m() {
      this.w *= 0.6F;
      if (!this.o.D && this.s(0) > 0) {
         Entity var1 = this.o.a(this.s(0));
         if (var1 != null) {
            if (this.t < var1.t || !this.cm() && this.t < var1.t + 5.0) {
               if (this.w < 0.0) {
                  this.w = 0.0;
               }

               this.w = this.w + (0.5 - this.w) * 0.6F;
            }

            double var2 = var1.s - this.s;
            double var4 = var1.u - this.u;
            double var6 = var2 * var2 + var4 * var4;
            if (var6 > 9.0) {
               double var8 = MathHelper.a(var6);
               this.v = this.v + (var2 / var8 * 0.5 - this.v) * 0.6F;
               this.x = this.x + (var4 / var8 * 0.5 - this.x) * 0.6F;
            }
         }
      }

      if (this.v * this.v + this.x * this.x > 0.05F) {
         this.y = (float)MathHelper.b(this.x, this.v) * (180.0F / (float)Math.PI) - 90.0F;
      }

      super.m();

      for (int var20 = 0; var20 < 2; var20++) {
         this.bm[var20] = this.b[var20];
         this.c[var20] = this.a[var20];
      }

      for (int var21 = 0; var21 < 2; var21++) {
         int var23 = this.s(var21 + 1);
         Entity var3 = null;
         if (var23 > 0) {
            var3 = this.o.a(var23);
         }

         if (var3 != null) {
            double var27 = this.t(var21 + 1);
            double var28 = this.u(var21 + 1);
            double var29 = this.v(var21 + 1);
            double var10 = var3.s - var27;
            double var12 = var3.t + var3.aS() - var28;
            double var14 = var3.u - var29;
            double var16 = MathHelper.a(var10 * var10 + var14 * var14);
            float var18 = (float)(MathHelper.b(var14, var10) * 180.0 / (float) Math.PI) - 90.0F;
            float var19 = (float)(-(MathHelper.b(var12, var16) * 180.0 / (float) Math.PI));
            this.a[var21] = this.b(this.a[var21], var19, 40.0F);
            this.b[var21] = this.b(this.b[var21], var18, 10.0F);
         } else {
            this.b[var21] = this.b(this.b[var21], this.aI, 10.0F);
         }
      }

      boolean var22 = this.cm();

      for (int var24 = 0; var24 < 3; var24++) {
         double var26 = this.t(var24);
         double var5 = this.u(var24);
         double var7 = this.v(var24);
         this.o
            .a(
               EnumParticleTypes.l,
               var26 + this.V.nextGaussian() * 0.3F,
               var5 + this.V.nextGaussian() * 0.3F,
               var7 + this.V.nextGaussian() * 0.3F,
               0.0,
               0.0,
               0.0
            );
         if (var22 && this.o.s.nextInt(4) == 0) {
            this.o
               .a(
                  EnumParticleTypes.p,
                  var26 + this.V.nextGaussian() * 0.3F,
                  var5 + this.V.nextGaussian() * 0.3F,
                  var7 + this.V.nextGaussian() * 0.3F,
                  0.7F,
                  0.7F,
                  0.5
               );
         }
      }

      if (this.cl() > 0) {
         for (int var25 = 0; var25 < 3; var25++) {
            this.o
               .a(
                  EnumParticleTypes.p,
                  this.s + this.V.nextGaussian() * 1.0,
                  this.t + this.V.nextFloat() * 3.3F,
                  this.u + this.V.nextGaussian() * 1.0,
                  0.7F,
                  0.7F,
                  0.9F
               );
         }
      }
   }

   @Override
   protected void E() {
      if (this.cl() > 0) {
         int var14 = this.cl() - 1;
         if (var14 <= 0) {
            this.o.a(this, this.s, this.t + this.aS(), this.u, 7.0F, false, this.o.Q().b("mobGriefing"));
            this.o.a(1013, new BlockPos(this), 0);
         }

         this.r(var14);
         if (this.W % 10 == 0) {
            this.h(10.0F);
         }
      } else {
         super.E();

         for (int var1 = 1; var1 < 3; var1++) {
            if (this.W >= this.bn[var1 - 1]) {
               this.bn[var1 - 1] = this.W + 10 + this.V.nextInt(10);
               if ((this.o.aa() == EnumDifficulty.c || this.o.aa() == EnumDifficulty.d) && this.bo[var1 - 1]++ > 15) {
                  float var2 = 10.0F;
                  float var3 = 5.0F;
                  double var4 = MathHelper.a(this.V, this.s - var2, this.s + var2);
                  double var6 = MathHelper.a(this.V, this.t - var3, this.t + var3);
                  double var8 = MathHelper.a(this.V, this.u - var2, this.u + var2);
                  this.a(var1 + 1, var4, var6, var8, true);
                  this.bo[var1 - 1] = 0;
               }

               int var15 = this.s(var1);
               if (var15 > 0) {
                  Entity var18 = this.o.a(var15);
                  if (var18 == null || !var18.ai() || this.h(var18) > 900.0 || !this.t(var18)) {
                     this.b(var1, 0);
                  } else if (var18 instanceof EntityPlayer && ((EntityPlayer)var18).bA.a) {
                     this.b(var1, 0);
                  } else {
                     this.a(var1 + 1, (EntityLivingBase)var18);
                     this.bn[var1 - 1] = this.W + 40 + this.V.nextInt(20);
                     this.bo[var1 - 1] = 0;
                  }
               } else {
                  List var17 = this.o.a(EntityLivingBase.class, this.aR().b(20.0, 8.0, 20.0), Predicates.and(bq, EntitySelectors.d));

                  for (int var20 = 0; var20 < 10 && !var17.isEmpty(); var20++) {
                     EntityLivingBase var5 = (EntityLivingBase)var17.get(this.V.nextInt(var17.size()));
                     if (var5 != this && var5.ai() && this.t(var5)) {
                        if (var5 instanceof EntityPlayer) {
                           if (!((EntityPlayer)var5).bA.a) {
                              this.b(var1, var5.F());
                           }
                        } else {
                           this.b(var1, var5.F());
                        }
                        break;
                     }

                     var17.remove(var5);
                  }
               }
            }
         }

         if (this.u() != null) {
            this.b(0, this.u().F());
         } else {
            this.b(0, 0);
         }

         if (this.bp > 0) {
            this.bp--;
            if (this.bp == 0 && this.o.Q().b("mobGriefing")) {
               int var13 = MathHelper.c(this.t);
               int var16 = MathHelper.c(this.s);
               int var19 = MathHelper.c(this.u);
               boolean var21 = false;

               for (int var22 = -1; var22 <= 1; var22++) {
                  for (int var23 = -1; var23 <= 1; var23++) {
                     for (int var7 = 0; var7 <= 3; var7++) {
                        int var24 = var16 + var22;
                        int var9 = var13 + var7;
                        int var10 = var19 + var23;
                        BlockPos var11 = new BlockPos(var24, var9, var10);
                        Block var12 = this.o.p(var11).c();
                        if (var12.t() != Material.a && a(var12)) {
                           var21 = this.o.b(var11, true) || var21;
                        }
                     }
                  }
               }

               if (var21) {
                  this.o.a(null, 1012, new BlockPos(this), 0);
               }
            }
         }

         if (this.W % 20 == 0) {
            this.h(1.0F);
         }
      }
   }

   public static boolean a(Block var0) {
      return var0 != Blocks.h && var0 != Blocks.bF && var0 != Blocks.bG && var0 != Blocks.bX && var0 != Blocks.cv;
   }

   @Override
   public void n() {
      this.r(220);
      this.i(this.bu() / 3.0F);
   }

   @Override
   public void aA() {
   }

   @Override
   public int br() {
      return 4;
   }

   private double t(int var1) {
      if (var1 <= 0) {
         return this.s;
      } else {
         float var2 = (this.aI + 180 * (var1 - 1)) / 180.0F * (float) Math.PI;
         float var3 = MathHelper.b(var2);
         return this.s + var3 * 1.3;
      }
   }

   private double u(int var1) {
      return var1 <= 0 ? this.t + 3.0 : this.t + 2.2;
   }

   private double v(int var1) {
      if (var1 <= 0) {
         return this.u;
      } else {
         float var2 = (this.aI + 180 * (var1 - 1)) / 180.0F * (float) Math.PI;
         float var3 = MathHelper.a(var2);
         return this.u + var3 * 1.3;
      }
   }

   private float b(float var1, float var2, float var3) {
      float var4 = MathHelper.g(var2 - var1);
      if (var4 > var3) {
         var4 = var3;
      }

      if (var4 < -var3) {
         var4 = -var3;
      }

      return var1 + var4;
   }

   private void a(int var1, EntityLivingBase var2) {
      this.a(var1, var2.s, var2.t + var2.aS() * 0.5, var2.u, var1 == 0 && this.V.nextFloat() < 0.001F);
   }

   private void a(int var1, double var2, double var4, double var6, boolean var8) {
      this.o.a(null, 1014, new BlockPos(this), 0);
      double var9 = this.t(var1);
      double var11 = this.u(var1);
      double var13 = this.v(var1);
      double var15 = var2 - var9;
      double var17 = var4 - var11;
      double var19 = var6 - var13;
      EntityWitherSkull var21 = new EntityWitherSkull(this.o, this, var15, var17, var19);
      if (var8) {
         var21.a(true);
      }

      var21.t = var11;
      var21.s = var9;
      var21.u = var13;
      this.o.d(var21);
   }

   @Override
   public void a(EntityLivingBase var1, float var2) {
      this.a(0, var1);
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (var1 == DamageSource.f || var1.j() instanceof EntityWither) {
         return false;
      } else if (this.cl() > 0 && var1 != DamageSource.j) {
         return false;
      } else {
         if (this.cm()) {
            Entity var3 = var1.i();
            if (var3 instanceof EntityArrow) {
               return false;
            }
         }

         Entity var5 = var1.j();
         if (var5 != null && !(var5 instanceof EntityPlayer) && var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).bz() == this.bz()) {
            return false;
         } else {
            if (this.bp <= 0) {
               this.bp = 20;
            }

            for (int var4 = 0; var4 < this.bo.length; var4++) {
               this.bo[var4] = this.bo[var4] + 3;
            }

            return super.a(var1, var2);
         }
      }
   }

   @Override
   protected void b(boolean var1, int var2) {
      EntityItem var3 = this.a(Items.bZ, 1);
      if (var3 != null) {
         var3.u();
      }

      if (!this.o.D) {
         for (EntityPlayer var5 : this.o.a(EntityPlayer.class, this.aR().b(50.0, 100.0, 50.0))) {
            var5.b(AchievementList.J);
         }
      }
   }

   @Override
   protected void D() {
      this.aQ = 0;
   }

   @Override
   public int b(float var1) {
      return 15728880;
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   public void c(PotionEffect var1) {
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(300.0);
      this.a(SharedMonsterAttributes.d).a(0.6F);
      this.a(SharedMonsterAttributes.b).a(40.0);
   }

   public float a(int var1) {
      return this.b[var1];
   }

   public float b(int var1) {
      return this.a[var1];
   }

   public int cl() {
      return this.ac.c(20);
   }

   public void r(int var1) {
      this.ac.b(20, var1);
   }

   public int s(int var1) {
      return this.ac.c(17 + var1);
   }

   public void b(int var1, int var2) {
      this.ac.b(17 + var1, var2);
   }

   public boolean cm() {
      return this.bn() <= this.bu() / 2.0F;
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.b;
   }

   @Override
   public void a(Entity var1) {
      this.m = null;
   }
}

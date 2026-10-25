package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlaze extends EntityMob {
   private float a = 0.5F;
   private int b;

   public EntityBlaze(World var1) {
      super(var1);
      this.ab = true;
      this.b_ = 10;
      this.i.a(4, new EntityBlaze.AIFireballAttack(this));
      this.i.a(5, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.i.a(7, new EntityAIWander(this, 1.0));
      this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, true));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.e).a(6.0);
      this.a(SharedMonsterAttributes.d).a(0.23F);
      this.a(SharedMonsterAttributes.b).a(48.0);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Byte((byte)0));
   }

   @Override
   protected String z() {
      return "mob.blaze.breathe";
   }

   @Override
   protected String bo() {
      return "mob.blaze.hit";
   }

   @Override
   protected String bp() {
      return "mob.blaze.death";
   }

   @Override
   public int b(float var1) {
      return 15728880;
   }

   @Override
   public float c(float var1) {
      return 1.0F;
   }

   @Override
   public void m() {
      if (!this.C && this.w < 0.0) {
         this.w *= 0.6;
      }

      if (this.o.D) {
         if (this.V.nextInt(24) == 0 && !this.R()) {
            this.o.a(this.s + 0.5, this.t + 0.5, this.u + 0.5, "fire.fire", 1.0F + this.V.nextFloat(), this.V.nextFloat() * 0.7F + 0.3F, false);
         }

         for (int var1 = 0; var1 < 2; var1++) {
            this.o
               .a(
                  EnumParticleTypes.m,
                  this.s + (this.V.nextDouble() - 0.5) * this.J,
                  this.t + this.V.nextDouble() * this.K,
                  this.u + (this.V.nextDouble() - 0.5) * this.J,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      super.m();
   }

   @Override
   protected void E() {
      if (this.U()) {
         this.a(DamageSource.f, 1.0F);
      }

      this.b--;
      if (this.b <= 0) {
         this.b = 100;
         this.a = 0.5F + (float)this.V.nextGaussian() * 3.0F;
      }

      EntityLivingBase var1 = this.u();
      if (var1 != null && var1.t + var1.aS() > this.t + this.aS() + this.a) {
         this.w = this.w + (0.3F - this.w) * 0.3F;
         this.ai = true;
      }

      super.E();
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected Item A() {
      return Items.bv;
   }

   @Override
   public boolean at() {
      return this.n();
   }

   @Override
   protected void b(boolean var1, int var2) {
      if (var1) {
         int var3 = this.V.nextInt(2 + var2);

         for (int var4 = 0; var4 < var3; var4++) {
            this.a(Items.bv, 1);
         }
      }
   }

   public boolean n() {
      return (this.ac.a(16) & 1) != 0;
   }

   public void a(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         var2 = (byte)(var2 | 1);
      } else {
         var2 = (byte)(var2 & -2);
      }

      this.ac.b(16, var2);
   }

   @Override
   protected boolean n_() {
      return true;
   }

   static class AIFireballAttack extends EntityAIBase {
      private EntityBlaze a;
      private int b;
      private int c;

      public AIFireballAttack(EntityBlaze var1) {
         this.a = var1;
         this.a(3);
      }

      @Override
      public boolean a() {
         EntityLivingBase var1 = this.a.u();
         return var1 != null && var1.ai();
      }

      @Override
      public void c() {
         this.b = 0;
      }

      @Override
      public void d() {
         this.a.a(false);
      }

      @Override
      public void e() {
         this.c--;
         EntityLivingBase var1 = this.a.u();
         double var2 = this.a.h(var1);
         if (var2 < 4.0) {
            if (this.c <= 0) {
               this.c = 20;
               this.a.r(var1);
            }

            this.a.q().a(var1.s, var1.t, var1.u, 1.0);
         } else if (var2 < 256.0) {
            double var4 = var1.s - this.a.s;
            double var6 = var1.aR().b + var1.K / 2.0F - (this.a.t + this.a.K / 2.0F);
            double var8 = var1.u - this.a.u;
            if (this.c <= 0) {
               this.b++;
               if (this.b == 1) {
                  this.c = 60;
                  this.a.a(true);
               } else if (this.b <= 4) {
                  this.c = 6;
               } else {
                  this.c = 100;
                  this.b = 0;
                  this.a.a(false);
               }

               if (this.b > 1) {
                  float var10 = MathHelper.c(MathHelper.a(var2)) * 0.5F;
                  this.a.o.a(null, 1009, new BlockPos((int)this.a.s, (int)this.a.t, (int)this.a.u), 0);

                  for (int var11 = 0; var11 < 1; var11++) {
                     EntitySmallFireball var12 = new EntitySmallFireball(
                        this.a.o, this.a, var4 + this.a.bc().nextGaussian() * var10, var6, var8 + this.a.bc().nextGaussian() * var10
                     );
                     var12.t = this.a.t + this.a.K / 2.0F + 0.5;
                     this.a.o.d(var12);
                  }
               }
            }

            this.a.p().a(var1, 10.0F, 10.0F);
         } else {
            this.a.s().n();
            this.a.q().a(var1.s, var1.t, var1.u, 1.0);
         }

         super.e();
      }
   }
}

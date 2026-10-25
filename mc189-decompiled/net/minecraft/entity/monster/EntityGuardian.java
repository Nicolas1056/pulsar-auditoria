package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGuardian extends EntityMob {
   private float a;
   private float b;
   private float c;
   private float bm;
   private float bn;
   private EntityLivingBase bo;
   private int bp;
   private boolean bq;
   private EntityAIWander br;

   public EntityGuardian(World var1) {
      super(var1);
      this.b_ = 10;
      this.a(0.85F, 0.85F);
      this.i.a(4, new EntityGuardian.AIGuardianAttack(this));
      EntityAIMoveTowardsRestriction var2;
      this.i.a(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0));
      this.i.a(7, this.br = new EntityAIWander(this, 1.0, 80));
      this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
      this.i.a(9, new EntityAILookIdle(this));
      this.br.a(3);
      var2.a(3);
      this.bi.a(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, new EntityGuardian.GuardianTargetSelector(this)));
      this.f = new EntityGuardian.GuardianMoveHelper(this);
      this.b = this.a = this.V.nextFloat();
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.e).a(6.0);
      this.a(SharedMonsterAttributes.d).a(0.5);
      this.a(SharedMonsterAttributes.b).a(16.0);
      this.a(SharedMonsterAttributes.a).a(30.0);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a(var1.n("Elder"));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Elder", this.cn());
   }

   @Override
   protected PathNavigate b(World var1) {
      return new PathNavigateSwimmer(this, var1);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Integer.valueOf(0));
      this.ac.a(17, Integer.valueOf(0));
   }

   private boolean a(int var1) {
      return (this.ac.c(16) & var1) != 0;
   }

   private void a(int var1, boolean var2) {
      int var3 = this.ac.c(16);
      if (var2) {
         this.ac.b(16, var3 | var1);
      } else {
         this.ac.b(16, var3 & ~var1);
      }
   }

   public boolean n() {
      return this.a(2);
   }

   private void l(boolean var1) {
      this.a(2, var1);
   }

   public int cm() {
      return this.cn() ? 60 : 80;
   }

   public boolean cn() {
      return this.a(4);
   }

   public void a(boolean var1) {
      this.a(4, var1);
      if (var1) {
         this.a(1.9975F, 1.9975F);
         this.a(SharedMonsterAttributes.d).a(0.3F);
         this.a(SharedMonsterAttributes.e).a(8.0);
         this.a(SharedMonsterAttributes.a).a(80.0);
         this.bX();
         this.br.b(400);
      }
   }

   public void co() {
      this.a(true);
      this.bn = this.bm = 1.0F;
   }

   private void b(int var1) {
      this.ac.b(17, var1);
   }

   public boolean cp() {
      return this.ac.c(17) != 0;
   }

   public EntityLivingBase cq() {
      if (!this.cp()) {
         return null;
      } else if (this.o.D) {
         if (this.bo != null) {
            return this.bo;
         } else {
            Entity var1 = this.o.a(this.ac.c(17));
            if (var1 instanceof EntityLivingBase) {
               this.bo = (EntityLivingBase)var1;
               return this.bo;
            } else {
               return null;
            }
         }
      } else {
         return this.u();
      }
   }

   @Override
   public void i(int var1) {
      super.i(var1);
      if (var1 == 16) {
         if (this.cn() && this.J < 1.0F) {
            this.a(1.9975F, 1.9975F);
         }
      } else if (var1 == 17) {
         this.bp = 0;
         this.bo = null;
      }
   }

   @Override
   public int w() {
      return 160;
   }

   @Override
   protected String z() {
      if (!this.V()) {
         return "mob.guardian.land.idle";
      } else {
         return this.cn() ? "mob.guardian.elder.idle" : "mob.guardian.idle";
      }
   }

   @Override
   protected String bo() {
      if (!this.V()) {
         return "mob.guardian.land.hit";
      } else {
         return this.cn() ? "mob.guardian.elder.hit" : "mob.guardian.hit";
      }
   }

   @Override
   protected String bp() {
      if (!this.V()) {
         return "mob.guardian.land.death";
      } else {
         return this.cn() ? "mob.guardian.elder.death" : "mob.guardian.death";
      }
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   public float aS() {
      return this.K * 0.5F;
   }

   @Override
   public float a(BlockPos var1) {
      return this.o.p(var1).c().t() == Material.h ? 10.0F + this.o.o(var1) - 0.5F : super.a(var1);
   }

   @Override
   public void m() {
      if (this.o.D) {
         this.b = this.a;
         if (!this.V()) {
            this.c = 2.0F;
            if (this.w > 0.0 && this.bq && !this.R()) {
               this.o.a(this.s, this.t, this.u, "mob.guardian.flop", 1.0F, 1.0F, false);
            }

            this.bq = this.w < 0.0 && this.o.d(new BlockPos(this).b(), false);
         } else if (this.n()) {
            if (this.c < 0.5F) {
               this.c = 4.0F;
            } else {
               this.c = this.c + (0.5F - this.c) * 0.1F;
            }
         } else {
            this.c = this.c + (0.125F - this.c) * 0.2F;
         }

         this.a = this.a + this.c;
         this.bn = this.bm;
         if (!this.V()) {
            this.bm = this.V.nextFloat();
         } else if (this.n()) {
            this.bm = this.bm + (0.0F - this.bm) * 0.25F;
         } else {
            this.bm = this.bm + (1.0F - this.bm) * 0.06F;
         }

         if (this.n() && this.V()) {
            Vec3 var1 = this.d(0.0F);

            for (int var2 = 0; var2 < 2; var2++) {
               this.o
                  .a(
                     EnumParticleTypes.e,
                     this.s + (this.V.nextDouble() - 0.5) * this.J - var1.a * 1.5,
                     this.t + this.V.nextDouble() * this.K - var1.b * 1.5,
                     this.u + (this.V.nextDouble() - 0.5) * this.J - var1.c * 1.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (this.cp()) {
            if (this.bp < this.cm()) {
               this.bp++;
            }

            EntityLivingBase var14 = this.cq();
            if (var14 != null) {
               this.p().a(var14, 90.0F, 90.0F);
               this.p().a();
               double var15 = this.q(0.0F);
               double var4 = var14.s - this.s;
               double var6 = var14.t + var14.K * 0.5F - (this.t + this.aS());
               double var8 = var14.u - this.u;
               double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
               var4 /= var10;
               var6 /= var10;
               var8 /= var10;
               double var12 = this.V.nextDouble();

               while (var12 < var10) {
                  var12 += 1.8 - var15 + this.V.nextDouble() * (1.7 - var15);
                  this.o.a(EnumParticleTypes.e, this.s + var4 * var12, this.t + var6 * var12 + this.aS(), this.u + var8 * var12, 0.0, 0.0, 0.0);
               }
            }
         }
      }

      if (this.Y) {
         this.h(300);
      } else if (this.C) {
         this.w += 0.5;
         this.v = this.v + (this.V.nextFloat() * 2.0F - 1.0F) * 0.4F;
         this.x = this.x + (this.V.nextFloat() * 2.0F - 1.0F) * 0.4F;
         this.y = this.V.nextFloat() * 360.0F;
         this.C = false;
         this.ai = true;
      }

      if (this.cp()) {
         this.y = this.aK;
      }

      super.m();
   }

   public float a(float var1) {
      return this.b + (this.a - this.b) * var1;
   }

   public float p(float var1) {
      return this.bn + (this.bm - this.bn) * var1;
   }

   public float q(float var1) {
      return (this.bp + var1) / this.cm();
   }

   @Override
   protected void E() {
      super.E();
      if (this.cn()) {
         short var1 = 1200;
         short var2 = 1200;
         short var3 = 6000;
         byte var4 = 2;
         if ((this.W + this.F()) % 1200 == 0) {
            Potion var5 = Potion.f;

            for (EntityPlayerMP var8 : this.o.b(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
               public boolean a(EntityPlayerMP var1) {
                  return EntityGuardian.this.h(var1) < 2500.0 && var1.c.c();
               }
            })) {
               if (!var8.a(var5) || var8.b(var5).c() < 2 || var8.b(var5).b() < 1200) {
                  var8.a.a(new S2BPacketChangeGameState(10, 0.0F));
                  var8.c(new PotionEffect(var5.H, 6000, 2));
               }
            }
         }

         if (!this.ck()) {
            this.a(new BlockPos(this), 16);
         }
      }
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3) + this.V.nextInt(var2 + 1);
      if (var3 > 0) {
         this.a(new ItemStack(Items.cC, var3, 0), 1.0F);
      }

      if (this.V.nextInt(3 + var2) > 1) {
         this.a(new ItemStack(Items.aU, 1, ItemFishFood.FishType.a.a()), 1.0F);
      } else if (this.V.nextInt(3 + var2) > 1) {
         this.a(new ItemStack(Items.cD, 1, 0), 1.0F);
      }

      if (var1 && this.cn()) {
         this.a(new ItemStack(Blocks.v, 1, 1), 1.0F);
      }
   }

   @Override
   protected void bq() {
      ItemStack var1 = WeightedRandom.a(this.V, EntityFishHook.j()).a(this.V);
      this.a(var1, 1.0F);
   }

   @Override
   protected boolean n_() {
      return true;
   }

   @Override
   public boolean bS() {
      return this.o.a(this.aR(), this) && this.o.a(this, this.aR()).isEmpty();
   }

   @Override
   public boolean bR() {
      return (this.V.nextInt(20) == 0 || !this.o.j(new BlockPos(this))) && super.bR();
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (!this.n() && !var1.s() && var1.i() instanceof EntityLivingBase) {
         EntityLivingBase var3 = (EntityLivingBase)var1.i();
         if (!var1.c()) {
            var3.a(DamageSource.a((Entity)this), 2.0F);
            var3.a("damage.thorns", 0.5F, 1.0F);
         }
      }

      this.br.f();
      return super.a(var1, var2);
   }

   @Override
   public int bQ() {
      return 180;
   }

   @Override
   public void g(float var1, float var2) {
      if (this.bM()) {
         if (this.V()) {
            this.a(var1, var2, 0.1F);
            this.d(this.v, this.w, this.x);
            this.v *= 0.9F;
            this.w *= 0.9F;
            this.x *= 0.9F;
            if (!this.n() && this.u() == null) {
               this.w -= 0.005;
            }
         } else {
            super.g(var1, var2);
         }
      } else {
         super.g(var1, var2);
      }
   }

   static class AIGuardianAttack extends EntityAIBase {
      private EntityGuardian a;
      private int b;

      public AIGuardianAttack(EntityGuardian var1) {
         this.a = var1;
         this.a(3);
      }

      @Override
      public boolean a() {
         EntityLivingBase var1 = this.a.u();
         return var1 != null && var1.ai();
      }

      @Override
      public boolean b() {
         return super.b() && (this.a.cn() || this.a.h(this.a.u()) > 9.0);
      }

      @Override
      public void c() {
         this.b = -10;
         this.a.s().n();
         this.a.p().a(this.a.u(), 90.0F, 90.0F);
         this.a.ai = true;
      }

      @Override
      public void d() {
         this.a.b(0);
         this.a.d(null);
         this.a.br.f();
      }

      @Override
      public void e() {
         EntityLivingBase var1 = this.a.u();
         this.a.s().n();
         this.a.p().a(var1, 90.0F, 90.0F);
         if (!this.a.t(var1)) {
            this.a.d(null);
         } else {
            this.b++;
            if (this.b == 0) {
               this.a.b(this.a.u().F());
               this.a.o.a(this.a, (byte)21);
            } else if (this.b >= this.a.cm()) {
               float var2 = 1.0F;
               if (this.a.o.aa() == EnumDifficulty.d) {
                  var2 += 2.0F;
               }

               if (this.a.cn()) {
                  var2 += 2.0F;
               }

               var1.a(DamageSource.b(this.a, this.a), var2);
               var1.a(DamageSource.a((EntityLivingBase)this.a), (float)this.a.a(SharedMonsterAttributes.e).e());
               this.a.d(null);
            } else if (this.b >= 60 && this.b % 20 == 0) {
            }

            super.e();
         }
      }
   }

   static class GuardianMoveHelper extends EntityMoveHelper {
      private EntityGuardian g;

      public GuardianMoveHelper(EntityGuardian var1) {
         super(var1);
         this.g = var1;
      }

      @Override
      public void c() {
         if (this.f && !this.g.s().m()) {
            double var1 = this.b - this.g.s;
            double var3 = this.c - this.g.t;
            double var5 = this.d - this.g.u;
            double var7 = var1 * var1 + var3 * var3 + var5 * var5;
            var7 = MathHelper.a(var7);
            var3 /= var7;
            float var9 = (float)(MathHelper.b(var5, var1) * 180.0 / (float) Math.PI) - 90.0F;
            this.g.y = this.a(this.g.y, var9, 30.0F);
            this.g.aI = this.g.y;
            float var10 = (float)(this.e * this.g.a(SharedMonsterAttributes.d).e());
            this.g.k(this.g.bI() + (var10 - this.g.bI()) * 0.125F);
            double var11 = Math.sin((this.g.W + this.g.F()) * 0.5) * 0.05;
            double var13 = Math.cos(this.g.y * (float) Math.PI / 180.0F);
            double var15 = Math.sin(this.g.y * (float) Math.PI / 180.0F);
            this.g.v += var11 * var13;
            this.g.x += var11 * var15;
            var11 = Math.sin((this.g.W + this.g.F()) * 0.75) * 0.05;
            this.g.w += var11 * (var15 + var13) * 0.25;
            this.g.w = this.g.w + this.g.bI() * var3 * 0.1;
            EntityLookHelper var17 = this.g.p();
            double var18 = this.g.s + var1 / var7 * 2.0;
            double var20 = this.g.aS() + this.g.t + var3 / var7 * 1.0;
            double var22 = this.g.u + var5 / var7 * 2.0;
            double var24 = var17.e();
            double var26 = var17.f();
            double var28 = var17.g();
            if (!var17.b()) {
               var24 = var18;
               var26 = var20;
               var28 = var22;
            }

            this.g.p().a(var24 + (var18 - var24) * 0.125, var26 + (var20 - var26) * 0.125, var28 + (var22 - var28) * 0.125, 10.0F, 40.0F);
            this.g.l(true);
         } else {
            this.g.k(0.0F);
            this.g.l(false);
         }
      }
   }

   static class GuardianTargetSelector implements Predicate<EntityLivingBase> {
      private EntityGuardian a;

      public GuardianTargetSelector(EntityGuardian var1) {
         this.a = var1;
      }

      public boolean a(EntityLivingBase var1) {
         return (var1 instanceof EntityPlayer || var1 instanceof EntitySquid) && var1.h(this.a) > 9.0;
      }
   }
}

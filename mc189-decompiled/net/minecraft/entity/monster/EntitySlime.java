package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntitySlime extends EntityLiving implements IMob {
   public float a;
   public float b;
   public float c;
   private boolean bk;

   public EntitySlime(World var1) {
      super(var1);
      this.f = new EntitySlime.SlimeMoveHelper(this);
      this.i.a(1, new EntitySlime.AISlimeFloat(this));
      this.i.a(2, new EntitySlime.AISlimeAttack(this));
      this.i.a(3, new EntitySlime.AISlimeFaceRandom(this));
      this.i.a(5, new EntitySlime.AISlimeHop(this));
      this.bi.a(1, new EntityAIFindEntityNearestPlayer(this));
      this.bi.a(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)1));
   }

   protected void a(int var1) {
      this.ac.b(16, (byte)var1);
      this.a(0.51000005F * var1, 0.51000005F * var1);
      this.b(this.s, this.t, this.u);
      this.a(SharedMonsterAttributes.a).a((double)(var1 * var1));
      this.a(SharedMonsterAttributes.d).a((double)(0.2F + 0.1F * var1));
      this.i(this.bu());
      this.b_ = var1;
   }

   public int cm() {
      return this.ac.a(16);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Size", this.cm() - 1);
      var1.a("wasOnGround", this.bk);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      int var2 = var1.f("Size");
      if (var2 < 0) {
         var2 = 0;
      }

      this.a(var2 + 1);
      this.bk = var1.n("wasOnGround");
   }

   protected EnumParticleTypes n() {
      return EnumParticleTypes.H;
   }

   protected String ck() {
      return "mob.slime." + (this.cm() > 1 ? "big" : "small");
   }

   @Override
   public void t_() {
      if (!this.o.D && this.o.aa() == EnumDifficulty.a && this.cm() > 0) {
         this.I = true;
      }

      this.b = this.b + (this.a - this.b) * 0.5F;
      this.c = this.b;
      super.t_();
      if (this.C && !this.bk) {
         int var1 = this.cm();

         for (int var2 = 0; var2 < var1 * 8; var2++) {
            float var3 = this.V.nextFloat() * (float) Math.PI * 2.0F;
            float var4 = this.V.nextFloat() * 0.5F + 0.5F;
            float var5 = MathHelper.a(var3) * var1 * 0.5F * var4;
            float var6 = MathHelper.b(var3) * var1 * 0.5F * var4;
            World var10000 = this.o;
            EnumParticleTypes var10001 = this.n();
            double var10002 = this.s + var5;
            double var10004 = this.u + var6;
            var10000.a(var10001, var10002, this.aR().b, var10004, 0.0, 0.0, 0.0);
         }

         if (this.cl()) {
            this.a(this.ck(), this.bB(), ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) / 0.8F);
         }

         this.a = -0.5F;
      } else if (!this.C && this.bk) {
         this.a = 1.0F;
      }

      this.bk = this.C;
      this.ch();
   }

   protected void ch() {
      this.a *= 0.6F;
   }

   protected int cg() {
      return this.V.nextInt(20) + 10;
   }

   protected EntitySlime cf() {
      return new EntitySlime(this.o);
   }

   @Override
   public void i(int var1) {
      if (var1 == 16) {
         int var2 = this.cm();
         this.a(0.51000005F * var2, 0.51000005F * var2);
         this.y = this.aK;
         this.aI = this.aK;
         if (this.V() && this.V.nextInt(20) == 0) {
            this.X();
         }
      }

      super.i(var1);
   }

   @Override
   public void J() {
      int var1 = this.cm();
      if (!this.o.D && var1 > 1 && this.bn() <= 0.0F) {
         int var2 = 2 + this.V.nextInt(3);

         for (int var3 = 0; var3 < var2; var3++) {
            float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
            float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
            EntitySlime var6 = this.cf();
            if (this.l_()) {
               var6.a(this.aM());
            }

            if (this.bZ()) {
               var6.bX();
            }

            var6.a(var1 / 2);
            var6.b(this.s + var4, this.t + 0.5, this.u + var5, this.V.nextFloat() * 360.0F, 0.0F);
            this.o.d(var6);
         }
      }

      super.J();
   }

   @Override
   public void i(Entity var1) {
      super.i(var1);
      if (var1 instanceof EntityIronGolem && this.ci()) {
         this.e((EntityLivingBase)var1);
      }
   }

   @Override
   public void d(EntityPlayer var1) {
      if (this.ci()) {
         this.e(var1);
      }
   }

   protected void e(EntityLivingBase var1) {
      int var2 = this.cm();
      if (this.t(var1) && this.h(var1) < 0.6 * var2 * (0.6 * var2) && var1.a(DamageSource.a((EntityLivingBase)this), (float)this.cj())) {
         this.a("mob.attack", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         this.a(this, var1);
      }
   }

   @Override
   public float aS() {
      return 0.625F * this.K;
   }

   protected boolean ci() {
      return this.cm() > 1;
   }

   protected int cj() {
      return this.cm();
   }

   @Override
   protected String bo() {
      return "mob.slime." + (this.cm() > 1 ? "big" : "small");
   }

   @Override
   protected String bp() {
      return "mob.slime." + (this.cm() > 1 ? "big" : "small");
   }

   @Override
   protected Item A() {
      return this.cm() == 1 ? Items.aM : null;
   }

   @Override
   public boolean bR() {
      BlockPos var1 = new BlockPos(MathHelper.c(this.s), 0, MathHelper.c(this.u));
      Chunk var2 = this.o.f(var1);
      if (this.o.P().u() == WorldType.c && this.V.nextInt(4) != 1) {
         return false;
      } else {
         if (this.o.aa() != EnumDifficulty.a) {
            BiomeGenBase var3 = this.o.b(var1);
            if (var3 == BiomeGenBase.v
               && this.t > 50.0
               && this.t < 70.0
               && this.V.nextFloat() < 0.5F
               && this.V.nextFloat() < this.o.y()
               && this.o.l(new BlockPos(this)) <= this.V.nextInt(8)) {
               return super.bR();
            }

            if (this.V.nextInt(10) == 0 && var2.a(987234911L).nextInt(10) == 0 && this.t < 40.0) {
               return super.bR();
            }
         }

         return false;
      }
   }

   @Override
   protected float bB() {
      return 0.4F * this.cm();
   }

   @Override
   public int bQ() {
      return 0;
   }

   protected boolean cn() {
      return this.cm() > 0;
   }

   protected boolean cl() {
      return this.cm() > 2;
   }

   @Override
   protected void bF() {
      this.w = 0.42F;
      this.ai = true;
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      int var3 = this.V.nextInt(3);
      if (var3 < 2 && this.V.nextFloat() < 0.5F * var1.c()) {
         var3++;
      }

      int var4 = 1 << var3;
      this.a(var4);
      return super.a(var1, var2);
   }

   static class AISlimeAttack extends EntityAIBase {
      private EntitySlime a;
      private int b;

      public AISlimeAttack(EntitySlime var1) {
         this.a = var1;
         this.a(2);
      }

      @Override
      public boolean a() {
         EntityLivingBase var1 = this.a.u();
         if (var1 == null) {
            return false;
         } else {
            return !var1.ai() ? false : !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).bA.a;
         }
      }

      @Override
      public void c() {
         this.b = 300;
         super.c();
      }

      @Override
      public boolean b() {
         EntityLivingBase var1 = this.a.u();
         if (var1 == null) {
            return false;
         } else if (!var1.ai()) {
            return false;
         } else {
            return var1 instanceof EntityPlayer && ((EntityPlayer)var1).bA.a ? false : --this.b > 0;
         }
      }

      @Override
      public void e() {
         this.a.a(this.a.u(), 10.0F, 10.0F);
         ((EntitySlime.SlimeMoveHelper)this.a.q()).a(this.a.y, this.a.ci());
      }
   }

   static class AISlimeFaceRandom extends EntityAIBase {
      private EntitySlime a;
      private float b;
      private int c;

      public AISlimeFaceRandom(EntitySlime var1) {
         this.a = var1;
         this.a(2);
      }

      @Override
      public boolean a() {
         return this.a.u() == null && (this.a.C || this.a.V() || this.a.ab());
      }

      @Override
      public void e() {
         if (--this.c <= 0) {
            this.c = 40 + this.a.bc().nextInt(60);
            this.b = this.a.bc().nextInt(360);
         }

         ((EntitySlime.SlimeMoveHelper)this.a.q()).a(this.b, false);
      }
   }

   static class AISlimeFloat extends EntityAIBase {
      private EntitySlime a;

      public AISlimeFloat(EntitySlime var1) {
         this.a = var1;
         this.a(5);
         ((PathNavigateGround)var1.s()).d(true);
      }

      @Override
      public boolean a() {
         return this.a.V() || this.a.ab();
      }

      @Override
      public void e() {
         if (this.a.bc().nextFloat() < 0.8F) {
            this.a.r().a();
         }

         ((EntitySlime.SlimeMoveHelper)this.a.q()).a(1.2);
      }
   }

   static class AISlimeHop extends EntityAIBase {
      private EntitySlime a;

      public AISlimeHop(EntitySlime var1) {
         this.a = var1;
         this.a(5);
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void e() {
         ((EntitySlime.SlimeMoveHelper)this.a.q()).a(1.0);
      }
   }

   static class SlimeMoveHelper extends EntityMoveHelper {
      private float g;
      private int h;
      private EntitySlime i;
      private boolean j;

      public SlimeMoveHelper(EntitySlime var1) {
         super(var1);
         this.i = var1;
      }

      public void a(float var1, boolean var2) {
         this.g = var1;
         this.j = var2;
      }

      public void a(double var1) {
         this.e = var1;
         this.f = true;
      }

      @Override
      public void c() {
         this.a.y = this.a(this.a.y, this.g, 30.0F);
         this.a.aK = this.a.y;
         this.a.aI = this.a.y;
         if (!this.f) {
            this.a.n(0.0F);
         } else {
            this.f = false;
            if (this.a.C) {
               this.a.k((float)(this.e * this.a.a(SharedMonsterAttributes.d).e()));
               if (this.h-- <= 0) {
                  this.h = this.i.cg();
                  if (this.j) {
                     this.h /= 3;
                  }

                  this.i.r().a();
                  if (this.i.cn()) {
                     this.i.a(this.i.ck(), this.i.bB(), ((this.i.bc().nextFloat() - this.i.bc().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                  }
               } else {
                  this.i.aZ = this.i.ba = 0.0F;
                  this.a.k(0.0F);
               }
            } else {
               this.a.k((float)(this.e * this.a.a(SharedMonsterAttributes.d).e()));
            }
         }
      }
   }
}

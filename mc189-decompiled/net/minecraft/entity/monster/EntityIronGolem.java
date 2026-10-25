package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityIronGolem extends EntityGolem {
   private int b;
   Village a;
   private int c;
   private int bm;

   public EntityIronGolem(World var1) {
      super(var1);
      this.a(1.4F, 2.9F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(1, new EntityAIAttackOnCollide(this, 1.0, true));
      this.i.a(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0F));
      this.i.a(3, new EntityAIMoveThroughVillage(this, 0.6, true));
      this.i.a(4, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.i.a(5, new EntityAILookAtVillager(this));
      this.i.a(6, new EntityAIWander(this, 0.6));
      this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIDefendVillage(this));
      this.bi.a(2, new EntityAIHurtByTarget(this, false));
      this.bi.a(3, new EntityIronGolem.AINearestAttackableTargetNonCreeper<>(this, EntityLiving.class, 10, false, true, IMob.e));
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)0));
   }

   @Override
   protected void E() {
      if (--this.b <= 0) {
         this.b = 70 + this.V.nextInt(50);
         this.a = this.o.ae().a(new BlockPos(this), 32);
         if (this.a == null) {
            this.cj();
         } else {
            BlockPos var1 = this.a.a();
            this.a(var1, (int)(this.a.b() * 0.6F));
         }
      }

      super.E();
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(100.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   protected int j(int var1) {
      return var1;
   }

   @Override
   protected void s(Entity var1) {
      if (var1 instanceof IMob && !(var1 instanceof EntityCreeper) && this.bc().nextInt(20) == 0) {
         this.d((EntityLivingBase)var1);
      }

      super.s(var1);
   }

   @Override
   public void m() {
      super.m();
      if (this.c > 0) {
         this.c--;
      }

      if (this.bm > 0) {
         this.bm--;
      }

      if (this.v * this.v + this.x * this.x > 2.5000003E-7F && this.V.nextInt(5) == 0) {
         int var1 = MathHelper.c(this.s);
         int var2 = MathHelper.c(this.t - 0.2F);
         int var3 = MathHelper.c(this.u);
         IBlockState var4 = this.o.p(new BlockPos(var1, var2, var3));
         Block var5 = var4.c();
         if (var5.t() != Material.a) {
            this.o
               .a(
                  EnumParticleTypes.L,
                  this.s + (this.V.nextFloat() - 0.5) * this.J,
                  this.aR().b + 0.1,
                  this.u + (this.V.nextFloat() - 0.5) * this.J,
                  4.0 * (this.V.nextFloat() - 0.5),
                  0.5,
                  (this.V.nextFloat() - 0.5) * 4.0,
                  Block.f(var4)
               );
         }
      }
   }

   @Override
   public boolean a(Class<? extends EntityLivingBase> var1) {
      if (this.cn() && EntityPlayer.class.isAssignableFrom(var1)) {
         return false;
      } else {
         return var1 == EntityCreeper.class ? false : super.a(var1);
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("PlayerCreated", this.cn());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.l(var1.n("PlayerCreated"));
   }

   @Override
   public boolean r(Entity var1) {
      this.c = 10;
      this.o.a(this, (byte)4);
      boolean var2 = var1.a(DamageSource.a((EntityLivingBase)this), (float)(7 + this.V.nextInt(15)));
      if (var2) {
         var1.w += 0.4F;
         this.a(this, var1);
      }

      this.a("mob.irongolem.throw", 1.0F, 1.0F);
      return var2;
   }

   @Override
   public void a(byte var1) {
      if (var1 == 4) {
         this.c = 10;
         this.a("mob.irongolem.throw", 1.0F, 1.0F);
      } else if (var1 == 11) {
         this.bm = 400;
      } else {
         super.a(var1);
      }
   }

   public Village n() {
      return this.a;
   }

   public int cl() {
      return this.c;
   }

   public void a(boolean var1) {
      this.bm = var1 ? 400 : 0;
      this.o.a(this, (byte)11);
   }

   @Override
   protected String bo() {
      return "mob.irongolem.hit";
   }

   @Override
   protected String bp() {
      return "mob.irongolem.death";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.irongolem.walk", 1.0F, 1.0F);
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Item.a(Blocks.O), 1, BlockFlower.EnumFlowerType.b.b());
      }

      int var6 = 3 + this.V.nextInt(3);

      for (int var5 = 0; var5 < var6; var5++) {
         this.a(Items.j, 1);
      }
   }

   public int cm() {
      return this.bm;
   }

   public boolean cn() {
      return (this.ac.a(16) & 1) != 0;
   }

   public void l(boolean var1) {
      byte var2 = this.ac.a(16);
      if (var1) {
         this.ac.b(16, (byte)(var2 | 1));
      } else {
         this.ac.b(16, (byte)(var2 & -2));
      }
   }

   @Override
   public void a(DamageSource var1) {
      if (!this.cn() && this.aN != null && this.a != null) {
         this.a.a(this.aN.e_(), -5);
      }

      super.a(var1);
   }

   static class AINearestAttackableTargetNonCreeper<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
      public AINearestAttackableTargetNonCreeper(
         final EntityCreature var1, Class<T> var2, int var3, boolean var4, boolean var5, final Predicate<? super T> var6
      ) {
         super(var1, var2, var3, var4, var5, var6);
         this.c = new Predicate<T>() {
            public boolean a(T var1x) {
               if (var6 != null && !var6.apply(var1x)) {
                  return false;
               } else if (var1x instanceof EntityCreeper) {
                  return false;
               } else {
                  if (var1x instanceof EntityPlayer) {
                     double var2x = AINearestAttackableTargetNonCreeper.this.f();
                     if (var1x.av()) {
                        var2x *= 0.8F;
                     }

                     if (var1x.ax()) {
                        float var4x = ((EntityPlayer)var1x).bY();
                        if (var4x < 0.1F) {
                           var4x = 0.1F;
                        }

                        var2x *= 0.7F * var4x;
                     }

                     if (var1x.g(var1) > var2x) {
                        return false;
                     }
                  }

                  return AINearestAttackableTargetNonCreeper.this.a(var1x, false);
               }
            }
         };
      }
   }
}

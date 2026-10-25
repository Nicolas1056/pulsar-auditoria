package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob {
   public EntitySpider(World var1) {
      super(var1);
      this.a(1.4F, 0.9F);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(3, new EntityAILeapAtTarget(this, 0.4F));
      this.i.a(4, new EntitySpider.AISpiderAttack(this, EntityPlayer.class));
      this.i.a(4, new EntitySpider.AISpiderAttack(this, EntityIronGolem.class));
      this.i.a(5, new EntityAIWander(this, 0.8));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(6, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, false));
      this.bi.a(2, new EntitySpider.AISpiderTarget<>(this, EntityPlayer.class));
      this.bi.a(3, new EntitySpider.AISpiderTarget<>(this, EntityIronGolem.class));
   }

   @Override
   public double an() {
      return this.K * 0.5F;
   }

   @Override
   protected PathNavigate b(World var1) {
      return new PathNavigateClimber(this, var1);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, new Byte((byte)0));
   }

   @Override
   public void t_() {
      super.t_();
      if (!this.o.D) {
         this.a(this.D);
      }
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(16.0);
      this.a(SharedMonsterAttributes.d).a(0.3F);
   }

   @Override
   protected String z() {
      return "mob.spider.say";
   }

   @Override
   protected String bo() {
      return "mob.spider.say";
   }

   @Override
   protected String bp() {
      return "mob.spider.death";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.spider.step", 0.15F, 1.0F);
   }

   @Override
   protected Item A() {
      return Items.F;
   }

   @Override
   protected void b(boolean var1, int var2) {
      super.b(var1, var2);
      if (var1 && (this.V.nextInt(3) == 0 || this.V.nextInt(1 + var2) > 0)) {
         this.a(Items.bB, 1);
      }
   }

   @Override
   public boolean k_() {
      return this.n();
   }

   @Override
   public void aA() {
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.c;
   }

   @Override
   public boolean d(PotionEffect var1) {
      return var1.a() == Potion.u.H ? false : super.d(var1);
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
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      if (this.o.s.nextInt(100) == 0) {
         EntitySkeleton var3 = new EntitySkeleton(this.o);
         var3.b(this.s, this.t, this.u, this.y, 0.0F);
         var3.a(var1, null);
         this.o.d(var3);
         var3.a(this);
      }

      if (var2 == null) {
         var2 = new EntitySpider.GroupData();
         if (this.o.aa() == EnumDifficulty.d && this.o.s.nextFloat() < 0.1F * var1.c()) {
            ((EntitySpider.GroupData)var2).a(this.o.s);
         }
      }

      if (var2 instanceof EntitySpider.GroupData) {
         int var5 = ((EntitySpider.GroupData)var2).a;
         if (var5 > 0 && Potion.a[var5] != null) {
            this.c(new PotionEffect(var5, Integer.MAX_VALUE));
         }
      }

      return var2;
   }

   @Override
   public float aS() {
      return 0.65F;
   }

   static class AISpiderAttack extends EntityAIAttackOnCollide {
      public AISpiderAttack(EntitySpider var1, Class<? extends Entity> var2) {
         super(var1, var2, 1.0, true);
      }

      @Override
      public boolean b() {
         float var1 = this.b.c(1.0F);
         if (var1 >= 0.5F && this.b.bc().nextInt(100) == 0) {
            this.b.d(null);
            return false;
         } else {
            return super.b();
         }
      }

      @Override
      protected double a(EntityLivingBase var1) {
         return 4.0F + var1.J;
      }
   }

   static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget {
      public AISpiderTarget(EntitySpider var1, Class<T> var2) {
         super(var1, var2, true);
      }

      @Override
      public boolean a() {
         float var1 = this.e.c(1.0F);
         return var1 >= 0.5F ? false : super.a();
      }
   }

   public static class GroupData implements IEntityLivingData {
      public int a;

      public void a(Random var1) {
         int var2 = var1.nextInt(5);
         if (var2 <= 1) {
            this.a = Potion.c.H;
         } else if (var2 <= 2) {
            this.a = Potion.g.H;
         } else if (var2 <= 3) {
            this.a = Potion.l.H;
         } else if (var2 <= 4) {
            this.a = Potion.p.H;
         }
      }
   }
}

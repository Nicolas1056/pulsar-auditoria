package net.minecraft.entity.monster;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob {
   private EntityAIArrowAttack a = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0F);
   private EntityAIAttackOnCollide b = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);

   public EntitySkeleton(World var1) {
      super(var1);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, new EntityAIRestrictSun(this));
      this.i.a(3, new EntityAIFleeSun(this, 1.0));
      this.i.a(3, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0, 1.2));
      this.i.a(4, new EntityAIWander(this, 1.0));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(6, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, false));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      this.bi.a(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
      if (var1 != null && !var1.D) {
         this.n();
      }
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(13, new Byte((byte)0));
   }

   @Override
   protected String z() {
      return "mob.skeleton.say";
   }

   @Override
   protected String bo() {
      return "mob.skeleton.hurt";
   }

   @Override
   protected String bp() {
      return "mob.skeleton.death";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.skeleton.step", 0.15F, 1.0F);
   }

   @Override
   public boolean r(Entity var1) {
      if (super.r(var1)) {
         if (this.cm() == 1 && var1 instanceof EntityLivingBase) {
            ((EntityLivingBase)var1).c(new PotionEffect(Potion.v.H, 200));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.b;
   }

   @Override
   public void m() {
      if (this.o.w() && !this.o.D) {
         float var1 = this.c(1.0F);
         BlockPos var2 = new BlockPos(this.s, (double)Math.round(this.t), this.u);
         if (var1 > 0.5F && this.V.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.o.i(var2)) {
            boolean var3 = true;
            ItemStack var4 = this.p(4);
            if (var4 != null) {
               if (var4.e()) {
                  var4.b(var4.h() + this.V.nextInt(2));
                  if (var4.h() >= var4.j()) {
                     this.b(var4);
                     this.c(4, null);
                  }
               }

               var3 = false;
            }

            if (var3) {
               this.e(8);
            }
         }
      }

      if (this.o.D && this.cm() == 1) {
         this.a(0.72F, 2.535F);
      }

      super.m();
   }

   @Override
   public void ak() {
      super.ak();
      if (this.m instanceof EntityCreature) {
         EntityCreature var1 = (EntityCreature)this.m;
         this.aI = var1.aI;
      }
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (var1.i() instanceof EntityArrow && var1.j() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)var1.j();
         double var3 = var2.s - this.s;
         double var5 = var2.u - this.u;
         if (var3 * var3 + var5 * var5 >= 2500.0) {
            var2.b(AchievementList.v);
         }
      } else if (var1.j() instanceof EntityCreeper && ((EntityCreeper)var1.j()).n() && ((EntityCreeper)var1.j()).cp()) {
         ((EntityCreeper)var1.j()).cq();
         this.a(new ItemStack(Items.bX, 1, this.cm() == 1 ? 1 : 0), 0.0F);
      }
   }

   @Override
   protected Item A() {
      return Items.g;
   }

   @Override
   protected void b(boolean var1, int var2) {
      if (this.cm() == 1) {
         int var3 = this.V.nextInt(3 + var2) - 1;

         for (int var4 = 0; var4 < var3; var4++) {
            this.a(Items.h, 1);
         }
      } else {
         int var5 = this.V.nextInt(3 + var2);

         for (int var7 = 0; var7 < var5; var7++) {
            this.a(Items.g, 1);
         }
      }

      int var6 = this.V.nextInt(3 + var2);

      for (int var8 = 0; var8 < var6; var8++) {
         this.a(Items.aX, 1);
      }
   }

   @Override
   protected void bq() {
      if (this.cm() == 1) {
         this.a(new ItemStack(Items.bX, 1, 1), 0.0F);
      }
   }

   @Override
   protected void a(DifficultyInstance var1) {
      super.a(var1);
      this.c(0, new ItemStack(Items.f));
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      if (this.o.t instanceof WorldProviderHell && this.bc().nextInt(5) > 0) {
         this.i.a(4, this.b);
         this.a(1);
         this.c(0, new ItemStack(Items.q));
         this.a(SharedMonsterAttributes.e).a(4.0);
      } else {
         this.i.a(4, this.a);
         this.a(var1);
         this.b(var1);
      }

      this.j(this.V.nextFloat() < 0.55F * var1.c());
      if (this.p(4) == null) {
         Calendar var3 = this.o.Y();
         if (var3.get(2) + 1 == 10 && var3.get(5) == 31 && this.V.nextFloat() < 0.25F) {
            this.c(4, new ItemStack(this.V.nextFloat() < 0.1F ? Blocks.aZ : Blocks.aU));
            this.bj[4] = 0.0F;
         }
      }

      return var2;
   }

   @Override
   public void n() {
      this.i.a(this.b);
      this.i.a(this.a);
      ItemStack var1 = this.bA();
      if (var1 != null && var1.b() == Items.f) {
         this.i.a(4, this.a);
      } else {
         this.i.a(4, this.b);
      }
   }

   @Override
   public void a(EntityLivingBase var1, float var2) {
      EntityArrow var3 = new EntityArrow(this.o, this, var1, 1.6F, 14 - this.o.aa().a() * 4);
      int var4 = EnchantmentHelper.a(Enchantment.v.B, this.bA());
      int var5 = EnchantmentHelper.a(Enchantment.w.B, this.bA());
      var3.b(var2 * 2.0F + (this.V.nextGaussian() * 0.25 + this.o.aa().a() * 0.11F));
      if (var4 > 0) {
         var3.b(var3.j() + var4 * 0.5 + 0.5);
      }

      if (var5 > 0) {
         var3.a(var5);
      }

      if (EnchantmentHelper.a(Enchantment.x.B, this.bA()) > 0 || this.cm() == 1) {
         var3.e(100);
      }

      this.a("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
      this.o.d(var3);
   }

   public int cm() {
      return this.ac.a(13);
   }

   public void a(int var1) {
      this.ac.b(13, (byte)var1);
      this.ab = var1 == 1;
      if (var1 == 1) {
         this.a(0.72F, 2.535F);
      } else {
         this.a(0.6F, 1.95F);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("SkeletonType", 99)) {
         byte var2 = var1.d("SkeletonType");
         this.a(var2);
      }

      this.n();
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("SkeletonType", (byte)this.cm());
   }

   @Override
   public void c(int var1, ItemStack var2) {
      super.c(var1, var2);
      if (!this.o.D && var1 == 0) {
         this.n();
      }
   }

   @Override
   public float aS() {
      return this.cm() == 1 ? super.aS() : 1.74F;
   }

   @Override
   public double am() {
      return this.j_() ? 0.0 : -0.35;
   }
}

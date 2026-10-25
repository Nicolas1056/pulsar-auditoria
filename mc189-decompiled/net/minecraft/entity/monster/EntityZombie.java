package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombie extends EntityMob {
   protected static final IAttribute a = new RangedAttribute(null, "zombie.spawnReinforcements", 0.0, 0.0, 1.0).a("Spawn Reinforcements Chance");
   private static final UUID b = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier c = new AttributeModifier(b, "Baby speed boost", 0.5, 1);
   private final EntityAIBreakDoor bm = new EntityAIBreakDoor(this);
   private int bn;
   private boolean bo = false;
   private float bp = -1.0F;
   private float bq;

   public EntityZombie(World var1) {
      super(var1);
      ((PathNavigateGround)this.s()).b(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
      this.i.a(5, new EntityAIMoveTowardsRestriction(this, 1.0));
      this.i.a(7, new EntityAIWander(this, 1.0));
      this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.n();
      this.a(0.6F, 1.95F);
   }

   @Override
   protected void n() {
      this.i.a(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
      this.i.a(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, true));
      this.i.a(6, new EntityAIMoveThroughVillage(this, 1.0, false));
      this.bi.a(1, new EntityAIHurtByTarget(this, true, EntityPigZombie.class));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.b).a(35.0);
      this.a(SharedMonsterAttributes.d).a(0.23F);
      this.a(SharedMonsterAttributes.e).a(3.0);
      this.by().b(a).a(this.V.nextDouble() * 0.1F);
   }

   @Override
   protected void h() {
      super.h();
      this.H().a(12, Byte.valueOf((byte)0));
      this.H().a(13, Byte.valueOf((byte)0));
      this.H().a(14, Byte.valueOf((byte)0));
   }

   @Override
   public int br() {
      int var1 = super.br() + 2;
      if (var1 > 20) {
         var1 = 20;
      }

      return var1;
   }

   public boolean cn() {
      return this.bo;
   }

   public void a(boolean var1) {
      if (this.bo != var1) {
         this.bo = var1;
         if (var1) {
            this.i.a(1, this.bm);
         } else {
            this.i.a(this.bm);
         }
      }
   }

   @Override
   public boolean j_() {
      return this.H().a(12) == 1;
   }

   @Override
   protected int b(EntityPlayer var1) {
      if (this.j_()) {
         this.b_ = (int)(this.b_ * 2.5F);
      }

      return super.b(var1);
   }

   public void l(boolean var1) {
      this.H().b(12, (byte)(var1 ? 1 : 0));
      if (this.o != null && !this.o.D) {
         IAttributeInstance var2 = this.a(SharedMonsterAttributes.d);
         var2.c(c);
         if (var1) {
            var2.b(c);
         }
      }

      this.n(var1);
   }

   public boolean co() {
      return this.H().a(13) == 1;
   }

   public void m(boolean var1) {
      this.H().b(13, (byte)(var1 ? 1 : 0));
   }

   @Override
   public void m() {
      if (this.o.w() && !this.o.D && !this.j_()) {
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

      if (this.au() && this.u() != null && this.m instanceof EntityChicken) {
         ((EntityLiving)this.m).s().a(this.s().j(), 1.5);
      }

      super.m();
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (super.a(var1, var2)) {
         EntityLivingBase var3 = this.u();
         if (var3 == null && var1.j() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)var1.j();
         }

         if (var3 != null && this.o.aa() == EnumDifficulty.d && this.V.nextFloat() < this.a(a).e()) {
            int var4 = MathHelper.c(this.s);
            int var5 = MathHelper.c(this.t);
            int var6 = MathHelper.c(this.u);
            EntityZombie var7 = new EntityZombie(this.o);

            for (int var8 = 0; var8 < 50; var8++) {
               int var9 = var4 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);
               int var10 = var5 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);
               int var11 = var6 + MathHelper.a(this.V, 7, 40) * MathHelper.a(this.V, -1, 1);
               if (World.a(this.o, new BlockPos(var9, var10 - 1, var11)) && this.o.l(new BlockPos(var9, var10, var11)) < 10) {
                  var7.b(var9, var10, var11);
                  if (!this.o.b(var9, var10, var11, 7.0) && this.o.a(var7.aR(), var7) && this.o.a(var7, var7.aR()).isEmpty() && !this.o.d(var7.aR())) {
                     this.o.d(var7);
                     var7.d(var3);
                     var7.a(this.o.E(new BlockPos(var7)), null);
                     this.a(a).b(new AttributeModifier("Zombie reinforcement caller charge", -0.05F, 0));
                     var7.a(a).b(new AttributeModifier("Zombie reinforcement callee charge", -0.05F, 0));
                     break;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void t_() {
      if (!this.o.D && this.cp()) {
         int var1 = this.cr();
         this.bn -= var1;
         if (this.bn <= 0) {
            this.cq();
         }
      }

      super.t_();
   }

   @Override
   public boolean r(Entity var1) {
      boolean var2 = super.r(var1);
      if (var2) {
         int var3 = this.o.aa().a();
         if (this.bA() == null && this.at() && this.V.nextFloat() < var3 * 0.3F) {
            var1.e(2 * var3);
         }
      }

      return var2;
   }

   @Override
   protected String z() {
      return "mob.zombie.say";
   }

   @Override
   protected String bo() {
      return "mob.zombie.hurt";
   }

   @Override
   protected String bp() {
      return "mob.zombie.death";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.zombie.step", 0.15F, 1.0F);
   }

   @Override
   protected Item A() {
      return Items.bt;
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.b;
   }

   @Override
   protected void bq() {
      switch (this.V.nextInt(3)) {
         case 0:
            this.a(Items.j, 1);
            break;
         case 1:
            this.a(Items.bR, 1);
            break;
         case 2:
            this.a(Items.bS, 1);
      }
   }

   @Override
   protected void a(DifficultyInstance var1) {
      super.a(var1);
      if (this.V.nextFloat() < (this.o.aa() == EnumDifficulty.d ? 0.05F : 0.01F)) {
         int var2 = this.V.nextInt(3);
         if (var2 == 0) {
            this.c(0, new ItemStack(Items.l));
         } else {
            this.c(0, new ItemStack(Items.a));
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.j_()) {
         var1.a("IsBaby", true);
      }

      if (this.co()) {
         var1.a("IsVillager", true);
      }

      var1.a("ConversionTime", this.cp() ? this.bn : -1);
      var1.a("CanBreakDoors", this.cn());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.n("IsBaby")) {
         this.l(true);
      }

      if (var1.n("IsVillager")) {
         this.m(true);
      }

      if (var1.b("ConversionTime", 99) && var1.f("ConversionTime") > -1) {
         this.a(var1.f("ConversionTime"));
      }

      this.a(var1.n("CanBreakDoors"));
   }

   @Override
   public void a(EntityLivingBase var1) {
      super.a(var1);
      if ((this.o.aa() == EnumDifficulty.c || this.o.aa() == EnumDifficulty.d) && var1 instanceof EntityVillager) {
         if (this.o.aa() != EnumDifficulty.d && this.V.nextBoolean()) {
            return;
         }

         EntityLiving var2 = (EntityLiving)var1;
         EntityZombie var3 = new EntityZombie(this.o);
         var3.m(var1);
         this.o.e(var1);
         var3.a(this.o.E(new BlockPos(var3)), null);
         var3.m(true);
         if (var1.j_()) {
            var3.l(true);
         }

         var3.k(var2.ce());
         if (var2.l_()) {
            var3.a(var2.aM());
            var3.g(var2.aN());
         }

         this.o.d(var3);
         this.o.a(null, 1016, new BlockPos((int)this.s, (int)this.t, (int)this.u), 0);
      }
   }

   @Override
   public float aS() {
      float var1 = 1.74F;
      if (this.j_()) {
         var1 = (float)(var1 - 0.81);
      }

      return var1;
   }

   @Override
   protected boolean a(ItemStack var1) {
      return var1.b() == Items.aP && this.j_() && this.au() ? false : super.a(var1);
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.a(var1, var2);
      float var3 = var1.c();
      this.j(this.V.nextFloat() < 0.55F * var3);
      if (var2 == null) {
         var2 = new EntityZombie.GroupData(this.o.s.nextFloat() < 0.05F, this.o.s.nextFloat() < 0.05F);
      }

      if (var2 instanceof EntityZombie.GroupData) {
         EntityZombie.GroupData var4 = (EntityZombie.GroupData)var2;
         if (var4.b) {
            this.m(true);
         }

         if (var4.a) {
            this.l(true);
            if (this.o.s.nextFloat() < 0.05) {
               List var5 = this.o.a(EntityChicken.class, this.aR().b(5.0, 3.0, 5.0), EntitySelectors.b);
               if (!var5.isEmpty()) {
                  EntityChicken var6 = (EntityChicken)var5.get(0);
                  var6.l(true);
                  this.a(var6);
               }
            } else if (this.o.s.nextFloat() < 0.05) {
               EntityChicken var10 = new EntityChicken(this.o);
               var10.b(this.s, this.t, this.u, this.y, 0.0F);
               var10.a(var1, null);
               var10.l(true);
               this.o.d(var10);
               this.a(var10);
            }
         }
      }

      this.a(this.V.nextFloat() < var3 * 0.1F);
      this.a(var1);
      this.b(var1);
      if (this.p(4) == null) {
         Calendar var8 = this.o.Y();
         if (var8.get(2) + 1 == 10 && var8.get(5) == 31 && this.V.nextFloat() < 0.25F) {
            this.c(4, new ItemStack(this.V.nextFloat() < 0.1F ? Blocks.aZ : Blocks.aU));
            this.bj[4] = 0.0F;
         }
      }

      this.a(SharedMonsterAttributes.c).b(new AttributeModifier("Random spawn bonus", this.V.nextDouble() * 0.05F, 0));
      double var9 = this.V.nextDouble() * 1.5 * var3;
      if (var9 > 1.0) {
         this.a(SharedMonsterAttributes.b).b(new AttributeModifier("Random zombie-spawn bonus", var9, 2));
      }

      if (this.V.nextFloat() < var3 * 0.05F) {
         this.a(a).b(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * 0.25 + 0.5, 0));
         this.a(SharedMonsterAttributes.a).b(new AttributeModifier("Leader zombie bonus", this.V.nextDouble() * 3.0 + 1.0, 2));
         this.a(true);
      }

      return var2;
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bZ();
      if (var2 != null && var2.b() == Items.ao && var2.i() == 0 && this.co() && this.a(Potion.t)) {
         if (!var1.bA.d) {
            var2.b--;
         }

         if (var2.b <= 0) {
            var1.bi.a(var1.bi.c, null);
         }

         if (!this.o.D) {
            this.a(this.V.nextInt(2401) + 3600);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void a(int var1) {
      this.bn = var1;
      this.H().b(14, (byte)1);
      this.m(Potion.t.H);
      this.c(new PotionEffect(Potion.g.H, var1, Math.min(this.o.aa().a() - 1, 0)));
      this.o.a(this, (byte)16);
   }

   @Override
   public void a(byte var1) {
      if (var1 == 16) {
         if (!this.R()) {
            this.o.a(this.s + 0.5, this.t + 0.5, this.u + 0.5, "mob.zombie.remedy", 1.0F + this.V.nextFloat(), this.V.nextFloat() * 0.7F + 0.3F, false);
         }
      } else {
         super.a(var1);
      }
   }

   @Override
   protected boolean C() {
      return !this.cp();
   }

   public boolean cp() {
      return this.H().a(14) == 1;
   }

   protected void cq() {
      EntityVillager var1 = new EntityVillager(this.o);
      var1.m(this);
      var1.a(this.o.E(new BlockPos(var1)), null);
      var1.cp();
      if (this.j_()) {
         var1.b(-24000);
      }

      this.o.e(this);
      var1.k(this.ce());
      if (this.l_()) {
         var1.a(this.aM());
         var1.g(this.aN());
      }

      this.o.d(var1);
      var1.c(new PotionEffect(Potion.k.H, 200, 0));
      this.o.a(null, 1017, new BlockPos((int)this.s, (int)this.t, (int)this.u), 0);
   }

   protected int cr() {
      int var1 = 1;
      if (this.V.nextFloat() < 0.01F) {
         int var2 = 0;
         BlockPos.MutableBlockPos var3 = new BlockPos.MutableBlockPos();

         for (int var4 = (int)this.s - 4; var4 < (int)this.s + 4 && var2 < 14; var4++) {
            for (int var5 = (int)this.t - 4; var5 < (int)this.t + 4 && var2 < 14; var5++) {
               for (int var6 = (int)this.u - 4; var6 < (int)this.u + 4 && var2 < 14; var6++) {
                  Block var7 = this.o.p(var3.c(var4, var5, var6)).c();
                  if (var7 == Blocks.bi || var7 == Blocks.C) {
                     if (this.V.nextFloat() < 0.3F) {
                        var1++;
                     }

                     var2++;
                  }
               }
            }
         }
      }

      return var1;
   }

   public void n(boolean var1) {
      this.a(var1 ? 0.5F : 1.0F);
   }

   @Override
   protected final void a(float var1, float var2) {
      boolean var3 = this.bp > 0.0F && this.bq > 0.0F;
      this.bp = var1;
      this.bq = var2;
      if (!var3) {
         this.a(1.0F);
      }
   }

   protected final void a(float var1) {
      super.a(this.bp * var1, this.bq * var1);
   }

   @Override
   public double am() {
      return this.j_() ? 0.0 : -0.35;
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (var1.j() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)var1.j()).n() && ((EntityCreeper)var1.j()).cp()) {
         ((EntityCreeper)var1.j()).cq();
         this.a(new ItemStack(Items.bX, 1, 2), 0.0F);
      }
   }

   class GroupData implements IEntityLivingData {
      public boolean a = false;
      public boolean b = false;

      private GroupData(boolean var2, boolean var3) {
         this.a = var2;
         this.b = var3;
      }
   }
}

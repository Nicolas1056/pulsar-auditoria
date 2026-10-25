package net.minecraft.entity.monster;

import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPigZombie extends EntityZombie {
   private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier c = new AttributeModifier(b, "Attacking speed boost", 0.05, 0).a(false);
   private int bm;
   private int bn;
   private UUID bo;

   public EntityPigZombie(World var1) {
      super(var1);
      this.ab = true;
   }

   @Override
   public void b(EntityLivingBase var1) {
      super.b(var1);
      if (var1 != null) {
         this.bo = var1.aK();
      }
   }

   @Override
   protected void n() {
      this.bi.a(1, new EntityPigZombie.AIHurtByAggressor(this));
      this.bi.a(2, new EntityPigZombie.AITargetAggressor(this));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(a).a(0.0);
      this.a(SharedMonsterAttributes.d).a(0.23F);
      this.a(SharedMonsterAttributes.e).a(5.0);
   }

   @Override
   public void t_() {
      super.t_();
   }

   @Override
   protected void E() {
      IAttributeInstance var1 = this.a(SharedMonsterAttributes.d);
      if (this.cm()) {
         if (!this.j_() && !var1.a(c)) {
            var1.b(c);
         }

         this.bm--;
      } else if (var1.a(c)) {
         var1.c(c);
      }

      if (this.bn > 0 && --this.bn == 0) {
         this.a("mob.zombiepig.zpigangry", this.bB() * 2.0F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) * 1.8F);
      }

      if (this.bm > 0 && this.bo != null && this.bd() == null) {
         EntityPlayer var2 = this.o.b(this.bo);
         this.b((EntityLivingBase)var2);
         this.aN = var2;
         this.aO = this.be();
      }

      super.E();
   }

   @Override
   public boolean bR() {
      return this.o.aa() != EnumDifficulty.a;
   }

   @Override
   public boolean bS() {
      return this.o.a(this.aR(), this) && this.o.a(this, this.aR()).isEmpty() && !this.o.d(this.aR());
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Anger", (short)this.bm);
      if (this.bo != null) {
         var1.a("HurtBy", this.bo.toString());
      } else {
         var1.a("HurtBy", "");
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.bm = var1.e("Anger");
      String var2 = var1.j("HurtBy");
      if (var2.length() > 0) {
         this.bo = UUID.fromString(var2);
         EntityPlayer var3 = this.o.b(this.bo);
         this.b((EntityLivingBase)var3);
         if (var3 != null) {
            this.aN = var3;
            this.aO = this.be();
         }
      }
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         Entity var3 = var1.j();
         if (var3 instanceof EntityPlayer) {
            this.b(var3);
         }

         return super.a(var1, var2);
      }
   }

   private void b(Entity var1) {
      this.bm = 400 + this.V.nextInt(400);
      this.bn = this.V.nextInt(40);
      if (var1 instanceof EntityLivingBase) {
         this.b((EntityLivingBase)var1);
      }
   }

   public boolean cm() {
      return this.bm > 0;
   }

   @Override
   protected String z() {
      return "mob.zombiepig.zpig";
   }

   @Override
   protected String bo() {
      return "mob.zombiepig.zpighurt";
   }

   @Override
   protected String bp() {
      return "mob.zombiepig.zpigdeath";
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(2 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Items.bt, 1);
      }

      var3 = this.V.nextInt(2 + var2);

      for (int var6 = 0; var6 < var3; var6++) {
         this.a(Items.bx, 1);
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return false;
   }

   @Override
   protected void bq() {
      this.a(Items.k, 1);
   }

   @Override
   protected void a(DifficultyInstance var1) {
      this.c(0, new ItemStack(Items.B));
   }

   @Override
   public IEntityLivingData a(DifficultyInstance var1, IEntityLivingData var2) {
      super.a(var1, var2);
      this.m(false);
      return var2;
   }

   static class AIHurtByAggressor extends EntityAIHurtByTarget {
      public AIHurtByAggressor(EntityPigZombie var1) {
         super(var1, true);
      }

      @Override
      protected void a(EntityCreature var1, EntityLivingBase var2) {
         super.a(var1, var2);
         if (var1 instanceof EntityPigZombie) {
            ((EntityPigZombie)var1).b((Entity)var2);
         }
      }
   }

   static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer> {
      public AITargetAggressor(EntityPigZombie var1) {
         super(var1, EntityPlayer.class, true);
      }

      @Override
      public boolean a() {
         return ((EntityPigZombie)this.e).cm() && super.a();
      }
   }
}

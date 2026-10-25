package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeper extends EntityMob {
   private int a;
   private int b;
   private int c = 30;
   private int bm = 3;
   private int bn = 0;

   public EntityCreeper(World var1) {
      super(var1);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, new EntityAICreeperSwell(this));
      this.i.a(3, new EntityAIAvoidEntity<>(this, EntityOcelot.class, 6.0F, 1.0, 1.2));
      this.i.a(4, new EntityAIAttackOnCollide(this, 1.0, false));
      this.i.a(5, new EntityAIWander(this, 0.8));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(6, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
      this.bi.a(2, new EntityAIHurtByTarget(this, false));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   public int aE() {
      return this.u() == null ? 3 : 3 + (int)(this.bn() - 1.0F);
   }

   @Override
   public void e(float var1, float var2) {
      super.e(var1, var2);
      this.b = (int)(this.b + var1 * 1.5F);
      if (this.b > this.c - 5) {
         this.b = this.c - 5;
      }
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)-1));
      this.ac.a(17, Byte.valueOf((byte)0));
      this.ac.a(18, Byte.valueOf((byte)0));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      if (this.ac.a(17) == 1) {
         var1.a("powered", true);
      }

      var1.a("Fuse", (short)this.c);
      var1.a("ExplosionRadius", (byte)this.bm);
      var1.a("ignited", this.cn());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.ac.b(17, (byte)(var1.n("powered") ? 1 : 0));
      if (var1.b("Fuse", 99)) {
         this.c = var1.e("Fuse");
      }

      if (var1.b("ExplosionRadius", 99)) {
         this.bm = var1.d("ExplosionRadius");
      }

      if (var1.n("ignited")) {
         this.co();
      }
   }

   @Override
   public void t_() {
      if (this.ai()) {
         this.a = this.b;
         if (this.cn()) {
            this.a(1);
         }

         int var1 = this.cm();
         if (var1 > 0 && this.b == 0) {
            this.a("creeper.primed", 1.0F, 0.5F);
         }

         this.b += var1;
         if (this.b < 0) {
            this.b = 0;
         }

         if (this.b >= this.c) {
            this.b = this.c;
            this.cr();
         }
      }

      super.t_();
   }

   @Override
   protected String bo() {
      return "mob.creeper.say";
   }

   @Override
   protected String bp() {
      return "mob.creeper.death";
   }

   @Override
   public void a(DamageSource var1) {
      super.a(var1);
      if (var1.j() instanceof EntitySkeleton) {
         int var2 = Item.b(Items.cq);
         int var3 = Item.b(Items.cB);
         int var4 = var2 + this.V.nextInt(var3 - var2 + 1);
         this.a(Item.b(var4), 1);
      } else if (var1.j() instanceof EntityCreeper && var1.j() != this && ((EntityCreeper)var1.j()).n() && ((EntityCreeper)var1.j()).cp()) {
         ((EntityCreeper)var1.j()).cq();
         this.a(new ItemStack(Items.bX, 1, 4), 0.0F);
      }
   }

   @Override
   public boolean r(Entity var1) {
      return true;
   }

   public boolean n() {
      return this.ac.a(17) == 1;
   }

   public float a(float var1) {
      return (this.a + (this.b - this.a) * var1) / (this.c - 2);
   }

   @Override
   protected Item A() {
      return Items.H;
   }

   public int cm() {
      return this.ac.a(16);
   }

   public void a(int var1) {
      this.ac.b(16, (byte)var1);
   }

   @Override
   public void a(EntityLightningBolt var1) {
      super.a(var1);
      this.ac.b(17, (byte)1);
   }

   @Override
   protected boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.d) {
         this.o.a(this.s + 0.5, this.t + 0.5, this.u + 0.5, "fire.ignite", 1.0F, this.V.nextFloat() * 0.4F + 0.8F);
         var1.bw();
         if (!this.o.D) {
            this.co();
            var2.a(1, var1);
            return true;
         }
      }

      return super.a(var1);
   }

   private void cr() {
      if (!this.o.D) {
         boolean var1 = this.o.Q().b("mobGriefing");
         float var2 = this.n() ? 2.0F : 1.0F;
         this.o.a(this, this.s, this.t, this.u, this.bm * var2, var1);
         this.J();
      }
   }

   public boolean cn() {
      return this.ac.a(18) != 0;
   }

   public void co() {
      this.ac.b(18, (byte)1);
   }

   public boolean cp() {
      return this.bn < 1 && this.o.Q().b("doMobLoot");
   }

   public void cq() {
      this.bn++;
   }
}

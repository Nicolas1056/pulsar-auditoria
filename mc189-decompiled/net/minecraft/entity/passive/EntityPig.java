package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPig extends EntityAnimal {
   private final EntityAIControlledByPlayer bm;

   public EntityPig(World var1) {
      super(var1);
      this.a(0.9F, 0.9F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIPanic(this, 1.25));
      this.i.a(2, this.bm = new EntityAIControlledByPlayer(this, 0.3F));
      this.i.a(3, new EntityAIMate(this, 1.0));
      this.i.a(4, new EntityAITempt(this, 1.2, Items.bY, false));
      this.i.a(4, new EntityAITempt(this, 1.2, Items.bR, false));
      this.i.a(5, new EntityAIFollowParent(this, 1.1));
      this.i.a(6, new EntityAIWander(this, 1.0));
      this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(8, new EntityAILookIdle(this));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(10.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   public boolean bW() {
      ItemStack var1 = ((EntityPlayer)this.l).bA();
      return var1 != null && var1.b() == Items.bY;
   }

   @Override
   protected void h() {
      super.h();
      this.ac.a(16, Byte.valueOf((byte)0));
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Saddle", this.cl());
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.l(var1.n("Saddle"));
   }

   @Override
   protected String z() {
      return "mob.pig.say";
   }

   @Override
   protected String bo() {
      return "mob.pig.say";
   }

   @Override
   protected String bp() {
      return "mob.pig.death";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.pig.step", 0.15F, 1.0F);
   }

   @Override
   public boolean a(EntityPlayer var1) {
      if (super.a(var1)) {
         return true;
      } else if (!this.cl() || this.o.D || this.l != null && this.l != var1) {
         return false;
      } else {
         var1.a(this);
         return true;
      }
   }

   @Override
   protected Item A() {
      return this.at() ? Items.am : Items.al;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3) + 1 + this.V.nextInt(1 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         if (this.at()) {
            this.a(Items.am, 1);
         } else {
            this.a(Items.al, 1);
         }
      }

      if (this.cl()) {
         this.a(Items.aA, 1);
      }
   }

   public boolean cl() {
      return (this.ac.a(16) & 1) != 0;
   }

   public void l(boolean var1) {
      if (var1) {
         this.ac.b(16, (byte)1);
      } else {
         this.ac.b(16, (byte)0);
      }
   }

   @Override
   public void a(EntityLightningBolt var1) {
      if (!this.o.D && !this.I) {
         EntityPigZombie var2 = new EntityPigZombie(this.o);
         var2.c(0, new ItemStack(Items.B));
         var2.b(this.s, this.t, this.u, this.y, this.z);
         var2.k(this.ce());
         if (this.l_()) {
            var2.a(this.aM());
            var2.g(this.aN());
         }

         this.o.d(var2);
         this.J();
      }
   }

   @Override
   public void e(float var1, float var2) {
      super.e(var1, var2);
      if (var1 > 5.0F && this.l instanceof EntityPlayer) {
         ((EntityPlayer)this.l).b(AchievementList.u);
      }
   }

   public EntityPig b(EntityAgeable var1) {
      return new EntityPig(this.o);
   }

   @Override
   public boolean d(ItemStack var1) {
      return var1 != null && var1.b() == Items.bR;
   }

   public EntityAIControlledByPlayer cm() {
      return this.bm;
   }
}

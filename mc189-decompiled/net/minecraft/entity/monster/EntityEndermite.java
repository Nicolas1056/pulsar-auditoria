package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite extends EntityMob {
   private int a = 0;
   private boolean b = false;

   public EntityEndermite(World var1) {
      super(var1);
      this.b_ = 3;
      this.a(0.4F, 0.3F);
      this.i.a(1, new EntityAISwimming(this));
      this.i.a(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
      this.i.a(3, new EntityAIWander(this, 1.0));
      this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.i.a(8, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAIHurtByTarget(this, true));
      this.bi.a(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   public float aS() {
      return 0.1F;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(8.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
      this.a(SharedMonsterAttributes.e).a(2.0);
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   protected String z() {
      return "mob.silverfish.say";
   }

   @Override
   protected String bo() {
      return "mob.silverfish.hit";
   }

   @Override
   protected String bp() {
      return "mob.silverfish.kill";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.silverfish.step", 0.15F, 1.0F);
   }

   @Override
   protected Item A() {
      return null;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.a = var1.f("Lifetime");
      this.b = var1.n("PlayerSpawned");
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("Lifetime", this.a);
      var1.a("PlayerSpawned", this.b);
   }

   @Override
   public void t_() {
      this.aI = this.y;
      super.t_();
   }

   public boolean n() {
      return this.b;
   }

   public void a(boolean var1) {
      this.b = var1;
   }

   @Override
   public void m() {
      super.m();
      if (this.o.D) {
         for (int var1 = 0; var1 < 2; var1++) {
            this.o
               .a(
                  EnumParticleTypes.y,
                  this.s + (this.V.nextDouble() - 0.5) * this.J,
                  this.t + this.V.nextDouble() * this.K,
                  this.u + (this.V.nextDouble() - 0.5) * this.J,
                  (this.V.nextDouble() - 0.5) * 2.0,
                  -this.V.nextDouble(),
                  (this.V.nextDouble() - 0.5) * 2.0
               );
         }
      } else {
         if (!this.bZ()) {
            this.a++;
         }

         if (this.a >= 2400) {
            this.J();
         }
      }
   }

   @Override
   protected boolean n_() {
      return true;
   }

   @Override
   public boolean bR() {
      if (super.bR()) {
         EntityPlayer var1 = this.o.a(this, 5.0);
         return var1 == null;
      } else {
         return false;
      }
   }

   @Override
   public EnumCreatureAttribute bz() {
      return EnumCreatureAttribute.c;
   }
}

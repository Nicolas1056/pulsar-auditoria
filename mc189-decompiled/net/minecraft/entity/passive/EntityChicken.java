package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends EntityAnimal {
   public float bm;
   public float bo;
   public float bp;
   public float bq;
   public float br = 1.0F;
   public int bs;
   public boolean bt;

   public EntityChicken(World var1) {
      super(var1);
      this.a(0.4F, 0.7F);
      this.bs = this.V.nextInt(6000) + 6000;
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIPanic(this, 1.4));
      this.i.a(2, new EntityAIMate(this, 1.0));
      this.i.a(3, new EntityAITempt(this, 1.0, Items.N, false));
      this.i.a(4, new EntityAIFollowParent(this, 1.1));
      this.i.a(5, new EntityAIWander(this, 1.0));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(7, new EntityAILookIdle(this));
   }

   @Override
   public float aS() {
      return this.K;
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(4.0);
      this.a(SharedMonsterAttributes.d).a(0.25);
   }

   @Override
   public void m() {
      super.m();
      this.bq = this.bm;
      this.bp = this.bo;
      this.bo = (float)(this.bo + (this.C ? -1 : 4) * 0.3);
      this.bo = MathHelper.a(this.bo, 0.0F, 1.0F);
      if (!this.C && this.br < 1.0F) {
         this.br = 1.0F;
      }

      this.br = (float)(this.br * 0.9);
      if (!this.C && this.w < 0.0) {
         this.w *= 0.6;
      }

      this.bm = this.bm + this.br * 2.0F;
      if (!this.o.D && !this.j_() && !this.cl() && --this.bs <= 0) {
         this.a("mob.chicken.plop", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
         this.a(Items.aP, 1);
         this.bs = this.V.nextInt(6000) + 6000;
      }
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected String z() {
      return "mob.chicken.say";
   }

   @Override
   protected String bo() {
      return "mob.chicken.hurt";
   }

   @Override
   protected String bp() {
      return "mob.chicken.hurt";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.chicken.step", 0.15F, 1.0F);
   }

   @Override
   protected Item A() {
      return Items.G;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3) + this.V.nextInt(1 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Items.G, 1);
      }

      if (this.at()) {
         this.a(Items.bl, 1);
      } else {
         this.a(Items.bk, 1);
      }
   }

   public EntityChicken b(EntityAgeable var1) {
      return new EntityChicken(this.o);
   }

   @Override
   public boolean d(ItemStack var1) {
      return var1 != null && var1.b() == Items.N;
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      this.bt = var1.n("IsChickenJockey");
      if (var1.c("EggLayTime")) {
         this.bs = var1.f("EggLayTime");
      }
   }

   @Override
   protected int b(EntityPlayer var1) {
      return this.cl() ? 10 : super.b(var1);
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("IsChickenJockey", this.bt);
      var1.a("EggLayTime", this.bs);
   }

   @Override
   protected boolean C() {
      return this.cl() && this.l == null;
   }

   @Override
   public void al() {
      super.al();
      float var1 = MathHelper.a(this.aI * (float) Math.PI / 180.0F);
      float var2 = MathHelper.b(this.aI * (float) Math.PI / 180.0F);
      float var3 = 0.1F;
      float var4 = 0.0F;
      this.l.b(this.s + var3 * var1, this.t + this.K * 0.5F + this.l.am() + var4, this.u - var3 * var2);
      if (this.l instanceof EntityLivingBase) {
         ((EntityLivingBase)this.l).aI = this.aI;
      }
   }

   public boolean cl() {
      return this.bt;
   }

   public void l(boolean var1) {
      this.bt = var1;
   }
}

package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
   public EntitySnowman(World var1) {
      super(var1);
      this.a(0.7F, 1.9F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0F));
      this.i.a(2, new EntityAIWander(this, 1.0));
      this.i.a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(4, new EntityAILookIdle(this));
      this.bi.a(1, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, true, false, IMob.d));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(4.0);
      this.a(SharedMonsterAttributes.d).a(0.2F);
   }

   @Override
   public void m() {
      super.m();
      if (!this.o.D) {
         int var1 = MathHelper.c(this.s);
         int var2 = MathHelper.c(this.t);
         int var3 = MathHelper.c(this.u);
         if (this.U()) {
            this.a(DamageSource.f, 1.0F);
         }

         if (this.o.b(new BlockPos(var1, 0, var3)).a(new BlockPos(var1, var2, var3)) > 1.0F) {
            this.a(DamageSource.c, 1.0F);
         }

         for (int var4 = 0; var4 < 4; var4++) {
            var1 = MathHelper.c(this.s + (var4 % 2 * 2 - 1) * 0.25F);
            var2 = MathHelper.c(this.t);
            var3 = MathHelper.c(this.u + (var4 / 2 % 2 * 2 - 1) * 0.25F);
            BlockPos var5 = new BlockPos(var1, var2, var3);
            if (this.o.p(var5).c().t() == Material.a && this.o.b(new BlockPos(var1, 0, var3)).a(var5) < 0.8F && Blocks.aH.d(this.o, var5)) {
               this.o.a(var5, Blocks.aH.Q());
            }
         }
      }
   }

   @Override
   protected Item A() {
      return Items.aD;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(16);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Items.aD, 1);
      }
   }

   @Override
   public void a(EntityLivingBase var1, float var2) {
      EntitySnowball var3 = new EntitySnowball(this.o, this);
      double var4 = var1.t + var1.aS() - 1.1F;
      double var6 = var1.s - this.s;
      double var8 = var4 - var3.t;
      double var10 = var1.u - this.u;
      float var12 = MathHelper.a(var6 * var6 + var10 * var10) * 0.2F;
      var3.c(var6, var8 + var12, var10, 1.6F, 12.0F);
      this.a("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
      this.o.d(var3);
   }

   @Override
   public float aS() {
      return 1.7F;
   }
}

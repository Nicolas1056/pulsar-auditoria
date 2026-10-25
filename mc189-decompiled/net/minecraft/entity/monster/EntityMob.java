package net.minecraft.entity.monster;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {
   public EntityMob(World var1) {
      super(var1);
      this.b_ = 5;
   }

   @Override
   public void m() {
      this.bx();
      float var1 = this.c(1.0F);
      if (var1 > 0.5F) {
         this.aQ += 2;
      }

      super.m();
   }

   @Override
   public void t_() {
      super.t_();
      if (!this.o.D && this.o.aa() == EnumDifficulty.a) {
         this.J();
      }
   }

   @Override
   protected String P() {
      return "game.hostile.swim";
   }

   @Override
   protected String aa() {
      return "game.hostile.swim.splash";
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else if (super.a(var1, var2)) {
         Entity var3 = var1.j();
         return this.l != var3 && this.m != var3 ? true : true;
      } else {
         return false;
      }
   }

   @Override
   protected String bo() {
      return "game.hostile.hurt";
   }

   @Override
   protected String bp() {
      return "game.hostile.die";
   }

   @Override
   protected String n(int var1) {
      return var1 > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
   }

   @Override
   public boolean r(Entity var1) {
      float var2 = (float)this.a(SharedMonsterAttributes.e).e();
      int var3 = 0;
      if (var1 instanceof EntityLivingBase) {
         var2 += EnchantmentHelper.a(this.bA(), ((EntityLivingBase)var1).bz());
         var3 += EnchantmentHelper.a((EntityLivingBase)this);
      }

      boolean var4 = var1.a(DamageSource.a((EntityLivingBase)this), var2);
      if (var4) {
         if (var3 > 0) {
            var1.g(-MathHelper.a(this.y * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1, MathHelper.b(this.y * (float) Math.PI / 180.0F) * var3 * 0.5F);
            this.v *= 0.6;
            this.x *= 0.6;
         }

         int var5 = EnchantmentHelper.b((EntityLivingBase)this);
         if (var5 > 0) {
            var1.e(var5 * 4);
         }

         this.a(this, var1);
      }

      return var4;
   }

   @Override
   public float a(BlockPos var1) {
      return 0.5F - this.o.o(var1);
   }

   protected boolean n_() {
      BlockPos var1 = new BlockPos(this.s, this.aR().b, this.u);
      if (this.o.b(EnumSkyBlock.a, var1) > this.V.nextInt(32)) {
         return false;
      } else {
         int var2 = this.o.l(var1);
         if (this.o.R()) {
            int var3 = this.o.ab();
            this.o.c(10);
            var2 = this.o.l(var1);
            this.o.c(var3);
         }

         return var2 <= this.V.nextInt(8);
      }
   }

   @Override
   public boolean bR() {
      return this.o.aa() != EnumDifficulty.a && this.n_() && super.bR();
   }

   @Override
   protected void aX() {
      super.aX();
      this.by().b(SharedMonsterAttributes.e);
   }

   @Override
   protected boolean ba() {
      return true;
   }
}

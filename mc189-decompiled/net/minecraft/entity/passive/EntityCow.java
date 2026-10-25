package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityCow extends EntityAnimal {
   public EntityCow(World var1) {
      super(var1);
      this.a(0.9F, 1.3F);
      ((PathNavigateGround)this.s()).a(true);
      this.i.a(0, new EntityAISwimming(this));
      this.i.a(1, new EntityAIPanic(this, 2.0));
      this.i.a(2, new EntityAIMate(this, 1.0));
      this.i.a(3, new EntityAITempt(this, 1.25, Items.O, false));
      this.i.a(4, new EntityAIFollowParent(this, 1.25));
      this.i.a(5, new EntityAIWander(this, 1.0));
      this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      this.i.a(7, new EntityAILookIdle(this));
   }

   @Override
   protected void aX() {
      super.aX();
      this.a(SharedMonsterAttributes.a).a(10.0);
      this.a(SharedMonsterAttributes.d).a(0.2F);
   }

   @Override
   protected String z() {
      return "mob.cow.say";
   }

   @Override
   protected String bo() {
      return "mob.cow.hurt";
   }

   @Override
   protected String bp() {
      return "mob.cow.hurt";
   }

   @Override
   protected void a(BlockPos var1, Block var2) {
      this.a("mob.cow.step", 0.15F, 1.0F);
   }

   @Override
   protected float bB() {
      return 0.4F;
   }

   @Override
   protected Item A() {
      return Items.aF;
   }

   @Override
   protected void b(boolean var1, int var2) {
      int var3 = this.V.nextInt(3) + this.V.nextInt(1 + var2);

      for (int var4 = 0; var4 < var3; var4++) {
         this.a(Items.aF, 1);
      }

      var3 = this.V.nextInt(3) + 1 + this.V.nextInt(1 + var2);

      for (int var6 = 0; var6 < var3; var6++) {
         if (this.at()) {
            this.a(Items.bj, 1);
         } else {
            this.a(Items.bi, 1);
         }
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      ItemStack var2 = var1.bi.h();
      if (var2 != null && var2.b() == Items.aw && !var1.bA.d && !this.j_()) {
         if (var2.b-- == 1) {
            var1.bi.a(var1.bi.c, new ItemStack(Items.aG));
         } else if (!var1.bi.a(new ItemStack(Items.aG))) {
            var1.a(new ItemStack(Items.aG, 1, 0), false);
         }

         return true;
      } else {
         return super.a(var1);
      }
   }

   public EntityCow b(EntityAgeable var1) {
      return new EntityCow(this.o);
   }

   @Override
   public float aS() {
      return this.K;
   }
}

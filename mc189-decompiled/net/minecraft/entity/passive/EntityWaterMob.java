package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals {
   public EntityWaterMob(World var1) {
      super(var1);
   }

   @Override
   public boolean aY() {
      return true;
   }

   @Override
   public boolean bR() {
      return true;
   }

   @Override
   public boolean bS() {
      return this.o.a(this.aR(), this);
   }

   @Override
   public int w() {
      return 120;
   }

   @Override
   protected boolean C() {
      return true;
   }

   @Override
   protected int b(EntityPlayer var1) {
      return 1 + this.o.s.nextInt(3);
   }

   @Override
   public void K() {
      int var1 = this.az();
      super.K();
      if (this.ai() && !this.V()) {
         this.h(--var1);
         if (this.az() == -20) {
            this.h(0);
            this.a(DamageSource.f, 2.0F);
         }
      } else {
         this.h(300);
      }
   }

   @Override
   public boolean aL() {
      return false;
   }
}

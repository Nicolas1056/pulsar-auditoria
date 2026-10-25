package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
   public EntityGolem(World var1) {
      super(var1);
   }

   @Override
   public void e(float var1, float var2) {
   }

   @Override
   protected String z() {
      return "none";
   }

   @Override
   protected String bo() {
      return "none";
   }

   @Override
   protected String bp() {
      return "none";
   }

   @Override
   public int w() {
      return 120;
   }

   @Override
   protected boolean C() {
      return false;
   }
}

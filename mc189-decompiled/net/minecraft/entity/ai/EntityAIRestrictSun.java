package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase {
   private EntityCreature a;

   public EntityAIRestrictSun(EntityCreature var1) {
      this.a = var1;
   }

   @Override
   public boolean a() {
      return this.a.o.w();
   }

   @Override
   public void c() {
      ((PathNavigateGround)this.a.s()).e(true);
   }

   @Override
   public void d() {
      ((PathNavigateGround)this.a.s()).e(false);
   }
}

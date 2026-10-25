package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAISwimming extends EntityAIBase {
   private EntityLiving a;

   public EntityAISwimming(EntityLiving var1) {
      this.a = var1;
      this.a(4);
      ((PathNavigateGround)var1.s()).d(true);
   }

   @Override
   public boolean a() {
      return this.a.V() || this.a.ab();
   }

   @Override
   public void e() {
      if (this.a.bc().nextFloat() < 0.8F) {
         this.a.r().a();
      }
   }
}

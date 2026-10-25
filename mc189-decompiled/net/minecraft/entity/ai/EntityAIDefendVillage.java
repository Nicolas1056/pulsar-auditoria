package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;

public class EntityAIDefendVillage extends EntityAITarget {
   EntityIronGolem a;
   EntityLivingBase b;

   public EntityAIDefendVillage(EntityIronGolem var1) {
      super(var1, false, true);
      this.a = var1;
      this.a(1);
   }

   @Override
   public boolean a() {
      Village var1 = this.a.n();
      if (var1 == null) {
         return false;
      } else {
         this.b = var1.b(this.a);
         if (this.b instanceof EntityCreeper) {
            return false;
         } else if (!this.a(this.b, false)) {
            if (this.e.bc().nextInt(20) == 0) {
               this.b = var1.c(this.a);
               return this.a(this.b, false);
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   @Override
   public void c() {
      this.a.d(this.b);
      super.c();
   }
}

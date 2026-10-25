package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsRestriction extends EntityAIBase {
   private EntityCreature a;
   private double b;
   private double c;
   private double d;
   private double e;

   public EntityAIMoveTowardsRestriction(EntityCreature var1, double var2) {
      this.a = var1;
      this.e = var2;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (this.a.cg()) {
         return false;
      } else {
         BlockPos var1 = this.a.ch();
         Vec3 var2 = RandomPositionGenerator.a(this.a, 16, 7, new Vec3(var1.n(), var1.o(), var1.p()));
         if (var2 == null) {
            return false;
         } else {
            this.b = var2.a;
            this.c = var2.b;
            this.d = var2.c;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.s().m();
   }

   @Override
   public void c() {
      this.a.s().a(this.b, this.c, this.d, this.e);
   }
}

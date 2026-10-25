package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIPanic extends EntityAIBase {
   private EntityCreature b;
   protected double a;
   private double c;
   private double d;
   private double e;

   public EntityAIPanic(EntityCreature var1, double var2) {
      this.b = var1;
      this.a = var2;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (this.b.bd() == null && !this.b.at()) {
         return false;
      } else {
         Vec3 var1 = RandomPositionGenerator.a(this.b, 5, 4);
         if (var1 == null) {
            return false;
         } else {
            this.c = var1.a;
            this.d = var1.b;
            this.e = var1.c;
            return true;
         }
      }
   }

   @Override
   public void c() {
      this.b.s().a(this.c, this.d, this.e, this.a);
   }

   @Override
   public boolean b() {
      return !this.b.s().m();
   }
}

package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.passive.EntityAnimal;

public class EntityAIFollowParent extends EntityAIBase {
   EntityAnimal a;
   EntityAnimal b;
   double c;
   private int d;

   public EntityAIFollowParent(EntityAnimal var1, double var2) {
      this.a = var1;
      this.c = var2;
   }

   @Override
   public boolean a() {
      if (this.a.l() >= 0) {
         return false;
      } else {
         List var1 = this.a.o.a(this.a.getClass(), this.a.aR().b(8.0, 4.0, 8.0));
         EntityAnimal var2 = null;
         double var3 = Double.MAX_VALUE;

         for (EntityAnimal var6 : var1) {
            if (var6.l() >= 0) {
               double var7 = this.a.h(var6);
               if (!(var7 > var3)) {
                  var3 = var7;
                  var2 = var6;
               }
            }
         }

         if (var2 == null) {
            return false;
         } else if (var3 < 9.0) {
            return false;
         } else {
            this.b = var2;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      if (this.a.l() >= 0) {
         return false;
      } else if (!this.b.ai()) {
         return false;
      } else {
         double var1 = this.a.h(this.b);
         return !(var1 < 9.0) && !(var1 > 256.0);
      }
   }

   @Override
   public void c() {
      this.d = 0;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (--this.d <= 0) {
         this.d = 10;
         this.a.s().a(this.b, this.c);
      }
   }
}

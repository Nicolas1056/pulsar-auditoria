package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;

public class EntityAIPlay extends EntityAIBase {
   private EntityVillager a;
   private EntityLivingBase b;
   private double c;
   private int d;

   public EntityAIPlay(EntityVillager var1, double var2) {
      this.a = var1;
      this.c = var2;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (this.a.l() >= 0) {
         return false;
      } else if (this.a.bc().nextInt(400) != 0) {
         return false;
      } else {
         List var1 = this.a.o.a(EntityVillager.class, this.a.aR().b(6.0, 3.0, 6.0));
         double var2 = Double.MAX_VALUE;

         for (EntityVillager var5 : var1) {
            if (var5 != this.a && !var5.cn() && var5.l() < 0) {
               double var6 = var5.h(this.a);
               if (!(var6 > var2)) {
                  var2 = var6;
                  this.b = var5;
               }
            }
         }

         if (this.b == null) {
            Vec3 var8 = RandomPositionGenerator.a(this.a, 16, 3);
            if (var8 == null) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public boolean b() {
      return this.d > 0;
   }

   @Override
   public void c() {
      if (this.b != null) {
         this.a.m(true);
      }

      this.d = 1000;
   }

   @Override
   public void d() {
      this.a.m(false);
      this.b = null;
   }

   @Override
   public void e() {
      this.d--;
      if (this.b != null) {
         if (this.a.h(this.b) > 4.0) {
            this.a.s().a(this.b, this.c);
         }
      } else if (this.a.s().m()) {
         Vec3 var1 = RandomPositionGenerator.a(this.a, 16, 3);
         if (var1 == null) {
            return;
         }

         this.a.s().a(var1.a, var1.b, var1.c, this.c);
      }
   }
}

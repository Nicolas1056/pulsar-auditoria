package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase {
   private EntityHorse a;
   private double b;
   private double c;
   private double d;
   private double e;

   public EntityAIRunAroundLikeCrazy(EntityHorse var1, double var2) {
      this.a = var1;
      this.b = var2;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (!this.a.co() && this.a.l != null) {
         Vec3 var1 = RandomPositionGenerator.a(this.a, 5, 4);
         if (var1 == null) {
            return false;
         } else {
            this.c = var1.a;
            this.d = var1.b;
            this.e = var1.c;
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void c() {
      this.a.s().a(this.c, this.d, this.e, this.b);
   }

   @Override
   public boolean b() {
      return !this.a.s().m() && this.a.l != null;
   }

   @Override
   public void e() {
      if (this.a.bc().nextInt(50) == 0) {
         if (this.a.l instanceof EntityPlayer) {
            int var1 = this.a.cC();
            int var2 = this.a.cI();
            if (var2 > 0 && this.a.bc().nextInt(var2) < var1) {
               this.a.h((EntityPlayer)this.a.l);
               this.a.o.a(this.a, (byte)7);
               return;
            }

            this.a.u(5);
         }

         this.a.l.a(null);
         this.a.l = null;
         this.a.cW();
         this.a.o.a(this.a, (byte)6);
      }
   }
}

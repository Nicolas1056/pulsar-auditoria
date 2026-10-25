package net.minecraft.entity.ai;

import java.util.List;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIFollowGolem extends EntityAIBase {
   private EntityVillager a;
   private EntityIronGolem b;
   private int c;
   private boolean d;

   public EntityAIFollowGolem(EntityVillager var1) {
      this.a = var1;
      this.a(3);
   }

   @Override
   public boolean a() {
      if (this.a.l() >= 0) {
         return false;
      } else if (!this.a.o.w()) {
         return false;
      } else {
         List var1 = this.a.o.a(EntityIronGolem.class, this.a.aR().b(6.0, 2.0, 6.0));
         if (var1.isEmpty()) {
            return false;
         } else {
            for (EntityIronGolem var3 : var1) {
               if (var3.cm() > 0) {
                  this.b = var3;
                  break;
               }
            }

            return this.b != null;
         }
      }
   }

   @Override
   public boolean b() {
      return this.b.cm() > 0;
   }

   @Override
   public void c() {
      this.c = this.a.bc().nextInt(320);
      this.d = false;
      this.b.s().n();
   }

   @Override
   public void d() {
      this.b = null;
      this.a.s().n();
   }

   @Override
   public void e() {
      this.a.p().a(this.b, 30.0F, 30.0F);
      if (this.b.cm() == this.c) {
         this.a.s().a(this.b, 0.5);
         this.d = true;
      }

      if (this.d && this.a.h(this.b) < 4.0) {
         this.b.a(false);
         this.a.s().n();
      }
   }
}

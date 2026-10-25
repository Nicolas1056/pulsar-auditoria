package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityAIOcelotAttack extends EntityAIBase {
   World a;
   EntityLiving b;
   EntityLivingBase c;
   int d;

   public EntityAIOcelotAttack(EntityLiving var1) {
      this.b = var1;
      this.a = var1.o;
      this.a(3);
   }

   @Override
   public boolean a() {
      EntityLivingBase var1 = this.b.u();
      if (var1 == null) {
         return false;
      } else {
         this.c = var1;
         return true;
      }
   }

   @Override
   public boolean b() {
      if (!this.c.ai()) {
         return false;
      } else {
         return this.b.h(this.c) > 225.0 ? false : !this.b.s().m() || this.a();
      }
   }

   @Override
   public void d() {
      this.c = null;
      this.b.s().n();
   }

   @Override
   public void e() {
      this.b.p().a(this.c, 30.0F, 30.0F);
      double var1 = this.b.J * 2.0F * (this.b.J * 2.0F);
      double var3 = this.b.e(this.c.s, this.c.aR().b, this.c.u);
      double var5 = 0.8;
      if (var3 > var1 && var3 < 16.0) {
         var5 = 1.33;
      } else if (var3 < 225.0) {
         var5 = 0.6;
      }

      this.b.s().a(this.c, var5);
      this.d = Math.max(this.d - 1, 0);
      if (!(var3 > var1)) {
         if (this.d <= 0) {
            this.d = 20;
            this.b.r(this.c);
         }
      }
   }
}

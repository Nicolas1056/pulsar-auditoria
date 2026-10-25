package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAICreeperSwell extends EntityAIBase {
   EntityCreeper a;
   EntityLivingBase b;

   public EntityAICreeperSwell(EntityCreeper var1) {
      this.a = var1;
      this.a(1);
   }

   @Override
   public boolean a() {
      EntityLivingBase var1 = this.a.u();
      return this.a.cm() > 0 || var1 != null && this.a.h(var1) < 9.0;
   }

   @Override
   public void c() {
      this.a.s().n();
      this.b = this.a.u();
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (this.b == null) {
         this.a.a(-1);
      } else if (this.a.h(this.b) > 49.0) {
         this.a.a(-1);
      } else if (!this.a.t().a(this.b)) {
         this.a.a(-1);
      } else {
         this.a.a(1);
      }
   }
}

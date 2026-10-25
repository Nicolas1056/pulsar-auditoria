package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtByTarget extends EntityAITarget {
   EntityTameable a;
   EntityLivingBase b;
   private int c;

   public EntityAIOwnerHurtByTarget(EntityTameable var1) {
      super(var1, false);
      this.a = var1;
      this.a(1);
   }

   @Override
   public boolean a() {
      if (!this.a.cl()) {
         return false;
      } else {
         EntityLivingBase var1 = this.a.co();
         if (var1 == null) {
            return false;
         } else {
            this.b = var1.bd();
            int var2 = var1.be();
            return var2 != this.c && this.a(this.b, false) && this.a.a(this.b, var1);
         }
      }
   }

   @Override
   public void c() {
      this.e.d(this.b);
      EntityLivingBase var1 = this.a.co();
      if (var1 != null) {
         this.c = var1.be();
      }

      super.c();
   }
}

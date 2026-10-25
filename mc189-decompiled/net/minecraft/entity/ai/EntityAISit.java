package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISit extends EntityAIBase {
   private EntityTameable a;
   private boolean b;

   public EntityAISit(EntityTameable var1) {
      this.a = var1;
      this.a(5);
   }

   @Override
   public boolean a() {
      if (!this.a.cl()) {
         return false;
      } else if (this.a.V()) {
         return false;
      } else if (!this.a.C) {
         return false;
      } else {
         EntityLivingBase var1 = this.a.co();
         if (var1 == null) {
            return true;
         } else {
            return this.a.h(var1) < 144.0 && var1.bd() != null ? false : this.b;
         }
      }
   }

   @Override
   public void c() {
      this.a.s().n();
      this.a.n(true);
   }

   @Override
   public void d() {
      this.a.n(false);
   }

   public void a(boolean var1) {
      this.b = var1;
   }
}

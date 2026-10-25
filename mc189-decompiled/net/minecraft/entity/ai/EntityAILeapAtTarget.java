package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {
   EntityLiving a;
   EntityLivingBase b;
   float c;

   public EntityAILeapAtTarget(EntityLiving var1, float var2) {
      this.a = var1;
      this.c = var2;
      this.a(5);
   }

   @Override
   public boolean a() {
      this.b = this.a.u();
      if (this.b == null) {
         return false;
      } else {
         double var1 = this.a.h(this.b);
         if (var1 < 4.0 || var1 > 16.0) {
            return false;
         } else {
            return !this.a.C ? false : this.a.bc().nextInt(5) == 0;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.C;
   }

   @Override
   public void c() {
      double var1 = this.b.s - this.a.s;
      double var3 = this.b.u - this.a.u;
      float var5 = MathHelper.a(var1 * var1 + var3 * var3);
      this.a.v = this.a.v + (var1 / var5 * 0.5 * 0.8F + this.a.v * 0.2F);
      this.a.x = this.a.x + (var3 / var5 * 0.5 * 0.8F + this.a.x * 0.2F);
      this.a.w = this.c;
   }
}

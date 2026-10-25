package net.minecraft.entity;

import net.minecraft.util.MathHelper;

public class EntityBodyHelper {
   private EntityLivingBase a;
   private int b;
   private float c;

   public EntityBodyHelper(EntityLivingBase var1) {
      this.a = var1;
   }

   public void a() {
      double var1 = this.a.s - this.a.p;
      double var3 = this.a.u - this.a.r;
      if (var1 * var1 + var3 * var3 > 2.5000003E-7F) {
         this.a.aI = this.a.y;
         this.a.aK = this.a(this.a.aI, this.a.aK, 75.0F);
         this.c = this.a.aK;
         this.b = 0;
      } else {
         float var5 = 75.0F;
         if (Math.abs(this.a.aK - this.c) > 15.0F) {
            this.b = 0;
            this.c = this.a.aK;
         } else {
            this.b++;
            byte var6 = 10;
            if (this.b > 10) {
               var5 = Math.max(1.0F - (this.b - 10) / 10.0F, 0.0F) * 75.0F;
            }
         }

         this.a.aI = this.a(this.a.aK, this.a.aI, var5);
      }
   }

   private float a(float var1, float var2, float var3) {
      float var4 = MathHelper.g(var1 - var2);
      if (var4 < -var3) {
         var4 = -var3;
      }

      if (var4 >= var3) {
         var4 = var3;
      }

      return var1 - var4;
   }
}

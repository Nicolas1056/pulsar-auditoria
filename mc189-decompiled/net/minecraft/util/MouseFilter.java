package net.minecraft.util;

public class MouseFilter {
   private float a;
   private float b;
   private float c;

   public float a(float var1, float var2) {
      this.a += var1;
      var1 = (this.a - this.b) * var2;
      this.c = this.c + (var1 - this.c) * 0.5F;
      if (var1 > 0.0F && var1 > this.c || var1 < 0.0F && var1 < this.c) {
         var1 = this.c;
      }

      this.b += var1;
      return var1;
   }

   public void a() {
      this.a = 0.0F;
      this.b = 0.0F;
      this.c = 0.0F;
   }
}

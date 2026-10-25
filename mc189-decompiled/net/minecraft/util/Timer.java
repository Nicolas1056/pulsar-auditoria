package net.minecraft.util;

import net.minecraft.client.Minecraft;

public class Timer {
   float a;
   private double f;
   public int b;
   public float c;
   public float d = 1.0F;
   public float e;
   private long g;
   private long h;
   private long i;
   private double j = 1.0;

   public Timer(float var1) {
      this.a = var1;
      this.g = Minecraft.J();
      this.h = System.nanoTime() / 1000000L;
   }

   public void a() {
      long var1 = Minecraft.J();
      long var3 = var1 - this.g;
      long var5 = System.nanoTime() / 1000000L;
      double var7 = var5 / 1000.0;
      if (var3 <= 1000L && var3 >= 0L) {
         this.i += var3;
         if (this.i > 1000L) {
            long var9 = var5 - this.h;
            double var11 = (double)this.i / var9;
            this.j = this.j + (var11 - this.j) * 0.2F;
            this.h = var5;
            this.i = 0L;
         }

         if (this.i < 0L) {
            this.h = var5;
         }
      } else {
         this.f = var7;
      }

      this.g = var1;
      double var13 = (var7 - this.f) * this.j;
      this.f = var7;
      var13 = MathHelper.a(var13, 0.0, 1.0);
      this.e = (float)(this.e + var13 * this.d * this.a);
      this.b = (int)this.e;
      this.e = this.e - this.b;
      if (this.b > 10) {
         this.b = 10;
      }

      this.c = this.e;
   }
}

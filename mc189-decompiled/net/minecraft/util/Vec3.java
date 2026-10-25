package net.minecraft.util;

public class Vec3 {
   public final double a;
   public final double b;
   public final double c;

   public Vec3(double var1, double var3, double var5) {
      if (var1 == -0.0) {
         var1 = 0.0;
      }

      if (var3 == -0.0) {
         var3 = 0.0;
      }

      if (var5 == -0.0) {
         var5 = 0.0;
      }

      this.a = var1;
      this.b = var3;
      this.c = var5;
   }

   public Vec3(Vec3i var1) {
      this(var1.n(), var1.o(), var1.p());
   }

   public Vec3 a(Vec3 var1) {
      return new Vec3(var1.a - this.a, var1.b - this.b, var1.c - this.c);
   }

   public Vec3 a() {
      double var1 = MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c);
      return var1 < 1.0E-4 ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.a / var1, this.b / var1, this.c / var1);
   }

   public double b(Vec3 var1) {
      return this.a * var1.a + this.b * var1.b + this.c * var1.c;
   }

   public Vec3 c(Vec3 var1) {
      return new Vec3(this.b * var1.c - this.c * var1.b, this.c * var1.a - this.a * var1.c, this.a * var1.b - this.b * var1.a);
   }

   public Vec3 d(Vec3 var1) {
      return this.a(var1.a, var1.b, var1.c);
   }

   public Vec3 a(double var1, double var3, double var5) {
      return this.b(-var1, -var3, -var5);
   }

   public Vec3 e(Vec3 var1) {
      return this.b(var1.a, var1.b, var1.c);
   }

   public Vec3 b(double var1, double var3, double var5) {
      return new Vec3(this.a + var1, this.b + var3, this.c + var5);
   }

   public double f(Vec3 var1) {
      double var2 = var1.a - this.a;
      double var4 = var1.b - this.b;
      double var6 = var1.c - this.c;
      return MathHelper.a(var2 * var2 + var4 * var4 + var6 * var6);
   }

   public double g(Vec3 var1) {
      double var2 = var1.a - this.a;
      double var4 = var1.b - this.b;
      double var6 = var1.c - this.c;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public double b() {
      return MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c);
   }

   public Vec3 a(Vec3 var1, double var2) {
      double var4 = var1.a - this.a;
      double var6 = var1.b - this.b;
      double var8 = var1.c - this.c;
      if (var4 * var4 < 1.0E-7F) {
         return null;
      } else {
         double var10 = (var2 - this.a) / var4;
         return !(var10 < 0.0) && !(var10 > 1.0) ? new Vec3(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
      }
   }

   public Vec3 b(Vec3 var1, double var2) {
      double var4 = var1.a - this.a;
      double var6 = var1.b - this.b;
      double var8 = var1.c - this.c;
      if (var6 * var6 < 1.0E-7F) {
         return null;
      } else {
         double var10 = (var2 - this.b) / var6;
         return !(var10 < 0.0) && !(var10 > 1.0) ? new Vec3(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
      }
   }

   public Vec3 c(Vec3 var1, double var2) {
      double var4 = var1.a - this.a;
      double var6 = var1.b - this.b;
      double var8 = var1.c - this.c;
      if (var8 * var8 < 1.0E-7F) {
         return null;
      } else {
         double var10 = (var2 - this.c) / var8;
         return !(var10 < 0.0) && !(var10 > 1.0) ? new Vec3(this.a + var4 * var10, this.b + var6 * var10, this.c + var8 * var10) : null;
      }
   }

   @Override
   public String toString() {
      return "(" + this.a + ", " + this.b + ", " + this.c + ")";
   }

   public Vec3 a(float var1) {
      float var2 = MathHelper.b(var1);
      float var3 = MathHelper.a(var1);
      double var4 = this.a;
      double var6 = this.b * var2 + this.c * var3;
      double var8 = this.c * var2 - this.b * var3;
      return new Vec3(var4, var6, var8);
   }

   public Vec3 b(float var1) {
      float var2 = MathHelper.b(var1);
      float var3 = MathHelper.a(var1);
      double var4 = this.a * var2 + this.c * var3;
      double var6 = this.b;
      double var8 = this.c * var2 - this.a * var3;
      return new Vec3(var4, var6, var8);
   }
}

package net.minecraft.util;

import com.google.common.base.Objects;

public class Vec3i implements Comparable<Vec3i> {
   public static final Vec3i b = new Vec3i(0, 0, 0);
   private final int a;
   private final int c;
   private final int d;

   public Vec3i(int var1, int var2, int var3) {
      this.a = var1;
      this.c = var2;
      this.d = var3;
   }

   public Vec3i(double var1, double var3, double var5) {
      this(MathHelper.c(var1), MathHelper.c(var3), MathHelper.c(var5));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Vec3i)) {
         return false;
      } else {
         Vec3i var2 = (Vec3i)var1;
         if (this.n() != var2.n()) {
            return false;
         } else {
            return this.o() != var2.o() ? false : this.p() == var2.p();
         }
      }
   }

   @Override
   public int hashCode() {
      return (this.o() + this.p() * 31) * 31 + this.n();
   }

   public int g(Vec3i var1) {
      if (this.o() == var1.o()) {
         return this.p() == var1.p() ? this.n() - var1.n() : this.p() - var1.p();
      } else {
         return this.o() - var1.o();
      }
   }

   public int n() {
      return this.a;
   }

   public int o() {
      return this.c;
   }

   public int p() {
      return this.d;
   }

   public Vec3i d(Vec3i var1) {
      return new Vec3i(this.o() * var1.p() - this.p() * var1.o(), this.p() * var1.n() - this.n() * var1.p(), this.n() * var1.o() - this.o() * var1.n());
   }

   public double c(double var1, double var3, double var5) {
      double var7 = this.n() - var1;
      double var9 = this.o() - var3;
      double var11 = this.p() - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double d(double var1, double var3, double var5) {
      double var7 = this.n() + 0.5 - var1;
      double var9 = this.o() + 0.5 - var3;
      double var11 = this.p() + 0.5 - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double i(Vec3i var1) {
      return this.c(var1.n(), var1.o(), var1.p());
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this).add("x", this.n()).add("y", this.o()).add("z", this.p()).toString();
   }
}

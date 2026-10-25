package net.minecraft.pathfinding;

import net.minecraft.util.MathHelper;

public class PathPoint {
   public final int a;
   public final int b;
   public final int c;
   private final int j;
   int d = -1;
   float e;
   float f;
   float g;
   PathPoint h;
   public boolean i;

   public PathPoint(int var1, int var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.j = a(var1, var2, var3);
   }

   public static int a(int var0, int var1, int var2) {
      return var1 & 0xFF | (var0 & 32767) << 8 | (var2 & 32767) << 24 | (var0 < 0 ? Integer.MIN_VALUE : 0) | (var2 < 0 ? 32768 : 0);
   }

   public float a(PathPoint var1) {
      float var2 = var1.a - this.a;
      float var3 = var1.b - this.b;
      float var4 = var1.c - this.c;
      return MathHelper.c(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public float b(PathPoint var1) {
      float var2 = var1.a - this.a;
      float var3 = var1.b - this.b;
      float var4 = var1.c - this.c;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(var1 instanceof PathPoint)) {
         return false;
      } else {
         PathPoint var2 = (PathPoint)var1;
         return this.j == var2.j && this.a == var2.a && this.b == var2.b && this.c == var2.c;
      }
   }

   @Override
   public int hashCode() {
      return this.j;
   }

   public boolean a() {
      return this.d >= 0;
   }

   @Override
   public String toString() {
      return this.a + ", " + this.b + ", " + this.c;
   }
}

package net.minecraft.util;

public class Vec4b {
   private byte a;
   private byte b;
   private byte c;
   private byte d;

   public Vec4b(byte var1, byte var2, byte var3, byte var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public Vec4b(Vec4b var1) {
      this.a = var1.a;
      this.b = var1.b;
      this.c = var1.c;
      this.d = var1.d;
   }

   public byte a() {
      return this.a;
   }

   public byte b() {
      return this.b;
   }

   public byte c() {
      return this.c;
   }

   public byte d() {
      return this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Vec4b)) {
         return false;
      } else {
         Vec4b var2 = (Vec4b)var1;
         if (this.a != var2.a) {
            return false;
         } else if (this.d != var2.d) {
            return false;
         } else {
            return this.b != var2.b ? false : this.c == var2.c;
         }
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.a;
      var1 = 31 * var1 + this.b;
      var1 = 31 * var1 + this.c;
      return 31 * var1 + this.d;
   }
}

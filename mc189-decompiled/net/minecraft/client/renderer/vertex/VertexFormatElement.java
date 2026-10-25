package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
   private static final Logger a = LogManager.getLogger();
   private final VertexFormatElement.EnumType b;
   private final VertexFormatElement.EnumUsage c;
   private int d;
   private int e;

   public VertexFormatElement(int var1, VertexFormatElement.EnumType var2, VertexFormatElement.EnumUsage var3, int var4) {
      if (!this.a(var1, var3)) {
         a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
         this.c = VertexFormatElement.EnumUsage.d;
      } else {
         this.c = var3;
      }

      this.b = var2;
      this.d = var1;
      this.e = var4;
   }

   private final boolean a(int var1, VertexFormatElement.EnumUsage var2) {
      return var1 == 0 || var2 == VertexFormatElement.EnumUsage.d;
   }

   public final VertexFormatElement.EnumType a() {
      return this.b;
   }

   public final VertexFormatElement.EnumUsage b() {
      return this.c;
   }

   public final int c() {
      return this.e;
   }

   public final int d() {
      return this.d;
   }

   @Override
   public String toString() {
      return this.e + "," + this.c.a() + "," + this.b.b();
   }

   public final int e() {
      return this.b.a() * this.e;
   }

   public final boolean f() {
      return this.c == VertexFormatElement.EnumUsage.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         VertexFormatElement var2 = (VertexFormatElement)var1;
         if (this.e != var2.e) {
            return false;
         } else if (this.d != var2.d) {
            return false;
         } else {
            return this.b != var2.b ? false : this.c == var2.c;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.b.hashCode();
      var1 = 31 * var1 + this.c.hashCode();
      var1 = 31 * var1 + this.d;
      return 31 * var1 + this.e;
   }

   public static enum EnumType {
      a(4, "Float", 5126),
      b(1, "Unsigned Byte", 5121),
      c(1, "Byte", 5120),
      d(2, "Unsigned Short", 5123),
      e(2, "Short", 5122),
      f(4, "Unsigned Int", 5125),
      g(4, "Int", 5124);

      private final int h;
      private final String i;
      private final int j;

      private EnumType(int var3, String var4, int var5) {
         this.h = var3;
         this.i = var4;
         this.j = var5;
      }

      public int a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public int c() {
         return this.j;
      }
   }

   public static enum EnumUsage {
      a("Position"),
      b("Normal"),
      c("Vertex Color"),
      d("UV"),
      e("Bone Matrix"),
      f("Blend Weight"),
      g("Padding");

      private final String h;

      private EnumUsage(String var3) {
         this.h = var3;
      }

      public String a() {
         return this.h;
      }
   }
}

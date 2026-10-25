package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public enum EnumFacing implements IStringSerializable {
   a(0, 1, -1, "down", EnumFacing.AxisDirection.b, EnumFacing.Axis.b, new Vec3i(0, -1, 0)),
   b(1, 0, -1, "up", EnumFacing.AxisDirection.a, EnumFacing.Axis.b, new Vec3i(0, 1, 0)),
   c(2, 3, 2, "north", EnumFacing.AxisDirection.b, EnumFacing.Axis.c, new Vec3i(0, 0, -1)),
   d(3, 2, 0, "south", EnumFacing.AxisDirection.a, EnumFacing.Axis.c, new Vec3i(0, 0, 1)),
   e(4, 5, 1, "west", EnumFacing.AxisDirection.b, EnumFacing.Axis.a, new Vec3i(-1, 0, 0)),
   f(5, 4, 3, "east", EnumFacing.AxisDirection.a, EnumFacing.Axis.a, new Vec3i(1, 0, 0));

   private final int g;
   private final int h;
   private final int i;
   private final String j;
   private final EnumFacing.Axis k;
   private final EnumFacing.AxisDirection l;
   private final Vec3i m;
   private static final EnumFacing[] n = new EnumFacing[6];
   private static final EnumFacing[] o = new EnumFacing[4];
   private static final Map<String, EnumFacing> p = Maps.newHashMap();

   private EnumFacing(int var3, int var4, int var5, String var6, EnumFacing.AxisDirection var7, EnumFacing.Axis var8, Vec3i var9) {
      this.g = var3;
      this.i = var5;
      this.h = var4;
      this.j = var6;
      this.k = var8;
      this.l = var7;
      this.m = var9;
   }

   public int a() {
      return this.g;
   }

   public int b() {
      return this.i;
   }

   public EnumFacing.AxisDirection c() {
      return this.l;
   }

   public EnumFacing d() {
      return a(this.h);
   }

   public EnumFacing a(EnumFacing.Axis var1) {
      switch (var1) {
         case a:
            if (this != e && this != f) {
               return this.n();
            }

            return this;
         case b:
            if (this != b && this != a) {
               return this.e();
            }

            return this;
         case c:
            if (this != c && this != d) {
               return this.p();
            }

            return this;
         default:
            throw new IllegalStateException("Unable to get CW facing for axis " + var1);
      }
   }

   public EnumFacing e() {
      switch (this) {
         case c:
            return f;
         case f:
            return d;
         case d:
            return e;
         case e:
            return c;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   private EnumFacing n() {
      switch (this) {
         case c:
            return a;
         case f:
         case e:
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + this);
         case d:
            return b;
         case b:
            return c;
         case a:
            return d;
      }
   }

   private EnumFacing p() {
      switch (this) {
         case f:
            return a;
         case d:
         default:
            throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case e:
            return b;
         case b:
            return f;
         case a:
            return e;
      }
   }

   public EnumFacing f() {
      switch (this) {
         case c:
            return e;
         case f:
            return c;
         case d:
            return f;
         case e:
            return d;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int g() {
      return this.k == EnumFacing.Axis.a ? this.l.a() : 0;
   }

   public int h() {
      return this.k == EnumFacing.Axis.b ? this.l.a() : 0;
   }

   public int i() {
      return this.k == EnumFacing.Axis.c ? this.l.a() : 0;
   }

   public String j() {
      return this.j;
   }

   public EnumFacing.Axis k() {
      return this.k;
   }

   public static EnumFacing a(String var0) {
      return var0 == null ? null : p.get(var0.toLowerCase());
   }

   public static EnumFacing a(int var0) {
      return n[MathHelper.a(var0 % n.length)];
   }

   public static EnumFacing b(int var0) {
      return o[MathHelper.a(var0 % o.length)];
   }

   public static EnumFacing a(double var0) {
      return b(MathHelper.c(var0 / 90.0 + 0.5) & 3);
   }

   public static EnumFacing a(Random var0) {
      return values()[var0.nextInt(values().length)];
   }

   public static EnumFacing a(float var0, float var1, float var2) {
      EnumFacing var3 = c;
      float var4 = Float.MIN_VALUE;

      for (EnumFacing var8 : values()) {
         float var9 = var0 * var8.m.n() + var1 * var8.m.o() + var2 * var8.m.p();
         if (var9 > var4) {
            var4 = var9;
            var3 = var8;
         }
      }

      return var3;
   }

   @Override
   public String toString() {
      return this.j;
   }

   @Override
   public String l() {
      return this.j;
   }

   public static EnumFacing a(EnumFacing.AxisDirection var0, EnumFacing.Axis var1) {
      for (EnumFacing var5 : values()) {
         if (var5.c() == var0 && var5.k() == var1) {
            return var5;
         }
      }

      throw new IllegalArgumentException("No such direction: " + var0 + " " + var1);
   }

   public Vec3i m() {
      return this.m;
   }

   static {
      for (EnumFacing var3 : values()) {
         n[var3.g] = var3;
         if (var3.k().c()) {
            o[var3.i] = var3;
         }

         p.put(var3.j().toLowerCase(), var3);
      }
   }

   public static enum Axis implements Predicate<EnumFacing>, IStringSerializable {
      a("x", EnumFacing.Plane.a),
      b("y", EnumFacing.Plane.b),
      c("z", EnumFacing.Plane.a);

      private static final Map<String, EnumFacing.Axis> d = Maps.newHashMap();
      private final String e;
      private final EnumFacing.Plane f;

      private Axis(String var3, EnumFacing.Plane var4) {
         this.e = var3;
         this.f = var4;
      }

      public static EnumFacing.Axis a(String var0) {
         return var0 == null ? null : d.get(var0.toLowerCase());
      }

      public String a() {
         return this.e;
      }

      public boolean b() {
         return this.f == EnumFacing.Plane.b;
      }

      public boolean c() {
         return this.f == EnumFacing.Plane.a;
      }

      @Override
      public String toString() {
         return this.e;
      }

      public boolean a(EnumFacing var1) {
         return var1 != null && var1.k() == this;
      }

      public EnumFacing.Plane d() {
         return this.f;
      }

      @Override
      public String l() {
         return this.e;
      }

      static {
         for (EnumFacing.Axis var3 : values()) {
            d.put(var3.a().toLowerCase(), var3);
         }
      }
   }

   public static enum AxisDirection {
      a(1, "Towards positive"),
      b(-1, "Towards negative");

      private final int c;
      private final String d;

      private AxisDirection(int var3, String var4) {
         this.c = var3;
         this.d = var4;
      }

      public int a() {
         return this.c;
      }

      @Override
      public String toString() {
         return this.d;
      }
   }

   public static enum Plane implements Predicate<EnumFacing>, Iterable<EnumFacing> {
      a,
      b;

      public EnumFacing[] a() {
         switch (this) {
            case a:
               return new EnumFacing[]{EnumFacing.c, EnumFacing.f, EnumFacing.d, EnumFacing.e};
            case b:
               return new EnumFacing[]{EnumFacing.b, EnumFacing.a};
            default:
               throw new Error("Someone's been tampering with the universe!");
         }
      }

      public EnumFacing a(Random var1) {
         EnumFacing[] var2 = this.a();
         return var2[var1.nextInt(var2.length)];
      }

      public boolean a(EnumFacing var1) {
         return var1 != null && var1.k().d() == this;
      }

      @Override
      public Iterator<EnumFacing> iterator() {
         return Iterators.forArray(this.a());
      }
   }
}

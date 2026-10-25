package net.minecraft.util;

public class IntHashMap<V> {
   private transient IntHashMap.Entry<V>[] a;
   private transient int b;
   private int c;
   private final float d = 0.75F;

   public IntHashMap() {
      this.c = 12;
      this.a = new IntHashMap.Entry[16];
   }

   private static int g(int var0) {
      var0 ^= var0 >>> 20 ^ var0 >>> 12;
      return var0 ^ var0 >>> 7 ^ var0 >>> 4;
   }

   private static int a(int var0, int var1) {
      return var0 & var1 - 1;
   }

   public V a(int var1) {
      int var2 = g(var1);

      for (IntHashMap.Entry var3 = this.a[a(var2, this.a.length)]; var3 != null; var3 = var3.c) {
         if (var3.a == var1) {
            return var3.b;
         }
      }

      return null;
   }

   public boolean b(int var1) {
      return this.c(var1) != null;
   }

   final IntHashMap.Entry<V> c(int var1) {
      int var2 = g(var1);

      for (IntHashMap.Entry var3 = this.a[a(var2, this.a.length)]; var3 != null; var3 = var3.c) {
         if (var3.a == var1) {
            return var3;
         }
      }

      return null;
   }

   public void a(int var1, V var2) {
      int var3 = g(var1);
      int var4 = a(var3, this.a.length);

      for (IntHashMap.Entry var5 = this.a[var4]; var5 != null; var5 = var5.c) {
         if (var5.a == var1) {
            var5.b = (V)var2;
            return;
         }
      }

      this.a(var3, var1, (V)var2, var4);
   }

   private void h(int var1) {
      IntHashMap.Entry[] var2 = this.a;
      int var3 = var2.length;
      if (var3 == 1073741824) {
         this.c = Integer.MAX_VALUE;
      } else {
         IntHashMap.Entry[] var4 = new IntHashMap.Entry[var1];
         this.a(var4);
         this.a = var4;
         this.c = (int)(var1 * this.d);
      }
   }

   private void a(IntHashMap.Entry<V>[] var1) {
      IntHashMap.Entry[] var2 = this.a;
      int var3 = var1.length;

      for (int var4 = 0; var4 < var2.length; var4++) {
         IntHashMap.Entry var5 = var2[var4];
         if (var5 != null) {
            var2[var4] = null;

            while (true) {
               IntHashMap.Entry var6 = var5.c;
               int var7 = a(var5.d, var3);
               var5.c = var1[var7];
               var1[var7] = var5;
               var5 = var6;
               if (var6 == null) {
                  break;
               }
            }
         }
      }
   }

   public V d(int var1) {
      IntHashMap.Entry var2 = this.e(var1);
      return var2 == null ? null : var2.b;
   }

   final IntHashMap.Entry<V> e(int var1) {
      int var2 = g(var1);
      int var3 = a(var2, this.a.length);
      IntHashMap.Entry var4 = this.a[var3];
      IntHashMap.Entry var5 = var4;

      while (var5 != null) {
         IntHashMap.Entry var6 = var5.c;
         if (var5.a == var1) {
            this.b--;
            if (var4 == var5) {
               this.a[var3] = var6;
            } else {
               var4.c = var6;
            }

            return var5;
         }

         var4 = var5;
         var5 = var6;
      }

      return var5;
   }

   public void c() {
      IntHashMap.Entry[] var1 = this.a;

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = null;
      }

      this.b = 0;
   }

   private void a(int var1, int var2, V var3, int var4) {
      IntHashMap.Entry var5 = this.a[var4];
      this.a[var4] = new IntHashMap.Entry<>(var1, var2, (V)var3, var5);
      if (this.b++ >= this.c) {
         this.h(2 * this.a.length);
      }
   }

   static class Entry<V> {
      final int a;
      V b;
      IntHashMap.Entry<V> c;
      final int d;

      Entry(int var1, int var2, V var3, IntHashMap.Entry<V> var4) {
         this.b = (V)var3;
         this.c = var4;
         this.a = var2;
         this.d = var1;
      }

      public final int a() {
         return this.a;
      }

      public final V b() {
         return this.b;
      }

      @Override
      public final boolean equals(Object var1) {
         if (!(var1 instanceof IntHashMap.Entry)) {
            return false;
         } else {
            IntHashMap.Entry var2 = (IntHashMap.Entry)var1;
            Integer var3 = this.a();
            Integer var4 = var2.a();
            if (var3 == var4 || var3 != null && var3.equals(var4)) {
               Object var5 = this.b();
               Object var6 = var2.b();
               if (var5 == var6 || var5 != null && var5.equals(var6)) {
                  return true;
               }
            }

            return false;
         }
      }

      @Override
      public final int hashCode() {
         return IntHashMap.g(this.a);
      }

      @Override
      public final String toString() {
         return this.a() + "=" + this.b();
      }
   }
}

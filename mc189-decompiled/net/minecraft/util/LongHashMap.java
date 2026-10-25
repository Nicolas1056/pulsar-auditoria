package net.minecraft.util;

public class LongHashMap<V> {
   private transient LongHashMap.Entry<V>[] a;
   private transient int b;
   private int c;
   private int d;
   private final float e = 0.75F;
   private transient volatile int f;

   public LongHashMap() {
      this.d = 3072;
      this.a = new LongHashMap.Entry[4096];
      this.c = this.a.length - 1;
   }

   private static int g(long var0) {
      return a((int)(var0 ^ var0 >>> 32));
   }

   private static int a(int var0) {
      var0 ^= var0 >>> 20 ^ var0 >>> 12;
      return var0 ^ var0 >>> 7 ^ var0 >>> 4;
   }

   private static int a(int var0, int var1) {
      return var0 & var1;
   }

   public int a() {
      return this.b;
   }

   public V a(long var1) {
      int var3 = g(var1);

      for (LongHashMap.Entry var4 = this.a[a(var3, this.c)]; var4 != null; var4 = var4.c) {
         if (var4.a == var1) {
            return var4.b;
         }
      }

      return null;
   }

   public boolean b(long var1) {
      return this.c(var1) != null;
   }

   final LongHashMap.Entry<V> c(long var1) {
      int var3 = g(var1);

      for (LongHashMap.Entry var4 = this.a[a(var3, this.c)]; var4 != null; var4 = var4.c) {
         if (var4.a == var1) {
            return var4;
         }
      }

      return null;
   }

   public void a(long var1, V var3) {
      int var4 = g(var1);
      int var5 = a(var4, this.c);

      for (LongHashMap.Entry var6 = this.a[var5]; var6 != null; var6 = var6.c) {
         if (var6.a == var1) {
            var6.b = (V)var3;
            return;
         }
      }

      this.f++;
      this.a(var4, var1, (V)var3, var5);
   }

   private void b(int var1) {
      LongHashMap.Entry[] var2 = this.a;
      int var3 = var2.length;
      if (var3 == 1073741824) {
         this.d = Integer.MAX_VALUE;
      } else {
         LongHashMap.Entry[] var4 = new LongHashMap.Entry[var1];
         this.a(var4);
         this.a = var4;
         this.c = this.a.length - 1;
         this.d = (int)(var1 * this.e);
      }
   }

   private void a(LongHashMap.Entry<V>[] var1) {
      LongHashMap.Entry[] var2 = this.a;
      int var3 = var1.length;

      for (int var4 = 0; var4 < var2.length; var4++) {
         LongHashMap.Entry var5 = var2[var4];
         if (var5 != null) {
            var2[var4] = null;

            while (true) {
               LongHashMap.Entry var6 = var5.c;
               int var7 = a(var5.d, var3 - 1);
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

   public V d(long var1) {
      LongHashMap.Entry var3 = this.e(var1);
      return var3 == null ? null : var3.b;
   }

   final LongHashMap.Entry<V> e(long var1) {
      int var3 = g(var1);
      int var4 = a(var3, this.c);
      LongHashMap.Entry var5 = this.a[var4];
      LongHashMap.Entry var6 = var5;

      while (var6 != null) {
         LongHashMap.Entry var7 = var6.c;
         if (var6.a == var1) {
            this.f++;
            this.b--;
            if (var5 == var6) {
               this.a[var4] = var7;
            } else {
               var5.c = var7;
            }

            return var6;
         }

         var5 = var6;
         var6 = var7;
      }

      return var6;
   }

   private void a(int var1, long var2, V var4, int var5) {
      LongHashMap.Entry var6 = this.a[var5];
      this.a[var5] = new LongHashMap.Entry<>(var1, var2, (V)var4, var6);
      if (this.b++ >= this.d) {
         this.b(2 * this.a.length);
      }
   }

   static class Entry<V> {
      final long a;
      V b;
      LongHashMap.Entry<V> c;
      final int d;

      Entry(int var1, long var2, V var4, LongHashMap.Entry<V> var5) {
         this.b = (V)var4;
         this.c = var5;
         this.a = var2;
         this.d = var1;
      }

      public final long a() {
         return this.a;
      }

      public final V b() {
         return this.b;
      }

      @Override
      public final boolean equals(Object var1) {
         if (!(var1 instanceof LongHashMap.Entry)) {
            return false;
         } else {
            LongHashMap.Entry var2 = (LongHashMap.Entry)var1;
            Long var3 = this.a();
            Long var4 = var2.a();
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
         return LongHashMap.g(this.a);
      }

      @Override
      public final String toString() {
         return this.a() + "=" + this.b();
      }
   }
}

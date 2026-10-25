package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Cartesian {
   public static <T> Iterable<T[]> a(Class<T> var0, Iterable<? extends Iterable<? extends T>> var1) {
      return new Cartesian.Product<>(var0, b(Iterable.class, var1));
   }

   public static <T> Iterable<List<T>> a(Iterable<? extends Iterable<? extends T>> var0) {
      return b(a(Object.class, var0));
   }

   private static <T> Iterable<List<T>> b(Iterable<Object[]> var0) {
      return Iterables.transform(var0, new Cartesian.GetList());
   }

   private static <T> T[] b(Class<? super T> var0, Iterable<? extends T> var1) {
      ArrayList var2 = Lists.newArrayList();

      for (Object var4 : var1) {
         var2.add(var4);
      }

      return (T[])var2.toArray(b(var0, var2.size()));
   }

   private static <T> T[] b(Class<? super T> var0, int var1) {
      return (T[])((Object[])Array.newInstance(var0, var1));
   }

   static class GetList<T> implements Function<Object[], List<T>> {
      private GetList() {
      }

      public List<T> a(Object[] var1) {
         return Arrays.asList((T[])var1);
      }
   }

   static class Product<T> implements Iterable<T[]> {
      private final Class<T> a;
      private final Iterable<? extends T>[] b;

      private Product(Class<T> var1, Iterable<? extends T>[] var2) {
         this.a = var1;
         this.b = var2;
      }

      @Override
      public Iterator<T[]> iterator() {
         return (Iterator<T[]>)(this.b.length <= 0
            ? Collections.singletonList(Cartesian.b(this.a, 0)).iterator()
            : new Cartesian.Product.ProductIterator(this.a, this.b));
      }

      static class ProductIterator<T> extends UnmodifiableIterator<T[]> {
         private int a = -2;
         private final Iterable<? extends T>[] b;
         private final Iterator<? extends T>[] c;
         private final T[] d;

         private ProductIterator(Class<T> var1, Iterable<? extends T>[] var2) {
            this.b = var2;
            this.c = Cartesian.b(Iterator.class, this.b.length);

            for (int var3 = 0; var3 < this.b.length; var3++) {
               this.c[var3] = var2[var3].iterator();
            }

            this.d = (T[])Cartesian.b(var1, this.c.length);
         }

         private void b() {
            this.a = -1;
            Arrays.fill(this.c, null);
            Arrays.fill(this.d, null);
         }

         public boolean hasNext() {
            if (this.a == -2) {
               this.a = 0;

               for (Iterator var4 : this.c) {
                  if (!var4.hasNext()) {
                     this.b();
                     break;
                  }
               }

               return true;
            } else {
               if (this.a >= this.c.length) {
                  for (this.a = this.c.length - 1; this.a >= 0; this.a--) {
                     Iterator var1 = this.c[this.a];
                     if (var1.hasNext()) {
                        break;
                     }

                     if (this.a == 0) {
                        this.b();
                        break;
                     }

                     var1 = this.b[this.a].iterator();
                     this.c[this.a] = var1;
                     if (!var1.hasNext()) {
                        this.b();
                        break;
                     }
                  }
               }

               return this.a >= 0;
            }
         }

         public T[] a() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               while (this.a < this.c.length) {
                  this.d[this.a] = (T)this.c[this.a].next();
                  this.a++;
               }

               return (T[])((Object[])this.d.clone());
            }
         }
      }
   }
}

package net.minecraft.util;

import java.lang.reflect.Array;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeBoundList<T> {
   private final T[] a;
   private final Class<? extends T> b;
   private final ReadWriteLock c = new ReentrantReadWriteLock();
   private int d;
   private int e;

   public ThreadSafeBoundList(Class<? extends T> var1, int var2) {
      this.b = var1;
      this.a = (T[])((Object[])Array.newInstance(var1, var2));
   }

   public T a(T var1) {
      this.c.writeLock().lock();
      this.a[this.e] = (T)var1;
      this.e = (this.e + 1) % this.b();
      if (this.d < this.b()) {
         this.d++;
      }

      this.c.writeLock().unlock();
      return (T)var1;
   }

   public int b() {
      this.c.readLock().lock();
      int var1 = this.a.length;
      this.c.readLock().unlock();
      return var1;
   }

   public T[] c() {
      Object[] var1 = (Object[])Array.newInstance(this.b, this.d);
      this.c.readLock().lock();

      for (int var2 = 0; var2 < this.d; var2++) {
         int var3 = (this.e - this.d + var2) % this.b();
         if (var3 < 0) {
            var3 += this.b();
         }

         var1[var2] = this.a[var3];
      }

      this.c.readLock().unlock();
      return (T[])var1;
   }
}

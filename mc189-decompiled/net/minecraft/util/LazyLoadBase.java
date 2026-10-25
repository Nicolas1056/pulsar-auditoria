package net.minecraft.util;

public abstract class LazyLoadBase<T> {
   private T a;
   private boolean b = false;

   public T c() {
      if (!this.b) {
         this.b = true;
         this.a = this.b();
      }

      return this.a;
   }

   protected abstract T b();
}

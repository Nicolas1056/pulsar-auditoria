package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V> {
   private final K d;
   private V e;

   public RegistryNamespacedDefaultedByKey(K var1) {
      this.d = (K)var1;
   }

   @Override
   public void a(int var1, K var2, V var3) {
      if (this.d.equals(var2)) {
         this.e = (V)var3;
      }

      super.a(var1, (K)var2, (V)var3);
   }

   public void a() {
      Validate.notNull(this.d);
   }

   @Override
   public V a(K var1) {
      Object var2 = super.a((K)var1);
      return (V)(var2 == null ? this.e : var2);
   }

   @Override
   public V a(int var1) {
      Object var2 = super.a(var1);
      return (V)(var2 == null ? this.e : var2);
   }
}

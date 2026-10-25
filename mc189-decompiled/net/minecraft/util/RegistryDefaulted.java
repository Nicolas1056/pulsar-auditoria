package net.minecraft.util;

public class RegistryDefaulted<K, V> extends RegistrySimple<K, V> {
   private final V a;

   public RegistryDefaulted(V var1) {
      this.a = (V)var1;
   }

   @Override
   public V a(K var1) {
      Object var2 = super.a((K)var1);
      return (V)(var2 == null ? this.a : var2);
   }
}

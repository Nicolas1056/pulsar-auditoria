package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V> {
   V a(K var1);

   void a(K var1, V var2);
}

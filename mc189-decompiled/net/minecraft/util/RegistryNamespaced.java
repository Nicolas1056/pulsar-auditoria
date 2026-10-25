package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;

public class RegistryNamespaced<K, V> extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
   protected final ObjectIntIdentityMap<V> a = new ObjectIntIdentityMap<>();
   protected final Map<V, K> b = ((BiMap)this.c).inverse();

   public void a(int var1, K var2, V var3) {
      this.a.a((V)var3, var1);
      this.a((K)var2, (V)var3);
   }

   @Override
   protected Map<K, V> b() {
      return HashBiMap.create();
   }

   @Override
   public V a(K var1) {
      return super.a((K)var1);
   }

   public K c(V var1) {
      return this.b.get(var1);
   }

   @Override
   public boolean d(K var1) {
      return super.d((K)var1);
   }

   public int b(V var1) {
      return this.a.b((V)var1);
   }

   public V a(int var1) {
      return this.a.a(var1);
   }

   @Override
   public Iterator<V> iterator() {
      return this.a.iterator();
   }
}

package net.minecraft.server.management;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class LowerStringMap<V> implements Map<String, V> {
   private final Map<String, V> a = Maps.newLinkedHashMap();

   @Override
   public int size() {
      return this.a.size();
   }

   @Override
   public boolean isEmpty() {
      return this.a.isEmpty();
   }

   @Override
   public boolean containsKey(Object var1) {
      return this.a.containsKey(var1.toString().toLowerCase());
   }

   @Override
   public boolean containsValue(Object var1) {
      return this.a.containsKey(var1);
   }

   @Override
   public V get(Object var1) {
      return this.a.get(var1.toString().toLowerCase());
   }

   public V a(String var1, V var2) {
      return this.a.put(var1.toLowerCase(), (V)var2);
   }

   @Override
   public V remove(Object var1) {
      return this.a.remove(var1.toString().toLowerCase());
   }

   @Override
   public void putAll(Map<? extends String, ? extends V> var1) {
      for (Entry var3 : var1.entrySet()) {
         this.a((String)var3.getKey(), (V)var3.getValue());
      }
   }

   @Override
   public void clear() {
      this.a.clear();
   }

   @Override
   public Set<String> keySet() {
      return this.a.keySet();
   }

   @Override
   public Collection<V> values() {
      return this.a.values();
   }

   @Override
   public Set<Entry<String, V>> entrySet() {
      return this.a.entrySet();
   }
}

package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassInheritanceMultiMap<T> extends AbstractSet<T> {
   private static final Set<Class<?>> a = Sets.newHashSet();
   private final Map<Class<?>, List<T>> b = Maps.newHashMap();
   private final Set<Class<?>> c = Sets.newIdentityHashSet();
   private final Class<T> d;
   private final List<T> e = Lists.newArrayList();

   public ClassInheritanceMultiMap(Class<T> var1) {
      this.d = var1;
      this.c.add(var1);
      this.b.put(var1, this.e);

      for (Class var3 : a) {
         this.a(var3);
      }
   }

   protected void a(Class<?> var1) {
      a.add(var1);

      for (Object var3 : this.e) {
         if (var1.isAssignableFrom(var3.getClass())) {
            this.a((T)var3, var1);
         }
      }

      this.c.add(var1);
   }

   protected Class<?> b(Class<?> var1) {
      if (this.d.isAssignableFrom(var1)) {
         if (!this.c.contains(var1)) {
            this.a(var1);
         }

         return var1;
      } else {
         throw new IllegalArgumentException("Don't know how to search for " + var1);
      }
   }

   @Override
   public boolean add(T var1) {
      for (Class var3 : this.c) {
         if (var3.isAssignableFrom(var1.getClass())) {
            this.a((T)var1, var3);
         }
      }

      return true;
   }

   private void a(T var1, Class<?> var2) {
      List var3 = this.b.get(var2);
      if (var3 == null) {
         this.b.put(var2, Lists.newArrayList(new Object[]{var1}));
      } else {
         var3.add(var1);
      }
   }

   @Override
   public boolean remove(Object var1) {
      Object var2 = var1;
      boolean var3 = false;

      for (Class var5 : this.c) {
         if (var5.isAssignableFrom(var2.getClass())) {
            List var6 = this.b.get(var5);
            if (var6 != null && var6.remove(var2)) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   @Override
   public boolean contains(Object var1) {
      return Iterators.contains(this.c(var1.getClass()).iterator(), var1);
   }

   public <S> Iterable<S> c(final Class<S> var1) {
      return new Iterable<S>() {
         @Override
         public Iterator<S> iterator() {
            List var1x = ClassInheritanceMultiMap.this.b.get(ClassInheritanceMultiMap.this.b(var1));
            if (var1x == null) {
               return Iterators.emptyIterator();
            } else {
               Iterator var2 = var1x.iterator();
               return Iterators.filter(var2, var1);
            }
         }
      };
   }

   @Override
   public Iterator<T> iterator() {
      return this.e.isEmpty() ? Iterators.emptyIterator() : Iterators.unmodifiableIterator(this.e.iterator());
   }

   @Override
   public int size() {
      return this.e.size();
   }
}

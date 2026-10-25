package net.minecraft.block.properties;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import net.minecraft.util.IStringSerializable;

public class PropertyEnum<T extends Enum<T> & IStringSerializable> extends PropertyHelper<T> {
   private final ImmutableSet<T> a;
   private final Map<String, T> b = Maps.newHashMap();

   protected PropertyEnum(String var1, Class<T> var2, Collection<T> var3) {
      super(var1, var2);
      this.a = ImmutableSet.copyOf(var3);

      for (Enum var5 : var3) {
         String var6 = ((IStringSerializable)var5).l();
         if (this.b.containsKey(var6)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + var6 + "'");
         }

         this.b.put(var6, (T)var5);
      }
   }

   @Override
   public Collection<T> c() {
      return this.a;
   }

   public String a(T var1) {
      return ((IStringSerializable)var1).l();
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> a(String var0, Class<T> var1) {
      return a(var0, var1, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> a(String var0, Class<T> var1, Predicate<T> var2) {
      return a(var0, var1, Collections2.filter(Lists.newArrayList(var1.getEnumConstants()), var2));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> a(String var0, Class<T> var1, T... var2) {
      return a(var0, var1, Lists.newArrayList(var2));
   }

   public static <T extends Enum<T> & IStringSerializable> PropertyEnum<T> a(String var0, Class<T> var1, Collection<T> var2) {
      return new PropertyEnum<>(var0, var1, var2);
   }
}

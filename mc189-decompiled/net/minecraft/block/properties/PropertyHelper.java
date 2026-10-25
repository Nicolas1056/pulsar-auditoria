package net.minecraft.block.properties;

import com.google.common.base.Objects;

public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
   private final Class<T> a;
   private final String b;

   protected PropertyHelper(String var1, Class<T> var2) {
      this.a = var2;
      this.b = var1;
   }

   @Override
   public String a() {
      return this.b;
   }

   @Override
   public Class<T> b() {
      return this.a;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.c()).toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         PropertyHelper var2 = (PropertyHelper)var1;
         return this.a.equals(var2.a) && this.b.equals(var2.b);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.a.hashCode() + this.b.hashCode();
   }
}

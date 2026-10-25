package net.minecraft.block.properties;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;

public class PropertyInteger extends PropertyHelper<Integer> {
   private final ImmutableSet<Integer> a;

   protected PropertyInteger(String var1, int var2, int var3) {
      super(var1, Integer.class);
      if (var2 < 0) {
         throw new IllegalArgumentException("Min value of " + var1 + " must be 0 or greater");
      } else if (var3 <= var2) {
         throw new IllegalArgumentException("Max value of " + var1 + " must be greater than min (" + var2 + ")");
      } else {
         HashSet var4 = Sets.newHashSet();

         for (int var5 = var2; var5 <= var3; var5++) {
            var4.add(var5);
         }

         this.a = ImmutableSet.copyOf(var4);
      }
   }

   @Override
   public Collection<Integer> c() {
      return this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null || this.getClass() != var1.getClass()) {
         return false;
      } else if (!super.equals(var1)) {
         return false;
      } else {
         PropertyInteger var2 = (PropertyInteger)var1;
         return this.a.equals(var2.a);
      }
   }

   @Override
   public int hashCode() {
      int var1 = super.hashCode();
      return 31 * var1 + this.a.hashCode();
   }

   public static PropertyInteger a(String var0, int var1, int var2) {
      return new PropertyInteger(var0, var1, var2);
   }

   public String a(Integer var1) {
      return var1.toString();
   }
}

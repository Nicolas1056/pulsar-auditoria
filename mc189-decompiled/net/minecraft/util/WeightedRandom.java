package net.minecraft.util;

import java.util.Collection;
import java.util.Random;

public class WeightedRandom {
   public static int a(Collection<? extends WeightedRandom.Item> var0) {
      int var1 = 0;

      for (WeightedRandom.Item var3 : var0) {
         var1 += var3.a;
      }

      return var1;
   }

   public static <T extends WeightedRandom.Item> T a(Random var0, Collection<T> var1, int var2) {
      if (var2 <= 0) {
         throw new IllegalArgumentException();
      } else {
         int var3 = var0.nextInt(var2);
         return a(var1, var3);
      }
   }

   public static <T extends WeightedRandom.Item> T a(Collection<T> var0, int var1) {
      for (WeightedRandom.Item var3 : var0) {
         var1 -= var3.a;
         if (var1 < 0) {
            return (T)var3;
         }
      }

      return null;
   }

   public static <T extends WeightedRandom.Item> T a(Random var0, Collection<T> var1) {
      return a(var0, var1, a(var1));
   }

   public static class Item {
      protected int a;

      public Item(int var1) {
         this.a = var1;
      }
   }
}

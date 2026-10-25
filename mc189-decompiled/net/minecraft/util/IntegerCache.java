package net.minecraft.util;

public class IntegerCache {
   private static final Integer[] a = new Integer[65535];

   public static Integer a(int var0) {
      return var0 > 0 && var0 < a.length ? a[var0] : var0;
   }

   static {
      int var0 = 0;

      for (int var1 = a.length; var0 < var1; var0++) {
         a[var0] = var0;
      }
   }
}

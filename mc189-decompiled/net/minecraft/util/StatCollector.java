package net.minecraft.util;

public class StatCollector {
   private static StringTranslate a = StringTranslate.a();
   private static StringTranslate b = new StringTranslate();

   public static String a(String var0) {
      return a.a(var0);
   }

   public static String a(String var0, Object... var1) {
      return a.a(var0, var1);
   }

   public static String b(String var0) {
      return b.a(var0);
   }

   public static boolean c(String var0) {
      return a.b(var0);
   }

   public static long a() {
      return a.c();
   }
}

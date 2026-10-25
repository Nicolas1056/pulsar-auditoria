package net.minecraft.client.resources;

public class I18n {
   private static Locale a;

   static void a(Locale var0) {
      a = var0;
   }

   public static String a(String var0, Object... var1) {
      return a.a(var0, var1);
   }
}

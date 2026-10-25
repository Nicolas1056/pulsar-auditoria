package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
   private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String a(int var0) {
      int var1 = var0 / 20;
      int var2 = var1 / 60;
      var1 %= 60;
      return var1 < 10 ? var2 + ":0" + var1 : var2 + ":" + var1;
   }

   public static String a(String var0) {
      return a.matcher(var0).replaceAll("");
   }

   public static boolean b(String var0) {
      return org.apache.commons.lang3.StringUtils.isEmpty(var0);
   }
}

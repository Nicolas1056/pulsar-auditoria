package net.minecraft.util;

public class ChatAllowedCharacters {
   public static final char[] a = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};

   public static boolean a(char var0) {
      return var0 != 167 && var0 >= ' ' && var0 != 127;
   }

   public static String a(String var0) {
      StringBuilder var1 = new StringBuilder();

      for (char var5 : var0.toCharArray()) {
         if (a(var5)) {
            var1.append(var5);
         }
      }

      return var1.toString();
   }
}

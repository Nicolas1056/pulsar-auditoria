package net.minecraft.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class StringTranslate {
   private static final Pattern a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   private static final Splitter b = Splitter.on('=').limit(2);
   private static StringTranslate c = new StringTranslate();
   private final Map<String, String> d = Maps.newHashMap();
   private long e;

   public StringTranslate() {
      try {
         InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");

         for (String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
            if (!var3.isEmpty() && var3.charAt(0) != '#') {
               String[] var4 = (String[])Iterables.toArray(b.split(var3), String.class);
               if (var4 != null && var4.length == 2) {
                  String var5 = var4[0];
                  String var6 = a.matcher(var4[1]).replaceAll("%$1s");
                  this.d.put(var5, var6);
               }
            }
         }

         this.e = System.currentTimeMillis();
      } catch (IOException var7) {
      }
   }

   static StringTranslate a() {
      return c;
   }

   public static synchronized void a(Map<String, String> var0) {
      c.d.clear();
      c.d.putAll(var0);
      c.e = System.currentTimeMillis();
   }

   public synchronized String a(String var1) {
      return this.c(var1);
   }

   public synchronized String a(String var1, Object... var2) {
      String var3 = this.c(var1);

      try {
         return String.format(var3, var2);
      } catch (IllegalFormatException var5) {
         return "Format error: " + var3;
      }
   }

   private String c(String var1) {
      String var2 = this.d.get(var1);
      return var2 == null ? var1 : var2;
   }

   public synchronized boolean b(String var1) {
      return this.d.containsKey(var1);
   }

   public long c() {
      return this.e;
   }
}

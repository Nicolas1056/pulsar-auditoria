package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public class Locale {
   private static final Splitter b = Splitter.on('=').limit(2);
   private static final Pattern c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
   Map<String, String> a = Maps.newHashMap();
   private boolean d;

   public synchronized void a(IResourceManager var1, List<String> var2) {
      this.a.clear();

      for (String var4 : var2) {
         String var5 = String.format("lang/%s.lang", var4);

         for (String var7 : var1.a()) {
            try {
               this.a(var1.b(new ResourceLocation(var7, var5)));
            } catch (IOException var9) {
            }
         }
      }

      this.b();
   }

   public boolean a() {
      return this.d;
   }

   private void b() {
      this.d = false;
      int var1 = 0;
      int var2 = 0;

      for (String var4 : this.a.values()) {
         int var5 = var4.length();
         var2 += var5;

         for (int var6 = 0; var6 < var5; var6++) {
            if (var4.charAt(var6) >= 256) {
               var1++;
            }
         }
      }

      float var7 = (float)var1 / var2;
      this.d = var7 > 0.1;
   }

   private void a(List<IResource> var1) throws IOException {
      for (IResource var3 : var1) {
         InputStream var4 = var3.b();

         try {
            this.a(var4);
         } finally {
            IOUtils.closeQuietly(var4);
         }
      }
   }

   private void a(InputStream var1) throws IOException {
      for (String var3 : IOUtils.readLines(var1, Charsets.UTF_8)) {
         if (!var3.isEmpty() && var3.charAt(0) != '#') {
            String[] var4 = (String[])Iterables.toArray(b.split(var3), String.class);
            if (var4 != null && var4.length == 2) {
               String var5 = var4[0];
               String var6 = c.matcher(var4[1]).replaceAll("%$1s");
               this.a.put(var5, var6);
            }
         }
      }
   }

   private String b(String var1) {
      String var2 = this.a.get(var1);
      return var2 == null ? var1 : var2;
   }

   public String a(String var1, Object[] var2) {
      String var3 = this.b(var1);

      try {
         return String.format(var3, var2);
      } catch (IllegalFormatException var5) {
         return "Format error: " + var3;
      }
   }
}

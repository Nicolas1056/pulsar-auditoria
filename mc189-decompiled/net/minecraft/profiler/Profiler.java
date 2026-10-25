package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
   private static final Logger b = LogManager.getLogger();
   private final List<String> c = Lists.newArrayList();
   private final List<Long> d = Lists.newArrayList();
   public boolean a;
   private String e = "";
   private final Map<String, Long> f = Maps.newHashMap();

   public void a() {
      this.f.clear();
      this.e = "";
      this.c.clear();
   }

   public void a(String var1) {
      if (this.a) {
         if (this.e.length() > 0) {
            this.e = this.e + ".";
         }

         this.e = this.e + var1;
         this.c.add(this.e);
         this.d.add(System.nanoTime());
      }
   }

   public void b() {
      if (this.a) {
         long var1 = System.nanoTime();
         long var3 = this.d.remove(this.d.size() - 1);
         this.c.remove(this.c.size() - 1);
         long var5 = var1 - var3;
         if (this.f.containsKey(this.e)) {
            this.f.put(this.e, this.f.get(this.e) + var5);
         } else {
            this.f.put(this.e, var5);
         }

         if (var5 > 100000000L) {
            b.warn("Something's taking too long! '" + this.e + "' took aprox " + var5 / 1000000.0 + " ms");
         }

         this.e = !this.c.isEmpty() ? this.c.get(this.c.size() - 1) : "";
      }
   }

   public List<Profiler.Result> b(String var1) {
      if (!this.a) {
         return null;
      } else {
         String var2 = var1;
         long var3 = this.f.containsKey("root") ? this.f.get("root") : 0L;
         long var5 = this.f.containsKey(var1) ? this.f.get(var1) : -1L;
         ArrayList var7 = Lists.newArrayList();
         if (var1.length() > 0) {
            var1 = var1 + ".";
         }

         long var8 = 0L;

         for (String var11 : this.f.keySet()) {
            if (var11.length() > var1.length() && var11.startsWith(var1) && var11.indexOf(".", var1.length() + 1) < 0) {
               var8 += this.f.get(var11);
            }
         }

         float var20 = (float)var8;
         if (var8 < var5) {
            var8 = var5;
         }

         if (var3 < var8) {
            var3 = var8;
         }

         for (String var12 : this.f.keySet()) {
            if (var12.length() > var1.length() && var12.startsWith(var1) && var12.indexOf(".", var1.length() + 1) < 0) {
               long var13 = this.f.get(var12);
               double var15 = var13 * 100.0 / var8;
               double var17 = var13 * 100.0 / var3;
               String var19 = var12.substring(var1.length());
               var7.add(new Profiler.Result(var19, var15, var17));
            }
         }

         for (String var23 : this.f.keySet()) {
            this.f.put(var23, this.f.get(var23) * 999L / 1000L);
         }

         if ((float)var8 > var20) {
            var7.add(new Profiler.Result("unspecified", ((float)var8 - var20) * 100.0 / var8, ((float)var8 - var20) * 100.0 / var3));
         }

         Collections.sort(var7);
         var7.add(0, new Profiler.Result(var2, 100.0, var8 * 100.0 / var3));
         return var7;
      }
   }

   public void c(String var1) {
      this.b();
      this.a(var1);
   }

   public String c() {
      return this.c.size() == 0 ? "[UNKNOWN]" : this.c.get(this.c.size() - 1);
   }

   public static final class Result implements Comparable<Profiler.Result> {
      public double a;
      public double b;
      public String c;

      public Result(String var1, double var2, double var4) {
         this.c = var1;
         this.a = var2;
         this.b = var4;
      }

      public int a(Profiler.Result var1) {
         if (var1.a < this.a) {
            return -1;
         } else {
            return var1.a > this.a ? 1 : var1.c.compareTo(this.c);
         }
      }

      public int a() {
         return (this.c.hashCode() & 11184810) + 4473924;
      }
   }
}

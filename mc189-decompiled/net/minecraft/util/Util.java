package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class Util {
   public static Util.EnumOS a() {
      String var0 = System.getProperty("os.name").toLowerCase();
      if (var0.contains("win")) {
         return Util.EnumOS.c;
      } else if (var0.contains("mac")) {
         return Util.EnumOS.d;
      } else if (var0.contains("solaris")) {
         return Util.EnumOS.b;
      } else if (var0.contains("sunos")) {
         return Util.EnumOS.b;
      } else if (var0.contains("linux")) {
         return Util.EnumOS.a;
      } else {
         return var0.contains("unix") ? Util.EnumOS.a : Util.EnumOS.e;
      }
   }

   public static <V> V a(FutureTask<V> var0, Logger var1) {
      try {
         var0.run();
         return (V)var0.get();
      } catch (ExecutionException var3) {
         var1.fatal("Error executing task", var3);
      } catch (InterruptedException var4) {
         var1.fatal("Error executing task", var4);
      }

      return null;
   }

   public static enum EnumOS {
      a,
      b,
      c,
      d,
      e;
   }
}

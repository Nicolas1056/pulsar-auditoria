package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;
import java.util.List;

public class IntCache {
   private static int a = 256;
   private static List<int[]> b = Lists.newArrayList();
   private static List<int[]> c = Lists.newArrayList();
   private static List<int[]> d = Lists.newArrayList();
   private static List<int[]> e = Lists.newArrayList();

   public static synchronized int[] a(int var0) {
      if (var0 <= 256) {
         if (b.isEmpty()) {
            int[] var5 = new int[256];
            c.add(var5);
            return var5;
         } else {
            int[] var4 = b.remove(b.size() - 1);
            c.add(var4);
            return var4;
         }
      } else if (var0 > a) {
         a = var0;
         d.clear();
         e.clear();
         int[] var3 = new int[a];
         e.add(var3);
         return var3;
      } else if (d.isEmpty()) {
         int[] var2 = new int[a];
         e.add(var2);
         return var2;
      } else {
         int[] var1 = d.remove(d.size() - 1);
         e.add(var1);
         return var1;
      }
   }

   public static synchronized void a() {
      if (!d.isEmpty()) {
         d.remove(d.size() - 1);
      }

      if (!b.isEmpty()) {
         b.remove(b.size() - 1);
      }

      d.addAll(e);
      b.addAll(c);
      e.clear();
      c.clear();
   }

   public static synchronized String b() {
      return "cache: " + d.size() + ", tcache: " + b.size() + ", allocated: " + e.size() + ", tallocated: " + c.size();
   }
}

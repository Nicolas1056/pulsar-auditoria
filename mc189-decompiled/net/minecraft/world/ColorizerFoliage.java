package net.minecraft.world;

public class ColorizerFoliage {
   private static int[] a = new int[65536];

   public static void a(int[] var0) {
      a = var0;
   }

   public static int a(double var0, double var2) {
      var2 *= var0;
      int var4 = (int)((1.0 - var0) * 255.0);
      int var5 = (int)((1.0 - var2) * 255.0);
      return a[var5 << 8 | var4];
   }

   public static int a() {
      return 6396257;
   }

   public static int b() {
      return 8431445;
   }

   public static int c() {
      return 4764952;
   }
}

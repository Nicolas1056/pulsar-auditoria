package net.minecraft.world;

public class ColorizerGrass {
   private static int[] a = new int[65536];

   public static void a(int[] var0) {
      a = var0;
   }

   public static int a(double var0, double var2) {
      var2 *= var0;
      int var4 = (int)((1.0 - var0) * 255.0);
      int var5 = (int)((1.0 - var2) * 255.0);
      int var6 = var5 << 8 | var4;
      return var6 > a.length ? -65281 : a[var6];
   }
}

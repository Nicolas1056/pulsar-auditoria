package net.minecraft.entity.boss;

public final class BossStatus {
   public static float a;
   public static int b;
   public static String c;
   public static boolean d;

   public static void a(IBossDisplayData var0, boolean var1) {
      a = var0.bn() / var0.bu();
      b = 100;
      c = var0.f_().d();
      d = var1;
   }
}

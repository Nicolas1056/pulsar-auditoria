package net.minecraft.world;

public enum EnumDifficulty {
   a(0, "options.difficulty.peaceful"),
   b(1, "options.difficulty.easy"),
   c(2, "options.difficulty.normal"),
   d(3, "options.difficulty.hard");

   private static final EnumDifficulty[] e = new EnumDifficulty[values().length];
   private final int f;
   private final String g;

   private EnumDifficulty(int var3, String var4) {
      this.f = var3;
      this.g = var4;
   }

   public int a() {
      return this.f;
   }

   public static EnumDifficulty a(int var0) {
      return e[var0 % e.length];
   }

   public String b() {
      return this.g;
   }

   static {
      for (EnumDifficulty var3 : values()) {
         e[var3.f] = var3;
      }
   }
}

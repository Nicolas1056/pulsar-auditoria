package net.minecraft.world;

public class WorldType {
   public static final WorldType[] a = new WorldType[16];
   public static final WorldType b = new WorldType(0, "default", 1).i();
   public static final WorldType c = new WorldType(1, "flat");
   public static final WorldType d = new WorldType(2, "largeBiomes");
   public static final WorldType e = new WorldType(3, "amplified").j();
   public static final WorldType f = new WorldType(4, "customized");
   public static final WorldType g = new WorldType(5, "debug_all_block_states");
   public static final WorldType h = new WorldType(8, "default_1_1", 0).a(false);
   private final int i;
   private final String j;
   private final int k;
   private boolean l;
   private boolean m;
   private boolean n;

   private WorldType(int var1, String var2) {
      this(var1, var2, 0);
   }

   private WorldType(int var1, String var2, int var3) {
      this.j = var2;
      this.k = var3;
      this.l = true;
      this.i = var1;
      a[var1] = this;
   }

   public String a() {
      return this.j;
   }

   public String b() {
      return "generator." + this.j;
   }

   public String c() {
      return this.b() + ".info";
   }

   public int d() {
      return this.k;
   }

   public WorldType a(int var1) {
      return this == b && var1 == 0 ? h : this;
   }

   private WorldType a(boolean var1) {
      this.l = var1;
      return this;
   }

   public boolean e() {
      return this.l;
   }

   private WorldType i() {
      this.m = true;
      return this;
   }

   public boolean f() {
      return this.m;
   }

   public static WorldType a(String var0) {
      for (int var1 = 0; var1 < a.length; var1++) {
         if (a[var1] != null && a[var1].j.equalsIgnoreCase(var0)) {
            return a[var1];
         }
      }

      return null;
   }

   public int g() {
      return this.i;
   }

   public boolean h() {
      return this.n;
   }

   private WorldType j() {
      this.n = true;
      return this;
   }
}

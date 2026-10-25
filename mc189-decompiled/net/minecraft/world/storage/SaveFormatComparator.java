package net.minecraft.world.storage;

import net.minecraft.world.WorldSettings;

public class SaveFormatComparator implements Comparable<SaveFormatComparator> {
   private final String a;
   private final String b;
   private final long c;
   private final long d;
   private final boolean e;
   private final WorldSettings.GameType f;
   private final boolean g;
   private final boolean h;

   public SaveFormatComparator(String var1, String var2, long var3, long var5, WorldSettings.GameType var7, boolean var8, boolean var9, boolean var10) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var5;
      this.f = var7;
      this.e = var8;
      this.g = var9;
      this.h = var10;
   }

   public String a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public long c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }

   public long e() {
      return this.c;
   }

   public int a(SaveFormatComparator var1) {
      if (this.c < var1.c) {
         return 1;
      } else {
         return this.c > var1.c ? -1 : this.a.compareTo(var1.a);
      }
   }

   public WorldSettings.GameType f() {
      return this.f;
   }

   public boolean g() {
      return this.g;
   }

   public boolean h() {
      return this.h;
   }
}

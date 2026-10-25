package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
   private final long a;
   private final WorldSettings.GameType b;
   private final boolean c;
   private final boolean d;
   private final WorldType e;
   private boolean f;
   private boolean g;
   private String h = "";

   public WorldSettings(long var1, WorldSettings.GameType var3, boolean var4, boolean var5, WorldType var6) {
      this.a = var1;
      this.b = var3;
      this.c = var4;
      this.d = var5;
      this.e = var6;
   }

   public WorldSettings(WorldInfo var1) {
      this(var1.b(), var1.r(), var1.s(), var1.t(), var1.u());
   }

   public WorldSettings a() {
      this.g = true;
      return this;
   }

   public WorldSettings b() {
      this.f = true;
      return this;
   }

   public WorldSettings a(String var1) {
      this.h = var1;
      return this;
   }

   public boolean c() {
      return this.g;
   }

   public long d() {
      return this.a;
   }

   public WorldSettings.GameType e() {
      return this.b;
   }

   public boolean f() {
      return this.d;
   }

   public boolean g() {
      return this.c;
   }

   public WorldType h() {
      return this.e;
   }

   public boolean i() {
      return this.f;
   }

   public static WorldSettings.GameType a(int var0) {
      return WorldSettings.GameType.a(var0);
   }

   public String j() {
      return this.h;
   }

   public static enum GameType {
      a(-1, ""),
      b(0, "survival"),
      c(1, "creative"),
      d(2, "adventure"),
      e(3, "spectator");

      int f;
      String g;

      private GameType(int var3, String var4) {
         this.f = var3;
         this.g = var4;
      }

      public int a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public void a(PlayerCapabilities var1) {
         if (this == c) {
            var1.c = true;
            var1.d = true;
            var1.a = true;
         } else if (this == e) {
            var1.c = true;
            var1.d = false;
            var1.a = true;
            var1.b = true;
         } else {
            var1.c = false;
            var1.d = false;
            var1.a = false;
            var1.b = false;
         }

         var1.e = !this.c();
      }

      public boolean c() {
         return this == d || this == e;
      }

      public boolean d() {
         return this == c;
      }

      public boolean e() {
         return this == b || this == d;
      }

      public static WorldSettings.GameType a(int var0) {
         for (WorldSettings.GameType var4 : values()) {
            if (var4.f == var0) {
               return var4;
            }
         }

         return b;
      }

      public static WorldSettings.GameType a(String var0) {
         for (WorldSettings.GameType var4 : values()) {
            if (var4.g.equals(var0)) {
               return var4;
            }
         }

         return b;
      }
   }
}

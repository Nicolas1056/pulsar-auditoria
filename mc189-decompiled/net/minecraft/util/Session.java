package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Map;
import java.util.UUID;

public class Session {
   private final String a;
   private final String b;
   private final String c;
   private final Session.Type d;

   public Session(String var1, String var2, String var3, String var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = Session.Type.a(var4);
   }

   public String a() {
      return "token:" + this.c + ":" + this.b;
   }

   public String b() {
      return this.b;
   }

   public String c() {
      return this.a;
   }

   public String d() {
      return this.c;
   }

   public GameProfile e() {
      try {
         UUID var1 = UUIDTypeAdapter.fromString(this.b());
         return new GameProfile(var1, this.c());
      } catch (IllegalArgumentException var2) {
         return new GameProfile(null, this.c());
      }
   }

   public Session.Type f() {
      return this.d;
   }

   public static enum Type {
      a("legacy"),
      b("mojang");

      private static final Map<String, Session.Type> c = Maps.newHashMap();
      private final String d;

      private Type(String var3) {
         this.d = var3;
      }

      public static Session.Type a(String var0) {
         return c.get(var0.toLowerCase());
      }

      static {
         for (Session.Type var3 : values()) {
            c.put(var3.d, var3);
         }
      }
   }
}

package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListOpsEntry extends UserListEntry<GameProfile> {
   private final int a;
   private final boolean b;

   public UserListOpsEntry(GameProfile var1, int var2, boolean var3) {
      super(var1);
      this.a = var2;
      this.b = var3;
   }

   public UserListOpsEntry(JsonObject var1) {
      super(b(var1), var1);
      this.a = var1.has("level") ? var1.get("level").getAsInt() : 0;
      this.b = var1.has("bypassesPlayerLimit") && var1.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.f() != null) {
         var1.addProperty("uuid", this.f().getId() == null ? "" : this.f().getId().toString());
         var1.addProperty("name", this.f().getName());
         super.a(var1);
         var1.addProperty("level", this.a);
         var1.addProperty("bypassesPlayerLimit", this.b);
      }
   }

   private static GameProfile b(JsonObject var0) {
      if (var0.has("uuid") && var0.has("name")) {
         String var1 = var0.get("uuid").getAsString();

         UUID var2;
         try {
            var2 = UUID.fromString(var1);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(var2, var0.get("name").getAsString());
      } else {
         return null;
      }
   }
}

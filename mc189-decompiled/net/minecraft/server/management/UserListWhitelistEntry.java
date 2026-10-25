package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserListWhitelistEntry extends UserListEntry<GameProfile> {
   public UserListWhitelistEntry(GameProfile var1) {
      super(var1);
   }

   public UserListWhitelistEntry(JsonObject var1) {
      super(b(var1), var1);
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.f() != null) {
         var1.addProperty("uuid", this.f().getId() == null ? "" : this.f().getId().toString());
         var1.addProperty("name", this.f().getName());
         super.a(var1);
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

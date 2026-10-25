package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListBans extends UserList<GameProfile, UserListBansEntry> {
   public UserListBans(File var1) {
      super(var1);
   }

   @Override
   protected UserListEntry<GameProfile> a(JsonObject var1) {
      return new UserListBansEntry(var1);
   }

   public boolean a(GameProfile var1) {
      return this.d(var1);
   }

   @Override
   public String[] a() {
      String[] var1 = new String[this.e().size()];
      int var2 = 0;

      for (UserListBansEntry var4 : this.e().values()) {
         var1[var2++] = var4.f().getName();
      }

      return var1;
   }

   protected String b(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile a(String var1) {
      for (UserListBansEntry var3 : this.e().values()) {
         if (var1.equalsIgnoreCase(var3.f().getName())) {
            return var3.f();
         }
      }

      return null;
   }
}

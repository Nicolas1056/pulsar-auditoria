package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry> {
   public UserListOps(File var1) {
      super(var1);
   }

   @Override
   protected UserListEntry<GameProfile> a(JsonObject var1) {
      return new UserListOpsEntry(var1);
   }

   @Override
   public String[] a() {
      String[] var1 = new String[this.e().size()];
      int var2 = 0;

      for (UserListOpsEntry var4 : this.e().values()) {
         var1[var2++] = var4.f().getName();
      }

      return var1;
   }

   public boolean b(GameProfile var1) {
      UserListOpsEntry var2 = this.b(var1);
      return var2 != null ? var2.b() : false;
   }

   protected String c(GameProfile var1) {
      return var1.getId().toString();
   }

   public GameProfile a(String var1) {
      for (UserListOpsEntry var3 : this.e().values()) {
         if (var1.equalsIgnoreCase(var3.f().getName())) {
            return var3.f();
         }
      }

      return null;
   }
}

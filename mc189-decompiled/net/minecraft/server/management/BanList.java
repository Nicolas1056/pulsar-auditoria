package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class BanList extends UserList<String, IPBanEntry> {
   public BanList(File var1) {
      super(var1);
   }

   @Override
   protected UserListEntry<String> a(JsonObject var1) {
      return new IPBanEntry(var1);
   }

   public boolean a(SocketAddress var1) {
      String var2 = this.c(var1);
      return this.d(var2);
   }

   public IPBanEntry b(SocketAddress var1) {
      String var2 = this.c(var1);
      return this.b(var2);
   }

   private String c(SocketAddress var1) {
      String var2 = var1.toString();
      if (var2.contains("/")) {
         var2 = var2.substring(var2.indexOf(47) + 1);
      }

      if (var2.contains(":")) {
         var2 = var2.substring(0, var2.indexOf(58));
      }

      return var2;
   }
}

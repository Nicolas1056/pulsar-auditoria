package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.util.Date;

public class IPBanEntry extends BanEntry<String> {
   public IPBanEntry(String var1) {
      this(var1, null, null, null, null);
   }

   public IPBanEntry(String var1, Date var2, String var3, Date var4, String var5) {
      super(var1, var2, var3, var4, var5);
   }

   public IPBanEntry(JsonObject var1) {
      super(b(var1), var1);
   }

   private static String b(JsonObject var0) {
      return var0.has("ip") ? var0.get("ip").getAsString() : null;
   }

   @Override
   protected void a(JsonObject var1) {
      if (this.f() != null) {
         var1.addProperty("ip", this.f());
         super.a(var1);
      }
   }
}

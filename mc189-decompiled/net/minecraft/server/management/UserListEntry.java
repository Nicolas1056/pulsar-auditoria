package net.minecraft.server.management;

import com.google.gson.JsonObject;

public class UserListEntry<T> {
   private final T a;

   public UserListEntry(T var1) {
      this.a = (T)var1;
   }

   protected UserListEntry(T var1, JsonObject var2) {
      this.a = (T)var1;
   }

   T f() {
      return this.a;
   }

   boolean e() {
      return false;
   }

   protected void a(JsonObject var1) {
   }
}

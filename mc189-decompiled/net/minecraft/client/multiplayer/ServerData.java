package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {
   public String a;
   public String b;
   public String c;
   public String d;
   public long e;
   public int f = 47;
   public String g = "1.8.9";
   public boolean h;
   public String i;
   private ServerData.ServerResourceMode j = ServerData.ServerResourceMode.c;
   private String k;
   private boolean l;

   public ServerData(String var1, String var2, boolean var3) {
      this.a = var1;
      this.b = var2;
      this.l = var3;
   }

   public NBTTagCompound a() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("name", this.a);
      var1.a("ip", this.b);
      if (this.k != null) {
         var1.a("icon", this.k);
      }

      if (this.j == ServerData.ServerResourceMode.a) {
         var1.a("acceptTextures", true);
      } else if (this.j == ServerData.ServerResourceMode.b) {
         var1.a("acceptTextures", false);
      }

      return var1;
   }

   public ServerData.ServerResourceMode b() {
      return this.j;
   }

   public void a(ServerData.ServerResourceMode var1) {
      this.j = var1;
   }

   public static ServerData a(NBTTagCompound var0) {
      ServerData var1 = new ServerData(var0.j("name"), var0.j("ip"), false);
      if (var0.b("icon", 8)) {
         var1.a(var0.j("icon"));
      }

      if (var0.b("acceptTextures", 1)) {
         if (var0.n("acceptTextures")) {
            var1.a(ServerData.ServerResourceMode.a);
         } else {
            var1.a(ServerData.ServerResourceMode.b);
         }
      } else {
         var1.a(ServerData.ServerResourceMode.c);
      }

      return var1;
   }

   public String c() {
      return this.k;
   }

   public void a(String var1) {
      this.k = var1;
   }

   public boolean d() {
      return this.l;
   }

   public void a(ServerData var1) {
      this.b = var1.b;
      this.a = var1.a;
      this.a(var1.b());
      this.k = var1.k;
      this.l = var1.l;
   }

   public static enum ServerResourceMode {
      a("enabled"),
      b("disabled"),
      c("prompt");

      private final IChatComponent d;

      private ServerResourceMode(String var3) {
         this.d = new ChatComponentTranslation("addServer.resourcePack." + var3);
      }

      public IChatComponent a() {
         return this.d;
      }
   }
}

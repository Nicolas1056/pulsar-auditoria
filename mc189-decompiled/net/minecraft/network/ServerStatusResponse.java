package net.minecraft.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class ServerStatusResponse {
   private IChatComponent a;
   private ServerStatusResponse.PlayerCountData b;
   private ServerStatusResponse.MinecraftProtocolVersionIdentifier c;
   private String d;

   public IChatComponent a() {
      return this.a;
   }

   public void a(IChatComponent var1) {
      this.a = var1;
   }

   public ServerStatusResponse.PlayerCountData b() {
      return this.b;
   }

   public void a(ServerStatusResponse.PlayerCountData var1) {
      this.b = var1;
   }

   public ServerStatusResponse.MinecraftProtocolVersionIdentifier c() {
      return this.c;
   }

   public void a(ServerStatusResponse.MinecraftProtocolVersionIdentifier var1) {
      this.c = var1;
   }

   public void a(String var1) {
      this.d = var1;
   }

   public String d() {
      return this.d;
   }

   public static class MinecraftProtocolVersionIdentifier {
      private final String a;
      private final int b;

      public MinecraftProtocolVersionIdentifier(String var1, int var2) {
         this.a = var1;
         this.b = var2;
      }

      public String a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public static class Serializer
         implements JsonDeserializer<ServerStatusResponse.MinecraftProtocolVersionIdentifier>,
         JsonSerializer<ServerStatusResponse.MinecraftProtocolVersionIdentifier> {
         public ServerStatusResponse.MinecraftProtocolVersionIdentifier a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject var4 = JsonUtils.l(var1, "version");
            return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(JsonUtils.h(var4, "name"), JsonUtils.m(var4, "protocol"));
         }

         public JsonElement a(ServerStatusResponse.MinecraftProtocolVersionIdentifier var1, Type var2, JsonSerializationContext var3) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("name", var1.a());
            var4.addProperty("protocol", var1.b());
            return var4;
         }
      }
   }

   public static class PlayerCountData {
      private final int a;
      private final int b;
      private GameProfile[] c;

      public PlayerCountData(int var1, int var2) {
         this.a = var1;
         this.b = var2;
      }

      public int a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public GameProfile[] c() {
         return this.c;
      }

      public void a(GameProfile[] var1) {
         this.c = var1;
      }

      public static class Serializer implements JsonDeserializer<ServerStatusResponse.PlayerCountData>, JsonSerializer<ServerStatusResponse.PlayerCountData> {
         public ServerStatusResponse.PlayerCountData a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject var4 = JsonUtils.l(var1, "players");
            ServerStatusResponse.PlayerCountData var5 = new ServerStatusResponse.PlayerCountData(JsonUtils.m(var4, "max"), JsonUtils.m(var4, "online"));
            if (JsonUtils.d(var4, "sample")) {
               JsonArray var6 = JsonUtils.t(var4, "sample");
               if (var6.size() > 0) {
                  GameProfile[] var7 = new GameProfile[var6.size()];

                  for (int var8 = 0; var8 < var7.length; var8++) {
                     JsonObject var9 = JsonUtils.l(var6.get(var8), "player[" + var8 + "]");
                     String var10 = JsonUtils.h(var9, "id");
                     var7[var8] = new GameProfile(UUID.fromString(var10), JsonUtils.h(var9, "name"));
                  }

                  var5.a(var7);
               }
            }

            return var5;
         }

         public JsonElement a(ServerStatusResponse.PlayerCountData var1, Type var2, JsonSerializationContext var3) {
            JsonObject var4 = new JsonObject();
            var4.addProperty("max", var1.a());
            var4.addProperty("online", var1.b());
            if (var1.c() != null && var1.c().length > 0) {
               JsonArray var5 = new JsonArray();

               for (int var6 = 0; var6 < var1.c().length; var6++) {
                  JsonObject var7 = new JsonObject();
                  UUID var8 = var1.c()[var6].getId();
                  var7.addProperty("id", var8 == null ? "" : var8.toString());
                  var7.addProperty("name", var1.c()[var6].getName());
                  var5.add(var7);
               }

               var4.add("sample", var5);
            }

            return var4;
         }
      }
   }

   public static class Serializer implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse> {
      public ServerStatusResponse a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject var4 = JsonUtils.l(var1, "status");
         ServerStatusResponse var5 = new ServerStatusResponse();
         if (var4.has("description")) {
            var5.a((IChatComponent)var3.deserialize(var4.get("description"), IChatComponent.class));
         }

         if (var4.has("players")) {
            var5.a((ServerStatusResponse.PlayerCountData)var3.deserialize(var4.get("players"), ServerStatusResponse.PlayerCountData.class));
         }

         if (var4.has("version")) {
            var5.a(
               (ServerStatusResponse.MinecraftProtocolVersionIdentifier)var3.deserialize(
                  var4.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class
               )
            );
         }

         if (var4.has("favicon")) {
            var5.a(JsonUtils.h(var4, "favicon"));
         }

         return var5;
      }

      public JsonElement a(ServerStatusResponse var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         if (var1.a() != null) {
            var4.add("description", var3.serialize(var1.a()));
         }

         if (var1.b() != null) {
            var4.add("players", var3.serialize(var1.b()));
         }

         if (var1.c() != null) {
            var4.add("version", var3.serialize(var1.c()));
         }

         if (var1.d() != null) {
            var4.addProperty("favicon", var1.d());
         }

         return var4;
      }
   }
}

package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

public class S00PacketServerInfo implements Packet<INetHandlerStatusClient> {
   private static final Gson a = new GsonBuilder()
      .registerTypeAdapter(
         ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()
      )
      .registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new ServerStatusResponse.PlayerCountData.Serializer())
      .registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer())
      .registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer())
      .registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer())
      .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
      .create();
   private ServerStatusResponse b;

   public S00PacketServerInfo() {
   }

   public S00PacketServerInfo(ServerStatusResponse var1) {
      this.b = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.b = (ServerStatusResponse)a.fromJson(var1.c(32767), ServerStatusResponse.class);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(a.toJson(this.b));
   }

   public void a(INetHandlerStatusClient var1) {
      var1.a(this);
   }

   public ServerStatusResponse a() {
      return this.b;
   }
}

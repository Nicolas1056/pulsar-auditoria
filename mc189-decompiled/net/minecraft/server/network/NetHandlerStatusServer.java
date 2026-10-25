package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
   private static final IChatComponent a = new ChatComponentText("Status request has been handled.");
   private final MinecraftServer b;
   private final NetworkManager c;
   private boolean d;

   public NetHandlerStatusServer(MinecraftServer var1, NetworkManager var2) {
      this.b = var1;
      this.c = var2;
   }

   @Override
   public void a(IChatComponent var1) {
   }

   @Override
   public void a(C00PacketServerQuery var1) {
      if (this.d) {
         this.c.a(a);
      } else {
         this.d = true;
         this.c.a(new S00PacketServerInfo(this.b.aG()));
      }
   }

   @Override
   public void a(C01PacketPing var1) {
      this.c.a(new S01PacketPong(var1.a()));
      this.c.a(a);
   }
}

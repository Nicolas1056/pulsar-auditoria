package net.minecraft.server.network;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeTCP implements INetHandlerHandshakeServer {
   private final MinecraftServer a;
   private final NetworkManager b;

   public NetHandlerHandshakeTCP(MinecraftServer var1, NetworkManager var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(C00Handshake var1) {
      switch (var1.a()) {
         case d:
            this.b.a(EnumConnectionState.d);
            if (var1.b() > 47) {
               ChatComponentText var2 = new ChatComponentText("Outdated server! I'm still on 1.8.9");
               this.b.a(new S00PacketDisconnect(var2));
               this.b.a(var2);
            } else if (var1.b() < 47) {
               ChatComponentText var3 = new ChatComponentText("Outdated client! Please use 1.8.9");
               this.b.a(new S00PacketDisconnect(var3));
               this.b.a(var3);
            } else {
               this.b.a(new NetHandlerLoginServer(this.a, this.b));
            }
            break;
         case c:
            this.b.a(EnumConnectionState.c);
            this.b.a(new NetHandlerStatusServer(this.a, this.b));
            break;
         default:
            throw new UnsupportedOperationException("Invalid intention " + var1.a());
      }
   }

   @Override
   public void a(IChatComponent var1) {
   }
}

package net.minecraft.client.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {
   private final MinecraftServer a;
   private final NetworkManager b;

   public NetHandlerHandshakeMemory(MinecraftServer var1, NetworkManager var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(C00Handshake var1) {
      this.b.a(var1.a());
      this.b.a(new NetHandlerLoginServer(this.a, this.b));
   }

   @Override
   public void a(IChatComponent var1) {
   }
}

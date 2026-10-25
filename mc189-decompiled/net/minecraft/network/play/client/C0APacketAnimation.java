package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0APacketAnimation implements Packet<INetHandlerPlayServer> {
   @Override
   public void a(PacketBuffer var1) throws IOException {
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }
}

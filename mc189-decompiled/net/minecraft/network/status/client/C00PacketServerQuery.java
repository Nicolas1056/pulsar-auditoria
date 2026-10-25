package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C00PacketServerQuery implements Packet<INetHandlerStatusServer> {
   @Override
   public void a(PacketBuffer var1) throws IOException {
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
   }

   public void a(INetHandlerStatusServer var1) {
      var1.a(this);
   }
}

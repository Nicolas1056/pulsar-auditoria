package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

public class S01PacketPong implements Packet<INetHandlerStatusClient> {
   private long a;

   public S01PacketPong() {
   }

   public S01PacketPong(long var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readLong();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeLong(this.a);
   }

   public void a(INetHandlerStatusClient var1) {
      var1.a(this);
   }
}

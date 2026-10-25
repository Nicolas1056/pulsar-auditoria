package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C01PacketPing implements Packet<INetHandlerStatusServer> {
   private long a;

   public C01PacketPing() {
   }

   public C01PacketPing(long var1) {
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

   public void a(INetHandlerStatusServer var1) {
      var1.a(this);
   }

   public long a() {
      return this.a;
   }
}

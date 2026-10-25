package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3FPacketCustomPayload implements Packet<INetHandlerPlayClient> {
   private String a;
   private PacketBuffer b;

   public S3FPacketCustomPayload() {
   }

   public S3FPacketCustomPayload(String var1, PacketBuffer var2) {
      this.a = var1;
      this.b = var2;
      if (var2.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(20);
      int var2 = var1.readableBytes();
      if (var2 >= 0 && var2 <= 1048576) {
         this.b = new PacketBuffer(var1.readBytes(var2));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeBytes(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public PacketBuffer b() {
      return this.b;
   }
}

package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C17PacketCustomPayload implements Packet<INetHandlerPlayServer> {
   private String a;
   private PacketBuffer b;

   public C17PacketCustomPayload() {
   }

   public C17PacketCustomPayload(String var1, PacketBuffer var2) {
      this.a = var1;
      this.b = var2;
      if (var2.writerIndex() > 32767) {
         throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(20);
      int var2 = var1.readableBytes();
      if (var2 >= 0 && var2 <= 32767) {
         this.b = new PacketBuffer(var1.readBytes(var2));
      } else {
         throw new IOException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeBytes(this.b);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public PacketBuffer b() {
      return this.b;
   }
}

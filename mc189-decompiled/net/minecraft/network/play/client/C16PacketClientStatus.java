package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C16PacketClientStatus implements Packet<INetHandlerPlayServer> {
   private C16PacketClientStatus.EnumState a;

   public C16PacketClientStatus() {
   }

   public C16PacketClientStatus(C16PacketClientStatus.EnumState var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.a(C16PacketClientStatus.EnumState.class);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public C16PacketClientStatus.EnumState a() {
      return this.a;
   }

   public static enum EnumState {
      a,
      b,
      c;
   }
}

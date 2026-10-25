package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2EPacketCloseWindow implements Packet<INetHandlerPlayClient> {
   private int a;

   public S2EPacketCloseWindow() {
   }

   public S2EPacketCloseWindow(int var1) {
      this.a = var1;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readUnsignedByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.a);
   }
}

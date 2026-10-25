package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C09PacketHeldItemChange implements Packet<INetHandlerPlayServer> {
   private int a;

   public C09PacketHeldItemChange() {
   }

   public C09PacketHeldItemChange(int var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readShort();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeShort(this.a);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }
}

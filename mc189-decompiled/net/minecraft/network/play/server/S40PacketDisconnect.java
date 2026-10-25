package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S40PacketDisconnect implements Packet<INetHandlerPlayClient> {
   private IChatComponent a;

   public S40PacketDisconnect() {
   }

   public S40PacketDisconnect(IChatComponent var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.d();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public IChatComponent a() {
      return this.a;
   }
}

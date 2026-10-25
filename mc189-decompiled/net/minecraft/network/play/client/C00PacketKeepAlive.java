package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C00PacketKeepAlive implements Packet<INetHandlerPlayServer> {
   private int a;

   public C00PacketKeepAlive() {
   }

   public C00PacketKeepAlive(int var1) {
      this.a = var1;
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
   }

   public int a() {
      return this.a;
   }
}

package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class S03PacketEnableCompression implements Packet<INetHandlerLoginClient> {
   private int a;

   public S03PacketEnableCompression() {
   }

   public S03PacketEnableCompression(int var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
   }

   public void a(INetHandlerLoginClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }
}

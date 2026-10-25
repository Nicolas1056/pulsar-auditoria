package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S46PacketSetCompressionLevel implements Packet<INetHandlerPlayClient> {
   private int a;

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }
}

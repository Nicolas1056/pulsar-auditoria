package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3APacketTabComplete implements Packet<INetHandlerPlayClient> {
   private String[] a;

   public S3APacketTabComplete() {
   }

   public S3APacketTabComplete(String[] var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = new String[var1.e()];

      for (int var2 = 0; var2 < this.a.length; var2++) {
         this.a[var2] = var1.c(32767);
      }
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a.length);

      for (String var5 : this.a) {
         var1.a(var5);
      }
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public String[] a() {
      return this.a;
   }
}

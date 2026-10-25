package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S48PacketResourcePackSend implements Packet<INetHandlerPlayClient> {
   private String a;
   private String b;

   public S48PacketResourcePackSend() {
   }

   public S48PacketResourcePackSend(String var1, String var2) {
      this.a = var1;
      this.b = var2;
      if (var2.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + var2.length() + ")");
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(32767);
      this.b = var1.c(40);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public String a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }
}

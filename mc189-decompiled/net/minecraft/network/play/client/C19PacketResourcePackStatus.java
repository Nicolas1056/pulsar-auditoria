package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C19PacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
   private String a;
   private C19PacketResourcePackStatus.Action b;

   public C19PacketResourcePackStatus() {
   }

   public C19PacketResourcePackStatus(String var1, C19PacketResourcePackStatus.Action var2) {
      if (var1.length() > 40) {
         var1 = var1.substring(0, 40);
      }

      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.c(40);
      this.b = var1.a(C19PacketResourcePackStatus.Action.class);
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public static enum Action {
      a,
      b,
      c,
      d;
   }
}

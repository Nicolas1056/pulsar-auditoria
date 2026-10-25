package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S47PacketPlayerListHeaderFooter implements Packet<INetHandlerPlayClient> {
   private IChatComponent a;
   private IChatComponent b;

   public S47PacketPlayerListHeaderFooter() {
   }

   public S47PacketPlayerListHeaderFooter(IChatComponent var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.d();
      this.b = var1.d();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.a(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public IChatComponent a() {
      return this.a;
   }

   public IChatComponent b() {
      return this.b;
   }
}

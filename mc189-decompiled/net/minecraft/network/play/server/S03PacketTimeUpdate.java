package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S03PacketTimeUpdate implements Packet<INetHandlerPlayClient> {
   private long a;
   private long b;

   public S03PacketTimeUpdate() {
   }

   public S03PacketTimeUpdate(long var1, long var3, boolean var5) {
      this.a = var1;
      this.b = var3;
      if (!var5) {
         this.b = -this.b;
         if (this.b == 0L) {
            this.b = -1L;
         }
      }
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readLong();
      this.b = var1.readLong();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeLong(this.a);
      var1.writeLong(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public long a() {
      return this.a;
   }

   public long b() {
      return this.b;
   }
}

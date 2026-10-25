package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0BPacketAnimation implements Packet<INetHandlerPlayClient> {
   private int a;
   private int b;

   public S0BPacketAnimation() {
   }

   public S0BPacketAnimation(Entity var1, int var2) {
      this.a = var1.F();
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readUnsignedByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }
}

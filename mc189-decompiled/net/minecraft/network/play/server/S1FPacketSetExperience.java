package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1FPacketSetExperience implements Packet<INetHandlerPlayClient> {
   private float a;
   private int b;
   private int c;

   public S1FPacketSetExperience() {
   }

   public S1FPacketSetExperience(float var1, int var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readFloat();
      this.c = var1.e();
      this.b = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.a);
      var1.b(this.c);
      var1.b(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public float a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }
}

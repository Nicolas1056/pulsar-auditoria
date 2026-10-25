package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S06PacketUpdateHealth implements Packet<INetHandlerPlayClient> {
   private float a;
   private int b;
   private float c;

   public S06PacketUpdateHealth() {
   }

   public S06PacketUpdateHealth(float var1, int var2, float var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.readFloat();
      this.b = var1.e();
      this.c = var1.readFloat();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.a);
      var1.b(this.b);
      var1.writeFloat(this.c);
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

   public float c() {
      return this.c;
   }
}

package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState implements Packet<INetHandlerPlayClient> {
   public static final String[] a = new String[]{"tile.bed.notValid"};
   private int b;
   private float c;

   public S2BPacketChangeGameState() {
   }

   public S2BPacketChangeGameState(int var1, float var2) {
      this.b = var1;
      this.c = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.b = var1.readUnsignedByte();
      this.c = var1.readFloat();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.b);
      var1.writeFloat(this.c);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int a() {
      return this.b;
   }

   public float b() {
      return this.c;
   }
}

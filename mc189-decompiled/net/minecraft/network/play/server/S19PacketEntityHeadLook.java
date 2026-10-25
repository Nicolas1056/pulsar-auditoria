package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook implements Packet<INetHandlerPlayClient> {
   private int a;
   private byte b;

   public S19PacketEntityHeadLook() {
   }

   public S19PacketEntityHeadLook(Entity var1, byte var2) {
      this.a = var1.F();
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public Entity a(World var1) {
      return var1.a(this.a);
   }

   public byte a() {
      return this.b;
   }
}

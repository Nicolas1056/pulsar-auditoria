package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S43PacketCamera implements Packet<INetHandlerPlayClient> {
   public int a;

   public S43PacketCamera() {
   }

   public S43PacketCamera(Entity var1) {
      this.a = var1.F();
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public Entity a(World var1) {
      return var1.a(this.a);
   }
}

package net.minecraft.network.play.client;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

public class C18PacketSpectate implements Packet<INetHandlerPlayServer> {
   private UUID a;

   public C18PacketSpectate() {
   }

   public C18PacketSpectate(UUID var1) {
      this.a = var1;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.g();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
   }

   public void a(INetHandlerPlayServer var1) {
      var1.a(this);
   }

   public Entity a(WorldServer var1) {
      return var1.a(this.a);
   }
}

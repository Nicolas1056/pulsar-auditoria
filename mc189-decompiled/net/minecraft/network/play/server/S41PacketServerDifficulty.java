package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

public class S41PacketServerDifficulty implements Packet<INetHandlerPlayClient> {
   private EnumDifficulty a;
   private boolean b;

   public S41PacketServerDifficulty() {
   }

   public S41PacketServerDifficulty(EnumDifficulty var1, boolean var2) {
      this.a = var1;
      this.b = var2;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = EnumDifficulty.a(var1.readUnsignedByte());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.writeByte(this.a.a());
   }

   public boolean a() {
      return this.b;
   }

   public EnumDifficulty b() {
      return this.a;
   }
}

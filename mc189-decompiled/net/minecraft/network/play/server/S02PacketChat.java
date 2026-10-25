package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S02PacketChat implements Packet<INetHandlerPlayClient> {
   private IChatComponent a;
   private byte b;

   public S02PacketChat() {
   }

   public S02PacketChat(IChatComponent var1) {
      this(var1, (byte)1);
   }

   public S02PacketChat(IChatComponent var1, byte var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.d();
      this.b = var1.readByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.a(this.a);
      var1.writeByte(this.b);
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public IChatComponent a() {
      return this.a;
   }

   public boolean b() {
      return this.b == 1 || this.b == 2;
   }

   public byte c() {
      return this.b;
   }
}

package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake implements Packet<INetHandlerHandshakeServer> {
   private int a;
   private String b;
   private int c;
   private EnumConnectionState d;

   public C00Handshake() {
   }

   public C00Handshake(int var1, String var2, int var3, EnumConnectionState var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.c(255);
      this.c = var1.readUnsignedShort();
      this.d = EnumConnectionState.a(var1.e());
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.a(this.b);
      var1.writeShort(this.c);
      var1.b(this.d.a());
   }

   public void a(INetHandlerHandshakeServer var1) {
      var1.a(this);
   }

   public EnumConnectionState a() {
      return this.d;
   }

   public int b() {
      return this.a;
   }
}

package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1DPacketEntityEffect implements Packet<INetHandlerPlayClient> {
   private int a;
   private byte b;
   private byte c;
   private int d;
   private byte e;

   public S1DPacketEntityEffect() {
   }

   public S1DPacketEntityEffect(int var1, PotionEffect var2) {
      this.a = var1;
      this.b = (byte)(var2.a() & 0xFF);
      this.c = (byte)(var2.c() & 0xFF);
      if (var2.b() > 32767) {
         this.d = 32767;
      } else {
         this.d = var2.b();
      }

      this.e = (byte)(var2.f() ? 1 : 0);
   }

   @Override
   public void a(PacketBuffer var1) throws IOException {
      this.a = var1.e();
      this.b = var1.readByte();
      this.c = var1.readByte();
      this.d = var1.e();
      this.e = var1.readByte();
   }

   @Override
   public void b(PacketBuffer var1) throws IOException {
      var1.b(this.a);
      var1.writeByte(this.b);
      var1.writeByte(this.c);
      var1.b(this.d);
      var1.writeByte(this.e);
   }

   public boolean a() {
      return this.d == 32767;
   }

   public void a(INetHandlerPlayClient var1) {
      var1.a(this);
   }

   public int b() {
      return this.a;
   }

   public byte c() {
      return this.b;
   }

   public byte d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public boolean f() {
      return this.e != 0;
   }
}
